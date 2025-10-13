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
        String sql = "SELECT id_transacao, id_conta_origem, id_conta_destino, valor, data FROM Transacao";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transacao transacao = new Transacao(
                    rs.getInt("id_transacao"),
                    rs.getInt("id_conta_origem"),
                    rs.getInt("id_conta_destino"),
                    rs.getDouble("valor"),
                    rs.getString("data")
                );
                transacoes.add(transacao);
            }

            System.out.println("Consulta realizada com sucesso! Total de transações: " + transacoes.size());

        } catch (SQLException e) {
            System.err.println("Erro ao consultar transações no banco de dados:");
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
        String sql = "INSERT INTO Transacao (id_transacao, id_conta_origem, id_conta_destino, valor, data) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transacao.getIdTransacao());
            pstmt.setInt(2, transacao.getIdContaOrigem());
            pstmt.setInt(3, transacao.getIdContaDestino());
            pstmt.setDouble(4, transacao.getValor());
            pstmt.setString(5, transacao.getData());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Transação inserida com sucesso! ID: " + transacao.getIdTransacao());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir transação no banco de dados:");
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
     * Busca uma transação por ID
     * @param idTransacao ID da transação a ser buscada
     * @return Objeto Transacao ou null se não encontrado
     */
    public Transacao findById(int idTransacao) {
        String sql = "SELECT id_transacao, id_conta_origem, id_conta_destino, valor, data " +
                     "FROM Transacao WHERE id_transacao = ?";
        Transacao transacao = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTransacao);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                transacao = new Transacao(
                    rs.getInt("id_transacao"),
                    rs.getInt("id_conta_origem"),
                    rs.getInt("id_conta_destino"),
                    rs.getDouble("valor"),
                    rs.getString("data")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar transação por ID:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return transacao;
    }
}

