package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import tn.esprit.entities.Exercice;
import tn.esprit.services.ServiceExercice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ExerciceController {

    @FXML
    private GridPane gridPane;



    private ServiceExercice serviceExercice;

    public ExerciceController() {
        serviceExercice = new ServiceExercice();
    }
    public void initialize() {
        try {
            List<Exercice> exercices = serviceExercice.afficher();
            displayExercises(exercices);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    private void displayExercises(List<Exercice> exercices) {
        // Ensure that gridPane is not null before using it
        if (gridPane != null) {
            int col = 0;
            int row = 0;

            for (Exercice exercice : exercices) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("exercise_item.fxml"));
                Parent exerciseItem;
                try {
                    exerciseItem = loader.load();
                } catch (IOException e) {
                    e.printStackTrace(); // Handle exception appropriately
                    continue;
                }

                ExerciceItemController itemController = loader.getController();
                itemController.setExerciceData(exercice);

                gridPane.add(exerciseItem, col, row);

                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
        }
    }
}
