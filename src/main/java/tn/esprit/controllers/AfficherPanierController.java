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
    private List <Panier> paniers = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            paniers = pa.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(paniers);
        intitialisationPanierList();
    }
    void intitialisationPanierList() {
        int row = 0;
        try {
            for (Panier panier : paniers) {
                FXMLLoader loader = new FXMLLoader(new File("C:/Users/User/IdeaProjects/GestionProduit/src/main/java/tn/esprit/resources/itemPanier.fxml").toURI().toURL());
                HBox item = loader.load();

                // Declare and initialize the itemCard1Controller
                ItemPanierController itemCard1Controller = loader.getController();

                // Check if itemCard1Controller is null
                if (itemCard1Controller == null) {
                    throw new RuntimeException("ItemPanierController is null");
                }

                itemCard1Controller.setData(panier);

                item.setStyle("-fx-background-color: transparent; -fx-border-color: #008152; -fx-border-width: 1px;");

                PanierContainer.add(item, 0, row);
                GridPane.setMargin(item, new Insets(20));

                // Set equal column width (100%)
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setPercentWidth(100);
                PanierContainer.getColumnConstraints().add(columnConstraints);

                // Set equal row heights
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPercentHeight(100 / paniers.size()); // Each row takes an equal percentage of the height
                PanierContainer.getRowConstraints().add(rowConstraints);

                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
