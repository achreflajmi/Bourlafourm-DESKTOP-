package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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
    private Panier panier;
    private List<Produit> selectedProducts;
    private List<Panier> PanierList;


    public AfficherPanierController() throws SQLException {
        ServicePanier servicePanier=ServicePanier.getInstance();
        try {
            PanierList = servicePanier.fetchProduitDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PanierContainer = new GridPane();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int col = 0;
        int rows = 0;
        try {
            PanierList = pa.fetchProduitDetails();
            int row = 0;
            for (Panier panier : PanierList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/itemPanier.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemPanierController itemController = fxmlLoader.getController();
                itemController.setData1(panier);
                PanierContainer.add(anchorPane, 0, row++);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    }






