package controller;

import model.Cartao;

import java.util.Scanner;

public class CartaoController {
    private Cartao cartao;

    public void criarCartao(Scanner input) {
        System.out.print("Número do cartão: ");
        String numero = input.nextLine();
        System.out.print("Validade: ");
        String validade = input.nextLine();
        System.out.print("Bandeira: ");
        String bandeira = input.nextLine();
        System.out.print("Limite: ");
        double limite = input.nextDouble();

        cartao = new Cartao(numero, validade, bandeira, limite);
        System.out.println("Cartão cadastrado com sucesso!");
    }
}