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
                    double limiteCredito = rs.getDouble("limite_credito");
                    
                    // Debug: mostrar valores recuperados
                    System.out.println("🔍 DEBUG - Valores recuperados do banco:");
                    System.out.println("  ID Cartão: " + rs.getInt("id_cartao"));
                    System.out.println("  ID Conta: " + rs.getInt("id_conta"));
                    System.out.println("  Tipo: " + rs.getString("tipo_cartao"));
                    System.out.println("  Número: " + rs.getString("numero_mascarado"));
                    System.out.println("  Validade: " + rs.getString("validade"));
                    System.out.println("  Limite: " + limiteCredito);
                    
                    Cartao cartao = new Cartao(
                        rs.getInt("id_cartao"),
                        rs.getInt("id_conta"),
                        rs.getString("tipo_cartao"),
                        rs.getString("numero_mascarado"),
                        rs.getString("validade"),
                        limiteCredito
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
                double limiteCredito = rs.getDouble("limite_credito");
                
                // Debug: mostrar valores recuperados
                System.out.println("🔍 DEBUG - Valores recuperados do banco (getAll):");
                System.out.println("  ID Cartão: " + rs.getInt("id_cartao"));
                System.out.println("  Limite: " + limiteCredito);
                
                Cartao cartao = new Cartao(
                    rs.getInt("id_cartao"),
                    rs.getInt("id_conta"),
                    rs.getString("tipo_cartao"),
                    rs.getString("numero_mascarado"),
                    rs.getString("validade"),
                    limiteCredito
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

            // Debug: mostrar valores antes da inserção
            System.out.println("🔍 DEBUG - Valores sendo inseridos:");
            System.out.println("  ID Cartão: " + proximoId);
            System.out.println("  ID Conta: " + cartao.getIdConta());
            System.out.println("  Tipo: " + cartao.getTipoCartao());
            System.out.println("  Número: " + cartao.getNumeroMascarado());
            System.out.println("  Validade: " + cartao.getValidade());
            System.out.println("  Limite: " + cartao.getLimiteCredito());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Cartão inserido com sucesso! ID: " + proximoId);
                System.out.println("💰 Limite salvo: R$ " + cartao.getLimiteCredito());
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

    /**
     * Método de teste para verificar se o limite está sendo salvo corretamente
     * @param idCartao ID do cartão para testar
     */
    public void testarLimite(int idCartao) {
        System.out.println("\n🧪 === TESTE DE LIMITE ===");
        
        // Buscar cartão específico
        Cartao cartao = findById(idCartao);
        
        if (cartao != null) {
            System.out.println("✅ Cartão encontrado!");
            System.out.println("🔍 ID: " + cartao.getIdCartao());
            System.out.println("💰 Limite armazenado: R$ " + cartao.getLimiteCredito());
            System.out.println("💳 Tipo: " + cartao.getTipoCartao());
            
            // Teste direto no banco
            String sql = "SELECT limite_credito FROM Cartao WHERE id_cartao = ?";
            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setInt(1, idCartao);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    double limiteBanco = rs.getDouble("limite_credito");
                    System.out.println("🏦 Limite no banco: R$ " + limiteBanco);
                    
                    if (limiteBanco == cartao.getLimiteCredito()) {
                        System.out.println("✅ LIMITE CORRETO - Valores coincidem!");
                    } else {
                        System.out.println("❌ PROBLEMA ENCONTRADO - Valores diferentes!");
                        System.out.println("   Objeto: R$ " + cartao.getLimiteCredito());
                        System.out.println("   Banco: R$ " + limiteBanco);
                    }
                } else {
                    System.out.println("❌ Cartão não encontrado no banco!");
                }
                
            } catch (SQLException e) {
                System.err.println("❌ Erro no teste: " + e.getMessage());
            }
        } else {
            System.out.println("❌ Cartão não encontrado!");
        }
        
        System.out.println("🧪 === FIM DO TESTE ===\n");
    }

    /**
     * Método de teste para verificar se a conexão e inserção estão funcionando
     */
    public void testarConexaoEInsercao() {
        System.out.println("\n🧪 === TESTE DE CONEXÃO E INSERÇÃO ===");
        
        try {
            // Testar conexão
            Connection conn = ConnectionFactory.getConnection();
            System.out.println("✅ Conexão estabelecida com sucesso!");
            
            // Testar geração de ID
            int proximoId = gerarProximoIdCartao();
            System.out.println("✅ Próximo ID gerado: " + proximoId);
            
            // Testar inserção simples
            String sql = "INSERT INTO Cartao (id_cartao, id_conta, tipo_cartao, numero_mascarado, validade, limite_credito) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, proximoId);
                pstmt.setInt(2, 1); // ID de conta teste
                pstmt.setString(3, "Teste");
                pstmt.setString(4, "**** **** **** 1234");
                pstmt.setString(5, "12/2025");
                pstmt.setDouble(6, 1000.0);
                
                System.out.println("🔍 Tentando inserir cartão de teste...");
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("✅ Inserção de teste bem-sucedida!");
                    System.out.println("💰 Limite inserido: R$ 1000.0");
                    
                    // Verificar se foi inserido corretamente
                    String sqlSelect = "SELECT limite_credito FROM Cartao WHERE id_cartao = ?";
                    try (PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
                        pstmtSelect.setInt(1, proximoId);
                        ResultSet rs = pstmtSelect.executeQuery();
                        
                        if (rs.next()) {
                            double limiteRecuperado = rs.getDouble("limite_credito");
                            System.out.println("🏦 Limite recuperado do banco: R$ " + limiteRecuperado);
                            
                            if (limiteRecuperado == 1000.0) {
                                System.out.println("✅ LIMITE CORRETO - Inserção e consulta funcionando!");
                            } else {
                                System.out.println("❌ PROBLEMA - Limite não foi salvo corretamente!");
                            }
                        }
                    }
                } else {
                    System.out.println("❌ Falha na inserção de teste!");
                }
            }
            
            conn.close();
            
        } catch (SQLException e) {
            System.err.println("❌ Erro no teste: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            e.printStackTrace();
        }
        
        System.out.println("🧪 === FIM DO TESTE DE CONEXÃO ===\n");
    }

    /**
     * Método específico para testar apenas o limite
     */
    public void testarLimiteEspecifico() {
        System.out.println("\n🧪 === TESTE ESPECÍFICO DO LIMITE ===");
        
        try {
            Connection conn = ConnectionFactory.getConnection();
            System.out.println("✅ Conexão estabelecida!");
            
            // Teste 1: Verificar se a tabela tem a coluna limite_credito
            System.out.println("\n1. Verificando estrutura da tabela...");
            String sqlCheck = "SELECT limite_credito FROM Cartao WHERE ROWNUM = 1";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCheck)) {
                ResultSet rs = pstmt.executeQuery();
                System.out.println("✅ Coluna limite_credito existe na tabela!");
            } catch (SQLException e) {
                System.err.println("❌ Problema com coluna limite_credito: " + e.getMessage());
                return;
            }
            
            // Teste 2: Inserir cartão com limite específico
            System.out.println("\n2. Inserindo cartão de teste com limite...");
            int proximoId = gerarProximoIdCartao();
            String sqlInsert = "INSERT INTO Cartao (id_cartao, id_conta, tipo_cartao, numero_mascarado, validade, limite_credito) " +
                               "VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                pstmt.setInt(1, proximoId);
                pstmt.setInt(2, 1); // ID conta teste
                pstmt.setString(3, "Teste Limite");
                pstmt.setString(4, "**** **** **** 9999");
                pstmt.setString(5, "12/2030");
                pstmt.setDouble(6, 5000.0); // Limite específico
                
                System.out.println("🔍 Inserindo limite: R$ 5000.0");
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("✅ Cartão inserido! ID: " + proximoId);
                    
                    // Teste 3: Verificar se o limite foi salvo
                    System.out.println("\n3. Verificando se limite foi salvo...");
                    String sqlSelect = "SELECT limite_credito FROM Cartao WHERE id_cartao = ?";
                    try (PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
                        pstmtSelect.setInt(1, proximoId);
                        ResultSet rs = pstmtSelect.executeQuery();
                        
                        if (rs.next()) {
                            double limiteRecuperado = rs.getDouble("limite_credito");
                            System.out.println("🏦 Limite recuperado: R$ " + limiteRecuperado);
                            
                            if (limiteRecuperado == 5000.0) {
                                System.out.println("✅ SUCESSO! Limite foi salvo corretamente!");
                            } else {
                                System.out.println("❌ PROBLEMA! Limite não foi salvo corretamente!");
                                System.out.println("   Esperado: R$ 5000.0");
                                System.out.println("   Encontrado: R$ " + limiteRecuperado);
                            }
                        } else {
                            System.out.println("❌ Cartão não encontrado após inserção!");
                        }
                    }
                } else {
                    System.out.println("❌ Falha na inserção!");
                }
            }
            
            conn.close();
            
        } catch (SQLException e) {
            System.err.println("❌ Erro no teste: " + e.getMessage());
            System.err.println("Código: " + e.getErrorCode());
            e.printStackTrace();
        }
        
        System.out.println("🧪 === FIM DO TESTE ESPECÍFICO ===\n");
    }

    /**
     * Atualiza o limite de crédito de um cartão no banco de dados
     * @param idCartao ID do cartão a ser atualizado
     * @param novoLimite Novo valor do limite de crédito
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean updateLimiteCredito(int idCartao, double novoLimite) {
        String sql = "UPDATE Cartao SET limite_credito = ? WHERE id_cartao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, novoLimite);
            pstmt.setInt(2, idCartao);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Limite do cartão " + idCartao + " atualizado para R$ " + novoLimite);
                return true;
            } else {
                System.err.println("❌ Nenhum cartão foi atualizado. Verifique se o ID do cartão existe.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar limite do cartão no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            
            // Tratamento específico para erros comuns
            if (e.getErrorCode() == 942) {
                System.err.println("ATENÇÃO: Tabela não encontrada. Verifique se a tabela Cartao existe no banco.");
            }
            
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Atualiza um cartão completo no banco de dados
     * @param cartao Objeto Cartao com os dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean update(Cartao cartao) {
        String sql = "UPDATE Cartao SET tipo_cartao = ?, numero_mascarado = ?, validade = ?, limite_credito = ? WHERE id_cartao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cartao.getTipoCartao());
            pstmt.setString(2, cartao.getNumeroMascarado());
            pstmt.setString(3, cartao.getValidade());
            pstmt.setDouble(4, cartao.getLimiteCredito());
            pstmt.setInt(5, cartao.getIdCartao());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Cartão " + cartao.getIdCartao() + " atualizado com sucesso!");
                return true;
            } else {
                System.err.println("❌ Nenhum cartão foi atualizado. Verifique se o ID do cartão existe.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar cartão no banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código do erro: " + e.getErrorCode());
            
            // Tratamento específico para erros comuns
            if (e.getErrorCode() == 942) {
                System.err.println("ATENÇÃO: Tabela não encontrada. Verifique se a tabela Cartao existe no banco.");
            }
            
            e.printStackTrace();
        }

        return false;
    }
}

