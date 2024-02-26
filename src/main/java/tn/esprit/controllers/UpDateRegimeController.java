package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

        String newBreakfest = breakfx.getText();
        String newSnack = snackfx.getText();
        String newLunch = lunchfx.getText();
        String newSnackb = snackbfx.getText();
        String newDinner = dinnerfx.getText();
        ps.modifierRegimeById(regimeIdToUpdate, newBreakfest, newSnack, newLunch,newSnackb,newDinner);
    }
}
