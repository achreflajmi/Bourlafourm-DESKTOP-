package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.entities.Categorie;
import tn.esprit.entities.Produit;
import tn.esprit.test.Main;
import tn.esprit.test.MainFX;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemPUserController implements Initializable {

    @FXML
    private Label nom_prod;

    @FXML
    private Label description_prod;

    @FXML
    private ImageView image_prod;

    @FXML
    private Label id_categorie;

    @FXML
    private Label prix_prod;

    @FXML
    private Label quantite_prod;
    @FXML
    private JFXButton add;
    private Produit produit;
    public void setData1(Produit produit){
        this.produit=produit;
        System.out.println(produit);
        nom_prod.setText(produit.getNom_prod());
        description_prod.setText(produit.getNom_prod());
        prix_prod.setText(produit.getPrix_prod()+" TND");
        quantite_prod.setText(String.valueOf(produit.getQuantite_prod()));
        id_categorie.setText(String.valueOf(produit.getId_categorie()));
        Image image = new Image(produit.getImage_prod());
        image_prod.setImage(image);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void AddToBasket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPanier.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Basket");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while loading the basket window.");
            alert.showAndWait();
        }
    }

}
