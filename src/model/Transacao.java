package model;

public class Transacao {
    private int idTransacao;
    private int idContaOrigem;
    private int idContaDestino;
    private double valor;
    private String data;
    private Integer idCartao; // Nullable - só preenchido para transações de cartão
    private String tipoTransacao; // TRANSFERENCIA, COMPRA_CARTAO, SAQUE, DEPOSITO
    private String descricao; // Descrição detalhada da transação

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

    // Construtor completo com novos campos
    public Transacao(int idTransacao, int idContaOrigem, int idContaDestino, double valor, String data, 
                     Integer idCartao, String tipoTransacao, String descricao) {
        this.idTransacao = idTransacao;
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.valor = valor;
        this.data = data;
        this.idCartao = idCartao;
        this.tipoTransacao = tipoTransacao;
        this.descricao = descricao;
    }

    // Construtor sem ID (para insert) com novos campos
    public Transacao(int idContaOrigem, int idContaDestino, double valor, String data, 
                     Integer idCartao, String tipoTransacao, String descricao) {
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.valor = valor;
        this.data = data;
        this.idCartao = idCartao;
        this.tipoTransacao = tipoTransacao;
        this.descricao = descricao;
    }

    // Construtor para transações de cartão
    public Transacao(int idContaOrigem, int idCartao, double valor, String tipoTransacao, String descricao) {
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = 0; // Transações de cartão não têm conta destino (usar 0 como padrão)
        this.valor = valor;
        this.data = null; // Será preenchida automaticamente pelo banco
        this.idCartao = idCartao;
        this.tipoTransacao = tipoTransacao;
        this.descricao = descricao;
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

    public Integer getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Integer idCartao) {
        this.idCartao = idCartao;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "idTransacao=" + idTransacao +
                ", idContaOrigem=" + idContaOrigem +
                ", idContaDestino=" + idContaDestino +
                ", valor=" + valor +
                ", data='" + data + '\'' +
                ", idCartao=" + idCartao +
                ", tipoTransacao='" + tipoTransacao + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
