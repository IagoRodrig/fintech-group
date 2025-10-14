package test;

import dao.InvestimentoDAO;
import model.Investimento;
import java.util.List;

/**
 * Classe de teste para InvestimentoDAO
 * Testa as operações de inserção e consulta de investimentos
 */
public class TesteInvestimentoDAO {
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("TESTE: InvestimentoDAO - Sistema FINTECH");
        System.out.println("==========================================\n");
        
        InvestimentoDAO investimentoDAO = new InvestimentoDAO();
        
        // Teste 1: Inserir 5 investimentos
        System.out.println(">>> TESTE 1: Inserindo 5 investimentos no banco de dados");
        System.out.println("------------------------------------------");
        
        Investimento inv1 = new Investimento(1, "joao.silva", "CDB", 10000.00, "2024-01-10");
        Investimento inv2 = new Investimento(2, "joao.silva", "Tesouro Direto", 5000.00, "2024-01-15");
        Investimento inv3 = new Investimento(3, "maria.santos", "LCI", 15000.00, "2024-02-05");
        Investimento inv4 = new Investimento(4, "maria.santos", "Ações", 8000.00, "2024-02-20");
        Investimento inv5 = new Investimento(5, "pedro.oliveira", "Fundos Imobiliários", 12000.00, "2024-03-01");
        
        investimentoDAO.insert(inv1);
        investimentoDAO.insert(inv2);
        investimentoDAO.insert(inv3);
        investimentoDAO.insert(inv4);
        investimentoDAO.insert(inv5);
        
        System.out.println("\n>>> TESTE 2: Consultando todos os investimentos cadastrados");
        System.out.println("------------------------------------------");
        
        List<Investimento> investimentos = investimentoDAO.getAll();
        
        if (investimentos.isEmpty()) {
            System.out.println("Nenhum investimento encontrado no banco de dados.");
        } else {
            System.out.println("\nLista de Investimentos:");
            System.out.println("==========================================");
            for (Investimento investimento : investimentos) {
                System.out.println(investimento);
            }
            System.out.println("==========================================");
            System.out.println("Total de investimentos: " + investimentos.size());
        }
        
        System.out.println("\n>>> TESTE 3: Buscando investimento específico por ID");
        System.out.println("------------------------------------------");
        
        Investimento invBuscado = investimentoDAO.findById(3);
        if (invBuscado != null) {
            System.out.println("Investimento encontrado: " + invBuscado);
        } else {
            System.out.println("Investimento não encontrado.");
        }
        
        System.out.println("\n==========================================");
        System.out.println("TESTES CONCLUÍDOS!");
        System.out.println("==========================================");
    }
}

