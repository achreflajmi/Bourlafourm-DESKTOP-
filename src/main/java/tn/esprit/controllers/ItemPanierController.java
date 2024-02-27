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
    private ImageView image_prod;

    @FXML
    private Label nom_prod;

    @FXML
    private Label prix_prod;
    @FXML
    private Label id_prod;
    private Panier panier;

    public void setData(Panier panier) throws SQLException {
        this.panier = panier;
        System.out.println(panier);
//        id_prod.setText(String.valueOf(panier.getId_prod()));
//
//        // Get the product associated with the panier item
//        ServiceProduit serviceProduit = new ServiceProduit();
//        Produit produit = serviceProduit.getProduitByProduitId(panier.getId_prod());
//
//        // Set the product name and price
//        nom_prod.setText(produit.getNom_prod());
//        prix_prod.setText(String.valueOf(produit.getPrix_prod()));
//
//        // Set the product image (if available)
//        if (produit.getImage_prod() != null && !produit.getImage_prod().isEmpty()) {
//            Image image = new Image(produit.getImage_prod());
//            image_prod.setImage(image);
//        }

    }
}
