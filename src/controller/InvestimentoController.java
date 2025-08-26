package controller;

import model.Investimento;

import java.util.Scanner;

public class InvestimentoController {
    private Investimento investimento;

    public void criarInvestimento(Scanner input) {
        System.out.print("Tipo de investimento: ");
        String tipo = input.nextLine();
        System.out.print("Valor aplicado: ");
        double valor = input.nextDouble();
        input.nextLine();
        System.out.print("Data da aplicação: ");
        String data = input.nextLine();

        investimento = new Investimento(tipo, valor, data);
        System.out.println("Investimento criado com sucesso!");
    }
}