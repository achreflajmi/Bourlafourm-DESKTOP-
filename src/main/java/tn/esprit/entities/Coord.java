package tn.esprit.entities;

public class Coord {

    private int id;
    private Sexe sexe;
    private int age;
    private double poids;
    private double taille;
    private double imc;

    @Override
    public String toString() {
        return "Coordonnees{" +
                "sexe=" + sexe +
                ", Age=" + age +
                ", Poids=" + poids +
                ", Taille=" + taille +
                ", Imc=" + imc +
                '}';
    }





    public Coord() {
    }

    public Coord(int i, double v, double parseDouble) {
    }

    public Coord(int id, Sexe sexe, int age, float poids, float taille, float imc) {
        this.id = id;
        this.sexe = sexe;
        this.age = age;
        this.poids = poids;
        this.taille = taille;
        this.imc = imc;
    }

    public Coord(int age, Sexe sexe, double poids, double taille, double imc) {
        this.age = age;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.imc = imc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }
}
