package controller;
import model.Transacao;
import model.Conta;
import dao.TransacaoDAO;
import dao.ContaDAO;

import java.util.List;
import java.util.Scanner;

public class TransacaoController {
    private TransacaoDAO transacaoDAO;
    private ContaDAO contaDAO;
    private LoginController loginController;
    private int op;
    private int valor, numero, cf;
    private String data;
    private Transacao t;
    private Conta conta;
    Scanner sc = new Scanner(System.in);

    public TransacaoController() {
        this.transacaoDAO = new TransacaoDAO();
        this.contaDAO = new ContaDAO();
    }
    
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    private void checagemTransacao () {
        if (cf == 1) {
            // Verificar se há uma conta definida ou buscar uma conta do usuário
            if (conta == null) {
                System.out.println("❌ Nenhuma conta vinculada para realizar a transação.");
                System.out.println("💡 Dica: Crie uma conta primeiro (opção 2) ou selecione uma conta existente.");
                return;
            }
            
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
            
            // Buscar conta destino para atualizar seu saldo também
            Conta contaDestino = contaDAO.findById(t.getIdContaDestino());
            if (contaDestino == null) {
                System.err.println("❌ Conta destino não encontrada. Transação cancelada.");
                return;
            }
            
            double novoSaldoDestino = contaDestino.getSaldo() + t.getValor();
            
            // Atualizar saldo da conta origem no banco de dados
            boolean saldoOrigemAtualizado = contaDAO.updateSaldo(conta.getIdConta(), novoSaldo);
            
            if (saldoOrigemAtualizado) {
                // Atualizar saldo da conta destino no banco de dados
                boolean saldoDestinoAtualizado = contaDAO.updateSaldo(contaDestino.getIdConta(), novoSaldoDestino);
                
                if (saldoDestinoAtualizado) {
                    // Atualizar saldos nos objetos em memória
                    conta.setSaldo(novoSaldo);
                    contaDestino.setSaldo(novoSaldoDestino);
                    
                    // Salvar transação no banco de dados
                    boolean transacaoSalva = transacaoDAO.insert(t);
                    
                    if (transacaoSalva) {
                        System.out.println("✅ Transação realizada com sucesso!");
                        System.out.println("💰 Seu novo saldo: R$ " + novoSaldo);
                        System.out.println("🏦 Conta destino atualizada: R$ " + novoSaldoDestino);
                        t.exibirTransacao();
                    } else {
                        System.err.println("❌ Erro ao salvar transação. Revertendo operação...");
                        // Reverter saldos no banco se a transação não foi salva
                        contaDAO.updateSaldo(conta.getIdConta(), conta.getSaldo() + t.getValor());
                        contaDAO.updateSaldo(contaDestino.getIdConta(), contaDestino.getSaldo() - t.getValor());
                    }
                } else {
                    System.err.println("❌ Erro ao atualizar saldo da conta destino. Revertendo operação...");
                    // Reverter saldo da conta origem
                    contaDAO.updateSaldo(conta.getIdConta(), conta.getSaldo() + t.getValor());
                }
            } else {
                System.err.println("❌ Erro ao atualizar saldo da conta origem. Transação cancelada.");
            }
        }
    }

    private void transacao () {
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para realizar transações!");
            System.out.println("💡 Use o menu de login primeiro.");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Usuário logado: " + usuarioLogado);
        
        // Verificar se há conta vinculada, se não, usar conta do usuário logado
        if (conta == null) {
            System.out.println("🔍 Buscando conta do usuário logado...");
            
            // Buscar conta origem pelo usuário logado
            int idContaOrigem = buscarIdContaPorUsuario(usuarioLogado);
            if (idContaOrigem == -1) {
                System.out.println("❌ Usuário logado não possui conta cadastrada!");
                System.out.println("💡 Crie uma conta primeiro (opção 2 do menu principal).");
                return;
            }
            
            // Buscar a conta completa
            conta = contaDAO.findById(idContaOrigem);
            if (conta != null) {
                System.out.println("✅ Conta origem encontrada!");
                System.out.println("🏦 ID da Conta: " + conta.getIdConta());
                System.out.println("👤 Usuário: " + usuarioLogado);
                System.out.println("💰 Saldo: R$ " + conta.getSaldo());
            }
        }

        System.out.println("\n📋 Dados da Transação:");
        System.out.println("🏦 Conta Origem: ID " + conta.getIdConta() + " | Saldo: R$ " + conta.getSaldo());
        
        System.out.print("\n💵 Valor da transação: R$ ");
        valor = sc.nextInt();
        sc.nextLine();

        System.out.print("👤 Nome de usuário da conta destino: ");
        String nomeUsuarioDestino = sc.nextLine();
        
        // Verificar se não está tentando transferir para si mesmo
        if (nomeUsuarioDestino.equals(usuarioLogado)) {
            System.out.println("❌ Não é possível transferir para sua própria conta!");
            System.out.println("💡 Digite o nome de usuário de outra pessoa.");
            return;
        }

        System.out.print("📅 Data da transação: ");
        data = sc.nextLine();

        // Buscar ID da conta destino pelo nome de usuário
        int idContaDestino = buscarIdContaPorUsuario(nomeUsuarioDestino);
        if (idContaDestino == -1) {
            System.out.println("❌ Usuário destino não encontrado!");
            System.out.println("💡 Verifique se o usuário existe e tem uma conta cadastrada.");
            return;
        }

        // Mostrar resumo da transação
        System.out.println("\n📋 RESUMO DA TRANSAÇÃO:");
        System.out.println("🏦 Origem: Conta ID " + conta.getIdConta() + " (Usuário: " + usuarioLogado + ")");
        System.out.println("🏦 Destino: Conta ID " + idContaDestino + " (Usuário: " + nomeUsuarioDestino + ")");
        System.out.println("💵 Valor: R$ " + valor);
        System.out.println("📅 Data: " + data);
        
        System.out.println("\n1 - Confirmar Transação\n2 - Cancelar");
        cf = sc.nextInt();
        sc.nextLine();

        numero = idContaDestino; // Usar o ID encontrado
        checagemTransacao();
    }

    public void transacaoMenu () {
       do { 
           // Mostrar informações do usuário logado
           if (loginController != null && loginController.isLogado()) {
               System.out.println("\n👤 Usuário Logado: " + loginController.getNomeUsuarioLogado());
           } else {
               System.out.println("\n⚠️  Nenhum usuário logado");
               System.out.println("💡 É necessário fazer login para realizar transações");
           }
           
           // Mostrar informações da conta atual
           if (conta != null) {
               System.out.println("🏦 Conta Atual: ID " + conta.getIdConta() + 
                                " | Tipo: " + conta.getTipoConta() + 
                                " | Saldo: R$ " + conta.getSaldo());
           } else {
               System.out.println("🏦 Conta: Será buscada automaticamente pelo usuário logado");
           }
           
           System.out.println("\n=== MENU DE TRANSAÇÕES ===");
           System.out.println("1 - 💸 Nova Transação");
           System.out.println("2 - 📋 Última Transação");
           System.out.println("3 - 📊 Listar Transações");
           System.out.println("4 - ⬅️  Voltar");
           System.out.print("Opção: ");
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
        // Verificar se há usuário logado
        if (loginController == null || !loginController.isLogado()) {
            System.out.println("❌ É necessário fazer login para visualizar transações!");
            System.out.println("💡 Use o menu de login primeiro.");
            return;
        }
        
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        System.out.println("👤 Usuário logado: " + usuarioLogado);
        
        // Buscar apenas transações do usuário logado
        List<Transacao> transacoes = transacaoDAO.getByUsuario(usuarioLogado);
        
        if (transacoes.isEmpty()) {
            System.out.println("📋 Nenhuma transação encontrada para este usuário!");
        } else {
            System.out.println("\n=== SUAS TRANSAÇÕES ===");
            System.out.println("🔒 Mostrando apenas suas transações (origem e destino)");
            for (Transacao transacao : transacoes) {
                exibirTransacaoSegura(transacao);
            }
        }
    }

    /**
     * Exibe dados da transação sem informações sensíveis
     */
    private void exibirTransacaoSegura(Transacao transacao) {
        String usuarioLogado = loginController.getNomeUsuarioLogado();
        
        // Determinar se é transação de entrada ou saída
        boolean isSaida = conta != null && transacao.getIdContaOrigem() == conta.getIdConta();
        
        System.out.println("💸 Transação ID: " + transacao.getIdTransacao());
        
        if (isSaida) {
            System.out.println("📤 Tipo: Saída (Você enviou)");
            System.out.println("💰 Valor: -R$ " + transacao.getValor());
        } else {
            System.out.println("📥 Tipo: Entrada (Você recebeu)");
            System.out.println("💰 Valor: +R$ " + transacao.getValor());
        }
        
        System.out.println("📅 Data: " + transacao.getData());
        System.out.println("🏦 Conta Origem: " + transacao.getIdContaOrigem());
        System.out.println("🏦 Conta Destino: " + transacao.getIdContaDestino());
        System.out.println("─────────────────────────────────────");
    }

    /**
     * Busca o ID da conta pelo nome de usuário dinamicamente
     */
    private int buscarIdContaPorUsuario(String nomeUsuario) {
        return contaDAO.buscarIdContaPorUsuario(nomeUsuario);
    }
}
