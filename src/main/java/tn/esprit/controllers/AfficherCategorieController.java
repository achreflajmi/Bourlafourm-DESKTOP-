package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.Categorie;

import tn.esprit.service.ServiceCategorie;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class AfficherCategorieController {
    @FXML
    private AnchorPane AfficherCategorieScene;

    @FXML
    private GridPane CategoriesContainer;
    private Timeline refreshTimeline;

    private final ServiceCategorie cs = new ServiceCategorie();
    @FXML
    void initialize() throws SQLException {

        refreshPage();
        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> refreshPage()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }
    private void refreshPage() {
        try {
            List<Categorie> categories = cs.recuperer();
            CategoriesContainer.getChildren().clear();

            if (!categories.isEmpty()) {
                int columnIndex = 0;
                int rowIndex = 0;
                for (Categorie categorie : categories) {
                    GridPane categoryPane = createCategoryPane(categorie);
                    CategoriesContainer.add(categoryPane, columnIndex, rowIndex);
                    columnIndex++;
                    if (columnIndex == 3) {
                        columnIndex = 0;
                        rowIndex++;
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Products Found");
                alert.setHeaderText(null);
                alert.setContentText("No products found in the database.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while retrieving categories from the database.");
            alert.showAndWait();
        }
    }

    private GridPane createCategoryPane(Categorie categorie) {
        GridPane categoryPane = new GridPane();
        categoryPane.getStyleClass().add("category-pane");
        categoryPane.setHgap(10);
        categoryPane.setVgap(10);

        Label nameLabel = new Label(categorie.getNom_categorie());
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font("System Bold", 16));

        Label descriptionLabel = new Label("Description: " + categorie.getDescription_Categorie());
        descriptionLabel.setTextFill(Color.WHITE);
        descriptionLabel.setFont(Font.font("System Bold", 16));

        Label typeLabel = new Label("Type: " + categorie.getType_categorie());
        typeLabel.setTextFill(Color.WHITE);
        typeLabel.setFont(Font.font("System Bold", 16));

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(descriptionLabel, 0, 1);
        GridPane.setConstraints(typeLabel, 0, 2);

        nameLabel.setPadding(new Insets(0, 0, 10, 0));

        JFXButton modifierButton = new JFXButton("Modifier");
        modifierButton.setStyle("-fx-background-color: #008152; -fx-text-fill: white;");
        modifierButton.setOnAction(event -> modifierCategorieOnClicked(event, categorie));
        JFXButton deleteButton = new JFXButton("Delete");
        deleteButton.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> deleteCategorieClicked(categorie.getId_categorie()));

        categoryPane.add(nameLabel, 0, 0);
        categoryPane.add(descriptionLabel, 0, 1);
        categoryPane.add(typeLabel, 0, 2);
        categoryPane.add(modifierButton, 1, 0);
        categoryPane.add(deleteButton, 1, 1);

        GridPane.setMargin(categoryPane, new Insets(10));

        return categoryPane;
    }
    void modifierCategorieOnClicked(ActionEvent event, Categorie cat) {
        cat.setFakeId(cat.getId_categorie());
        System.out.println(cat.getFakeId());
        System.out.println("initializeData called with category: " + cat);
        JFXButton modifierButton = (JFXButton) event.getSource();
        GridPane categoryPane = (GridPane) modifierButton.getParent();
        Categorie categorie = (Categorie) categoryPane.getUserData();

        try {

            FXMLLoader loader = new FXMLLoader(new File("C:/Users/User//IdeaProjects/GestionProduit/src/main/java/tn/esprit/resources/ModifierCategorie.fxml").toURI().toURL());
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Modifier la catégorie");
            //Image logo = new Image("logo.png");
            //stage.getIcons().add(logo);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            initialize();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while loading the category modification window.");
            alert.showAndWait();
        }
    }
    private void deleteCategorieClicked(int categorieId) {
        try {
            cs.supprimer(categorieId);
            CategoriesContainer.getChildren().clear();
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while deleting the product.");
            alert.showAndWait();
        }
    }
    }


