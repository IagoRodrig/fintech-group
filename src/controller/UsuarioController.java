package controller;

import model.Usuario;

import java.util.Scanner;

public class UsuarioController {
    private Usuario usuario;

    public void criarUsuario(Scanner input) {
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("CPF: ");
        String cpf = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Telefone: ");
        String telefone = input.nextLine();

        usuario = new Usuario(nome, cpf, email, telefone);
        System.out.println("Usuário cadastrado com sucesso: " + usuario.getNome());
    }

    public Usuario getUsuario() {
        return usuario;
    }
}