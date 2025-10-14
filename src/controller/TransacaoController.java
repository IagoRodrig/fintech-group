package controller;
import model.Transacao;
import model.Conta;
import dao.TransacaoDAO;

import java.util.List;
import java.util.Scanner;

public class TransacaoController {
    private TransacaoDAO transacaoDAO;
    private int op;
    private int valor, numero, cf;
    private String data;
    private Transacao t;
    private Conta conta;
    Scanner sc = new Scanner(System.in);

    public TransacaoController() {
        this.transacaoDAO = new TransacaoDAO();
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    private void checagemTransacao () {
        if (cf == 1) {
            t = new Transacao(conta.getIdConta(), numero, valor, data); 
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
            return;
        } else if (t.getValor() > conta.getSaldo()) {
            System.out.println("Saldo insuficiente.");
            return;
        } else if (t.getValor() < 0) {
            System.out.println("Erro - Valor abaixo de 0 (valor inválido)");
            return;
        } else {
            double novoSaldo = conta.getSaldo() - t.getValor();
            conta.setSaldo(novoSaldo);
            System.out.println("Transação realizada com sucesso!");
            t.exibirTransacao();
            
            // Salvar transação no banco de dados
            transacaoDAO.insert(t);
        }
    }

    private void transacao () {
        System.out.println("Qual o valor da transação: ");
        valor = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite o nome de usuário da conta destino: ");
        String nomeUsuarioDestino = sc.nextLine();

        System.out.println("Digite a data de hoje: ");
        data = sc.nextLine();

        System.out.println("1 - Confirmar\n2 - Cancelar ");
        cf = sc.nextInt();
        sc.nextLine();

        // Buscar ID da conta destino pelo nome de usuário
        int idContaDestino = buscarIdContaPorUsuario(nomeUsuarioDestino);
        if (idContaDestino == -1) {
            System.out.println("❌ Usuário destino não encontrado!");
            return;
        }

        numero = idContaDestino; // Usar o ID encontrado
        checagemTransacao();
    }

    public void transacaoMenu () {
       do { 
           System.out.println("1 - Transação\n2 - Ultima transação\n3 - Listar transações\n4 - Voltar");
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
                   listarTransacoes();
               }
               case 4 -> {
                   System.out.println("Voltando...");
               }
           } 
       } while (op != 4);
    }

    public void listarTransacoes() {
        List<Transacao> transacoes = transacaoDAO.getAll();
        
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada!");
        } else {
            System.out.println("\n=== LISTA DE TRANSAÇÕES ===");
            for (Transacao transacao : transacoes) {
                exibirTransacaoSegura(transacao);
            }
        }
    }

    /**
     * Exibe dados da transação sem informações sensíveis
     */
    private void exibirTransacaoSegura(Transacao transacao) {
        System.out.println("💰 Valor: R$ " + transacao.getValor());
        System.out.println("📅 Data: " + transacao.getData());
        System.out.println("─────────────────────────────────────");
    }

    /**
     * Busca o ID da conta pelo nome de usuário
     */
    private int buscarIdContaPorUsuario(String nomeUsuario) {
        // Implementação simples - em um sistema real, isso seria feito via DAO
        // Por enquanto, vamos simular com alguns usuários conhecidos
        switch (nomeUsuario.toLowerCase()) {
            case "joao.silva":
                return 1;
            case "maria.santos":
                return 2;
            case "pedro.oliveira":
                return 3;
            default:
                return -1; // Usuário não encontrado
        }
    }
}
