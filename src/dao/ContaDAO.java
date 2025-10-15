package dao;

import model.Conta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade Conta
 * Responsável por operações de acesso ao banco de dados
 */
public class ContaDAO {

    /**
     * Retorna todas as contas cadastradas no banco de dados
     * @return Lista de objetos Conta
     */
    public List<Conta> getAll() {
        List<Conta> contas = new ArrayList<>();
        String sql = "SELECT id_conta, id_usuario, saldo, tipo_conta, valor, data_criacao FROM Conta";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Conta conta = new Conta(
                    rs.getInt("id_conta"),
                    rs.getString("id_usuario"),
                    rs.getDouble("saldo"),
                    rs.getString("tipo_conta"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data_criacao").toString()
                );
                contas.add(conta);
            }

            System.out.println("✅ Consulta realizada com sucesso! Total de contas: " + contas.size());

        } catch (SQLException e) {
            System.err.println("❌ Erro ao consultar contas no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }

        return contas;
    }

    /**
     * Insere uma nova conta no banco de dados
     * @param conta Objeto Conta a ser inserido
     * @return true se inserido com sucesso, false caso contrário
     */
    public boolean insert(Conta conta) {
        // Primeiro, buscar o ID do usuário pelo nome de usuário
        String idUsuario = buscarIdUsuarioPorNome(conta.getIdUsuario());
        if (idUsuario == null) {
            System.err.println("❌ Usuário não encontrado: " + conta.getIdUsuario());
            return false;
        }

        // Gerar próximo ID da conta
        int proximoId = gerarProximoIdConta();
        
        String sql = "INSERT INTO Conta (id_conta, id_usuario, saldo, tipo_conta, valor) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, proximoId);
            pstmt.setString(2, idUsuario);
            pstmt.setDouble(3, conta.getSaldo());
            pstmt.setString(4, conta.getTipoConta());
            pstmt.setDouble(5, conta.getValor());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Conta inserida com sucesso! ID: " + proximoId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir conta no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            
            // Tratamento específico para erros comuns
            if (e.getErrorCode() == 1) {
                System.err.println("ATENÇÃO: Chave primária duplicada. A conta já existe.");
            } else if (e.getErrorCode() == 2291) {
                System.err.println("ATENÇÃO: Chave estrangeira inválida. Verifique se o usuário existe.");
            } else if (e.getErrorCode() == 942) {
                System.err.println("ATENÇÃO: Tabela não encontrada. Verifique se a tabela Conta existe no banco.");
            }
            
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Busca uma conta por ID
     * @param idConta ID da conta a ser buscada
     * @return Objeto Conta ou null se não encontrado
     */
    public Conta findById(int idConta) {
        String sql = "SELECT id_conta, id_usuario, saldo, tipo_conta, valor, data_criacao FROM Conta WHERE id_conta = ?";
        Conta conta = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idConta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                conta = new Conta(
                    rs.getInt("id_conta"),
                    rs.getString("id_usuario"),
                    rs.getDouble("saldo"),
                    rs.getString("tipo_conta"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data_criacao").toString()
                );
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar conta por ID:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return conta;
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
     * Gera o próximo ID da conta
     */
    private int gerarProximoIdConta() {
        String sql = "SELECT NVL(MAX(id_conta), 0) + 1 AS proximo_id FROM Conta";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("proximo_id");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao gerar próximo ID da conta:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return 1; // Fallback
    }

    /**
     * Busca o ID da conta pelo nome de usuário
     * @param nomeUsuario Nome do usuário
     * @return ID da primeira conta encontrada ou -1 se não encontrado
     */
    public int buscarIdContaPorUsuario(String nomeUsuario) {
        String sql = "SELECT c.id_conta FROM Conta c " +
                     "INNER JOIN Usuario u ON c.id_usuario = u.id_usuario " +
                     "WHERE u.nome_usuario = ? AND ROWNUM = 1";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_conta");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar ID da conta por usuário:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return -1; // Usuário não encontrado
    }

    /**
     * Busca todas as contas de um usuário
     * @param nomeUsuario Nome do usuário
     * @return Lista de IDs das contas do usuário
     */
    public List<Integer> buscarIdsContasPorUsuario(String nomeUsuario) {
        List<Integer> idsContas = new ArrayList<>();
        String sql = "SELECT c.id_conta FROM Conta c " +
                     "INNER JOIN Usuario u ON c.id_usuario = u.id_usuario " +
                     "WHERE u.nome_usuario = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                idsContas.add(rs.getInt("id_conta"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar IDs das contas por usuário:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return idsContas;
    }

    /**
     * Atualiza o saldo de uma conta no banco de dados
     * @param idConta ID da conta a ser atualizada
     * @param novoSaldo Novo valor do saldo
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean updateSaldo(int idConta, double novoSaldo) {
        String sql = "UPDATE Conta SET saldo = ? WHERE id_conta = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, novoSaldo);
            pstmt.setInt(2, idConta);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Saldo da conta " + idConta + " atualizado para R$ " + novoSaldo);
                return true;
            } else {
                System.err.println("❌ Nenhuma conta foi atualizada. Verifique se o ID da conta existe.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar saldo da conta no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            
            // Tratamento específico para erros comuns
            if (e.getErrorCode() == 942) {
                System.err.println("ATENÇÃO: Tabela não encontrada. Verifique se a tabela Conta existe no banco.");
            }
            
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Atualiza uma conta completa no banco de dados
     * @param conta Objeto Conta com os dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean update(Conta conta) {
        String sql = "UPDATE Conta SET saldo = ?, tipo_conta = ?, valor = ? WHERE id_conta = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, conta.getSaldo());
            pstmt.setString(2, conta.getTipoConta());
            pstmt.setDouble(3, conta.getValor());
            pstmt.setInt(4, conta.getIdConta());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Conta " + conta.getIdConta() + " atualizada com sucesso!");
                return true;
            } else {
                System.err.println("❌ Nenhuma conta foi atualizada. Verifique se o ID da conta existe.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar conta no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            
            // Tratamento específico para erros comuns
            if (e.getErrorCode() == 942) {
                System.err.println("ATENÇÃO: Tabela não encontrada. Verifique se a tabela Conta existe no banco.");
            }
            
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Verifica se um usuário já possui uma conta do tipo especificado
     * @param nomeUsuario Nome do usuário
     * @param tipoConta Tipo da conta (Corrente, Poupança, Investimento)
     * @return true se já existe conta deste tipo, false caso contrário
     */
    public boolean existeContaPorTipo(String nomeUsuario, String tipoConta) {
        String sql = "SELECT COUNT(*) as total FROM Conta c " +
                     "INNER JOIN Usuario u ON c.id_usuario = u.id_usuario " +
                     "WHERE u.nome_usuario = ? AND UPPER(c.tipo_conta) = UPPER(?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeUsuario);
            pstmt.setString(2, tipoConta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao verificar conta por tipo:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retorna os tipos de conta que um usuário já possui
     * @param nomeUsuario Nome do usuário
     * @return Lista de tipos de conta já existentes
     */
    public List<String> getTiposContaExistentes(String nomeUsuario) {
        List<String> tiposExistentes = new ArrayList<>();
        String sql = "SELECT DISTINCT c.tipo_conta FROM Conta c " +
                     "INNER JOIN Usuario u ON c.id_usuario = u.id_usuario " +
                     "WHERE u.nome_usuario = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tiposExistentes.add(rs.getString("tipo_conta"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar tipos de conta existentes:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return tiposExistentes;
    }
}

