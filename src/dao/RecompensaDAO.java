package dao;

import model.Recompensa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade Recompensa
 * Responsável por operações de acesso ao banco de dados
 */
public class RecompensaDAO {

    /**
     * Busca recompensas por nome de usuário
     * @param nomeUsuario Nome do usuário
     * @return Lista de recompensas do usuário
     */
    public List<Recompensa> getRecompensasPorUsuario(String nomeUsuario) {
        List<Recompensa> recompensas = new ArrayList<>();
        String sql = "SELECT r.id_bonus, r.id_usuario, r.descricao, r.valor, r.status " +
                     "FROM Recompensa r " +
                     "INNER JOIN Usuario u ON r.id_usuario = u.id_usuario " +
                     "WHERE u.nome_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Recompensa recompensa = new Recompensa(
                        rs.getInt("id_bonus"),
                        rs.getString("id_usuario"),
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getString("status")
                    );
                    recompensas.add(recompensa);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar recompensas por usuário:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return recompensas;
    }

    /**
     * Retorna todas as recompensas cadastradas no banco de dados
     * @return Lista de objetos Recompensa
     */
    public List<Recompensa> getAll() {
        List<Recompensa> recompensas = new ArrayList<>();
        String sql = "SELECT id_bonus, id_usuario, descricao, valor, status FROM Recompensa";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Recompensa recompensa = new Recompensa(
                    rs.getInt("id_bonus"),
                    rs.getString("id_usuario"),
                    rs.getString("descricao"),
                    rs.getDouble("valor"),
                    rs.getString("status")
                );
                recompensas.add(recompensa);
            }

            System.out.println("Consulta realizada com sucesso! Total de recompensas: " + recompensas.size());

        } catch (SQLException e) {
            System.err.println("Erro ao consultar recompensas no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }

        return recompensas;
    }

    /**
     * Insere uma nova recompensa no banco de dados
     * @param recompensa Objeto Recompensa a ser inserido
     * @return true se inserido com sucesso, false caso contrário
     */
    public boolean insert(Recompensa recompensa) {
        // Primeiro, buscar o ID do usuário pelo nome de usuário
        String idUsuario = buscarIdUsuarioPorNome(recompensa.getIdUsuario());
        if (idUsuario == null) {
            System.err.println("❌ Usuário não encontrado: " + recompensa.getIdUsuario());
            return false;
        }

        // Gerar próximo ID da recompensa
        int proximoId = gerarProximoIdRecompensa();
        
        String sql = "INSERT INTO Recompensa (id_bonus, id_usuario, descricao, valor, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, proximoId);
            pstmt.setString(2, idUsuario);
            pstmt.setString(3, recompensa.getDescricao());
            pstmt.setDouble(4, recompensa.getValor());
            pstmt.setString(5, recompensa.getStatus());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Recompensa inserida com sucesso! ID: " + proximoId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir recompensa no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            
            // Tratamento específico para erros comuns
            if (e.getErrorCode() == 1) {
                System.err.println("ATENÇÃO: Chave primária duplicada. A recompensa já existe.");
            } else if (e.getErrorCode() == 2291) {
                System.err.println("ATENÇÃO: Chave estrangeira inválida. Verifique se o usuário existe.");
            } else if (e.getErrorCode() == 942) {
                System.err.println("ATENÇÃO: Tabela não encontrada. Verifique se a tabela Recompensa existe no banco.");
            }
            
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Busca uma recompensa por ID
     * @param idBonus ID da recompensa a ser buscada
     * @return Objeto Recompensa ou null se não encontrado
     */
    public Recompensa findById(int idBonus) {
        String sql = "SELECT id_bonus, id_usuario, descricao, valor, status " +
                     "FROM Recompensa WHERE id_bonus = ?";
        Recompensa recompensa = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idBonus);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                recompensa = new Recompensa(
                    rs.getInt("id_bonus"),
                    rs.getString("id_usuario"),
                    rs.getString("descricao"),
                    rs.getDouble("valor"),
                    rs.getString("status")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar recompensa por ID:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return recompensa;
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
     * Gera o próximo ID da recompensa
     */
    private int gerarProximoIdRecompensa() {
        String sql = "SELECT NVL(MAX(id_bonus), 0) + 1 AS proximo_id FROM Recompensa";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("proximo_id");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao gerar próximo ID da recompensa:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return 1; // Fallback
    }
}

