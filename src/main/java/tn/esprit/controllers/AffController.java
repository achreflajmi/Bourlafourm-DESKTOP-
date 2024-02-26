package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.entities.Coord;
import tn.esprit.entities.Exercice;
import tn.esprit.entities.Regime;
import tn.esprit.services.ServiceCoord;
import tn.esprit.services.ServiceRegime;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AffController {

    @FXML
    private Label coldejL;

    @FXML
    private VBox vBox;

    @FXML
    private Label sexeLabel;

    @FXML
    private Label ColpdejL;

    @FXML
    private Label pettidejL;

    @FXML
    private Label tailleLabel;

    @FXML
    private Label dinerL;

    @FXML
    private Label poidsLabel;

    @FXML
    private GridPane gridP;

    @FXML
    private Label imcLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label dejL;



    GridPane gridPane = new GridPane();
    private ServiceCoord serviceCoord;
    private ServiceRegime serviceRegime;

   public void initialize() {
        serviceCoord = new ServiceCoord();
        serviceRegime = new ServiceRegime();
        afficherCoordonneesParId(1);
        afficherRegimeParId(1);
    }

    //----------------------  Affichage  --------------------------------
    private void afficherRegimeParId(int id) {
        try {

            Regime regime = serviceRegime.getRegimeById(id);

            if (regime != null) {
                pettidejL.setText("Petit Déjeuner: " + regime.getPetitdej());
                ColpdejL.setText("Collation Petit Déjeuner: " + regime.getColpdej());
                dejL.setText("Déjeuner: " + regime.getDej());
                coldejL.setText("Collation Déjeuner: " + regime.getColdej());
                dinerL.setText("Dîner: " + regime.getDiner());

            } else {
                // Alert not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void afficherCoordonneesParId(int id) {
        try {
            Coord coordonnees = serviceCoord.getCoordById(id);


            if (coordonnees != null) {


                sexeLabel.setText("Sexe: " + coordonnees.getSexe());
                ageLabel.setText("Age: " + coordonnees.getAge());
                poidsLabel.setText("Poids: " + coordonnees.getPoids());
                tailleLabel.setText("Taille: " + coordonnees.getTaille());
                imcLabel.setText("IMC: " + coordonnees.getImc());
            } else {
                //  Alert not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //----------------------  Modif --------------------------------
    @FXML
    void handleUpdateRegime(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateRegime.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update DIET Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleUpdateButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateForm.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
