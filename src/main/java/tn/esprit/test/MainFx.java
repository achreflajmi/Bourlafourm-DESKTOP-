package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainFx extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override

    public void start(Stage primaryStage) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Signup.fxml"));
        try{
            Parent root = fxmlloader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Add User");
            primaryStage.show();
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            //alert.showAndWait();
            System.out.println(e.getMessage());
        }

    }}
