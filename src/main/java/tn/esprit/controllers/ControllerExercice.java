package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Exercice;
import tn.esprit.services.ServiceExercice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControllerExercice {
    @FXML
    private GridPane gridPane;

    private ServiceExercice serviceExercice;

    public ControllerExercice() {
        serviceExercice = new ServiceExercice();
    }

    @FXML
    public void initialize() {
        if (gridPane != null) {
            try {
                afficherExercices();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("GridPane is not initialized.");
        }

    }

    /*
    private void afficherExercices() throws SQLException {
        List<Exercice> exercices = serviceExercice.afficher();
        if (gridPane != null) {
        int rowIndex = 0;
        for (Exercice exercice : exercices) {
            // Créez des éléments d'interface utilisateur pour afficher les détails de l'exercice (par exemple, Label)
            Label label = new Label(exercice.getNom() + " - " + exercice.getDescription());

            // Ajoutez les éléments d'interface utilisateur au GridPane
            gridPane.add(label, 0, rowIndex);

            // Vous pouvez ajuster la disposition selon vos besoins
            RowConstraints rowConstraints = new RowConstraints();
            gridPane.getRowConstraints().add(rowConstraints);

            rowIndex++;
        }
        } else {
            System.err.println("GridPane is not initialized.");
        }
    }
     */
/*
    private void afficherExercices() throws SQLException {
        List<Exercice> exercices = serviceExercice.afficher();
        if (gridPane != null) {
            int rowIndex = 0;
            for (Exercice exercice : exercices) {
                // Create a VBox for each exercise
                VBox exerciseBox = new VBox();
                exerciseBox.setSpacing(10); // Adjust the spacing as needed

                // Add the exercise details to the VBox
                Label nameLabel = new Label("Name: " + exercice.getNom());
                Label descriptionLabel = new Label("Description: " + exercice.getDescription());
                Label repetitionLabel = new Label("Repetition: " + exercice.getNbr_rep());

                // Load the image
                ImageView imageView = new ImageView(new Image(exercice.getImage()));
                imageView.setFitWidth(100); // Adjust the width as needed
                imageView.setFitHeight(100); // Adjust the height as needed

                // Add the labels and image to the VBox
                exerciseBox.getChildren().addAll(nameLabel, descriptionLabel, repetitionLabel, imageView);

                // Add the VBox to the GridPane
                gridPane.add(exerciseBox, 0, rowIndex);

                // Adjust the row constraints
                RowConstraints rowConstraints = new RowConstraints();
                gridPane.getRowConstraints().add(rowConstraints);

                rowIndex++;
            }
        } else {
            System.err.println("GridPane is not initialized.");
        }
    }

 */

    private void afficherExercices() throws SQLException {
        List<Exercice> exercices = serviceExercice.afficher();
        if (gridPane != null) {
            int rowIndex = 0;
            for (Exercice exercice : exercices) {
                try {
                    // Load the ExerciceItem.fxml file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/exercise_item.fxml"));
                    Parent exerciceItem = loader.load();

                    // Get the controller
                    ExerciceItemController exerciceItemController = loader.getController();

                    // Set the exercise data in the controller
                    exerciceItemController.setExerciceData(exercice);

                    // Add the exerciceItem to the GridPane
                    gridPane.add(exerciceItem, 0, rowIndex);

                    // Adjust the row constraints
                    RowConstraints rowConstraints = new RowConstraints();
                    gridPane.getRowConstraints().add(rowConstraints);

                    rowIndex++;
                } catch (IOException e) {
                    System.err.println("Error loading ExerciceItem.fxml: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("GridPane is not initialized.");
        }
    }
}
