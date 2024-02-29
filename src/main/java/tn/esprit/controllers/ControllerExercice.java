package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import tn.esprit.entities.Exercice;
import tn.esprit.services.ServiceExercice;

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
        try {
            afficherExercices();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherExercices() throws SQLException {
        List<Exercice> exercices = serviceExercice.afficher();

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
    }
}
