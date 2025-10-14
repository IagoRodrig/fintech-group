package dao;

import model.Investimento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade Investimento
 * Responsável por operações de acesso ao banco de dados
 */
public class InvestimentoDAO {

    /**
     * Retorna todos os investimentos cadastrados no banco de dados
     * @return Lista de objetos Investimento
     */
    public List<Investimento> getAll() {
        List<Investimento> investimentos = new ArrayList<>();
        String sql = "SELECT id_investimento, id_usuario, tipo, valor_investido, data_aplicacao FROM Investimento";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Investimento investimento = new Investimento(
                    rs.getInt("id_investimento"),
                    rs.getString("id_usuario"),
                    rs.getString("tipo"),
                    rs.getDouble("valor_investido"),
                    rs.getString("data_aplicacao")
                );
                investimentos.add(investimento);
            }

            System.out.println("Consulta realizada com sucesso! Total de investimentos: " + investimentos.size());

        } catch (SQLException e) {
            System.err.println("Erro ao consultar investimentos no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }

        return investimentos;
    }

    /**
     * Insere um novo investimento no banco de dados
     * @param investimento Objeto Investimento a ser inserido
     * @return true se inserido com sucesso, false caso contrário
     */
    public boolean insert(Investimento investimento) {
        // Primeiro, buscar o ID do usuário pelo nome de usuário
        String idUsuario = buscarIdUsuarioPorNome(investimento.getIdUsuario());
        if (idUsuario == null) {
            System.err.println("❌ Usuário não encontrado: " + investimento.getIdUsuario());
            return false;
        }

        // Gerar próximo ID do investimento
        int proximoId = gerarProximoIdInvestimento();
        
        String sql = "INSERT INTO Investimento (id_investimento, id_usuario, tipo, valor_investido) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, proximoId);
            pstmt.setString(2, idUsuario);
            pstmt.setString(3, investimento.getTipo());
            pstmt.setDouble(4, investimento.getValorInvestido());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Investimento inserido com sucesso! ID: " + proximoId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir investimento no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            
            // Tratamento específico para erros comuns
            if (e.getErrorCode() == 1) {
                System.err.println("ATENÇÃO: Chave primária duplicada. O investimento já existe.");
            } else if (e.getErrorCode() == 2291) {
                System.err.println("ATENÇÃO: Chave estrangeira inválida. Verifique se o usuário existe.");
            } else if (e.getErrorCode() == 942) {
                System.err.println("ATENÇÃO: Tabela não encontrada. Verifique se a tabela Investimento existe no banco.");
            }
            
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Busca um investimento por ID
     * @param idInvestimento ID do investimento a ser buscado
     * @return Objeto Investimento ou null se não encontrado
     */
    public Investimento findById(int idInvestimento) {
        String sql = "SELECT id_investimento, id_usuario, tipo, valor_investido, data_aplicacao " +
                     "FROM Investimento WHERE id_investimento = ?";
        Investimento investimento = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idInvestimento);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                investimento = new Investimento(
                    rs.getInt("id_investimento"),
                    rs.getString("id_usuario"),
                    rs.getString("tipo"),
                    rs.getDouble("valor_investido"),
                    rs.getString("data_aplicacao")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar investimento por ID:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return investimento;
    }

    /**
     * Busca o ID do usuário pelo nome de usuário
     */
    private String buscarIdUsuarioPorNome(String nomeUsuario) {
        String sql = "SELECT id_usuario FROM Usuario WHERE nome_usuario = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("id_usuario");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar ID do usuário:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gera o próximo ID do investimento
     */
    private int gerarProximoIdInvestimento() {
        String sql = "SELECT NVL(MAX(id_investimento), 0) + 1 AS proximo_id FROM Investimento";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("proximo_id");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao gerar próximo ID do investimento:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return 1; // Fallback
    }
}

