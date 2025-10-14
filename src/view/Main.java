package view;

import controller.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Controllers
        LoginController loginController = new LoginController();
        UsuarioController usuarioController = new UsuarioController();
        ContaController contaController = new ContaController();
        CartaoController cartaoController = new CartaoController(input);
        InvestimentoController investimentoController = new InvestimentoController();
        TransacaoController transacaoController = new TransacaoController();
        RecompensaController recompensaController = new RecompensaController();
        
        // Configurar dependências
        transacaoController.setLoginController(loginController);
        contaController.setLoginController(loginController);
        usuarioController.setLoginController(loginController);
        cartaoController.setLoginController(loginController);
        investimentoController.setLoginController(loginController);
        recompensaController.setLoginController(loginController);

        int opcao;
        do {
            // Mostrar status do login
            if (loginController.isLogado()) {
                System.out.println("\n👤 Usuário Logado: " + loginController.getNomeUsuarioLogado());
            } else {
                System.out.println("\n⚠️  Nenhum usuário logado");
            }
            
            System.out.println("\n=== Bem Vindo ao BankMe ===");
            System.out.println("0 - 🔐 Login/Logout");
            
            if (!loginController.isLogado()) {
                System.out.println("1 - 👤 Criar Usuário");
                System.out.println("💡 Faça login para acessar outras funcionalidades");
            } else {
                System.out.println("2 - 🏦 Criar Conta");
                System.out.println("3 - 💳 Criar Cartão");
                System.out.println("4 - 📈 Criar Investimento");
                System.out.println("5 - 🏦 Operações Conta");
                System.out.println("6 - 💳 Gerenciar Cartões");
                System.out.println("7 - 💸 Transação Bancária");
                System.out.println("8 - 📈 Operações Investimento");
                System.out.println("9 - 🎁 Gerenciar Recompensas");
            }
            
            System.out.println("10 - 🚪 Sair");
            System.out.print("Opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 0 -> loginController.menuLogin();
                case 1 -> {
                    if (!loginController.isLogado()) {
                        usuarioController.operacoesUsuario(input);
                    } else {
                        System.out.println("❌ Opção inválida quando logado!");
                    }
                }
                case 2 -> {
                    if (loginController.isLogado()) {
                        contaController.criarConta(input);
                        transacaoController.setConta(contaController.getConta());
                    } else {
                        System.out.println("❌ É necessário fazer login primeiro!");
                    }
                }
                case 3 -> {
                    if (loginController.isLogado()) {
                        cartaoController.criarCartao();
                    } else {
                        System.out.println("❌ É necessário fazer login primeiro!");
                    }
                }
                case 4 -> {
                    if (loginController.isLogado()) {
                        investimentoController.criarInvestimento(input);
                    } else {
                        System.out.println("❌ É necessário fazer login primeiro!");
                    }
                }
                case 5 -> {
                    if (loginController.isLogado()) {
                        contaController.operacoes();
                    } else {
                        System.out.println("❌ É necessário fazer login primeiro!");
                    }
                }
                case 6 -> {
                    if (loginController.isLogado()) {
                        cartaoController.gerenciarCartoes();
                    } else {
                        System.out.println("❌ É necessário fazer login primeiro!");
                    }
                }
                case 7 -> {
                    if (loginController.isLogado()) {
                        transacaoController.transacaoMenu();
                    } else {
                        System.out.println("❌ É necessário fazer login primeiro!");
                    }
                }
                case 8 -> {
                    if (loginController.isLogado()) {
                        investimentoController.operacoesInvestimento(input);
                    } else {
                        System.out.println("❌ É necessário fazer login primeiro!");
                    }
                }
                case 9 -> {
                    if (loginController.isLogado()) {
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
                    } else {
                        System.out.println("❌ É necessário fazer login primeiro!");
                    }
                }
                case 10 -> {
                    loginController.fazerLogout();
                    System.out.println("👋 Saindo...");
                }
                default -> System.out.println("❌ Opção inválida!");
            }

        } while (opcao != 10);

        input.close();
    }
}
