package test;

import dao.RecompensaDAO;
import model.Recompensa;
import java.util.List;

/**
 * Classe de teste para RecompensaDAO
 * Testa as operações de inserção e consulta de recompensas
 */
public class TesteRecompensaDAO {
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("TESTE: RecompensaDAO - Sistema FINTECH");
        System.out.println("==========================================\n");
        
        RecompensaDAO recompensaDAO = new RecompensaDAO();
        
        // Teste 1: Inserir 5 recompensas
        System.out.println(">>> TESTE 1: Inserindo 5 recompensas no banco de dados");
        System.out.println("------------------------------------------");
        
        Recompensa rec1 = new Recompensa(1, "iago.rocha", "Cashback compras", 50.00, "Ativo");
        Recompensa rec2 = new Recompensa(2, "maria.santos", "Bônus abertura conta", 100.00, "Resgatado");
        Recompensa rec3 = new Recompensa(3, "pedro.oliveira", "Indicação amigo", 75.00, "Ativo");
        Recompensa rec4 = new Recompensa(4, "maria.santos", "Programa fidelidade", 150.00, "Pendente");
        Recompensa rec5 = new Recompensa(5, "pedro.oliveira", "Cashback investimentos", 200.00, "Ativo");
        
        recompensaDAO.insert(rec1);
        recompensaDAO.insert(rec2);
        recompensaDAO.insert(rec3);
        recompensaDAO.insert(rec4);
        recompensaDAO.insert(rec5);
        
        System.out.println("\n>>> TESTE 2: Consultando todas as recompensas cadastradas");
        System.out.println("------------------------------------------");
        
        List<Recompensa> recompensas = recompensaDAO.getAll();
        
        if (recompensas.isEmpty()) {
            System.out.println("Nenhuma recompensa encontrada no banco de dados.");
        } else {
            System.out.println("\nLista de Recompensas:");
            System.out.println("==========================================");
            for (Recompensa recompensa : recompensas) {
                System.out.println(recompensa);
            }
            System.out.println("==========================================");
            System.out.println("Total de recompensas: " + recompensas.size());
        }
        
        System.out.println("\n>>> TESTE 3: Buscando recompensa específica por ID");
        System.out.println("------------------------------------------");
        
        Recompensa recBuscada = recompensaDAO.findById(4);
        if (recBuscada != null) {
            System.out.println("Recompensa encontrada: " + recBuscada);
        } else {
            System.out.println("Recompensa não encontrada.");
        }
        
        System.out.println("\n==========================================");
        System.out.println("TESTES CONCLUÍDOS!");
        System.out.println("==========================================");
    }
}

