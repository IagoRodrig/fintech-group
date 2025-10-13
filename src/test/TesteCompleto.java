package test;

/**
 * Classe para executar todos os testes de uma vez
 * Executa os testes de todos os DAOs do sistema FINTECH
 */
public class TesteCompleto {
    
    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("##################################################");
        System.out.println("#                                                #");
        System.out.println("#     SISTEMA FINTECH - SUITE DE TESTES DAO      #");
        System.out.println("#                                                #");
        System.out.println("##################################################");
        System.out.println("\n");
        
        // Aviso importante
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║            CONFIGURAÇÃO IMPORTANTE             ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║ Antes de executar, configure em:              ║");
        System.out.println("║ dao/ConnectionFactory.java                     ║");
        System.out.println("║                                                ║");
        System.out.println("║ - USUARIO: seu RM da FIAP                      ║");
        System.out.println("║ - SENHA: sua senha do Oracle                   ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("\n");
        
        try {
            // Teste 1: ContaDAO
            System.out.println("\n[1/5] Executando testes de ContaDAO...");
            TesteContaDAO.main(args);
            Thread.sleep(2000);
            
            // Teste 2: CartaoDAO
            System.out.println("\n[2/5] Executando testes de CartaoDAO...");
            TesteCartaoDAO.main(args);
            Thread.sleep(2000);
            
            // Teste 3: TransacaoDAO
            System.out.println("\n[3/5] Executando testes de TransacaoDAO...");
            TesteTransacaoDAO.main(args);
            Thread.sleep(2000);
            
            // Teste 4: InvestimentoDAO
            System.out.println("\n[4/5] Executando testes de InvestimentoDAO...");
            TesteInvestimentoDAO.main(args);
            Thread.sleep(2000);
            
            // Teste 5: RecompensaDAO
            System.out.println("\n[5/5] Executando testes de RecompensaDAO...");
            TesteRecompensaDAO.main(args);
            
            System.out.println("\n");
            System.out.println("##################################################");
            System.out.println("#                                                #");
            System.out.println("#     TODOS OS TESTES FORAM EXECUTADOS!          #");
            System.out.println("#                                                #");
            System.out.println("##################################################");
            System.out.println("\n");
            
        } catch (InterruptedException e) {
            System.err.println("Erro ao executar testes: " + e.getMessage());
        }
    }
}

