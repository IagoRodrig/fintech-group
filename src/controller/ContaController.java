package controller;

import model.Conta;
import dao.ContaDAO;

import java.util.List;
import java.util.Scanner;

public class ContaController {

    private ContaDAO contaDAO;
    private Conta contaAtual;
    Scanner input = new Scanner(System.in);

    public ContaController() {
        this.contaDAO = new ContaDAO();
    }

    public void criarConta(Scanner input) {
        System.out.print("Nome de usuário: ");
        String nomeUsuario = input.nextLine();
        System.out.print("Tipo da conta (Corrente/Poupança/Investimento): ");
        String tipoConta = input.nextLine();
        System.out.print("Saldo inicial: ");
        double saldo = input.nextDouble();
        input.nextLine();

        Conta conta = new Conta(nomeUsuario, saldo, tipoConta, saldo, "2024-01-01");
        
        if (contaDAO.insert(conta)) {
            System.out.println("Conta criada com sucesso!");
            this.contaAtual = conta;
        } else {
            System.out.println("Erro ao criar conta!");
        }
    }

    public void operacoes() {
        int opcao;
        do {
            System.out.println("\n=== Qual operação de conta deseja realizar? ===");
            System.out.println("1 - Consultar conta");
            System.out.println("2 - Alterar saldo");
            System.out.println("3 - Listar contas");
            System.out.println("4 - Voltar");
            System.out.print("Opção: ");
            opcao = input.nextInt();

            switch (opcao) {
                case 1 -> this.get();
                case 2 -> this.setSaldo();
                case 3 -> this.listarContas();
                case 4 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 4);

    }

    public void get() {
        if(contaAtual != null) {
            System.out.println("==================================");
            System.out.println("Dados da conta:");
            System.out.println("Tipo da conta: "+ contaAtual.getTipoConta());
            System.out.println("Saldo: R$ "+ contaAtual.getSaldo());
            System.out.println("Data: "+ contaAtual.getData());
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
            input.nextLine(); // Limpar buffer

            if(newValue >= 0 ) {
                contaAtual.setSaldo(newValue);
                System.out.println("Valor alterado com sucesso!!");
                valida = true;
                
                // TODO: Implementar update no banco de dados
            } else {
                System.out.println("Valor invalido!");
                valida = false;
            }

        } while (!valida);
    }

    public void listarContas() {
        List<Conta> contas = contaDAO.getAll();
        
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta encontrada!");
        } else {
            System.out.println("\n=== LISTA DE CONTAS ===");
            for (Conta conta : contas) {
                exibirContaSegura(conta);
            }
        }
    }

    /**
     * Exibe dados da conta sem informações sensíveis
     */
    private void exibirContaSegura(Conta conta) {
        System.out.println("🏦 Tipo: " + conta.getTipoConta());
        System.out.println("💰 Saldo: R$ " + conta.getSaldo());
        System.out.println("📅 Data: " + conta.getData());
        System.out.println("─────────────────────────────────────");
    }

    public Conta getConta() {
        return contaAtual;
    }
}