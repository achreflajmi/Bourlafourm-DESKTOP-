package tn.esprit.entities;

public class Kine {
    private  int id_kine;
    private  String nomcab;
    private  String nomkine;
    private  String adresse;

    public Kine() {
    }

    public Kine(int id_kine, String nomcab, String nomkine, String adresse) {
        this.id_kine = id_kine;
        this.nomcab = nomcab;
        this.nomkine = nomkine;
        this.adresse = adresse;
    }

    public Kine(String nomcab, String nomkine, String adresse) {
        this.nomcab = nomcab;
        this.nomkine = nomkine;
        this.adresse = adresse;
    }

    public void setId_kine(int id_kine) {
        this.id_kine = id_kine;
    }

    public void setNomcab(String nomcab) {
        this.nomcab = nomcab;
    }

    public void setNomkine(String nomkine) {
        this.nomkine = nomkine;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId_kine() {
        return id_kine;
    }

    public String getNomcab() {
        return nomcab;
    }

    public String getNomkine() {
        return nomkine;
    }

    public String getAdresse() {
        return adresse;
    }

    @Override
    public String toString() {
        return "kine{" +
                "id_kine=" + id_kine +
                ", nomcab='" + nomcab + '\'' +
                ", nomkine='" + nomkine + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
