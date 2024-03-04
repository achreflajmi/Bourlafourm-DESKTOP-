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
import tn.esprit.entities.Panier;
import tn.esprit.entities.Produit;
import tn.esprit.entities.Sms;
import tn.esprit.service.ServicePanier;
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
    private JFXButton add;

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

    public void AddToBasket(ActionEvent event) throws SQLException {
        ServicePanier servicePanier = ServicePanier.getInstance();
        int id_prod = produit.getId_prod();
        String nom_prod = produit.getNom_prod();
        double prix_prod = produit.getPrix_prod();
        String image_prod = produit.getImage_prod();
        double total_panier = produit.getPrix_prod();
        Panier panier = new Panier(total_panier, id_prod, nom_prod, prix_prod, image_prod);
        try {
            servicePanier.ajouter(panier);
            //Twilo API              //Twilo API
            /**/      String recipientPhoneNumber = "+21696378492"; // Replace with the recipient's phone number
            /**/  String messageText = "Votre produit "+nom_prod+" est ajoutée au panier avec succès !"; // Provide your message
            /**/ Sms.sendTwilioSMS(recipientPhoneNumber, messageText);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPanier.fxml"));
            Parent root = loader.load(); // Load the FXML file
            Scene scene = add.getScene();
            Stage stage = (Stage) scene.getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while loading the basket.");
            alert.showAndWait();
        }
    }
}
