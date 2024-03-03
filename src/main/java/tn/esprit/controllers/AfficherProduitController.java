package tn.esprit.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.util.Duration;
import tn.esprit.entities.ExcelGenerator;
import tn.esprit.service.ProduitListener;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.entities.Categorie;
import tn.esprit.entities.Produit;
import tn.esprit.service.ServiceProduit;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.ScrollPane;

public class AfficherProduitController implements Initializable, ProduitListener {
    private Timeline refreshTimeline;
    @FXML
    private JFXButton search;

    @FXML
    private JFXTextField searchField;

    @FXML
    private AnchorPane AfficherProduitScene;

    @FXML
    GridPane grid;

    @FXML
    private ScrollPane scroll;
    private final ServiceProduit ps = new ServiceProduit();
    private List<Produit> produits = new ArrayList<>();

    private List<Produit> originalProduits = new ArrayList<>();

    void intitialisationProduitList() {
        int row = 0;
        try {
            for (Produit produit : produits) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/item.fxml"));
                AnchorPane item = loader.load();

                ItemController itemCardController = loader.getController();
                itemCardController.setData(produit);
                itemCardController.setProduitListener(this);
                item.setStyle("-fx-background-color: transparent; -fx-border-color: #008152; -fx-border-width: 1px;");

                grid.add(item, 0, row);
                GridPane.setMargin(item, new Insets(20));

                // Set equal column width (100%)
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
    private void ajouterProduitOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter un produit");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshPage();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while loading the add product window.");
            alert.showAndWait();
        }
    }

    private void refreshPage() {
        try {
            // Retrieve the latest list of products from the database
            produits = ps.recuperer();

            // Clear the original products list
            originalProduits.clear();

            // Add the retrieved products to the original products list
            originalProduits.addAll(produits);

            // Clear the grid to remove existing products
            grid.getChildren().clear();

            // Initialize the list of products again
            intitialisationProduitList();
            onClose();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while retrieving products from the database.");
            alert.showAndWait();
        }
    }


    @FXML
    private void exportToExcel(ActionEvent event) throws SQLException {
        onClose();
        ExcelGenerator excelGenerator = new ExcelGenerator();
        ServiceProduit produitService = new ServiceProduit();

        List<Produit> produits = produitService.recuperer();

        excelGenerator.generateExcel(produits);

        System.out.println("Export vers Excel terminé !");
    }

    @FXML
    private void searchProduct(ActionEvent event) {
        onClose();
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

    @Override
    public void OnModifier(Produit produit) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            produits = ps.recuperer();
            originalProduits.addAll(produits); // Store original products
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        intitialisationProduitList();

        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> refreshPage()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
        onClose();
    }

    private void stopRefreshTimeline() {
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
    }

    // Call this method when closing the scene or disposing of the controller
    public void onClose() {
        stopRefreshTimeline();
    }
}
