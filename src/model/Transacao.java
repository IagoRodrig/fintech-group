package model;

public class Transacao {
    private int idTransacao;
    private int idContaOrigem;
    private int idContaDestino;
    private double valor;
    private String data;

    public Transacao() {
    }

    // Construtor completo
    public Transacao(int idTransacao, int idContaOrigem, int idContaDestino, double valor, String data) {
        this.idTransacao = idTransacao;
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.valor = valor;
        this.data = data;
    }

    // Construtor sem ID (para insert)
    public Transacao(int idContaOrigem, int idContaDestino, double valor, String data) {
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.valor = valor;
        this.data = data;
    }

    public void exibirTransacao() {
        System.out.println("=== Transação ===");
        System.out.println("Valor: R$ " + valor);
        System.out.println("Data: " + data);
        System.out.println("=================");
    }

    // Getters e Setters
    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public int getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(int idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public int getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(int idContaDestino) {
        this.idContaDestino = idContaDestino;
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
        return "Transacao{" +
                "idTransacao=" + idTransacao +
                ", idContaOrigem=" + idContaOrigem +
                ", idContaDestino=" + idContaDestino +
                ", valor=" + valor +
                ", data='" + data + '\'' +
                '}';
    }
}
