package model;

public class Investimento {
    private int idInvestimento;
    private int idUsuario;
    private String tipo;
    private double valorInvestido;
    private String dataAplicacao;

    public Investimento() {
    }

    // Construtor completo
    public Investimento(int idInvestimento, int idUsuario, String tipo, double valorInvestido, String dataAplicacao) {
        this.idInvestimento = idInvestimento;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.valorInvestido = valorInvestido;
        this.dataAplicacao = dataAplicacao;
    }

    // Construtor sem ID (para insert)
    public Investimento(int idUsuario, String tipo, double valorInvestido, String dataAplicacao) {
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.valorInvestido = valorInvestido;
        this.dataAplicacao = dataAplicacao;
    }

    // Getters e Setters
    public int getIdInvestimento() {
        return idInvestimento;
    }

    public void setIdInvestimento(int idInvestimento) {
        this.idInvestimento = idInvestimento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(double valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    public String getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(String dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    @Override
    public String toString() {
        return "Investimento{" +
                "idInvestimento=" + idInvestimento +
                ", idUsuario=" + idUsuario +
                ", tipo='" + tipo + '\'' +
                ", valorInvestido=" + valorInvestido +
                ", dataAplicacao='" + dataAplicacao + '\'' +
                '}';
    }
}