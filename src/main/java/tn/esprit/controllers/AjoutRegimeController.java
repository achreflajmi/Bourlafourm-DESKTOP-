package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import tn.esprit.entities.Coord;
import tn.esprit.entities.Regime;
import tn.esprit.services.ServiceRegime;

import java.sql.SQLException;

public class AjoutRegimeController {
    private final ServiceRegime ps = new ServiceRegime();

    @FXML
    private TextArea coldejTA;

    @FXML
    private TextArea petitdejTA;

    @FXML
    private TextArea dejTA;

    @FXML
    private Button ajoutBT;

    @FXML
    private TextArea dinerTA;

    @FXML
    private TextArea colpdejTA;

    @FXML
    void ajoutRegime(ActionEvent event) throws SQLException {
        try {
            if (!isValidInput(petitdejTA.getText())) {
                throw new NumberFormatException("Invalid input. Special characters are not allowed.");
            }
            if (!isValidInput(colpdejTA.getText())) {
                throw new NumberFormatException("Invalid input. Special characters are not allowed.");
            }
            if (!isValidInput(dejTA.getText())) {
                throw new NumberFormatException("Invalid input. Special characters are not allowed.");
            }
            if (!isValidInput(coldejTA.getText())) {
                throw new NumberFormatException("Invalid input. Special characters are not allowed.");
            }
            if (!isValidInput(dinerTA.getText())) {
                throw new NumberFormatException("Invalid input. Special characters are not allowed.");
            }

            String petitdej = petitdejTA.getText();
            String colpdej = colpdejTA.getText();
            String dej = dejTA.getText();
            String coldej = coldejTA.getText();
            String diner = dinerTA.getText();
            ps.ajouter(new Regime(petitdej, colpdej, dej, coldej, diner));
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setContentText("Form added successfully!");
            successAlert.showAndWait();
        }catch (IllegalArgumentException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    private boolean isValidInput(String input) {
        // Use a regular expression to check for the absence of special characters
        return input.matches("[a-zA-Z0-9 ]+");
    }
}
