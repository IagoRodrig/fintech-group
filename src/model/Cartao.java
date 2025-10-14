package model;

public class Cartao {
    private int idCartao;
    private int idConta;
    private String tipoCartao;
    private String numeroMascarado;
    private String validade;
    private double limiteCredito;

//    public Cartao() {
//    }

    // Construtor completo
//    public Cartao(int idCartao, int idConta, String tipoCartao, String numeroMascarado, String validade, double limiteCredito) {
//        this.idCartao = idCartao;
//        this.idConta = idConta;
//        this.tipoCartao = tipoCartao;
//        this.numeroMascarado = numeroMascarado;
//        this.validade = validade;
//        this.limiteCredito = limiteCredito;
//    }

    // Construtor completo
    public Cartao(int idCartao, int idConta, String tipoCartao, String numeroMascarado, String validade, double limiteCredito) {
        this.idCartao = idCartao;
        this.idConta = idConta;
        this.tipoCartao = tipoCartao;
        this.numeroMascarado = numeroMascarado;
        this.validade = validade;
        this.limiteCredito = limiteCredito;
    }

    // Construtor sem ID (para insert)
    public Cartao(int idConta, String tipoCartao, String numeroMascarado, String validade, double limiteCredito) {
        this.idConta = idConta;
        this.tipoCartao = tipoCartao;
        this.numeroMascarado = numeroMascarado;
        this.validade = validade;
        this.limiteCredito = limiteCredito;
    }

    // Getters e Setters
    public int getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(int idCartao) {
        this.idCartao = idCartao;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public String getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(String tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    public String getNumeroMascarado() {
        return numeroMascarado;
    }

    public void setNumeroMascarado(String numeroMascarado) {
        this.numeroMascarado = numeroMascarado;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "idCartao=" + idCartao +
                ", idConta=" + idConta +
                ", tipoCartao='" + tipoCartao + '\'' +
                ", numeroMascarado='" + numeroMascarado + '\'' +
                ", validade='" + validade + '\'' +
                ", limiteCredito=" + limiteCredito +
                '}';
    }
}