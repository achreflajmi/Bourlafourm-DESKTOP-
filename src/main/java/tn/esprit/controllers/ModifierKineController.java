package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.entities.Kine;
import tn.esprit.services.ServiceKine;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModifierKineController {
    private final ServiceKine sk = new ServiceKine();
    @FXML
    private TextField idTF;
    @FXML
    private TextField nomcabTF;
    @FXML
    private TextField nomkineTF;
    @FXML
    private TextField adresseTF;

    @FXML
    private Button btnmodifier;
    @FXML
    void modifier(ActionEvent event) {
        String stringid= idTF.getText();
        try {
            System.out.println(stringid);
            sk.modifier(new Kine(Integer.parseInt(stringid), nomcabTF.getText(), nomkineTF.getText(), adresseTF.getText()));
            System.out.println("updated");

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());;
        }
    }

}
