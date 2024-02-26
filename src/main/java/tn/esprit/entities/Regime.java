package tn.esprit.entities;

public class Regime {
    private int id;
    private String petitdej;
    private String colpdej;
    private String dej;
    private String coldej;
    private String diner;

    @Override
    public String toString() {
        return "Regime{" +
                "Petit déjeuner='" + petitdej + '\'' +
                ", Collation petit déjeuner='" + colpdej + '\'' +
                ", Déjeuner='" + dej + '\'' +
                ", Collation déjeuner ='" + coldej + '\'' +
                ", Diner='" + diner + '\'' +
                '}';
    }




    public Regime() {
    }

    public Regime(int id, String petitdej, String colpdej, String dej, String coldej, String diner) {
        this.id = id;
        this.petitdej = petitdej;
        this.colpdej = colpdej;
        this.dej = dej;
        this.coldej = coldej;
        this.diner = diner;
    }

    public Regime(String petitdej, String colpdej, String dej, String coldej, String diner) {
        this.petitdej = petitdej;
        this.colpdej = colpdej;
        this.dej = dej;
        this.coldej = coldej;
        this.diner = diner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPetitdej() {
        return petitdej;
    }

    public void setPetitdej(String petitdej) {
        this.petitdej = petitdej;
    }

    public String getColpdej() {
        return colpdej;
    }

    public void setColpdej(String colpdej) {
        this.colpdej = colpdej;
    }

    public String getDej() {
        return dej;
    }

    public void setDej(String dej) {
        this.dej = dej;
    }

    public String getColdej() {
        return coldej;
    }

    public void setColdej(String coldej) {
        this.coldej = coldej;
    }

    public String getDiner() {
        return diner;
    }

    public void setDiner(String diner) {
        this.diner = diner;
    }
}
