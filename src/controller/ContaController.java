package controller;

import model.Conta;
import dao.ContaDAO;

import java.util.List;
import java.util.Scanner;

public class ContaController {

    private ContaDAO contaDAO;
    private Conta contaAtual;
    private LoginController loginController;
    Scanner input = new Scanner(System.in);

    public ContaController() {
        this.contaDAO = new ContaDAO();
    }
    
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void criarConta(Scanner input) {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para criar uma conta!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Criando conta para: " + usuarioLogado);
        
        // Verificar tipos de conta já existentes
        List<String> tiposExistentes = contaDAO.getTiposContaExistentes(usuarioLogado);
        
        if (!tiposExistentes.isEmpty()) {
            System.out.println("\n📋 Você já possui os seguintes tipos de conta:");
            for (String tipo : tiposExistentes) {
                System.out.println("  ✅ " + tipo);
            }
            System.out.println("\n💡 Você só pode ter UMA conta de cada tipo!");
        }
        
        System.out.print("\nTipo da conta (Corrente/Poupança/Investimento): ");
        String tipoConta = input.nextLine();
        
        // Validar se já existe conta deste tipo
        if (contaDAO.existeContaPorTipo(usuarioLogado, tipoConta)) {
            System.out.println("❌ Você já possui uma conta do tipo '" + tipoConta + "'!");
            System.out.println("💡 Escolha um tipo diferente ou use sua conta existente.");
            return;
        }
        
        System.out.print("Saldo inicial: ");
        double saldo = input.nextDouble();
        input.nextLine();

        Conta conta = new Conta(usuarioLogado, saldo, tipoConta, saldo, "2024-01-01");
        
        if (contaDAO.insert(conta)) {
            // Buscar a conta criada para obter o ID real
            int idContaCriada = contaDAO.buscarIdContaPorUsuario(usuarioLogado);
            if (idContaCriada != -1) {
                System.out.println("✅ Conta criada com sucesso!");
                System.out.println("🏦 ID da Conta: " + idContaCriada);
                System.out.println("👤 Usuário: " + usuarioLogado);
                System.out.println("💰 Tipo: " + tipoConta);
                System.out.println("💵 Saldo: R$ " + saldo);
                System.out.println("📝 Guarde o ID da conta para fazer transações!");
                
                // Buscar a conta completa do banco
                this.contaAtual = contaDAO.findById(idContaCriada);
            } else {
                System.out.println("❌ Erro: Conta criada mas não foi possível obter o ID!");
            }
        } else {
            System.out.println("❌ Erro ao criar conta!");
        }
    }

    public void operacoes() {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para realizar operações de conta!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Operações de conta para: " + usuarioLogado);
        
        int opcao;
        do {
            System.out.println("\n=== OPERAÇÕES DE CONTA ===");
            System.out.println("1 - 📋 Consultar Conta");
            System.out.println("2 - 💰 Alterar Saldo");
            System.out.println("3 - 📊 Listar Contas");
            System.out.println("4 - ⬅️  Voltar");
            System.out.print("Opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1 -> this.get();
                case 2 -> this.setSaldo();
                case 3 -> this.listarContas();
                case 4 -> System.out.println("⬅️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 4);
    }

    public void get() {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para consultar contas!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        
        // Se não há conta atual, buscar uma conta do usuário
        if (contaAtual == null) {
            List<Integer> idsContas = contaDAO.buscarIdsContasPorUsuario(usuarioLogado);
            
            if (idsContas.isEmpty()) {
                System.out.println("❌ Nenhuma conta encontrada para este usuário!");
                System.out.println("💡 Crie uma conta primeiro (opção 2 do menu principal).");
                return;
            }
            
            // Se há múltiplas contas, permitir seleção
            if (idsContas.size() > 1) {
                System.out.println("📋 Suas contas disponíveis:");
                for (int i = 0; i < idsContas.size(); i++) {
                    int idConta = idsContas.get(i);
                    var conta = contaDAO.findById(idConta);
                    if (conta != null) {
                        System.out.println((i + 1) + " - ID: " + idConta + " | Tipo: " + conta.getTipoConta() + " | Saldo: R$ " + conta.getSaldo());
                    }
                }
                System.out.print("Escolha uma conta (número): ");
                int escolha = input.nextInt() - 1;
                input.nextLine();
                
                if (escolha < 0 || escolha >= idsContas.size()) {
                    System.out.println("❌ Opção inválida!");
                    return;
                }
                
                contaAtual = contaDAO.findById(idsContas.get(escolha));
            } else {
                // Se há apenas uma conta, usar ela automaticamente
                contaAtual = contaDAO.findById(idsContas.get(0));
            }
        }
        
        if (contaAtual != null) {
            System.out.println("\n==================================");
            System.out.println("📋 DADOS DA CONTA:");
            System.out.println("🏦 ID da conta: " + contaAtual.getIdConta());
            System.out.println("👤 ID do usuário: " + contaAtual.getIdUsuario());
            System.out.println("💰 Tipo da conta: " + contaAtual.getTipoConta());
            System.out.println("💵 Saldo: R$ " + contaAtual.getSaldo());
            System.out.println("📅 Data: " + contaAtual.getData());
            System.out.println("==================================");
        } else {
            System.out.println("❌ Erro ao carregar dados da conta!");
        }
    }

    public void setSaldo() {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para alterar saldo!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        
        // Se não há conta atual, buscar uma conta do usuário
        if (contaAtual == null) {
            List<Integer> idsContas = contaDAO.buscarIdsContasPorUsuario(usuarioLogado);
            
            if (idsContas.isEmpty()) {
                System.out.println("❌ Nenhuma conta encontrada para este usuário!");
                System.out.println("💡 Crie uma conta primeiro (opção 2 do menu principal).");
                return;
            }
            
            // Se há múltiplas contas, permitir seleção
            if (idsContas.size() > 1) {
                System.out.println("📋 Suas contas disponíveis:");
                for (int i = 0; i < idsContas.size(); i++) {
                    int idConta = idsContas.get(i);
                    var conta = contaDAO.findById(idConta);
                    if (conta != null) {
                        System.out.println((i + 1) + " - ID: " + idConta + " | Tipo: " + conta.getTipoConta() + " | Saldo: R$ " + conta.getSaldo());
                    }
                }
                System.out.print("Escolha uma conta (número): ");
                int escolha = input.nextInt() - 1;
                input.nextLine();
                
                if (escolha < 0 || escolha >= idsContas.size()) {
                    System.out.println("❌ Opção inválida!");
                    return;
                }
                
                contaAtual = contaDAO.findById(idsContas.get(escolha));
            } else {
                // Se há apenas uma conta, usar ela automaticamente
                contaAtual = contaDAO.findById(idsContas.get(0));
            }
        }
        
        if (contaAtual == null) {
            System.out.println("❌ Erro ao carregar dados da conta!");
            return;
        }
        
        boolean valida;
        do {
            System.out.println("\n=== ALTERAR SALDO ===");
            System.out.println("🏦 Conta: ID " + contaAtual.getIdConta() + " | Tipo: " + contaAtual.getTipoConta());
            System.out.println("💵 Saldo atual: R$ " + contaAtual.getSaldo());
            System.out.print("💰 Novo valor: R$ ");
            double newValue = input.nextDouble();
            input.nextLine();

            if (newValue >= 0) {
                // Atualizar saldo no banco de dados
                boolean saldoAtualizado = contaDAO.updateSaldo(contaAtual.getIdConta(), newValue);
                
                if (saldoAtualizado) {
                    // Atualizar saldo no objeto em memória
                    contaAtual.setSaldo(newValue);
                    System.out.println("✅ Valor alterado com sucesso!");
                    System.out.println("💵 Novo saldo: R$ " + contaAtual.getSaldo());
                    valida = true;
                } else {
                    System.err.println("❌ Erro ao atualizar saldo no banco de dados.");
                    valida = false;
                }
            } else {
                System.out.println("❌ Valor inválido! Deve ser maior ou igual a zero.");
                valida = false;
            }

        } while (!valida);
    }

    public void listarContas() {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para visualizar contas!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Contas do usuário: " + usuarioLogado);
        
        // Buscar apenas contas do usuário logado
        List<Integer> idsContas = contaDAO.buscarIdsContasPorUsuario(usuarioLogado);
        
        if (idsContas.isEmpty()) {
            System.out.println("❌ Nenhuma conta encontrada para este usuário!");
            System.out.println("💡 Crie uma conta primeiro (opção 2 do menu principal).");
        } else {
            System.out.println("\n=== SUAS CONTAS ===");
            for (Integer idConta : idsContas) {
                Conta conta = contaDAO.findById(idConta);
                if (conta != null) {
                    System.out.println("🏦 ID: " + conta.getIdConta() + 
                                     " | Tipo: " + conta.getTipoConta() + 
                                     " | Saldo: R$ " + conta.getSaldo());
                }
            }
            System.out.println("📊 Total de contas: " + idsContas.size());
        }
    }

    public Conta getConta() {
        return contaAtual;
    }
}