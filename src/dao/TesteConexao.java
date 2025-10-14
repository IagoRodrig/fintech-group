package dao;

/**
 * Classe para testar a conexão com o banco de dados
 */
public class TesteConexao {
    
    public static void main(String[] args) {
        System.out.println("🔍 Testando conexão com o banco de dados...");
        System.out.println("📊 Banco configurado: " + ConnectionFactory.getDatabaseInfo());
        System.out.println();
        
        boolean sucesso = ConnectionFactory.testConnection();
        
        if (sucesso) {
            System.out.println();
            System.out.println("🎉 CONEXÃO ESTABELECIDA COM SUCESSO!");
            System.out.println("✅ Você pode executar o programa principal agora.");
        } else {
            System.out.println();
            System.out.println("❌ FALHA NA CONEXÃO!");
            System.out.println("📋 Verifique:");
            System.out.println("   1. Se o driver Oracle JDBC está na pasta lib/");
            System.out.println("   2. Se suas credenciais FIAP estão corretas");
            System.out.println("   3. Se o servidor Oracle está acessível");
            System.out.println();
            System.out.println("📖 Consulte o arquivo ORACLE_SETUP.md para mais detalhes");
        }
    }
}
