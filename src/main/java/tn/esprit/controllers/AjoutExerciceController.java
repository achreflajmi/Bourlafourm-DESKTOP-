package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import tn.esprit.entities.Coord;
import tn.esprit.entities.Exercice;
import tn.esprit.services.ServiceCoord;
import tn.esprit.services.ServiceExercice;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjoutExerciceController {


    @FXML
    private TextField nameTF;

    @FXML
    private Button addBT;

    @FXML
    private TextField descTF;

    @FXML
    private TextField nbrTF,path;
    @FXML
    private ImageView imgTF;
    private String imgpath;
    private final ServiceExercice ps = new ServiceExercice();



    private void initialize(URL url, ResourceBundle resourceBundle){
        imgTF.setOnMouseClicked(event -> importImage());
        path.setVisible(false);
    }

    @FXML
    public void importImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                Image image = new Image(fileInputStream);
                imgTF.setImage(image);
                imgpath = selectedFile.toURI().toString();
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to load image: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void AjouterExercice() {
        try {

            if (!isValidPositiveInteger(nbrTF.getText())) {
                throw new NumberFormatException("Invalid number. Please enter a positive integer.");
            }

            if (!isValidInput(nameTF.getText()) || !isValidInput(descTF.getText())) {
                throw new IllegalArgumentException("Invalid input. Special characters are not allowed.");
            }



            String path1;
            path.setText(imgpath.toString());
            path1 = path.getText();

            String name = nameTF.getText();
            String desc = descTF.getText();
            int nbr = Integer.parseInt(nbrTF.getText());
            Exercice exercice = new Exercice(name, desc, path1, nbr);
            ps.ajouter(exercice);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setContentText("Exercise added successfully!");
            successAlert.showAndWait();

        } catch (IllegalArgumentException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean isValidPositiveInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidInput(String input) {
        // Use a regular expression to check for the absence of special characters
        return input.matches("[a-zA-Z0-9 ]+");
    }
}