package dao;

import model.Usuario;
import java.sql.*;
import java.util.*;

/**
 * Data Access Object para a entidade Usuario
 */
public class UsuarioDAO {

    /**
     * Retorna todos os usuários cadastrados
     * @return Lista de objetos Usuario
     */
    public List<Usuario> getAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario ORDER BY nome_usuario";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("id_usuario"),
                    rs.getString("nome_usuario"),
                    rs.getString("nome_completo"),
                    rs.getString("telefone"),
                    rs.getString("senha"),
                    rs.getTimestamp("data_criacao")
                );
                usuarios.add(usuario);
            }

            System.out.println("✅ Consulta realizada com sucesso! Total de usuários: " + usuarios.size());
        } catch (SQLException e) {
            System.err.println("❌ Erro ao consultar usuários no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }

        return usuarios;
    }

    /**
     * Insere um novo usuário
     * @param usuario Objeto Usuario a ser inserido
     * @return true se inserido com sucesso, false caso contrário
     */
    public boolean insert(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nome_usuario, nome_completo, telefone, senha, data_criacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getNomeCompleto());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getSenha());
            stmt.setTimestamp(5, new Timestamp(usuario.getDataCriacao().getTime()));

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Usuário inserido com sucesso!");
                return true;
            } else {
                System.err.println("❌ Nenhuma linha foi inserida!");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir usuário no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca um usuário por ID
     * @param idUsuario ID do usuário a ser buscado
     * @return Objeto Usuario ou null se não encontrado
     */
    public Usuario findById(String idUsuario) {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";
        Usuario usuario = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getString("id_usuario"),
                        rs.getString("nome_usuario"),
                        rs.getString("nome_completo"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getTimestamp("data_criacao")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar usuário por ID:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * Busca um usuário por nome de usuário
     * @param nomeUsuario Nome de usuário a ser buscado
     * @return Objeto Usuario ou null se não encontrado
     */
    public Usuario findByNomeUsuario(String nomeUsuario) {
        String sql = "SELECT * FROM Usuario WHERE nome_usuario = ?";
        Usuario usuario = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getString("id_usuario"),
                        rs.getString("nome_usuario"),
                        rs.getString("nome_completo"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getTimestamp("data_criacao")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar usuário por nome:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }

        return usuario;
    }
}