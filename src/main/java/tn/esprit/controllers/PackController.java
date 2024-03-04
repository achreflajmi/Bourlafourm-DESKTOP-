package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import tn.esprit.entities.Whatsapp;

public class PackController {
    @FXML
    private Button Start;

    @FXML
    private Button Premium;

    @FXML
    void StartFunction(ActionEvent event) {
        Whatsapp whats= new Whatsapp();
        whats.Whats("Start");
    }
    @FXML
    void PremimFunction(ActionEvent event) {
        Whatsapp whats= new Whatsapp();
        whats.Whats("Premium");

    }
}
