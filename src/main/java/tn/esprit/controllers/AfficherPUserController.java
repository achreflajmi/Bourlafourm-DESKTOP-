package tn.esprit.controllers;
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
    private ScrollPane scroll;
    private final ServiceProduit ps = new ServiceProduit();
    private List <Produit> produits = new ArrayList<>();

    @FXML
    void initialize() throws SQLException, IOException {
        produits = ps.recuperer();
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


//    @FXML
//    void modifierProduitClicked(ActionEvent event) {
//        JFXButton modifierButton = (JFXButton) event.getSource();
//        GridPane productPane = (GridPane) modifierButton.getParent();
//        Produit produit = (Produit) productPane.getUserData();
//
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            InputStream fxmlStream = getClass().getResourceAsStream("/ModifierProduit.fxml");
//            Parent root = loader.load(fxmlStream);
//            ModifierProduitController modifierController = loader.getController();
//            modifierController.setProduit(produit);
//
//            Stage stage = new Stage();
//            stage.setTitle("Modifier le produit");
//            Image logo = new Image("logo.png");
//            stage.getIcons().add(logo);
//            stage.setScene(new Scene(root));
//            stage.showAndWait();
//
//            grid.getChildren().clear();
//            initialize();
//        } catch (IOException | SQLException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Error occurred while loading the product modification window.");
//            alert.showAndWait();
//        }
//    }
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