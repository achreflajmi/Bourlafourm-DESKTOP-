package tn.esprit.controllers;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.esprit.entities.Coord;

import java.io.IOException;
import java.sql.SQLException;

import tn.esprit.entities.Sexe;
import tn.esprit.services.ServiceCoord;

public class AjoutCoordController {


    private final ServiceCoord ps = new ServiceCoord();
    @FXML
    private TextField ageTF;
    @FXML
    private TextField tailleTF;
    @FXML
    private TextField poidsTF;
    @FXML
    private ChoiceBox<Sexe> sexeCB;
    @FXML
    private TextField imcTF;


    @FXML
    private void initialize(){
        ObservableList<Sexe> sexOptions = FXCollections.observableArrayList(Sexe.homme, Sexe.femme);
        sexeCB.setItems(sexOptions);
        sexeCB.setValue(Sexe.femme);

    }

    @FXML
    void ajouterCoord(MouseEvent event) {
        try {
            if (!isValidPositiveInteger(ageTF.getText())) {
                    throw new NumberFormatException("Invalid age. Please enter a positive integer.");
            }

            if (!isValidPositiveDouble(poidsTF.getText())) {
                throw new NumberFormatException("Invalid weight. Please enter a positive number.");
            }

            if (!isValidPositiveDouble(tailleTF.getText())) {
                throw new NumberFormatException("Invalid height. Please enter a positive number.");
            }

            double poids = Double.parseDouble(poidsTF.getText());
            double taille = Double.parseDouble(tailleTF.getText());


            if (taille < 0 || taille > 3) {
                throw new NumberFormatException("Invalid height. Please enter a positive number in meters(0<height<3).");
            }

            double imc = poids / (taille * taille);
            imcTF.setText(String.valueOf(imc));

            int age = Integer.parseInt(ageTF.getText());

            ps.ajouter(new Coord(age, sexeCB.getValue(), poids, taille, imc));
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setContentText("Form added successfully!");
            successAlert.showAndWait();

        } catch (NumberFormatException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean isValidPositiveInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidPositiveDouble(String input) {
        try {
            double value = Double.parseDouble(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}



