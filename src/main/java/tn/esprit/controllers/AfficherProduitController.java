package tn.esprit.controllers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.util.Duration;
import tn.esprit.service.ProduitListener;
import tn.esprit.controllers.ItemController;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.ScrollPane;

public class AfficherProduitController implements Initializable, ProduitListener {
    private Timeline refreshTimeline;

    @FXML
    private AnchorPane AfficherProduitScene;

    @FXML
     GridPane grid;

    @FXML
    private ScrollPane scroll;
    private final ServiceProduit ps = new ServiceProduit();
    private List <Produit> produits = new ArrayList<>();

    @FXML
    void initialize() throws SQLException, IOException {

    }
    void intitialisationProduitList() {
        int row = 0;
        try {
            for (Produit produit : produits) {
                FXMLLoader loader = new FXMLLoader(new File("C:/Users/User//IdeaProjects/GestionProduit/src/main/java/tn/esprit/resources/item.fxml").toURI().toURL());
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

    public void ajouterProduitOnClick(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader(new File("C:/Users/User//IdeaProjects/GestionProduit/src/main/java/tn/esprit/resources/AjouterProduit.fxml").toURI().toURL());
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter un produit");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            produits = ps.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(produits);
        intitialisationProduitList();


        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> refreshPage()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();

    }
    private void refreshPage() {
        try {
            // Retrieve the latest list of products from the database
            produits = ps.recuperer();

            // Clear the grid to remove existing products
            grid.getChildren().clear();

            // Initialize the list of products again
            intitialisationProduitList();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while retrieving products from the database.");
            alert.showAndWait();
        }
    }

    @Override
    public void OnModifier(Produit produit) {
        produit.setFakeIdP(produit.getId_prod());
        System.out.println(produit.getFakeIdP());
        System.out.println("initializeData called with produitfake: " + produit);


        try {

            FXMLLoader loader = new FXMLLoader(new File("C:/Users/User//IdeaProjects/GestionProduit/src/main/java/tn/esprit/resources/ModifierProduit.fxml").toURI().toURL());
            Parent root = loader.load();
            ItemController itemCardController = loader.getController();
            itemCardController.setData(produit);
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


//    private void deleteProduitClicked(int productId) {
//        try {
//            ps.supprimer(productId);
//            grid.getChildren().clear();
//            initialize();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Delete Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Error occurred while deleting the product.");
//            alert.showAndWait();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//

}

//System.out.println(event);
//        id_Event.setText(String.valueOf(event.getIdEvent()));
//        Event_nom.setText(event.getNomEvent());
//        Event_capacite.setText(String.valueOf(event.getCapacite()));
//        if(event.getPath()==null){
//
//        String path="file:///C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/Unimage.png";
//        Image image=new Image(path,190,94,false,true);
//        Event_image.setImage(image);
//    }
//        else{
//        Image image=new Image(event.getPath(),190,94,false,true);
//        Event_image.setImage(image);
//
//    }
//        Event_date.setText(String.valueOf(event.getdate_Date_deb()));
//}

//    void RecupererImage(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        File f = fileChooser.showOpenDialog(null);
//
//        if(f != null)
//        {
//            System.out.println("Selected file est "+f.getAbsolutePath());
//            String path = f.getAbsolutePath();
//            Image.setImage(new Image("file:///"+path));
//            s = "file:///"+path;
//            p = f.getAbsolutePath();
//        }
//
//    }