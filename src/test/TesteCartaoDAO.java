package test;

import dao.CartaoDAO;
import model.Cartao;
import java.util.List;

/**
 * Classe de teste para CartaoDAO
 * Testa as operações de inserção e consulta de cartões
 */
public class TesteCartaoDAO {
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("TESTE: CartaoDAO - Sistema FINTECH");
        System.out.println("==========================================\n");
        
        CartaoDAO cartaoDAO = new CartaoDAO();
        
        // Teste 1: Inserir 5 cartões
        System.out.println(">>> TESTE 1: Inserindo 5 cartões no banco de dados");
        System.out.println("------------------------------------------");
        
        Cartao cartao1 = new Cartao(1, 1, "Débito", "**** **** **** 1234", "12/2028", 0.0);
        Cartao cartao2 = new Cartao(2, 1, "Crédito", "**** **** **** 5678", "06/2027", 5000.00);
        Cartao cartao3 = new Cartao(3, 2, "Crédito", "**** **** **** 9012", "03/2026", 3000.00);
        Cartao cartao4 = new Cartao(4, 3, "Débito", "**** **** **** 3456", "09/2029", 0.0);
        Cartao cartao5 = new Cartao(5, 3, "Crédito", "**** **** **** 7890", "11/2025", 2500.00);
        
        cartaoDAO.insert(cartao1);
        cartaoDAO.insert(cartao2);
        cartaoDAO.insert(cartao3);
        cartaoDAO.insert(cartao4);
        cartaoDAO.insert(cartao5);
        
        System.out.println("\n>>> TESTE 2: Consultando todos os cartões cadastrados");
        System.out.println("------------------------------------------");
        
        List<Cartao> cartoes = cartaoDAO.getAll();
        
        if (cartoes.isEmpty()) {
            System.out.println("Nenhum cartão encontrado no banco de dados.");
        } else {
            System.out.println("\nLista de Cartões:");
            System.out.println("==========================================");
            for (Cartao cartao : cartoes) {
                System.out.println(cartao);
            }
            System.out.println("==========================================");
            System.out.println("Total de cartões: " + cartoes.size());
        }
        
        System.out.println("\n>>> TESTE 3: Buscando cartão específico por ID");
        System.out.println("------------------------------------------");
        
        Cartao cartaoBuscado = cartaoDAO.findById(2);
        if (cartaoBuscado != null) {
            System.out.println("Cartão encontrado: " + cartaoBuscado);
        } else {
            System.out.println("Cartão não encontrado.");
        }
        
        System.out.println("\n==========================================");
        System.out.println("TESTES CONCLUÍDOS!");
        System.out.println("==========================================");
    }
}

