package tn.esprit.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Categorie;
import tn.esprit.entities.Produit;
import tn.esprit.service.ServiceCategorie;
import tn.esprit.service.ServiceProduit;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ModifierProduitController implements Initializable {
    private final ServiceProduit ps = new ServiceProduit();
    private Produit produit;
    @FXML
    private TextField imgpathstring;

    @FXML
    private TextField nom_prod;
    @FXML
    private TextField prix_prod;
    @FXML
    private TextField description_prod;
    @FXML
    private TextField quantite_prod;
    @FXML
    private ImageView image_prod;
    @FXML
    private JFXComboBox<String> categorieComboBox;
    private String imagePath;


    private Produit selectedProduit;

    public void setProduit(Produit selectedProduit) {
        System.out.println("initializeData called with category: " + selectedProduit);
        this.selectedProduit = selectedProduit;
        if (selectedProduit != null) {
            nom_prod.setText(selectedProduit.getNom_prod());
            prix_prod.setText(String.valueOf(selectedProduit.getPrix_prod()));
            description_prod.setText(selectedProduit.getDescription_prod());
            quantite_prod.setText(String.valueOf(selectedProduit.getQuantite_prod()));
            //image_prod.setText(selectedProduit.getDescription_prod());
            int selectedCategoryId = selectedProduit.getId_categorie();
            String selectedCategory = produit.getNomCategorie();
            categorieComboBox.getSelectionModel().select(selectedCategory);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image_prod.setOnMouseClicked(event -> importImage());

        prix_prod.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                prix_prod.setText(oldValue);
            }
        });

        quantite_prod.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantite_prod.setText(oldValue);
            }
        });
        try {
            List<String> categoryNames = ps.getAllCategories();
            categorieComboBox.getItems().addAll(categoryNames);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load category names: " + e.getMessage());
            alert.showAndWait();
        }
        imgpathstring.setVisible(false);
        try {
            ServiceProduit serviceProduit= new ServiceProduit();
            selectedProduit = serviceProduit.getProduitByProduitId(Produit.getFakeIdP());
            System.out.println(selectedProduit.getNom_prod());
            fillInputs(selectedProduit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void fillInputs(Produit produit){
        nom_prod.setText(produit.getNom_prod());
        description_prod.setText(produit.getDescription_prod());


    }
    @FXML
    public void importImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                Image image = new Image(fileInputStream);
                image_prod.setImage(image);
                image_prod.setStyle("-fx-image: url('logo.png');");
                imagePath = selectedFile.toURI().toString();
                System.out.println("*****************************************"+imagePath);
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to load image: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }


    @FXML
    public void modifierProduit() {
        String path;
        imgpathstring.setText(imagePath.toString());

        path=imgpathstring .getText();
        try {
            if (nom_prod.getText().isEmpty() || prix_prod.getText().isEmpty() || description_prod.getText().isEmpty()
                    || quantite_prod.getText().isEmpty() || imagePath == null || categorieComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please fill in all the fields.");
                alert.showAndWait();
                return;

            }

            double prix;
            int quantite;

            try {
                prix = Double.parseDouble(prix_prod.getText());
                quantite = Integer.parseInt(quantite_prod.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please enter valid values for price and quantity.");
                alert.showAndWait();
                return;
            }

            if (prix <= 0 || quantite <= 0) {
                // Le prix et la quantité doivent être supérieurs à zéro
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Price and quantity must be greater than zero.");
                alert.showAndWait();
                return;
            }

            String selectedCategoryName = categorieComboBox.getValue();
            int categoryId = ps.fetchCategoryIdByName(selectedCategoryName);
            Produit produit = new Produit(
                    selectedProduit.getId_prod(),
                    nom_prod.getText(),
                    prix,
                    description_prod.getText(),
                    quantite,
                    path,
                    categoryId
            );
            ServiceProduit ps = new ServiceProduit();
            ps.modifier(produit);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("C:\\Users\\User\\IdeaProjects\\GestionProduit\\src\\main\\java\\tn\\esprit\\resources\\AfficherCategorie.fxml"));
//            Parent root = loader.load();
//            Stage afficherStage = new Stage();
//            afficherStage.setTitle("Afficher produit");
//            Image logo = new Image("logo.png");
//            afficherStage.getIcons().add(logo);
//            afficherStage.setScene(new Scene(root));
//            afficherStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//
//
//        }
    }
//    @FXML
//    public void modifierCategorie(ActionEvent event) throws SQLException {
//
//        String nom_categorie1 = nom_categorie.getText();
//        String description_categorie1 = description_categorie.getText();
//        String type_categorie1 = type_categorie.getText();
//
//        if (nom_categorie1.isEmpty() || description_categorie1.isEmpty() || type_categorie1.isEmpty()) {
//            AfficherAlerte("Warning", "Please fill in all the fields.");
//            return;
//        }
//        Categorie categorie = new Categorie(selectedCategorie.getId_categorie(), nom_categorie1, description_categorie1, type_categorie1);
//        ServiceCategorie cs = new ServiceCategorie();
//        cs.modifier(categorie);
//        Stage stage = (Stage) Modifier.getScene().getWindow();
//        stage.close();
//    }

    private void AfficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
}