package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.esprit.entities.Categorie;
import tn.esprit.service.ServiceCategorie;

import java.io.IOException;
import java.sql.SQLException;


public class AjouterCategorieController {
    private final ServiceCategorie cs = new ServiceCategorie();

    @FXML
    private JFXButton Afficher;

    @FXML
    private JFXButton Ajouter;

    @FXML
    private JFXTextField description_categorie;

    @FXML
    private JFXTextField nom_categorie;

    @FXML
    private JFXTextField type_categorie;

    @FXML
    public void ajouterCategorie(javafx.event.ActionEvent actionEvent) throws IOException {
        try {
            if (nom_categorie.getText().isEmpty() || description_categorie.getText().isEmpty() || type_categorie.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please fill in all the fields.");
                alert.showAndWait();
                return;
            }

            Categorie categorie = new Categorie(
                    nom_categorie.getText(),
                    description_categorie.getText(),
                    type_categorie.getText()
            );
            cs.ajouter(categorie);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Category added successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCategorie.fxml"));
            Parent root = loader.load();
            Stage afficherStage = new Stage();
            afficherStage.setTitle("Afficher produit");
            Image logo = new Image("logo.png");
            afficherStage.getIcons().add(logo);
            afficherStage.setScene(new Scene(root));
            afficherStage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }
    }



