package tn.esprit.entities;

public class Exercice {
    private int id ;
    private String nom;
    private  String description;
    private String image;
    private int nbr_rep;

    public Exercice() {
    }

    public Exercice(String nom, String description, String image, int nbr_rep) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.nbr_rep = nbr_rep;
    }

    public Exercice(int id, String nom, String description, String image, int nbr_rep) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.nbr_rep = nbr_rep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNbr_rep() {
        return nbr_rep;
    }

    public void setNbr_rep(int nbr_rep) {
        this.nbr_rep = nbr_rep;
    }


}
