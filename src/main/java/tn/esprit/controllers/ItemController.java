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
import javafx.stage.Stage;
import tn.esprit.entities.Produit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import tn.esprit.service.ProduitListener;

public class ItemController implements Initializable {

    ProduitListener Listener;
    @FXML
    private JFXButton Modifier;
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
    private Produit produit;
    public void setData(Produit produit){




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
    @FXML
    void handlemodif(ActionEvent event){
        if(Listener != null){
            Listener.OnModifier(produit);
        }
    }
    @FXML
    void handlemodif(){
        if(Listener != null){
            Listener.OnModifier(produit);
        }
    }


    public void setProduitListener(ProduitListener listener) {
        this.Listener = listener;
    }


    private void initialize() {

    }

//    public void OnModifier(ActionEvent event) {
//        try {
//
//            FXMLLoader loader = new FXMLLoader(new File("C:/Users/User//IdeaProjects/GestionProduit/src/main/java/tn/esprit/resources/ModifierProduit.fxml").toURI().toURL());
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setTitle("Modifier la catégorie");
//            //Image logo = new Image("logo.png");
//            //stage.getIcons().add(logo);
//            stage.setScene(new Scene(root));
//            stage.showAndWait();
//
//            initialize();
//        } catch (IOException e) {
//
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Error occurred while loading the category modification window.");
//            alert.showAndWait();
//        }
//    }

//    public void modifierProduitOnClicked(ActionEvent event ) {
//
//
//      //  produit.setFakeIdP(produit.getId_prod());
//
////          prod.setFakeIdP(prod.getId_prod());
//        //  System.out.println(produit.getFakeIdP());
////        System.out.println("initializeData called with product: " + prod);
//        try {
//            FXMLLoader loader = new FXMLLoader(new File("C:/Users/User//IdeaProjects/GestionProduit/src/main/java/tn/esprit/resources/ModifierProduit.fxml").toURI().toURL());
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setTitle("Modifier la produit");
//            //Image logo = new Image("logo.png");
//            //stage.getIcons().add(logo);
//            stage.setScene(new Scene(root));
//            stage.showAndWait();
//
//            //initialize();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Error occurred while loading the category modification window.");
//            alert.showAndWait();
//        }
//    }
@FXML
private void handleModification() {
    try {
        // Load the FXML file for the modification window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("C:/Users/User//IdeaProjects/GestionProduit/src/main/java/tn/esprit/resources/ModifierProduit.fxml"));
        Parent root = loader.load();

        // Access the controller of the modification window
        ModifierProduitController modifierController = loader.getController();
        modifierController.setProduit(produit);

        // Set up a new stage to display the modification window
        Stage stage = new Stage();
        stage.setTitle("Modifier le produit");
        stage.setScene(new Scene(root));

        // Show the modification window
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Error occurred while loading the product modification window.");
        alert.showAndWait();
    }
}



}
