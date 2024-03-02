package tn.esprit.controllers;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.Panier;
import tn.esprit.service.ServicePanier;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ItemPanierController {

    @FXML
    private ImageView itemimg;

    @FXML
    private Label itemname;

    @FXML
    private Label itemprice;

    @FXML
    private Label quantite;

    @FXML
    private ImageView moins; // ImageView for decrementing quantity

    @FXML
    private ImageView plus; // ImageView for incrementing quantity

    private static Map<Integer, Integer> productQuantities = new HashMap<>();

    Panier currentPanier;
    private QuantityUpdateListener quantityUpdateListener;
    public interface QuantityUpdateListener {
        void onQuantityUpdate(int productId, int newQuantity);
    }
    public void setQuantityUpdateListener(QuantityUpdateListener listener) {
        this.quantityUpdateListener = listener;
    }
    public void setData1(Panier panier) {
        currentPanier = panier;
        System.out.println("Current panier ID: " + currentPanier.getId_panier()); // Print the ID for debugging
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
        updateQuantityDisplay(panier.getId_panier());
    }

    public void updateQuantity(int newQuantity) {
        updateQuantityDisplay(newQuantity);
    }


    private void updateQuantityDisplay(int productId) {
        int quantity = productQuantities.getOrDefault(productId, 0); // Get the current quantity from the map
        quantite.setText("" + quantity);
    }

    private void notifyQuantityUpdate(int productId, int newQuantity) {
        if (quantityUpdateListener != null) {
            quantityUpdateListener.onQuantityUpdate(productId, newQuantity);
        }
    }
    @FXML
    private void onMoinsClicked(MouseEvent event) {
        int productId = currentPanier.getId_prod();
        int quantity = productQuantities.getOrDefault(productId, 1); // Default quantity is 1 if the product is added for the first time
        if (quantity > 0) {
            quantity--;
            productQuantities.put(productId, quantity); // Update the quantity first
            updateQuantityDisplay(productId);
            if (quantity == 0) {
                // Call the method to handle basket deletion with a dummy ActionEvent
                ActionEvent dummyEvent = new ActionEvent();
                handleBasketDeletion(dummyEvent);
            }
        }
    }

    @FXML
    private void onPlusClicked(MouseEvent event) {
        int productId = currentPanier.getId_prod();
        int quantity = productQuantities.getOrDefault(productId, 0);
        quantity++;
        productQuantities.put(productId, quantity);
        updateQuantityDisplay(productId);

        // Update quantity in the database
        try {
            ServicePanier.getInstance().updateQuantitePanier(productId, quantity);
        } catch (SQLException e) {
            System.out.println("Error updating quantity in the database: " + e.getMessage());
        }
    }
    @FXML
    private void handleBasketDeletion(ActionEvent event) {
        int panierId = currentPanier.getId_panier();
        System.out.println("Deleting item with ID: " + panierId); // Check if the correct ID is being passed

        productQuantities.remove(panierId);

        try {
            ServicePanier.getInstance().supprimer(panierId);
            System.out.println("Item deleted successfully from the database."); // Print success message
        } catch (SQLException e) {
            System.out.println("Error deleting product from the database: " + e.getMessage()); // Print SQL error message
            e.printStackTrace(); // Print stack trace for detailed error analysis
        }

        // Notify the listener if set
        if (quantityUpdateListener != null) {
            quantityUpdateListener.onQuantityUpdate(panierId, 0);
        }


    }


}

