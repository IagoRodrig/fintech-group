package controller;

import java.util.List;
import java.util.Scanner;
import model.Bonus;
import model.Recompensa;
import dao.RecompensaDAO;

public class RecompensaController {
    private RecompensaDAO recompensaDAO;

    public RecompensaController() {
        this.recompensaDAO = new RecompensaDAO();
    }

    public void criarRecompensa(Scanner input) {
        System.out.print("Nome de usuário: ");
        String nomeUsuario = input.nextLine();
        System.out.print("Descrição: ");
        String descricao = input.nextLine();
        System.out.print("Valor: ");
        double valor = input.nextDouble();
        input.nextLine();
        System.out.print("Status: ");
        String status = input.nextLine();
        
        Recompensa recompensa = new Recompensa(0, nomeUsuario, descricao, valor, status);
        
        if (recompensaDAO.insert(recompensa)) {
            System.out.println("Recompensa criada com sucesso!");
        } else {
            System.out.println("Erro ao criar recompensa!");
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
        List<Recompensa> recompensas = recompensaDAO.getAll();
        
        if (recompensas.isEmpty()) {
            System.out.println("Nenhuma recompensa cadastrada!");
        } else {
            System.out.println("\n=== LISTA DE RECOMPENSAS ===");
            for (Recompensa recompensa : recompensas) {
                recompensa.exibirDetalhes();
            }
        }
    }
}
