package dao;

import model.Cartao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade Cartao
 * Responsável por operações de acesso ao banco de dados
 */
public class CartaoDAO {

    /**
     * Busca cartões por uma lista de IDs de contas
     * @param idsContas Lista de IDs de contas
     * @return Lista de cartões das contas especificadas
     */
    public List<Cartao> getCartoesPorContas(List<Integer> idsContas) {
        List<Cartao> cartoes = new ArrayList<>();
        
        if (idsContas.isEmpty()) {
            return cartoes;
        }
        
        // Construir a query com IN clause
        StringBuilder sql = new StringBuilder("SELECT id_cartao, id_conta, tipo_cartao, numero_mascarado, validade, limite_credito FROM Cartao WHERE id_conta IN (");
        for (int i = 0; i < idsContas.size(); i++) {
            sql.append("?");
            if (i < idsContas.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            // Definir os parâmetros
            for (int i = 0; i < idsContas.size(); i++) {
                pstmt.setInt(i + 1, idsContas.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cartao cartao = new Cartao(
                        rs.getInt("id_cartao"),
                        rs.getInt("id_conta"),
                        rs.getString("tipo_cartao"),
                        rs.getString("numero_mascarado"),
                        rs.getString("validade"),
                        rs.getDouble("limite_credito")
                    );
                    cartoes.add(cartao);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar cartões por contas:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return cartoes;
    }

    /**
     * Retorna todos os cartões cadastrados no banco de dados
     * @return Lista de objetos Cartao
     */
    public List<Cartao> getAll() {
        List<Cartao> cartoes = new ArrayList<>();
        String sql = "SELECT id_cartao, id_conta, tipo_cartao, numero_mascarado, validade, limite_credito FROM Cartao";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cartao cartao = new Cartao(
                    rs.getInt("id_cartao"),
                    rs.getInt("id_conta"),
                    rs.getString("tipo_cartao"),
                    rs.getString("numero_mascarado"),
                    rs.getString("validade"),
                    rs.getDouble("limite_credito")
                );
                cartoes.add(cartao);
            }

            System.out.println("Consulta realizada com sucesso! Total de cartões: " + cartoes.size());

        } catch (SQLException e) {
            System.err.println("Erro ao consultar cartões no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }

        return cartoes;
    }

    /**
     * Insere um novo cartão no banco de dados
     * @param cartao Objeto Cartao a ser inserido
     * @return true se inserido com sucesso, false caso contrário
     */
    public boolean insert(Cartao cartao) {
        // Gerar próximo ID do cartão
        int proximoId = gerarProximoIdCartao();
        
        String sql = "INSERT INTO Cartao (id_cartao, id_conta, tipo_cartao, numero_mascarado, validade, limite_credito) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, proximoId);
            pstmt.setInt(2, cartao.getIdConta());
            pstmt.setString(3, cartao.getTipoCartao());
            pstmt.setString(4, cartao.getNumeroMascarado());
            pstmt.setString(5, cartao.getValidade());
            pstmt.setDouble(6, cartao.getLimiteCredito());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Cartão inserido com sucesso! ID: " + proximoId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir cartão no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            
            // Tratamento específico para erros comuns
            if (e.getErrorCode() == 1) {
                System.err.println("ATENÇÃO: Chave primária duplicada. O cartão já existe.");
            } else if (e.getErrorCode() == 2291) {
                System.err.println("ATENÇÃO: Chave estrangeira inválida. Verifique se a conta existe.");
            } else if (e.getErrorCode() == 942) {
                System.err.println("ATENÇÃO: Tabela não encontrada. Verifique se a tabela Cartao existe no banco.");
            }
            
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gera o próximo ID do cartão
     */
    private int gerarProximoIdCartao() {
        String sql = "SELECT NVL(MAX(id_cartao), 0) + 1 AS proximo_id FROM Cartao";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("proximo_id");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao gerar próximo ID do cartão:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return 1; // Fallback
    }

    /**
     * Busca um cartão por ID
     * @param idCartao ID do cartão a ser buscado
     * @return Objeto Cartao ou null se não encontrado
     */
    public Cartao findById(int idCartao) {
        String sql = "SELECT id_cartao, id_conta, tipo_cartao, numero_mascarado, validade, limite_credito " +
                     "FROM Cartao WHERE id_cartao = ?";
        Cartao cartao = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCartao);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cartao = new Cartao(
                    rs.getInt("id_cartao"),
                    rs.getInt("id_conta"),
                    rs.getString("tipo_cartao"),
                    rs.getString("numero_mascarado"),
                    rs.getString("validade"),
                    rs.getDouble("limite_credito")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cartão por ID:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return cartao;
    }
}

