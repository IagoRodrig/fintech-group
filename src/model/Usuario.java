package model;

import java.util.Date;

public class Usuario {
    private String idUsuario;
    private String nomeUsuario;
    private String nomeCompleto;
    private String telefone;
    private String senha;
    private Date dataCriacao;

    // Construtor vazio
    public Usuario() {
    }

    // Construtor completo
    public Usuario(String idUsuario, String nomeUsuario, String nomeCompleto, String telefone, String senha, Date dataCriacao) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.nomeCompleto = nomeCompleto;
        this.telefone = telefone;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
    }

    // Construtor sem ID (para insert)
    public Usuario(String nomeUsuario, String nomeCompleto, String telefone, String senha) {
        this.nomeUsuario = nomeUsuario;
        this.nomeCompleto = nomeCompleto;
        this.telefone = telefone;
        this.senha = senha;
        this.dataCriacao = new Date(); // Define a data atual automaticamente
    }

    // Getters e Setters
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}