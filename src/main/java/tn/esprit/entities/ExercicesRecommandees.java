package tn.esprit.entities;

import tn.esprit.entities.User;

import javax.persistence.*;

@Entity
@Table(name = "exercices_recommandees")
public class ExercicesRecommandees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercice_recommandee")
    private int idExerciceRecommandee;

    @ManyToOne
    @JoinColumn(name = "id_coach") // Assuming this is the foreign key to the coach in the User entity
    private User coach;

    @ManyToOne
    @JoinColumn(name = "id_sportif") // Assuming this is the foreign key to the athlete in the User entity
    private User athlete;

    public int getIdExerciceRecommandee() {
        return idExerciceRecommandee;
    }

    public void setIdExerciceRecommandee(int idExerciceRecommandee) {
        this.idExerciceRecommandee = idExerciceRecommandee;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public User getAthlete() {
        return athlete;
    }

    public void setAthlete(User athlete) {
        this.athlete = athlete;
    }
}
