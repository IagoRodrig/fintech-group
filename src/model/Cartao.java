package model;

public class Cartao {
    private String numero;
    private String validade;
    private String bandeira;
    private double limite;

    // Construtor
    public Cartao(String numero, String validade, String bandeira, double limite) {
        this.numero = numero;
        this.validade = validade;
        this.bandeira = bandeira;
        this.limite = limite;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }
}