package model;

public class Transacao {
    private int numero;
    private String agencia;
    private int valor;
    private String data;
    private String status;

    public Transacao(int valor, String data, int numero, String agencia) {
        this.valor = valor;
        this.data = data;
        this.numero = numero;
        this.agencia = agencia;
        this.status = "Pendente";
    }


    public void exibirTransacao() {
        System.out.println("=== Última Transação ===");
        System.out.println("Conta: " + numero + " / Agência: " + agencia);
        System.out.println("Valor: " + valor);
        System.out.println("Data: " + data);
        System.out.println("Status: " + status);
        System.out.println("=======================");
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
