package controller;

import model.Conta;

import java.util.Scanner;

public class ContaController {

    private Conta conta;

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
}