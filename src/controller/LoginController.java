package controller;

import model.Usuario;
import dao.UsuarioDAO;
import java.util.Scanner;

/**
 * Controller responsável pelo sistema de autenticação e login
 */
public class LoginController {
    
    private UsuarioDAO usuarioDAO;
    private Usuario usuarioLogado;
    private Scanner scanner;
    
    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Realiza o login do usuário
     * @return true se login bem-sucedido, false caso contrário
     */
    public boolean fazerLogin() {
        System.out.println("\n🔐 === SISTEMA DE LOGIN ===");
        
        System.out.print("👤 Nome de usuário: ");
        String nomeUsuario = scanner.nextLine();
        
        System.out.print("🔑 Senha: ");
        String senha = scanner.nextLine();
        
        // Buscar usuário no banco
        Usuario usuario = usuarioDAO.findByNomeUsuario(nomeUsuario);
        
        if (usuario != null && usuario.getSenha().equals(senha)) {
            this.usuarioLogado = usuario;
            System.out.println("✅ Login realizado com sucesso!");
            System.out.println("👋 Bem-vindo, " + usuario.getNomeCompleto() + "!");
            return true;
        } else {
            System.out.println("❌ Usuário ou senha incorretos!");
            return false;
        }
    }
    
    /**
     * Realiza logout do usuário
     */
    public void fazerLogout() {
        if (usuarioLogado != null) {
            System.out.println("👋 Até logo, " + usuarioLogado.getNomeCompleto() + "!");
            this.usuarioLogado = null;
        }
    }
    
    /**
     * Verifica se há usuário logado
     * @return true se há usuário logado, false caso contrário
     */
    public boolean isLogado() {
        return usuarioLogado != null;
    }
    
    /**
     * Retorna o usuário logado
     * @return Usuario logado ou null se não houver
     */
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    /**
     * Retorna o nome de usuário logado
     * @return Nome de usuário ou null se não houver
     */
    public String getNomeUsuarioLogado() {
        return usuarioLogado != null ? usuarioLogado.getNomeUsuario() : null;
    }
    
    /**
     * Exibe informações do usuário logado
     */
    public void exibirUsuarioLogado() {
        if (usuarioLogado != null) {
            System.out.println("\n👤 === USUÁRIO LOGADO ===");
            System.out.println("👤 Nome: " + usuarioLogado.getNomeCompleto());
            System.out.println("📧 Usuário: " + usuarioLogado.getNomeUsuario());
            System.out.println("📱 Telefone: " + usuarioLogado.getTelefone());
            System.out.println("📅 Data de Criação: " + usuarioLogado.getDataCriacao());
        } else {
            System.out.println("⚠️  Nenhum usuário logado!");
        }
    }
    
    /**
     * Menu de login com opções
     */
    public void menuLogin() {
        int opcao;
        do {
            System.out.println("\n🔐 === MENU DE LOGIN ===");
            System.out.println("1 - 🔑 Fazer Login");
            System.out.println("2 - 👤 Ver Usuário Logado");
            System.out.println("3 - 🚪 Fazer Logout");
            System.out.println("4 - ⬅️  Voltar");
            System.out.print("Opção: ");
            
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
                
                switch (opcao) {
                    case 1 -> {
                        if (fazerLogin()) {
                            System.out.println("✅ Login realizado com sucesso!");
                        }
                    }
                    case 2 -> exibirUsuarioLogado();
                    case 3 -> fazerLogout();
                    case 4 -> System.out.println("⬅️  Voltando...");
                    default -> System.out.println("❌ Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro na entrada. Tente novamente.");
                scanner.nextLine(); // Limpar buffer
                opcao = 0;
            }
        } while (opcao != 4);
    }
}
