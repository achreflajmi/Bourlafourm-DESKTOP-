package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.entities.Coord;
import tn.esprit.entities.Exercice;
import tn.esprit.entities.Regime;
import tn.esprit.services.ServiceCoord;
import tn.esprit.services.ServiceExercice;
import tn.esprit.services.ServiceRegime;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AffController {
    @FXML
    private Button upeBT;
    @FXML
    private Button sportBT;
    @FXML
    private Button dietBT;
    @FXML
    private Button detailsBT;

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

    @FXML
    private Label nameLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Label nbrLabel;
    @FXML
    private ImageView imageView;
    GridPane gridPane = new GridPane();
    private ServiceCoord serviceCoord;
    private ServiceRegime serviceRegime;
    private ServiceExercice serviceExercice;


    public void initialize() {
        serviceCoord = new ServiceCoord();
        serviceRegime = new ServiceRegime();
        serviceExercice = new ServiceExercice();

        afficherCoordonneesParId(1);
        afficherRegimeParId(1);
        afficherExercice();
    }

    //----------------------  Affichage  --------------------------------
    private void afficherExercice() {
        try {
            List<Exercice> exercices = serviceExercice.afficher();


            gridPane.getChildren().clear();


            int row = 0;
            for (Exercice exercice : exercices) {
                Label nameLabel = new Label(exercice.getNom());
                Label descLabel = new Label(exercice.getDescription());
                Label nbrLabel = new Label(String.valueOf(exercice.getNbr_rep()));
                ImageView imageView = new ImageView(new Image(exercice.getImage()));
                gridPane.add(nameLabel, 0, row);
                gridPane.add(imageView, 1, row);
                gridPane.add(descLabel, 2, row);
                gridPane.add(nbrLabel, 3, row);

                row++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void afficherRegimeParId(int id) {
        try {

            Regime regime = serviceRegime.getRegimeById(id);

            if (regime != null) {
                pettidejL.setText("Petit Déjeuner: " + regime.getPetitdej());
                ColpdejL.setText("Collation Petit Déjeuner: " + regime.getColpdej());
                dejL.setText("Déjeuner: " + regime.getDej());
                coldejL.setText("Collation Déjeuner: " + regime.getColdej());
                dinerL.setText("Dîner: " + regime.getDiner());

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //----------------------  Modif --------------------------------
    @FXML
    void UpdateExercice(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateExercice.fxml"));
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
    void AjoutExercice(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutExercice.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Exercice Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    @FXML
    void AjoutRegime(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutR.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add DIET Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    @FXML
    void AjoutCoord(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCoord.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add DETAILS Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }}



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
