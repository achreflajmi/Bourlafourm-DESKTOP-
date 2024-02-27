package tn.esprit.controllers;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Produit;
import tn.esprit.service.ServiceProduit;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class AjouterProduitController implements Initializable {
    private final ServiceProduit ps = new ServiceProduit();

    @FXML
    private TextField nom_prod,imgpathstring;
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
    private String imagePath1;

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



    }

    @FXML
    public void ajouterProduit(javafx.event.ActionEvent actionEvent) throws IOException {

        String path;
        imgpathstring.setText(imagePath1.toString());

        path=imgpathstring .getText();
        try {
            if (nom_prod.getText().isEmpty() || prix_prod.getText().isEmpty() || description_prod.getText().isEmpty()
                    || quantite_prod.getText().isEmpty() || imagePath1 == null || categorieComboBox.getValue() == null) {
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
                    nom_prod.getText(),
                    prix,
                    description_prod.getText(),
                    quantite,
                   path,
                    categoryId
            );
            ps.ajouter(produit);
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
                imagePath1 = selectedFile.toURI().toString();
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to load image: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }


}
