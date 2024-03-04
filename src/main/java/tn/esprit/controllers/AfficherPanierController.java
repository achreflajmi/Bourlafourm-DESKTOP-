package tn.esprit.controllers;
import com.jfoenix.controls.JFXButton;
import com.pdfjet.*;
import com.pdfjet.TextLine;
import com.pdfjet.Font;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;
import com.pdfjet.TextLine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import javafx.stage.Stage;
import javafx.util.Duration;

import tn.esprit.entities.Panier;
import tn.esprit.entities.Produit;
import tn.esprit.service.ServicePanier;
import tn.esprit.service.ServiceProduit;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AfficherPanierController implements Initializable, ItemPanierController.QuantityUpdateListener{

    private final ServicePanier pa = new ServicePanier();

    @FXML
    private GridPane PanierContainer;
    private final VBox vbox = new VBox(); // Create a VBox to hold the items
    @FXML
    private JFXButton ShopButton;
    private List<Produit> produits;
    private Panier panier;
    private Map<Integer, Integer> productQuantities = new HashMap<>();

    private Map<Integer, Panier> panierMap = new HashMap<>(); // Map to store products in the basket

    public AfficherPanierController() throws SQLException {
        ServicePanier servicePanier = ServicePanier.getInstance();
        try {
            List<Panier> fetchedPanierList = servicePanier.recuperer();
            for (Panier panier : fetchedPanierList) {
                panierMap.put(panier.getId_prod(), panier);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PanierContainer = new GridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int row = 0;
            for (Panier panier : panierMap.values()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/itemPanier.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemPanierController itemController = fxmlLoader.getController();
                itemController.setData1(panier);
                PanierContainer.add(anchorPane, 0, row++);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            loadPanierItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create a Timeline to refresh the page every 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            try {
                loadPanierItems(); // Reload the items
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline
    }

    private void loadPanierItems() throws SQLException, IOException {
        PanierContainer.getChildren().clear(); // Clear existing items
        panierMap.clear(); // Clear existing panier map

        ServicePanier servicePanier = ServicePanier.getInstance(); // Create an instance of ServicePanier
        List<Panier> panierList = servicePanier.recuperer();

        // Iterate over the fetched panierList
        for (Panier panier : panierList) {
            // Check if the panierMap already contains the item
            if (panierMap.containsKey(panier.getId_panier())) {
                // Update the quantity of the existing item
                int existingQuantity = productQuantities.getOrDefault(panier.getId_panier(), 0);
                int newQuantity = existingQuantity + 1;
                productQuantities.put(panier.getId_panier(), newQuantity);
                // Find the associated ItemPanierController and update its quantity display
                for (Node node : PanierContainer.getChildren()) {
                    if (node instanceof AnchorPane) {
                        ItemPanierController itemController = ((ItemPanierController) ((AnchorPane) node).getProperties().get("controller"));
                        if (itemController != null && itemController.currentPanier.getId_panier() == panier.getId_panier()) {
                            itemController.updateQuantity(newQuantity);
                            break; // Once found, exit the loop
                        }
                    }
                }
            } else {
                // Add the item to the panierMap
                panierMap.put(panier.getId_panier(), panier);
                // Load the item into the UI
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/itemPanier.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemPanierController itemController = fxmlLoader.getController();
                itemController.setData1(panier);
                itemController.setQuantityUpdateListener(this); // Set the listener for quantity updates
                PanierContainer.add(anchorPane, 0, PanierContainer.getChildren().size()); // Add at the end
                // Update the quantity
                productQuantities.put(panier.getId_panier(), 1);
                itemController.updateQuantity(1); // Update the quantity display
            }
        }

    }

@FXML
public void printBasket(ActionEvent actionEvent) {
    try {
        File out = new File("ListeProduit.pdf");
        FileOutputStream fos = new FileOutputStream(out);
        PDF pdf = new PDF(fos);
        Page page = new Page(pdf, A4.PORTRAIT);
        com.pdfjet.Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
        com.pdfjet.Font f2 = new Font(pdf, CoreFont.HELVETICA);
        com.pdfjet.Font titleFont = new Font(pdf, CoreFont.HELVETICA_BOLD);
        com.pdfjet.Font headerFont = new Font(pdf, CoreFont.HELVETICA_BOLD_OBLIQUE);


        TextLine title = new TextLine(titleFont, "Basket\n");
        title.setFontSize(16f);
        title.setPosition(40f, 40f);
        title.drawOn(page);

        float titleWidth = title.getWidth();
        float titleHeight = title.getHeight();
        float lineY = 40f + titleHeight + 5f;
        float lineLength = 550f;
        float lineStartX = 20f;
        float lineEndX = lineStartX + lineLength;

        page.setPenWidth(1f);
        page.moveTo(lineStartX, lineY);
        page.lineTo(lineEndX, lineY);
        page.strokePath();

        Table table = new Table();
        List<List<Cell>> tableData = new ArrayList<>();

        List<Cell> headerRow = new ArrayList<>();
        Cell headerCell = new Cell(f1, "Nom du produit");
        headerCell.setFont(headerFont);
        headerCell.setFgColor(Color.white);
        headerCell.setBgColor(Color.darkgray);
        headerRow.add(headerCell);

        headerCell = new Cell(f1, "Price");
        headerCell.setFont(headerFont);
        headerCell.setFgColor(Color.white);
        headerCell.setBgColor(Color.darkgray);
        headerRow.add(headerCell);

        headerCell = new Cell(f1, "Quantite");
        headerCell.setFont(headerFont);
        headerCell.setFgColor(Color.white);
        headerCell.setBgColor(Color.darkgray);
        headerRow.add(headerCell);

        headerCell = new Cell(f1, "Total Prix");
        headerCell.setFont(headerFont);
        headerCell.setFgColor(Color.white);
        headerCell.setBgColor(Color.darkgray);
        headerRow.add(headerCell);

        tableData.add(headerRow);

        List<Panier> paniers = pa.recuperer();
        float totalPrice = 0;
        for (Panier panier : paniers) {
            List<Cell> dataRow = new ArrayList<>();
            Cell nomProduitCell = new Cell(f2, panier.getNom_prod());
            Cell prixProduitCell = new Cell(f2, String.valueOf(panier.getPrix_prod()));
            Cell quantiteProduitCell = new Cell(f2, String.valueOf(panier.getQuantite_panier()));
            Cell totalPrixCell = new Cell(f2, String.valueOf(panier.getTotal_panier()));

            dataRow.add(nomProduitCell);
            dataRow.add(prixProduitCell);
            dataRow.add(quantiteProduitCell);
            dataRow.add(totalPrixCell);

            totalPrice += panier.getTotal_panier(); // Accumulate total price
            tableData.add(dataRow);
        }

        List<Cell> totalRow = new ArrayList<>();
        totalRow.add(new Cell(f1, "Total:"));
        totalRow.add(new Cell(f1, ""));
        totalRow.add(new Cell(f1, ""));
        totalRow.add(new Cell(f1, String.valueOf(totalPrice)));
        tableData.add(totalRow);

        table.setData(tableData);
        table.setPosition(40f, 60f);
        table.setColumnWidth(0, 150f);
        table.setColumnWidth(1, 80f);
        table.setColumnWidth(2, 70f);
        table.setColumnWidth(3, 90f);
        table.setPosition(50f, 110f);

        while (true) {
            table.drawOn(page);
            if (!table.hasMoreData()) {
                table.resetRenderedPagesCount();
                break;
            }
            page = new Page(pdf, A4.PORTRAIT);
        }

        TextLine contactTitle = new TextLine(titleFont, "Pour nous contacter!\n");
        contactTitle.setFontSize(12f);
        contactTitle.setPosition(50f, 300f);
        contactTitle.drawOn(page);

        TextLine phoneNumber = new TextLine(f2, "+216 56809401\n");
        phoneNumber.setFontSize(10f);
        phoneNumber.setPosition(50f, 320f);
        phoneNumber.drawOn(page);

        TextLine websiteTitle = new TextLine(titleFont, "Veuillez consulter notre page web\n");
        websiteTitle.setFontSize(12f);
        websiteTitle.setPosition(50f, 350f);
        websiteTitle.drawOn(page);

        TextLine websiteUrl = new TextLine(f2, "www.BourLaFourme.com\n");
        websiteUrl.setFontSize(10f);
        websiteUrl.setPosition(50f, 370f);
        websiteUrl.drawOn(page);

        pdf.flush();
        Alert alertSuces = new Alert(Alert.AlertType.CONFIRMATION);
        alertSuces.setTitle("Succes");
        alertSuces.setContentText("PDF bien enregistré");
        alertSuces.showAndWait();

        System.out.println("Saved to " + out.getAbsolutePath());
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}


    @Override
    public void onQuantityUpdate(int productId, int newQuantity) {
        if (newQuantity == 0) {
            // Remove the product from the UI
            PanierContainer.getChildren().removeIf(node -> {
                if (node instanceof AnchorPane) {
                    ItemPanierController controller = (ItemPanierController) ((AnchorPane) node).getProperties().get("controller");
                    if (controller != null && controller.currentPanier.getId_prod() == productId) {
                        PanierContainer.getChildren().remove(node);
                        return true;
                    }
                }
                return false;
            });
        }
    }
    @FXML
    void openShop(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPUser.fxml"));
            Parent root = loader.load(); // Load the FXML file
            Scene scene = ShopButton.getScene();
            Stage stage = (Stage) scene.getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while loading the basket.");
            alert.showAndWait();
        }
    }
    @FXML
    void clearBasket(ActionEvent event) {
        try {
            for (int id_panier : panierMap.keySet()) {
                ServicePanier.getInstance().supprimer(id_panier);
            }
            panierMap.clear();
            loadPanierItems();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while clearing the basket.");
            alert.showAndWait();
        }
    }

}
