package controller;
import model.Transacao;
import model.Conta;
import java.util.Scanner;

public class TransacaoController {
    private int op;
    private int valor, numero, cf;
    private String agencia, data;
    private Transacao t;
    private Conta conta;
    Scanner sc = new Scanner(System.in);

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    private void checagemTransacao () {
        if (cf == 1) {
            t = new Transacao(valor, data, numero, agencia); // agora usa atributos
            efetuarTransacao();
        } else if (cf == 2) {
            return;
        } else {
            System.out.println("Opção Inválida.");
        }
    }

    private void efetuarTransacao () {
        if (conta == null) {
            System.out.println("Nenhuma conta vinculada para realizar a transação.");
            t.setStatus("Cancelada");
            return;
        } else if (t.getValor() > conta.getSaldo()) {
            System.out.println("Saldo insuficiente.");
            t.setStatus("Cancelada");
            return;
        } else if (t.getValor() < 0) {
            System.out.println("Erro - Valor abaixo de 0 (valor inválido)");
            t.setStatus("Cancelada");
            return;
        } else {
            double novoSaldo = conta.getSaldo() - t.getValor();
            conta.setSaldo(novoSaldo);
            System.out.println("Transação realizada com sucesso!");
            t.setStatus("Efetuada com sucesso");
            t.exibirTransacao();
        }
    }


    private void transacao () {
        System.out.println("Qual o valor da transação: ");
        valor = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite a agência: ");
        agencia = sc.nextLine();

        System.out.println("Qual o número: ");
        numero = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite a data de hoje: ");
        data = sc.nextLine();

        System.out.println("1 - Confirmar\n2 - Cancelar ");
        cf = sc.nextInt();
        sc.nextLine();

        checagemTransacao();
    }

    public void transacaoMenu () {
       do { System.out.println("1 - Transação\n2 - Ultima transação\n3 - Voltar");
        op = sc.nextInt();
        switch (op) {
            case 1 -> {
                transacao();
            }
            case 2 -> {
                if (t != null) {t.exibirTransacao();}

                else {System.out.println("Nenhuma transação realizada ainda!");}
            }
            case 3 -> {
                System.out.println("Voltando...");
            }
        } } while (op != 3);
    }
}
