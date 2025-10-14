package controller;

import model.Investimento;
import dao.InvestimentoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvestimentoController {
    private InvestimentoDAO investimentoDAO;
    private LoginController loginController;

    public InvestimentoController() {
        this.investimentoDAO = new InvestimentoDAO();
    }
    
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void criarInvestimento(Scanner input) {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para criar investimentos!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Criando investimento para: " + usuarioLogado);
        
        System.out.print("Tipo de investimento (CDB, Tesouro Direto, LCI, Ações, Fundos Imobiliários): ");
        String tipo = input.nextLine();
        System.out.print("Valor aplicado: R$ ");
        double valor = input.nextDouble();
        input.nextLine();

        Investimento investimento = new Investimento(usuarioLogado, tipo, valor, "2024-01-01");
        
        if (investimentoDAO.insert(investimento)) {
            System.out.println("\n✅ Investimento criado com sucesso!");
            System.out.println("📈 Tipo: " + tipo);
            System.out.println("💰 Valor: R$ " + valor);
            System.out.println("👤 Usuário: " + usuarioLogado);
            System.out.println("📅 Data: 2024-01-01");
        } else {
            System.out.println("❌ Erro ao criar investimento!");
        }
    }

    public void exibirInvestimentos() {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para visualizar investimentos!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Investimentos do usuário: " + usuarioLogado);
        
        // Buscar investimentos do usuário logado
        List<Investimento> investimentosUsuario = investimentoDAO.getInvestimentosPorUsuario(usuarioLogado);
        
        if (investimentosUsuario.isEmpty()) {
            System.out.println("❌ Nenhum investimento encontrado para este usuário!");
            System.out.println("💡 Crie um investimento primeiro (opção 4 do menu principal).");
        } else {
            System.out.println("\n=== SEUS INVESTIMENTOS ===");
            double totalInvestido = 0;
            for (Investimento investimento : investimentosUsuario) {
                System.out.println("📈 ID: " + investimento.getIdInvestimento() + 
                                 " | Tipo: " + investimento.getTipo() + 
                                 " | Valor: R$ " + investimento.getValorInvestido() + 
                                 " | Data: " + investimento.getDataAplicacao());
                totalInvestido += investimento.getValorInvestido();
            }
            System.out.println("💰 Total investido: R$ " + totalInvestido);
            System.out.println("📊 Total de investimentos: " + investimentosUsuario.size());
        }
    }

    public void operacoesInvestimento(Scanner input) {
        int opcao;
        do {
            System.out.println("\n=== OPERAÇÕES DE INVESTIMENTO ===");
            System.out.println("1 - Criar Investimento");
            System.out.println("2 - Exibir Investimentos");
            System.out.println("3 - Voltar");
            System.out.print("Opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1 -> criarInvestimento(input);
                case 2 -> exibirInvestimentos();
                case 3 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 3);
    }

    public Investimento getInvestimento() {
        List<Investimento> investimentos = investimentoDAO.getAll();
        return investimentos.isEmpty() ? null : investimentos.get(0);
    }
}