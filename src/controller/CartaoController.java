package controller;

import model.Cartao;
import dao.CartaoDAO;

import java.util.List;
import java.util.Scanner;

public class CartaoController {
    private CartaoDAO cartaoDAO;
    private Scanner input;

    public CartaoController(Scanner input) {
        this.input = input;
        this.cartaoDAO = new CartaoDAO();
    }

    // Cria e adiciona um cartão ao banco de dados
    public void criarCartao() {
        System.out.print("ID da Conta: ");
        int idConta = input.nextInt();
        input.nextLine();
        System.out.print("Tipo do cartão (Débito/Crédito): ");
        String tipoCartao = input.nextLine();
        System.out.print("Número do cartão: ");
        String numero = input.nextLine();
        System.out.print("Validade: ");
        String validade = input.nextLine();
        System.out.print("Limite: ");
        double limite = input.nextDouble();
        input.nextLine();

        // Criar número mascarado
        String numeroMascarado = mascararNumero(numero);
        
        Cartao cartao = new Cartao(idConta, tipoCartao, numeroMascarado, validade, limite);
        
        if (cartaoDAO.insert(cartao)) {
            System.out.println("Cartão cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar cartão!");
        }
    }

    // Lista cartões e permite escolher e operar sobre eles
    public void gerenciarCartoes() {
        List<Cartao> cartoes = cartaoDAO.getAll();
        
        if (cartoes.isEmpty()) {
            System.out.println("Nenhum cartão cadastrado!");
            return;
        }

        while (true) {
            System.out.println("\n=== CARTÕES CADASTRADOS ===");
            for (int i = 0; i < cartoes.size(); i++) {
                Cartao c = cartoes.get(i);
                System.out.println((i + 1) + " - " + c.getIdConta() + " | " + c.getTipoCartao() + " | Limite: " + c.getLimiteCredito());
            }
            System.out.print("Escolha o cartão pelo número (0 para voltar): ");
            int escolha = input.nextInt() - 1;
            input.nextLine();
            if (escolha == -1) break;

            if (escolha < 0 || escolha >= cartoes.size()) {
                System.out.println("Opção inválida!");
                continue;
            }
            Cartao selecionado = cartoes.get(escolha);
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
                input.nextLine();
                
                if (valor <= 0) {
                    System.out.println("Valor inválido! Deve ser maior que zero.");
                    return;
                }
                
                if (valor > cartao.getLimiteCredito()) {
                    System.out.println("Compra negada! Valor excede o limite disponível.");
                    System.out.println("Limite disponível: " + cartao.getLimiteCredito());
                    return;
                }
                
                double novoLimite = cartao.getLimiteCredito() - valor;
                cartao.setLimiteCredito(novoLimite);
                System.out.println("Compra de R$ " + valor + " realizada com sucesso!");
                System.out.println("Limite restante: R$ " + novoLimite);
                
                // TODO: Implementar update no banco de dados
            }
            case 2 -> {
                System.out.print("Valor do novo limite: ");
                double novoLimite = input.nextDouble();
                cartao.setLimiteCredito(novoLimite);
                input.nextLine();
                System.out.println("Limite do cartão " + cartao.getIdCartao() + " Alterado para: " + novoLimite);
                
                // TODO: Implementar update no banco de dados
            }
            case 0 -> System.out.println("Voltando ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    public void listarCartoes() {
        List<Cartao> cartoes = cartaoDAO.getAll();
        
        if (cartoes.isEmpty()) {
            System.out.println("Nenhum cartão encontrado!");
        } else {
            System.out.println("\n=== LISTA DE CARTÕES ===");
            for (Cartao cartao : cartoes) {
                System.out.println(cartao);
            }
        }
    }

    /**
     * Mascara o número do cartão mostrando apenas os últimos 4 dígitos
     */
    private String mascararNumero(String numero) {
        if (numero.length() >= 4) {
            return "**** **** **** " + numero.substring(numero.length() - 4);
        }
        return "**** **** **** " + numero;
    }
}