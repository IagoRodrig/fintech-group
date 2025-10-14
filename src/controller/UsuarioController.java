package controller;

import model.Usuario;
import dao.UsuarioDAO;

import java.util.List;
import java.util.Scanner;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void criarUsuario(Scanner input) {
        System.out.print("Nome de usuário: ");
        String nomeUsuario = input.nextLine();
        System.out.print("Nome completo: ");
        String nomeCompleto = input.nextLine();
        System.out.print("Telefone: ");
        String telefone = input.nextLine();
        System.out.print("Senha: ");
        String senha = input.nextLine();

        Usuario usuario = new Usuario(nomeUsuario, nomeCompleto, telefone, senha);
        
        if (usuarioDAO.insert(usuario)) {
            System.out.println("Usuário cadastrado com sucesso: " + usuario.getNomeCompleto());
        } else {
            System.out.println("Erro ao cadastrar usuário!");
        }
    }

    public void listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.getAll();
        
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado!");
        } else {
            System.out.println("\n=== LISTA DE USUÁRIOS ===");
            for (Usuario usuario : usuarios) {
                exibirUsuarioSeguro(usuario);
            }
        }
    }

    public void buscarUsuario(Scanner input) {
        System.out.print("Digite o nome de usuário: ");
        String nomeUsuario = input.nextLine();
        
        Usuario usuario = usuarioDAO.findByNomeUsuario(nomeUsuario);
        
        if (usuario != null) {
            System.out.println("\n=== USUÁRIO ENCONTRADO ===");
            exibirUsuarioSeguro(usuario);
        } else {
            System.out.println("Usuário não encontrado!");
        }
    }

    public void operacoesUsuario(Scanner input) {
        int opcao;
        do {
            System.out.println("\n=== OPERAÇÕES DE USUÁRIO ===");
            System.out.println("1 - Criar Usuário");
            System.out.println("2 - Listar Usuários");
            System.out.println("3 - Buscar Usuário");
            System.out.println("4 - Voltar");
            System.out.print("Opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1 -> criarUsuario(input);
                case 2 -> listarUsuarios();
                case 3 -> buscarUsuario(input);
                case 4 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }

    /**
     * Exibe dados do usuário sem informações sensíveis
     */
    private void exibirUsuarioSeguro(Usuario usuario) {
        System.out.println("👤 Nome: " + usuario.getNomeCompleto());
        System.out.println("📧 Usuário: " + usuario.getNomeUsuario());
        System.out.println("📱 Telefone: " + usuario.getTelefone());
        System.out.println("📅 Data de Criação: " + usuario.getDataCriacao());
        System.out.println("─────────────────────────────────────");
    }

    public Usuario getUsuario() {
        // Retorna o primeiro usuário da lista para compatibilidade
        List<Usuario> usuarios = usuarioDAO.getAll();
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }
}