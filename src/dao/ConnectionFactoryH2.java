package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar as conexões com o banco de dados
 * Versão alternativa usando H2 Database para testes locais
 */
public class ConnectionFactoryH2 {
    
    // Configurações de conexão H2 (banco em memória)
    private static final String URL = "jdbc:h2:mem:fintech;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USUARIO = "rm561399";
    private static final String SENHA = "131106";
    
    /**
     * Obtém uma conexão com o banco de dados H2
     * @return Connection objeto de conexão com o banco
     * @throws SQLException caso ocorra erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Registra o driver JDBC do H2
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver H2 JDBC não encontrado. Adicione h2.jar ao projeto.", e);
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
    
    /**
     * Cria as tabelas necessárias no banco H2
     */
    public static void criarTabelas() {
        String sql = """
            -- Criar tabela Usuario
            CREATE TABLE IF NOT EXISTS Usuario (
                id_usuario VARCHAR(36) PRIMARY KEY,
                nome_usuario VARCHAR(25) UNIQUE NOT NULL,
                nome_completo VARCHAR(100) NOT NULL,
                telefone VARCHAR(20),
                senha VARCHAR(100) NOT NULL,
                data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            
            -- Criar tabela Conta
            CREATE TABLE IF NOT EXISTS Conta (
                id_conta INT PRIMARY KEY,
                id_usuario VARCHAR(36) NOT NULL,
                saldo DECIMAL(15,2) DEFAULT 0,
                tipo_conta VARCHAR(50) NOT NULL,
                valor DECIMAL(15,2) DEFAULT 0,
                data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            
            -- Criar tabela Cartao
            CREATE TABLE IF NOT EXISTS Cartao (
                id_cartao INT PRIMARY KEY,
                id_conta INT NOT NULL,
                tipo_cartao VARCHAR(50) NOT NULL,
                numero_mascarado VARCHAR(20) NOT NULL,
                validade VARCHAR(10) NOT NULL,
                limite_credito DECIMAL(15,2) DEFAULT 0
            );
            
            -- Criar tabela Transacao
            CREATE TABLE IF NOT EXISTS Transacao (
                id_transacao VARCHAR(36) PRIMARY KEY,
                id_conta_origem INT NOT NULL,
                id_conta_destino INT,
                valor DECIMAL(15,2) NOT NULL,
                data_transacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                status VARCHAR(50) DEFAULT 'Pendente'
            );
            
            -- Criar tabela Investimento
            CREATE TABLE IF NOT EXISTS Investimento (
                id_investimento INT PRIMARY KEY,
                id_usuario VARCHAR(36) NOT NULL,
                tipo VARCHAR(100) NOT NULL,
                valor_investido DECIMAL(15,2) NOT NULL,
                data_aplicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            
            -- Criar tabela Recompensa
            CREATE TABLE IF NOT EXISTS Recompensa (
                id_bonus INT PRIMARY KEY,
                id_usuario VARCHAR(36) NOT NULL,
                descricao VARCHAR(200) NOT NULL,
                valor DECIMAL(15,2) NOT NULL,
                status VARCHAR(50) NOT NULL
            );
            """;
        
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(sql);
            System.out.println("Tabelas criadas com sucesso no banco H2!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
