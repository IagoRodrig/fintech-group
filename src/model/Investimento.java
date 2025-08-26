package model;

public class Investimento {
    private String tipo;
    private double valorAplicado;
    private String dataAplicacao;

    //Construtor
    public Investimento(String tipo, double valorAplicado, String dataAplicacao) {
        this.tipo = tipo;
        this.valorAplicado = valorAplicado;
        this.dataAplicacao = dataAplicacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValorAplicado() {
        return valorAplicado;
    }

    public void setValorAplicado(double valorAplicado) {
        this.valorAplicado = valorAplicado;
    }

    public String getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(String dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }
}