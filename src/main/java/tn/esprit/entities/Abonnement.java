package tn.esprit.entities;

public class Abonnement {
    private int id_ab;
    private String nomprenom ;

    private Type type;

    private Sexe sexe;

    private int age;

    private int id_kine;

    public Abonnement() {
    }

    public Abonnement(int id_ab, String nomprenom, Type type, Sexe sexe, int age, int id_kine) {
        this.id_ab = id_ab;
        this.nomprenom = nomprenom;
        this.type = type;
        this.sexe = sexe;
        this.age = age;
        this.id_kine = id_kine;
    }

    public Abonnement(String nomprenom, Type type, Sexe sexe, int age, int id_kine) {
        this.nomprenom = nomprenom;
        this.type = type;
        this.sexe = sexe;
        this.age = age;
        this.id_kine = id_kine;
    }

    public int getId_ab() {
        return id_ab;
    }

    public  String getNomprenom() {
        return nomprenom;
    }

    public Type getType() {
        return type;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public int getAge() {
        return age;
    }

    public int getId_kine() {
        return id_kine;
    }

    public void setId_ab(int id_ab) {
        this.id_ab = id_ab;
    }

    public void setNomprenom(String nomprenom) {
        this.nomprenom = nomprenom;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId_kine(int id_kine) {
        this.id_kine = id_kine;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "id_ab=" + id_ab +
                ", nomprenom='" + nomprenom + '\'' +
                ", type=" + type +
                ", genre=" + sexe +
                ", age=" + age +
                ", id_kine=" + id_kine +
                '}';
    }
}
