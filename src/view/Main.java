package view;

import controller.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        UsuarioController usuarioController = new UsuarioController();
        ContaController contaController = new ContaController();
        CartaoController cartaoController = new CartaoController(input);
        InvestimentoController investimentoController = new InvestimentoController();
        TransacaoController transacaoController = new TransacaoController();
        RecompensaController recompensaController = new RecompensaController();

        int opcao;
        do {
            System.out.println("\n=== Bem Vindo ao BankMe ===");
            System.out.println("1 - Criar Usuário");
            System.out.println("2 - Criar Conta");
            System.out.println("3 - Criar Cartão");
            System.out.println("4 - Criar Investimento");
            System.out.println("5 - Operações Conta");
            System.out.println("6 - Gerenciar Cartões");
            System.out.println("7 - Transação Bancária");
            System.out.println("8 - Operações Investimento");
            System.out.println("9 - Gerenciar Recompensas");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1 -> usuarioController.operacoesUsuario(input);
                case 2 -> {
                    contaController.criarConta(input);
                    transacaoController.setConta(contaController.getConta());
                }
                case 3 -> cartaoController.criarCartao();
                case 4 -> investimentoController.criarInvestimento(input);
                case 5 -> contaController.operacoes();
                case 6 -> cartaoController.gerenciarCartoes();
                case 7 -> transacaoController.transacaoMenu();
                case 8 -> investimentoController.operacoesInvestimento(input);
                case 9 -> {
                    System.out.println("\n=== Menu de Recompensas ===");
                    System.out.println("1 - Criar Recompensa");
                    System.out.println("2 - Criar Bônus");
                    System.out.println("3 - Exibir Recompensas");
                    System.out.print("Opção: ");
                    int opcaoRecompensa = input.nextInt();
                    input.nextLine();
                    
                    switch (opcaoRecompensa) {
                        case 1 -> recompensaController.criarRecompensa(input);
                        case 2 -> recompensaController.criarBonus(input);
                        case 3 -> recompensaController.exibir();
                        default -> System.out.println("Opção inválida!");
                    }
                }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        input.close();
    }
}
