package controller;

import model.Investimento;
import dao.InvestimentoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvestimentoController {
    private InvestimentoDAO investimentoDAO;

    public InvestimentoController() {
        this.investimentoDAO = new InvestimentoDAO();
    }

    public void criarInvestimento(Scanner input) {
        System.out.print("Nome de usuário: ");
        String nomeUsuario = input.nextLine();
        System.out.print("Tipo de investimento: ");
        String tipo = input.nextLine();
        System.out.print("Valor aplicado: ");
        double valor = input.nextDouble();
        input.nextLine();

        Investimento investimento = new Investimento(nomeUsuario, tipo, valor, "2024-01-01");
        
        if (investimentoDAO.insert(investimento)) {
            System.out.println("Investimento criado com sucesso!");
        } else {
            System.out.println("Erro ao criar investimento!");
        }
    }

    public void exibirInvestimentos() {
        List<Investimento> investimentos = investimentoDAO.getAll();
        
        if (investimentos.isEmpty()) {
            System.out.println("Nenhum investimento cadastrado!");
        } else {
            System.out.println("\n=== LISTA DE INVESTIMENTOS ===");
            for (Investimento investimento : investimentos) {
                System.out.println(investimento);
            }
        }
    }

    public void operacoesInvestimento(Scanner input) {
        int opcao;
        do {
            System.out.println("\n=== OPERAÇÕES DE INVESTIMENTO ===");
            System.out.println("1 - Criar Investimento");
            System.out.println("2 - Exibir Investimentos");
            System.out.println("3 - Voltar");
            System.out.print("Opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1 -> criarInvestimento(input);
                case 2 -> exibirInvestimentos();
                case 3 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 3);
    }

    public Investimento getInvestimento() {
        List<Investimento> investimentos = investimentoDAO.getAll();
        return investimentos.isEmpty() ? null : investimentos.get(0);
    }
}