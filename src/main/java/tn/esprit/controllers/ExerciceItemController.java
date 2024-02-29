package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Exercice;

public class ExerciceItemController {

    @FXML
    private ImageView image;

    @FXML
    private Label nombreRepetition;

    @FXML
    private Label description;

    @FXML
    private Label nom;



    public void setExerciceData(Exercice exercice) {
        if (exercice != null) {
            nom.setText(exercice.getNom());
            description.setText(exercice.getDescription());
            nombreRepetition.setText(String.valueOf(exercice.getNbr_rep()));
            Image img = new Image(exercice.getImage());

            image.setImage(img);
        }
    }


}
