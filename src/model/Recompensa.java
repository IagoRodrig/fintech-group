package model;

public class Recompensa {
    private int idBonus;
    private String idUsuario;
    private String descricao;
    private double valor;
    private String status;

    public Recompensa() {
    }

    // Construtor completo
    public Recompensa(int idBonus, String idUsuario, String descricao, double valor, String status) {
        this.idBonus = idBonus;
        this.idUsuario = idUsuario;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
    }

    // Construtor sem ID (para insert)
    public Recompensa(String idUsuario, String descricao, double valor, String status) {
        this.idUsuario = idUsuario;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
    }

    public int getIdBonus() {
        return this.idBonus;
    }

    public void setIdBonus(int idBonus) {
        this.idBonus = idBonus;
    }

    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void exibirDetalhes() {
        System.out.println("Recompensa #" + this.idBonus);
        System.out.println("Usuário ID: " + this.idUsuario);
        System.out.println("Descrição: " + this.descricao);
        System.out.println("Valor: " + this.valor);
        System.out.println("Status: " + this.status);
        System.out.println("--------------------------------");
    }

    @Override
    public String toString() {
        return "Recompensa{" +
                "idBonus=" + idBonus +
                ", idUsuario=" + idUsuario +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", status='" + status + '\'' +
                '}';
    }
}
