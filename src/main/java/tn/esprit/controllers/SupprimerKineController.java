package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.entities.Kine;
import tn.esprit.services.ServiceKine;

import java.sql.SQLException;

public class SupprimerKineController {
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
    private Button btnasupprimer;

    public void supprimer(ActionEvent actionEvent) {
        String stringid= idTF.getText();
        try {
            System.out.println(Integer.parseInt(stringid));
            sk.supprimer(Integer.parseInt(stringid));
            System.out.println("updated");
            System.out.println(stringid);
        } catch (
                SQLException e) {
            System.out.println(stringid);
            System.out.println(e.getMessage());;
        }
    }
}
