package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Panier;
import tn.esprit.entities.Produit;
import tn.esprit.service.ServiceProduit;

import java.sql.SQLException;

public class ItemPanierController {



    @FXML
    private ImageView itemimg;

    @FXML
    private Label itemname;

    @FXML
    private Label itemprice;

    public void setData1(Panier panier) {
        itemname.setText(panier.getNom_prod());
        itemprice.setText(panier.getPrix_prod() + " TND");
        // Load image only if the URL is not empty
        if (panier.getImage_prod() != null && !panier.getImage_prod().isEmpty()) {
            try {
                Image image = new Image(panier.getImage_prod());
                itemimg.setImage(image);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        }
    }

}
