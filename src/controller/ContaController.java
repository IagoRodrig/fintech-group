package controller;

import model.Conta;

import java.util.Scanner;

public class ContaController {

    private Conta conta;
    Scanner input = new Scanner(System.in);

    public void criarConta(Scanner input) {
        System.out.print("Número da conta: ");
        int numero = input.nextInt();
        input.nextLine();
        System.out.print("Agência: ");
        String agencia = input.nextLine();
        System.out.print("Saldo inicial: ");
        double saldo = input.nextDouble();

        conta = new Conta(numero, agencia, saldo);
        System.out.println("Conta criada com sucesso!");
    }

    public void operacoes() {
        int opcao;
        do {
            System.out.println("\n=== Qual operação de conta deseja realizar? ===");
            System.out.println("1 - Consultar conta");
            System.out.println("2 - Alterar saldo");
            System.out.println("3 - Voltar");
            System.out.print("Opção: ");
            opcao = input.nextInt();

            switch (opcao) {
                case 1 -> this.get();
                case 2 -> this.setSaldo();
                case 3 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 3);

    }

    public void get() {
        if(conta.getNumero() != null) {
            System.out.println("==================================");
            System.out.println("Dados da conta:");
            System.out.println("Numero da conta: "+ conta.getNumero());
            System.out.println("Agência: "+ conta.getAgencia());
            System.out.println("Saldo: "+ conta.getSaldo());
            System.out.println("==================================");
        } else {
            System.out.println("Conta não encontrada!");
        }
    }

    public void setSaldo() {
        boolean valida;
        do {
            System.out.println("\n=== Qual será o novo valor? ===");
            System.out.print("Novo valor: ");
            double newValue = input.nextDouble();

            if(newValue >= 0 ) {
                conta.setSaldo(newValue);
                System.out.println("Valor alterado com sucesso!!");
                valida = true;
            } else {
                System.out.println("Valor invalido!");
                valida = false;
            }

        } while (!valida);
    }

    public Conta getConta() {
        return conta;
    }

}
