package controller;

import model.Cartao;
import model.Transacao;
import dao.CartaoDAO;
import dao.ContaDAO;
import dao.TransacaoDAO;

import java.util.List;
import java.util.Scanner;

public class CartaoController {
    private CartaoDAO cartaoDAO;
    private ContaDAO contaDAO;
    private TransacaoDAO transacaoDAO;
    private LoginController loginController;
    private Scanner input;

    public CartaoController(Scanner input) {
        this.input = input;
        this.cartaoDAO = new CartaoDAO();
        this.contaDAO = new ContaDAO();
        this.transacaoDAO = new TransacaoDAO();
    }
    
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    // Cria e adiciona um cartão ao banco de dados
    public void criarCartao() {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para criar cartões!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Criando cartão para: " + usuarioLogado);
        
        // Listar contas do usuário logado
        List<Integer> idsContas = contaDAO.buscarIdsContasPorUsuario(usuarioLogado);
        
        if (idsContas.isEmpty()) {
            System.out.println("❌ Nenhuma conta encontrada para este usuário!");
            System.out.println("💡 Crie uma conta primeiro (opção 2 do menu principal).");
            return;
        }
        
        System.out.println("\n📋 Suas contas disponíveis:");
        for (int i = 0; i < idsContas.size(); i++) {
            int idConta = idsContas.get(i);
            var conta = contaDAO.findById(idConta);
            if (conta != null) {
                System.out.println((i + 1) + " - ID: " + idConta + " | Tipo: " + conta.getTipoConta() + " | Saldo: R$ " + conta.getSaldo());
            }
        }
        
        System.out.print("\nEscolha uma conta (número): ");
        int escolhaConta = input.nextInt() - 1;
        input.nextLine();
        
        if (escolhaConta < 0 || escolhaConta >= idsContas.size()) {
            System.out.println("❌ Opção inválida!");
            return;
        }
        
        int idConta = idsContas.get(escolhaConta);
        
        System.out.print("Tipo do cartão (Débito/Crédito): ");
        String tipoCartao = input.nextLine();
        System.out.print("Número do cartão: ");
        String numero = input.nextLine();
        System.out.print("Validade (MM/AAAA): ");
        String validade = input.nextLine();
        System.out.print("Limite: R$ ");
        double limite = input.nextDouble();
        input.nextLine();

        // Criar número mascarado
        String numeroMascarado = mascararNumero(numero);
        
        Cartao cartao = new Cartao(idConta, tipoCartao, numeroMascarado, validade, limite);
        
        if (cartaoDAO.insert(cartao)) {
            System.out.println("\n✅ Cartão cadastrado com sucesso!");
            System.out.println("💳 Tipo: " + tipoCartao);
            System.out.println("🔢 Número: " + numeroMascarado);
            System.out.println("📅 Validade: " + validade);
            System.out.println("💰 Limite: R$ " + limite);
            System.out.println("🏦 Conta vinculada: ID " + idConta);
        } else {
            System.out.println("❌ Erro ao cadastrar cartão!");
        }
    }

    // Lista cartões e permite escolher e operar sobre eles
    public void gerenciarCartoes() {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para gerenciar cartões!");
            System.out.println("💡 Use o menu de login primeiro (opção 0).");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Cartões do usuário: " + usuarioLogado);
        
        // Buscar contas do usuário logado
        List<Integer> idsContas = contaDAO.buscarIdsContasPorUsuario(usuarioLogado);
        
        if (idsContas.isEmpty()) {
            System.out.println("❌ Nenhuma conta encontrada para este usuário!");
            System.out.println("💡 Crie uma conta primeiro (opção 2 do menu principal).");
            return;
        }
        
        // Buscar cartões das contas do usuário
        List<Cartao> cartoesUsuario = cartaoDAO.getCartoesPorContas(idsContas);
        
        if (cartoesUsuario.isEmpty()) {
            System.out.println("❌ Nenhum cartão encontrado para este usuário!");
            System.out.println("💡 Crie um cartão primeiro (opção 3 do menu principal).");
            return;
        }

        while (true) {
            System.out.println("\n=== SEUS CARTÕES ===");
            for (int i = 0; i < cartoesUsuario.size(); i++) {
                Cartao c = cartoesUsuario.get(i);
                System.out.println((i + 1) + " - ID: " + c.getIdCartao() + 
                                 " | Tipo: " + c.getTipoCartao() + 
                                 " | Número: " + c.getNumeroMascarado() + 
                                 " | Limite: R$ " + c.getLimiteCredito());
            }
            System.out.print("Escolha o cartão pelo número (0 para voltar): ");
            int escolha = input.nextInt() - 1;
            input.nextLine();
            if (escolha == -1) break;

            if (escolha < 0 || escolha >= cartoesUsuario.size()) {
                System.out.println("❌ Opção inválida!");
                continue;
            }
            Cartao selecionado = cartoesUsuario.get(escolha);
            menuCartao(selecionado);
        }
    }

    // Menu de operações para um cartão específico
    private void menuCartao(Cartao cartao) {
        System.out.println("\n💳 === MEU CARTÃO ===");
        System.out.println("🏦 Conta: ID " + cartao.getIdConta());
        System.out.println("💳 Tipo: " + cartao.getTipoCartao());
        System.out.println("🔢 Número: " + cartao.getNumeroMascarado());
        System.out.println("📅 Validade: " + cartao.getValidade());
        System.out.println("💰 Limite: R$ " + cartao.getLimiteCredito());
        System.out.println("\n1 - 💸 Realizar Compra");
        System.out.println("2 - 🔄 Alterar Limite");
        System.out.println("3 - 🧪 Testar Limite (Debug)");
        System.out.println("0 - ⬅️  Voltar");
        System.out.print("Opção: ");
        
        int opcao = input.nextInt();
        input.nextLine();

        switch (opcao) {
            case 1 -> {
                System.out.print("💵 Valor da compra: R$ ");
                double valor = input.nextDouble();
                input.nextLine();
                
                if (valor <= 0) {
                    System.out.println("❌ Valor inválido! Deve ser maior que zero.");
                    return;
                }
                
                if (valor > cartao.getLimiteCredito()) {
                    System.out.println("❌ Compra negada! Valor excede o limite disponível.");
                    System.out.println("💰 Limite disponível: R$ " + cartao.getLimiteCredito());
                    return;
                }
                
                double novoLimite = cartao.getLimiteCredito() - valor;
                
                // Atualizar limite no banco de dados
                boolean limiteAtualizado = cartaoDAO.updateLimiteCredito(cartao.getIdCartao(), novoLimite);
                
                if (limiteAtualizado) {
                    // Atualizar limite no objeto em memória
                    cartao.setLimiteCredito(novoLimite);
                    
                    // Criar transação da compra
                    Transacao transacaoCompra = new Transacao(
                        cartao.getIdConta(), // Conta vinculada ao cartão
                        cartao.getIdCartao(), // ID do cartão usado
                        valor, // Valor da compra
                        "COMPRA_CARTAO", // Tipo da transação
                        "Compra realizada com cartão " + cartao.getNumeroMascarado() + " (" + cartao.getTipoCartao() + ")"
                    );
                    
                    // Salvar transação no banco
                    boolean transacaoSalva = transacaoDAO.insert(transacaoCompra);
                    
                    if (transacaoSalva) {
                        System.out.println("✅ Compra de R$ " + valor + " realizada com sucesso!");
                        System.out.println("💰 Limite restante: R$ " + novoLimite);
                        System.out.println("📋 Transação registrada no histórico!");
                    } else {
                        System.out.println("✅ Compra de R$ " + valor + " realizada com sucesso!");
                        System.out.println("💰 Limite restante: R$ " + novoLimite);
                        System.err.println("⚠️ Compra realizada, mas transação não foi registrada no histórico.");
                    }
                } else {
                    System.err.println("❌ Erro ao atualizar limite no banco de dados. Compra cancelada.");
                }
            }
            case 2 -> {
                System.out.print("💰 Novo limite: R$ ");
                double novoLimite = input.nextDouble();
                input.nextLine();
                
                if (novoLimite < 0) {
                    System.out.println("❌ Limite inválido! Deve ser maior ou igual a zero.");
                    return;
                }
                
                // Atualizar limite no banco de dados
                boolean limiteAtualizado = cartaoDAO.updateLimiteCredito(cartao.getIdCartao(), novoLimite);
                
                if (limiteAtualizado) {
                    // Atualizar limite no objeto em memória
                    cartao.setLimiteCredito(novoLimite);
                    System.out.println("✅ Limite do cartão " + cartao.getIdCartao() + " alterado para: R$ " + novoLimite);
                } else {
                    System.err.println("❌ Erro ao atualizar limite no banco de dados.");
                }
            }
            case 3 -> cartaoDAO.testarLimite(cartao.getIdCartao());
            case 0 -> System.out.println("⬅️  Voltando...");
            default -> System.out.println("❌ Opção inválida!");
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

    /**
     * Método público para testar conexão e inserção de cartões
     */
    public void testarConexaoEInsercao() {
        cartaoDAO.testarConexaoEInsercao();
    }

    /**
     * Método público para testar especificamente o limite
     */
    public void testarLimiteEspecifico() {
        cartaoDAO.testarLimiteEspecifico();
    }
}