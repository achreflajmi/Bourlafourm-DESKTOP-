package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import tn.esprit.entities.Panier;
import tn.esprit.entities.Produit;
import tn.esprit.service.ServicePanier;
import tn.esprit.service.ServiceProduit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherPanierController implements Initializable {

    private final ServicePanier pa = new ServicePanier();

    @FXML
    private GridPane PanierContainer;

    private final VBox vbox = new VBox(); // Create a VBox to hold the items

    private List<Produit> produits;

    public AfficherPanierController() throws SQLException {
        ServiceProduit serviceProduit = ServiceProduit.getInstance();
        try {
            produits = serviceProduit.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vbox.setSpacing(10); // Set spacing between items
        PanierContainer.add(vbox, 0, 0);
    }

    public void addItemToBasket(Produit produit) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ItemPanier.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            ItemPanierController itemController = fxmlLoader.getController();
            itemController.setData(produit);

            // Add the anchorPane to the VBox
            vbox.getChildren().add(anchorPane);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
