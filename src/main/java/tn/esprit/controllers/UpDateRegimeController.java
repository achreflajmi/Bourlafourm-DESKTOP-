package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.services.ServiceRegime;

import java.sql.SQLException;

public class UpDateRegimeController {
    private final ServiceRegime ps = new ServiceRegime();
    @FXML
    private TextField snackfx;

    @FXML
    private TextField lunchfx;

    @FXML
    private TextField breakfx;

    @FXML
    private TextField snackbfx;

    @FXML
    private TextField dinnerfx;

    @FXML
    void handleSubmit(ActionEvent event) throws SQLException {

        int regimeIdToUpdate = 1;
    try {
        if (!isValidInput(breakfx.getText())) {
            throw new NumberFormatException("Invalid input. Special characters are not allowed.");
        }
        if (!isValidInput(snackfx.getText())) {
            throw new NumberFormatException("Invalid input. Special characters are not allowed.");
        }
        if (!isValidInput(lunchfx.getText())) {
            throw new NumberFormatException("Invalid input. Special characters are not allowed.");
        }
        if (!isValidInput(snackbfx.getText())) {
            throw new NumberFormatException("Invalid input. Special characters are not allowed.");
        }
        if (!isValidInput(dinnerfx.getText())) {
            throw new NumberFormatException("Invalid input. Special characters are not allowed.");
        }

        String newBreakfest = breakfx.getText();
        String newSnack = snackfx.getText();
        String newLunch = lunchfx.getText();
        String newSnackb = snackbfx.getText();
        String newDinner = dinnerfx.getText();
        ps.modifierRegimeById(regimeIdToUpdate, newBreakfest, newSnack, newLunch, newSnackb, newDinner);
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
