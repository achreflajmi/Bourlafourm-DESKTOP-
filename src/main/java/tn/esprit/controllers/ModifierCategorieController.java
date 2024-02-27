package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.entities.Categorie;
import tn.esprit.service.ServiceCategorie;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierCategorieController implements Initializable {


    @FXML
    private JFXTextField description_categorie;

    @FXML
    private JFXTextField nom_categorie;

    @FXML
    private JFXTextField type_categorie;
    @FXML
    private JFXButton Modifier;

    private Categorie selectedCategorie;


    public void setCategorie(Categorie selectedCategorie) {
        System.out.println("initializeData called with category: " + selectedCategorie);
        this.selectedCategorie = selectedCategorie;
        if (selectedCategorie != null) {
            nom_categorie.setText(selectedCategorie.getNom_categorie());
            description_categorie.setText(selectedCategorie.getDescription_Categorie());
            type_categorie.setText(selectedCategorie.getType_categorie());
        }
    }

    @FXML
    public void modifierCategorie(ActionEvent event) throws SQLException {

            String nom_categorie1 = nom_categorie.getText();
            String description_categorie1 = description_categorie.getText();
            String type_categorie1 = type_categorie.getText();

            if (nom_categorie1.isEmpty() || description_categorie1.isEmpty() || type_categorie1.isEmpty()) {
                AfficherAlerte("Warning", "Please fill in all the fields.");
                return;
            }
            Categorie categorie = new Categorie(selectedCategorie.getId_categorie(), nom_categorie1, description_categorie1, type_categorie1);
            ServiceCategorie cs = new ServiceCategorie();
            cs.modifier(categorie);
        Stage stage = (Stage) Modifier.getScene().getWindow();
        stage.close();
    }

    private void fillInputs(Categorie categorie){
        nom_categorie.setText(categorie.getNom_categorie());
        description_categorie.setText(categorie.getDescription_Categorie());
        type_categorie.setText(categorie.getType_categorie());

    }

    private void AfficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceCategorie serviceCategorie= new ServiceCategorie();

            selectedCategorie = serviceCategorie.getCategorieByCategorieId(Categorie.getFakeId());
            System.out.println(selectedCategorie.getNom_categorie());
        fillInputs(selectedCategorie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
