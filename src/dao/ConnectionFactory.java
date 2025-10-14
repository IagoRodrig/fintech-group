package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar as conexões com o banco de dados Oracle
 */
public class ConnectionFactory {
    
    // Configurações Oracle FIAP
    private static final String ORACLE_URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String ORACLE_USUARIO = "rm561399"; // Seu RM
    private static final String ORACLE_SENHA = "131106";     // Sua senha
    
    /**
     * Obtém uma conexão com o banco de dados Oracle
     * @return Connection objeto de conexão com o banco
     * @throws SQLException caso ocorra erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Registra o driver JDBC do Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Estabelece a conexão
            Connection conn = DriverManager.getConnection(ORACLE_URL, ORACLE_USUARIO, ORACLE_SENHA);
            
            System.out.println("✅ Conexão com Oracle estabelecida com sucesso!");
            return conn;
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("❌ Driver Oracle JDBC não encontrado. Adicione ojdbc8.jar ao projeto.", e);
        } catch (SQLException e) {
            throw new SQLException("❌ Erro ao conectar com Oracle: " + e.getMessage(), e);
        }
    }
    
    /**
     * Fecha a conexão com o banco de dados
     * @param connection conexão a ser fechada
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✅ Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.err.println("❌ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    /**
     * Retorna informações sobre o banco em uso
     */
    public static String getDatabaseInfo() {
        return "Oracle Database (FIAP)";
    }
    
    /**
     * Testa a conexão com o banco
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Teste de conexão bem-sucedido!");
            System.out.println("📊 Banco em uso: " + getDatabaseInfo());
            System.out.println("🔗 URL: " + ORACLE_URL);
            System.out.println("👤 Usuário: " + ORACLE_USUARIO);
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Falha no teste de conexão: " + e.getMessage());
            return false;
        }
    }
}