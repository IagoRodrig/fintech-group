package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar as conexões com o banco de dados Oracle
 * Padrão Singleton para garantir uma única instância
 */
public class ConnectionFactory {
    
    // Configurações de conexão Oracle FIAP
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USUARIO = "seu_usuario"; // SUBSTITUA pelo seu RM
    private static final String SENHA = "sua_senha";     // SUBSTITUA pela sua senha
    
    /**
     * Obtém uma conexão com o banco de dados Oracle
     * @return Connection objeto de conexão com o banco
     * @throws SQLException caso ocorra erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Registra o driver JDBC do Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver Oracle JDBC não encontrado. Adicione ojdbc.jar ao projeto.", e);
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
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}

