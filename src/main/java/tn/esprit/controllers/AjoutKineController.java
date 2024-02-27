package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import tn.esprit.entities.Kine;
import tn.esprit.services.ServiceKine;

import java.sql.SQLException;

public class AjoutKineController {
    private final ServiceKine sk = new ServiceKine();
    @FXML
    private TextField nomcabTF;
    @FXML
    private TextField nomkineTF;
    @FXML
    private TextField adresseTF;

    @FXML
    private Button btnajouter;

    public void ajouter(ActionEvent actionEvent) {
        try {
            sk.ajouter(new Kine(nomcabTF.getText(), nomkineTF.getText(), adresseTF.getText()));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }





    }


