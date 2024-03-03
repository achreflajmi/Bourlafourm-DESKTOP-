package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Exercice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExerciceItemController {

    @FXML
    private ImageView imageview;

    @FXML
    private Label nombreRepetition;

    @FXML
    private Label description;

    @FXML
    private Label nom;
    @FXML
    private CheckBox checkBox;


    @FXML
    private void handleAddExButton() {
        // Implement the logic to handle the button click
        if (checkBox.isSelected()) {
            // Perform the action to save the selected exercise to another database table
            saveSelectedExerciceToDatabase();
        }
    }

    private void saveSelectedExerciceToDatabase() {
    }

    public void setExerciceData(Exercice exercice) {
        if (exercice != null) {
            nom.setText(exercice.getNom());
            description.setText(exercice.getDescription());
            nombreRepetition.setText(String.valueOf(exercice.getNbr_rep()));

            try {
                Image image = new Image(exercice.getImage());

                imageview.setImage(image);
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }



}
