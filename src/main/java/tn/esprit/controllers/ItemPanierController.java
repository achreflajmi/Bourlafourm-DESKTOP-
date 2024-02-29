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

    public void setData(Produit produit)  {
        System.out.println(produit);
        itemname.setText(produit.getNom_prod());
        itemprice.setText(produit.getPrix_prod()+" TND");
        Image image = new Image(produit.getImage_prod());
        itemimg.setImage(image);

    }

}
