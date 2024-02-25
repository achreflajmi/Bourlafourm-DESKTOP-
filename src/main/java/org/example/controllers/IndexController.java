package org.example.controllers;
import javafx.scene.Node;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private AnchorPane IndexPane;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnReduce;

    @FXML
    private JFXButton btnToAdmin;

    @FXML
    private JFXButton btnToLoginClient;

    @FXML
    void NaviguerBackEnd(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ListEvent.fxml"));
        try {
            Parent parent = loader.load();

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Replace the current scene with the new one
            currentStage.setScene(new Scene(parent));

        } catch (IOException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }

    }
    @FXML
    void NaviguerFrontEnd(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ListEventFront.fxml"));
        try {
            Parent parent = loader.load();

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Replace the current scene with the new one
            currentStage.setScene(new Scene(parent));

        } catch (IOException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }


    }


}




