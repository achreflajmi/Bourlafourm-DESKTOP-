package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import tn.esprit.entities.Coord;
import tn.esprit.services.ServiceCoord;

import java.sql.SQLException;
import java.util.List;

public class AfficherController {
    @FXML
    private Label ageL;
    @FXML
    private Label sexeL;
    @FXML
    private Label poidsL;
    @FXML
    private Label tailleL;
    @FXML
    private Label imcL;

    private ServiceCoord serviceCoord = new ServiceCoord();


    public void displayCoordForId(int id) {
        try {
            Coord coord = serviceCoord.getCoordById(id);

            if (coord != null) {
                ageL.setText(String.valueOf(coord.getAge()));
                sexeL.setText(String.valueOf(coord.getSexe()));
                poidsL.setText(String.valueOf(coord.getPoids()));
                tailleL.setText(String.valueOf(coord.getTaille()));
                imcL.setText(String.valueOf(coord.getImc()));
            } else {

                System.out.println("Coordinates not found for ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}


