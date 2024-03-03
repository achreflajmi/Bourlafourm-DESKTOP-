package tn.esprit.controllers;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.layout.*;
import tn.esprit.controllers.ItemPUserController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tn.esprit.entities.Categorie;
import tn.esprit.entities.Produit;
import tn.esprit.service.ServiceProduit;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ScrollPane;

public class AfficherPUserController {

    @FXML
    private AnchorPane AfficherProduitScene;

    @FXML
    GridPane grid;
    @FXML
    private JFXButton search;

    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXButton BasketButton;
    @FXML
    private ScrollPane scroll;
    private final ServiceProduit ps = new ServiceProduit();
    private List<Produit> produits = new ArrayList<>();
    private List<Produit> originalProduits = new ArrayList<>(); // Added originalProduits list

    @FXML
    void initialize() throws SQLException, IOException {
        produits = ps.recuperer();
        originalProduits.addAll(produits);
        System.out.println(produits);
        intitialisationProduitList();
    }

    void intitialisationProduitList() {
        int row = 0;
        try {
            for (Produit produit : produits) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemPUser.fxml"));
                AnchorPane item = loader.load();

                ItemPUserController itemCardController = loader.getController();
                itemCardController.setData1(produit);

                item.setStyle("-fx-background-color: transparent; -fx-border-color: #008152; -fx-border-width: 1px;");

                grid.add(item, 0, row);
                GridPane.setMargin(item, new Insets(20));

                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setPercentWidth(100);
                grid.getColumnConstraints().add(columnConstraints);

                // Set equal row heights
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPercentHeight(100 / produits.size()); // Each row takes an equal percentage of the height
                grid.getRowConstraints().add(rowConstraints);

                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void openBasket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPanier.fxml"));
            Parent root = loader.load(); // Load the FXML file

            // Get the scene and stage from the event source
            Scene scene = BasketButton.getScene();
            Stage stage = (Stage) scene.getWindow();

            // Set the scene to the AfficherPanier.fxml
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
    @FXML
    private void searchProduct(ActionEvent event) {
        String query = searchField.getText().trim();
        if (!query.isEmpty()) {
            List<Produit> searchResults = new ArrayList<>();
            for (Produit produit : originalProduits) {
                if (produit.getNom_prod().toLowerCase().contains(query.toLowerCase())) {
                    searchResults.add(produit);
                }
            }
            // Clear the grid to remove existing products
            grid.getChildren().clear();
            // Initialize the list of products with search results
            produits = searchResults;
            intitialisationProduitList();
        } else {
            // If the search query is empty, reset the products list to original
            produits.clear();
            produits.addAll(originalProduits);
            // Clear the grid to remove existing products
            grid.getChildren().clear();
            // Initialize the list of products again
            intitialisationProduitList();
        }
    }
}
