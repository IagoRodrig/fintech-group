package controller;

import model.Cartao;

import java.util.ArrayList;
import java.util.Scanner;

public class CartaoController {
    private Cartao cartao;
    private ArrayList<Cartao> cartoesLista = new ArrayList<>();
    private Scanner input;

    public CartaoController(Scanner input) {
        this.input = input;
    }

    // Cria e adiciona um cartão à lista
    public void criarCartao() {
        System.out.print("Número do cartão: ");
        String numero = input.nextLine();
        System.out.print("Validade: ");
        String validade = input.nextLine();
        System.out.print("Bandeira: ");
        String bandeira = input.nextLine();
        System.out.print("Limite: ");
        double limite = input.nextDouble();
        input.nextLine();

        Cartao cartao = new Cartao(numero, validade, bandeira, limite);
        cartoesLista.add(cartao);
        System.out.println("Cartão cadastrado com sucesso! Total de cartões: " + cartoesLista.size());
    }

    // Lista cartões e permite escolher e operar sobre eles
    public void gerenciarCartoes() {
        if (cartoesLista.isEmpty()) {
            System.out.println("Nenhum cartão cadastrado!");
            return;
        }

        while (true) {
            System.out.println("\n=== CARTÕES CADASTRADOS ===");
            for (int i = 0; i < cartoesLista.size(); i++) {
                Cartao c = cartoesLista.get(i);
                System.out.println((i + 1) + " - " + c.getNumero() + " | " + c.getBandeira() + " | Limite: " + c.getLimite());
            }
            System.out.print("Escolha o cartão pelo número (0 para voltar): ");
            int escolha = input.nextInt() - 1;
            input.nextLine();
            if (escolha == -1) break;

            if (escolha < 0 || escolha >= cartoesLista.size()) {
                System.out.println("Opção inválida!");
                continue;
            }
            Cartao selecionado = cartoesLista.get(escolha);
            menuCartao(selecionado);
        }
    }

    // Menu de operações para um cartão específico
    private void menuCartao(Cartao cartao) {
        System.out.println("=== Meu Cartão ===");
        System.out.println("1 - Realizar compra\n2 - Novo limite");
        int opcao = input.nextInt();

        switch (opcao) {
            case 1 -> {
                System.out.print("Valor da compra: ");
                double valor = input.nextDouble();
                double novoSaldo = cartao.getLimite() - valor;
                cartao.setLimite((novoSaldo < 0) ? 0 : novoSaldo);
                input.nextLine();
                System.out.println("Compra de " + valor + " realizada no cartão " + cartao.getNumero());
            }
            case 2 -> {
                System.out.print("Valor do novo limite: ");
                double novoLimite = input.nextDouble();
                cartao.setLimite(novoLimite);
                input.nextLine();
                System.out.println("Limite do cartão " + cartao.getNumero() + " Alterado para: " + novoLimite);
            }
            case 0 -> System.out.println("Voltando ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }


}