package tn.esprit.entities;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id_user;
    private String nom_user;
    private  String prenom_user;
    private String email_user;
    private String password_user;
    private Role role_user;
    private int disponibilite_conseiller;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
    private List<ExercicesRecommandees> recommendedExercises;


    @Override
    public String toString() {
        return "User{" +
                "idUser=" + id_user +
                ", nom_user='" + nom_user + '\'' +
                ", prenom_user='" + prenom_user + '\'' +
                ", email_user='" + email_user + '\'' +
                ", password_user='" + password_user + '\'' +
                ", role_user=" + role_user +
                ", disponibilite_conseiller=" + disponibilite_conseiller +
                ", recommendedExercises=" + recommendedExercises +
                '}';
    }

    public User() {
    }

    public User(int id_user, String nom_user, String prenom_user, String email_user, String password_user, Role role_user, int disponibilite_conseiller) {
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.role_user = role_user;
        this.disponibilite_conseiller = disponibilite_conseiller;
    }

    public User(String nom_user, String prenom_user, String email_user, String password_user, Role role_user, int disponibilite_conseiller) {
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.role_user = role_user;
        this.disponibilite_conseiller = disponibilite_conseiller;
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

    public Role getRole_user() {
        return role_user;
    }

    public void setRole_user(Role role_user) {
        this.role_user = role_user;
    }

    public int getDisponibilite_conseiller() {
        return disponibilite_conseiller;
    }

    public void setDisponibilite_conseiller(int disponibilite_conseiller) {
        this.disponibilite_conseiller = disponibilite_conseiller;
    }
}
