package dao;

import model.Transacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade Transacao
 * Responsável por operações de acesso ao banco de dados
 */
public class TransacaoDAO {

    /**
     * Retorna todas as transações cadastradas no banco de dados
     * @return Lista de objetos Transacao
     */
    public List<Transacao> getAll() {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT id_transacao, id_conta_origem, id_conta_destino, valor, data_transacao, id_cartao, tipo_transacao, descricao FROM Transacao";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Verificar se id_conta_destino é null
                int idContaDestino = 0;
                if (rs.getObject("id_conta_destino") != null) {
                    idContaDestino = rs.getInt("id_conta_destino");
                }
                
                // Verificar se id_cartao é null
                Integer idCartao = null;
                if (rs.getObject("id_cartao") != null) {
                    idCartao = rs.getInt("id_cartao");
                }
                
                Transacao transacao = new Transacao(
                    rs.getString("id_transacao").hashCode(), // Converter RAW(16) para int
                    rs.getInt("id_conta_origem"),
                    idContaDestino,
                    rs.getDouble("valor"),
                    rs.getTimestamp("data_transacao").toString(),
                    idCartao,
                    rs.getString("tipo_transacao"),
                    rs.getString("descricao")
                );
                transacoes.add(transacao);
            }

            System.out.println("✅ Consulta realizada com sucesso! Total de transações: " + transacoes.size());

        } catch (SQLException e) {
            System.err.println("❌ Erro ao consultar transações no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }

        return transacoes;
    }

    /**
     * Insere uma nova transação no banco de dados
     * @param transacao Objeto Transacao a ser inserido
     * @return true se inserido com sucesso, false caso contrário
     */
    public boolean insert(Transacao transacao) {
        String sql = "INSERT INTO Transacao (id_conta_origem, id_conta_destino, valor, id_cartao, tipo_transacao, descricao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transacao.getIdContaOrigem());
            
            // id_conta_destino pode ser null para transações de cartão
            if (transacao.getIdContaDestino() != 0) {
                pstmt.setInt(2, transacao.getIdContaDestino());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }
            
            pstmt.setDouble(3, transacao.getValor());
            
            // id_cartao pode ser null para transferências normais
            if (transacao.getIdCartao() != null) {
                pstmt.setInt(4, transacao.getIdCartao());
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            }
            
            // tipo_transacao e descricao
            pstmt.setString(5, transacao.getTipoTransacao() != null ? transacao.getTipoTransacao() : "TRANSFERENCIA");
            pstmt.setString(6, transacao.getDescricao());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Transação inserida com sucesso!");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir transação no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            
            // Tratamento específico para erros comuns
            if (e.getErrorCode() == 1) {
                System.err.println("ATENÇÃO: Chave primária duplicada. A transação já existe.");
            } else if (e.getErrorCode() == 2291) {
                System.err.println("ATENÇÃO: Chave estrangeira inválida. Verifique se as contas existem.");
            } else if (e.getErrorCode() == 942) {
                System.err.println("ATENÇÃO: Tabela não encontrada. Verifique se a tabela Transacao existe no banco.");
            }
            
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Busca transações relacionadas a um usuário específico
     * @param nomeUsuario Nome do usuário
     * @return Lista de transações onde o usuário é origem ou destino
     */
    public List<Transacao> getByUsuario(String nomeUsuario) {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT t.id_transacao, t.id_conta_origem, t.id_conta_destino, t.valor, t.data_transacao " +
                     "FROM Transacao t " +
                     "INNER JOIN Conta co ON t.id_conta_origem = co.id_conta " +
                     "INNER JOIN Usuario uo ON co.id_usuario = uo.id_usuario " +
                     "LEFT JOIN Conta cd ON t.id_conta_destino = cd.id_conta " +
                     "LEFT JOIN Usuario ud ON cd.id_usuario = ud.id_usuario " +
                     "WHERE uo.nome_usuario = ? OR ud.nome_usuario = ? " +
                     "ORDER BY t.data_transacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeUsuario);
            pstmt.setString(2, nomeUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transacao transacao = new Transacao(
                    rs.getString("id_transacao").hashCode(), // Converter RAW(16) para int
                    rs.getInt("id_conta_origem"),
                    rs.getInt("id_conta_destino"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data_transacao").toString()
                );
                transacoes.add(transacao);
            }

            System.out.println("✅ Consulta de transações do usuário realizada com sucesso! Total: " + transacoes.size());

        } catch (SQLException e) {
            System.err.println("❌ Erro ao consultar transações do usuário no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }

        return transacoes;
    }

    /**
     * Busca uma transação por ID
     * @param idTransacao ID da transação a ser buscada (hash do RAW)
     * @return Objeto Transacao ou null se não encontrado
     */
    public Transacao findById(int idTransacao) {
        // Como o ID é RAW(16) no banco, vamos buscar todas as transações e comparar o hash
        List<Transacao> transacoes = getAll();
        
        for (Transacao transacao : transacoes) {
            if (transacao.getIdTransacao() == idTransacao) {
                return transacao;
            }
        }
        
        return null;
    }
}

