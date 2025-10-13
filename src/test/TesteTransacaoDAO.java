package test;

import dao.TransacaoDAO;
import model.Transacao;
import java.util.List;

/**
 * Classe de teste para TransacaoDAO
 * Testa as operações de inserção e consulta de transações
 */
public class TesteTransacaoDAO {
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("TESTE: TransacaoDAO - Sistema FINTECH");
        System.out.println("==========================================\n");
        
        TransacaoDAO transacaoDAO = new TransacaoDAO();
        
        // Teste 1: Inserir 5 transações
        System.out.println(">>> TESTE 1: Inserindo 5 transações no banco de dados");
        System.out.println("------------------------------------------");
        
        Transacao transacao1 = new Transacao(1, 1, 2, 500.00, "2024-01-16");
        Transacao transacao2 = new Transacao(2, 2, 1, 300.00, "2024-01-21");
        Transacao transacao3 = new Transacao(3, 1, 3, 1000.00, "2024-02-11");
        Transacao transacao4 = new Transacao(4, 3, 2, 250.00, "2024-02-16");
        Transacao transacao5 = new Transacao(5, 2, 3, 750.00, "2024-03-02");
        
        transacaoDAO.insert(transacao1);
        transacaoDAO.insert(transacao2);
        transacaoDAO.insert(transacao3);
        transacaoDAO.insert(transacao4);
        transacaoDAO.insert(transacao5);
        
        System.out.println("\n>>> TESTE 2: Consultando todas as transações cadastradas");
        System.out.println("------------------------------------------");
        
        List<Transacao> transacoes = transacaoDAO.getAll();
        
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada no banco de dados.");
        } else {
            System.out.println("\nLista de Transações:");
            System.out.println("==========================================");
            for (Transacao transacao : transacoes) {
                System.out.println(transacao);
            }
            System.out.println("==========================================");
            System.out.println("Total de transações: " + transacoes.size());
        }
        
        System.out.println("\n>>> TESTE 3: Buscando transação específica por ID");
        System.out.println("------------------------------------------");
        
        Transacao transacaoBuscada = transacaoDAO.findById(1);
        if (transacaoBuscada != null) {
            System.out.println("Transação encontrada: " + transacaoBuscada);
        } else {
            System.out.println("Transação não encontrada.");
        }
        
        System.out.println("\n==========================================");
        System.out.println("TESTES CONCLUÍDOS!");
        System.out.println("==========================================");
    }
}

