package controller;

import java.util.ArrayList;
import java.util.Scanner;
import model.Bonus;
import model.Recompensa;

public class RecompensaController {
    private ArrayList<Recompensa> recompensasLista = new ArrayList();

    public void criarRecompensa(Scanner input) {
        System.out.print("ID da Recompensa: ");
        int idBonus = input.nextInt();
        input.nextLine();
        System.out.print("ID do Usuário: ");
        int idUsuario = input.nextInt();
        input.nextLine();
        System.out.print("Descrição: ");
        String descricao = input.nextLine();
        System.out.print("Valor: ");
        double valor = input.nextDouble();
        input.nextLine();
        System.out.print("Status: ");
        String status = input.nextLine();
        Recompensa recompensa = new Recompensa(idBonus, idUsuario, descricao, valor, status);
        this.recompensasLista.add(recompensa);
        System.out.println("Recompensa criada com sucesso! Total: " + this.recompensasLista.size());
    }

    public void criarBonus(Scanner input) {
        System.out.print("ID do Bônus: ");
        int idBonus = input.nextInt();
        input.nextLine();
        System.out.print("ID do Usuário: ");
        int idUsuario = input.nextInt();
        input.nextLine();
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
        this.recompensasLista.add(bonus);
        System.out.println("Bônus criado com sucesso! Total: " + this.recompensasLista.size());
    }

    public void exibir() {
        if (this.recompensasLista.isEmpty()) {
            System.out.println("Nenhuma recompensa cadastrada!");
        } else {
            System.out.println("\n=== LISTA DE RECOMPENSAS ===");

            for(int i = 0; i < this.recompensasLista.size(); ++i) {
                Recompensa r = (Recompensa)this.recompensasLista.get(i);
                System.out.print(i + 1 + " - ");
                r.exibirDetalhes();
            }

        }
    }
}
