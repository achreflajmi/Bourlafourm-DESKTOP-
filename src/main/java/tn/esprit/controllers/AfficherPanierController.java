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

    private List <Produit> produits;

    public AfficherPanierController() throws SQLException{
        ServiceProduit serviceProduit=ServiceProduit.getInstance();
        try {
            produits = serviceProduit.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PanierContainer = new GridPane();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox vbox = new VBox(); // Create a VBox to hold the items
            vbox.setSpacing(10); // Set spacing between items

            for (Produit produit : produits) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemPanier.fxml"));
                System.out.println("Hello");
                AnchorPane anchorPane = fxmlLoader.load();
                ItemPanierController itemController = fxmlLoader.getController();
                itemController.setData(produit);

                // Add the anchorPane to the VBox
                vbox.getChildren().add(anchorPane);
            }

            // Add the VBox to the PanierContainer
            PanierContainer.getChildren().add(vbox);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//    void intitialisationPanierList() {
//        int row = 0;
//        try {
//            for (Panier panier : paniers) {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemPanier.fxml"));
//                HBox item = loader.load();
//
//                // Declare and initialize the itemCard1Controller
//                ItemPanierController itemCard1Controller = loader.getController();
//
//                // Check if itemCard1Controller is null
//                if (itemCard1Controller == null) {
//                    throw new RuntimeException("ItemPanierController is null");
//                }
//
//                itemCard1Controller.setData(panier);
//
//                item.setStyle("-fx-background-color: transparent; -fx-border-color: #008152; -fx-border-width: 1px;");
//
//                PanierContainer.add(item, 0, row);
//                GridPane.setMargin(item, new Insets(20));
//
//                // Set equal column width (100%)
//                ColumnConstraints columnConstraints = new ColumnConstraints();
//                columnConstraints.setPercentWidth(100);
//                PanierContainer.getColumnConstraints().add(columnConstraints);
//
//                // Set equal row heights
//                RowConstraints rowConstraints = new RowConstraints();
//                rowConstraints.setPercentHeight(100 / paniers.size()); // Each row takes an equal percentage of the height
//                PanierContainer.getRowConstraints().add(rowConstraints);
//
//                row++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
