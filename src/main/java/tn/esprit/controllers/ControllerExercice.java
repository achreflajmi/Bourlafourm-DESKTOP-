package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
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

    public void handleAddExButton(ActionEvent actionEvent) {
    }
}
