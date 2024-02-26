package tn.esprit.entities;

public class User {

    private int id_user;
    private String nom_user;
    private String prenom_user;
    private String email_user;
    private String password_user;
    private Double poids_sportif;
    private Double taille_sportif;
    private boolean disponibilte_conseiller;
    private Role_user role_user ;

    public User() {
    }


    public User( String nom_user, String prenom_user, String email_user, String password_user, boolean disponibilite_conseiller, Role_user rolee , double poids_sportif, double taille_sportif) {
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.role_user = rolee;
        this.disponibilte_conseiller=disponibilite_conseiller;
        this.poids_sportif = poids_sportif;
        this.taille_sportif = taille_sportif;
    }
    public User( String nom_user, String prenom_user, String email_user, String password_user, boolean disponibilite_conseiller, Role_user rolee ) {
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.role_user = rolee;
        this.disponibilte_conseiller=disponibilite_conseiller;

    }
/*
    public User(String nom_user, String prenom_user, String email_user, String password_user, boolean disponibilite_conseiller, Role_user rolee, double poids_sportif, double taille_sportif) {
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.role_user = rolee;
        if (rolee == Role_user.Sportif) {
            this.disponibilte_conseiller = disponibilite_conseiller;
            this.poids_sportif = poids_sportif;
            this.taille_sportif = taille_sportif;
        } else {
            this.disponibilte_conseiller = disponibilite_conseiller;
            this.poids_sportif = 0.0; // Valeur par défaut pour Coach et Nutritionist
            this.taille_sportif = 0.0; // Valeur par défaut pour Coach et Nutritionist
        }
    }

    public User(String nom_user, String prenom_user, String email_user, String password_user, boolean disponibilite_conseiller, Role_user rolee) {
        this(nom_user, prenom_user, email_user, password_user, disponibilite_conseiller, rolee, 0.0, 0.0);
    }
*/

    public Double getPoids_sportif() {
        return poids_sportif;
    }

    public void setPoids_sportif(Double poids_sportif) {
        this.poids_sportif = poids_sportif;
    }

    public Double getTaille_sportif() {
        return taille_sportif;
    }

    public void setTaille_sportif(Double taille_sportif) {
        this.taille_sportif = taille_sportif;
    }

    public Boolean getDisponibilte_conseiller() {
        return disponibilte_conseiller;
    }

    public void setDisponibilte_conseiller(Boolean disponibilte_conseiller) {
        this.disponibilte_conseiller = disponibilte_conseiller;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getPrenom_user() {
        return prenom_user;
    }

    public void setPrenom_user(String prenom_user) {
        this.prenom_user = prenom_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public Role_user getRole_user() {
        return role_user;
    }

    public void setRole_user(Role_user role_user) {
        this.role_user = role_user;
    }

    @Override
    public String toString() {
        return "User{" +

                ", nom_user='" + nom_user + '\'' +
                ", prenom_user='" + prenom_user + '\'' +
                ", email_user='" + email_user + '\'' +
                ", password_user='" + password_user + '\'' +
                ", poids_sportif=" + poids_sportif +
                ", taille_sportif=" + taille_sportif +
                ", disponibilte_conseiller=" + disponibilte_conseiller +
                ", role_user=" + role_user +
                '}';
    }
}
