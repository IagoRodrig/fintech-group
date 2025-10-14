package controller;

import java.util.List;
import java.util.Scanner;
import model.Bonus;
import model.Recompensa;
import dao.RecompensaDAO;

public class RecompensaController {
    private RecompensaDAO recompensaDAO;
    private LoginController loginController;

    public RecompensaController() {
        this.recompensaDAO = new RecompensaDAO();
    }
    
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void criarRecompensa(Scanner input) {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para criar recompensas!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Criando recompensa para: " + usuarioLogado);
        
        System.out.print("Descrição da recompensa: ");
        String descricao = input.nextLine();
        System.out.print("Valor: R$ ");
        double valor = input.nextDouble();
        input.nextLine();
        System.out.print("Status (Ativo/Pendente/Resgatado): ");
        String status = input.nextLine();
        
        Recompensa recompensa = new Recompensa(0, usuarioLogado, descricao, valor, status);
        
        if (recompensaDAO.insert(recompensa)) {
            System.out.println("\n✅ Recompensa criada com sucesso!");
            System.out.println("🎁 Descrição: " + descricao);
            System.out.println("💰 Valor: R$ " + valor);
            System.out.println("📊 Status: " + status);
            System.out.println("👤 Usuário: " + usuarioLogado);
        } else {
            System.out.println("❌ Erro ao criar recompensa!");
        }
    }

    public void criarBonus(Scanner input) {
        System.out.print("ID do Bônus: ");
        int idBonus = input.nextInt();
        input.nextLine();
        System.out.print("ID do Usuário: ");
        String idUsuario = input.nextLine();
        System.out.print("Descrição: ");
        String descricao = input.nextLine();
        System.out.print("Valor: ");
        double valor = input.nextDouble();
        input.nextLine();
        System.out.print("Status: ");
        String status = input.nextLine();
        System.out.print("Tipo do Bônus: ");
        String tipoBonus = input.nextLine();
        
        Bonus bonus = new Bonus(idBonus, idUsuario, descricao, valor, status, tipoBonus);
        
        if (recompensaDAO.insert(bonus)) {
            System.out.println("Bônus criado com sucesso!");
        } else {
            System.out.println("Erro ao criar bônus!");
        }
    }

    public void exibir() {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para visualizar recompensas!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Recompensas do usuário: " + usuarioLogado);
        
        // Buscar recompensas do usuário logado
        List<Recompensa> recompensasUsuario = recompensaDAO.getRecompensasPorUsuario(usuarioLogado);
        
        if (recompensasUsuario.isEmpty()) {
            System.out.println("❌ Nenhuma recompensa encontrada para este usuário!");
            System.out.println("💡 Crie uma recompensa primeiro (opção 9 do menu principal).");
        } else {
            System.out.println("\n=== SUAS RECOMPENSAS ===");
            double totalRecompensas = 0;
            for (Recompensa recompensa : recompensasUsuario) {
                System.out.println("🎁 ID: " + recompensa.getIdBonus() + 
                                 " | Descrição: " + recompensa.getDescricao() + 
                                 " | Valor: R$ " + recompensa.getValor() + 
                                 " | Status: " + recompensa.getStatus());
                totalRecompensas += recompensa.getValor();
            }
            System.out.println("💰 Total em recompensas: R$ " + totalRecompensas);
            System.out.println("📊 Total de recompensas: " + recompensasUsuario.size());
        }
    }
}
