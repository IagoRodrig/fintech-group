package model;

public class Bonus extends Recompensa {
    private String tipoBonus;

    public Bonus(int idBonus, String idUsuario, String descricao, double valor, String status, String tipoBonus) {
        super(idBonus, idUsuario, descricao, valor, status);
        this.tipoBonus = tipoBonus;
    }

    public String getTipoBonus() {
        return this.tipoBonus;
    }

    public void setTipoBonus(String tipoBonus) {
        this.tipoBonus = tipoBonus;
    }

    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Tipo de bônus: " + this.tipoBonus);
        System.out.println("================================");
    }
}
