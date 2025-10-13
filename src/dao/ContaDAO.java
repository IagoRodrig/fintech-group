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
        String sql = "SELECT id_conta, id_usuario, saldo, tipo_conta, valor, data FROM Conta";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Conta conta = new Conta(
                    rs.getInt("id_conta"),
                    rs.getInt("id_usuario"),
                    rs.getDouble("saldo"),
                    rs.getString("tipo_conta"),
                    rs.getDouble("valor"),
                    rs.getString("data")
                );
                contas.add(conta);
            }

            System.out.println("Consulta realizada com sucesso! Total de contas: " + contas.size());

        } catch (SQLException e) {
            System.err.println("Erro ao consultar contas no banco de dados:");
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
        String sql = "INSERT INTO Conta (id_conta, id_usuario, saldo, tipo_conta, valor, data) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, conta.getIdConta());
            pstmt.setInt(2, conta.getIdUsuario());
            pstmt.setDouble(3, conta.getSaldo());
            pstmt.setString(4, conta.getTipoConta());
            pstmt.setDouble(5, conta.getValor());
            pstmt.setString(6, conta.getData());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Conta inserida com sucesso! ID: " + conta.getIdConta());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir conta no banco de dados:");
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
        String sql = "SELECT id_conta, id_usuario, saldo, tipo_conta, valor, data FROM Conta WHERE id_conta = ?";
        Conta conta = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idConta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                conta = new Conta(
                    rs.getInt("id_conta"),
                    rs.getInt("id_usuario"),
                    rs.getDouble("saldo"),
                    rs.getString("tipo_conta"),
                    rs.getDouble("valor"),
                    rs.getString("data")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar conta por ID:");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }

        return conta;
    }
}

