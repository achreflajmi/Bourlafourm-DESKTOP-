package tn.esprit.controllers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.Panier;
import tn.esprit.entities.Produit;
import tn.esprit.service.ServicePanier;
import tn.esprit.service.ServiceProduit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AfficherPanierController implements Initializable, ItemPanierController.QuantityUpdateListener{

    private final ServicePanier pa = new ServicePanier();

    @FXML
    private GridPane PanierContainer;
    private final VBox vbox = new VBox(); // Create a VBox to hold the items

    private List<Produit> produits;
    private Panier panier;
    private Map<Integer, Panier> panierMap = new HashMap<>(); // Map to store products in the basket

    public AfficherPanierController() throws SQLException {
        ServicePanier servicePanier = ServicePanier.getInstance();
        try {
            List<Panier> fetchedPanierList = servicePanier.recuperer();
            for (Panier panier : fetchedPanierList) {
                panierMap.put(panier.getId_prod(), panier);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PanierContainer = new GridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int row = 0;
            for (Panier panier : panierMap.values()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/itemPanier.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemPanierController itemController = fxmlLoader.getController();
                itemController.setData1(panier);
                PanierContainer.add(anchorPane, 0, row++);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            loadPanierItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create a Timeline to refresh the page every 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            try {
                loadPanierItems(); // Reload the items
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline
    }

    private void loadPanierItems() throws SQLException, IOException {
        PanierContainer.getChildren().clear(); // Clear existing items
        panierMap.clear(); // Clear existing panier map

        int row = 0;
        ServicePanier servicePanier = ServicePanier.getInstance(); // Create an instance of ServicePanier
        for (Panier panier : servicePanier.recuperer()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/itemPanier.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            ItemPanierController itemController = fxmlLoader.getController();
            itemController.setData1(panier);
            itemController.setQuantityUpdateListener(this); // Set the listener for quantity updates
            PanierContainer.add(anchorPane, 0, row++);
            panierMap.put(panier.getId_panier(), panier);
        }
    }



    @Override
    public void onQuantityUpdate(int productId, int newQuantity) {
        if (newQuantity == 0) {
            // Remove the product from the UI
            PanierContainer.getChildren().removeIf(node -> {
                if (node instanceof AnchorPane) {
                    ItemPanierController controller = (ItemPanierController) ((AnchorPane) node).getProperties().get("controller");
                    if (controller != null && controller.currentPanier.getId_prod() == productId) {
                        PanierContainer.getChildren().remove(node);
                        return true;
                    }
                }
                return false;
            });
        }
    }
}
