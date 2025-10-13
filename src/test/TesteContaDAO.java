package test;

import dao.ContaDAO;
import model.Conta;
import java.util.List;

/**
 * Classe de teste para ContaDAO
 * Testa as operações de inserção e consulta de contas
 */
public class TesteContaDAO {
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("TESTE: ContaDAO - Sistema FINTECH");
        System.out.println("==========================================\n");
        
        ContaDAO contaDAO = new ContaDAO();
        
        // Teste 1: Inserir 5 contas
        System.out.println(">>> TESTE 1: Inserindo 5 contas no banco de dados");
        System.out.println("------------------------------------------");
        
        Conta conta1 = new Conta(1, 1, 5000.00, "Corrente", 5000.00, "2024-01-15");
        Conta conta2 = new Conta(2, 1, 10000.00, "Poupança", 10000.00, "2024-01-20");
        Conta conta3 = new Conta(3, 2, 3500.50, "Corrente", 3500.50, "2024-02-10");
        Conta conta4 = new Conta(4, 2, 7500.00, "Investimento", 7500.00, "2024-02-15");
        Conta conta5 = new Conta(5, 3, 2000.00, "Corrente", 2000.00, "2024-03-01");
        
        contaDAO.insert(conta1);
        contaDAO.insert(conta2);
        contaDAO.insert(conta3);
        contaDAO.insert(conta4);
        contaDAO.insert(conta5);
        
        System.out.println("\n>>> TESTE 2: Consultando todas as contas cadastradas");
        System.out.println("------------------------------------------");
        
        List<Conta> contas = contaDAO.getAll();
        
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta encontrada no banco de dados.");
        } else {
            System.out.println("\nLista de Contas:");
            System.out.println("==========================================");
            for (Conta conta : contas) {
                System.out.println(conta);
            }
            System.out.println("==========================================");
            System.out.println("Total de contas: " + contas.size());
        }
        
        System.out.println("\n>>> TESTE 3: Buscando conta específica por ID");
        System.out.println("------------------------------------------");
        
        Conta contaBuscada = contaDAO.findById(1);
        if (contaBuscada != null) {
            System.out.println("Conta encontrada: " + contaBuscada);
        } else {
            System.out.println("Conta não encontrada.");
        }
        
        System.out.println("\n==========================================");
        System.out.println("TESTES CONCLUÍDOS!");
        System.out.println("==========================================");
    }
}

