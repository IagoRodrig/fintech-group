package model;

public class Conta {
    private int idConta;
    private String idUsuario;
    private double saldo;
    private String tipoConta;
    private double valor;
    private String data;

    public Conta() {
    }

    // Construtor completo
    public Conta(int idConta, String idUsuario, double saldo, String tipoConta, double valor, String data) {
        this.idConta = idConta;
        this.idUsuario = idUsuario;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
        this.valor = valor;
        this.data = data;
    }

    // Construtor sem ID (para insert)
    public Conta(String idUsuario, double saldo, String tipoConta, double valor, String data) {
        this.idUsuario = idUsuario;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
        this.valor = valor;
        this.data = data;
    }

    // Getters e Setters
    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "idConta=" + idConta +
                ", idUsuario=" + idUsuario +
                ", saldo=" + saldo +
                ", tipoConta='" + tipoConta + '\'' +
                ", valor=" + valor +
                ", data='" + data + '\'' +
                '}';
    }
}
