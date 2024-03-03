package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainFX extends Application {

public static final String CURRENCY = "TND";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPUser.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Shop");
            Image logo = new Image("file:///C:/Users/User/IdeaProjects/GestionProduit/src/main/java/tn/esprit/resources/logo.png");
            stage.getIcons().add(logo);
            stage.setWidth(1325);
            stage.setHeight(1080);
            stage.setFullScreen(false);
            stage.setFullScreenExitHint("Press F to exit!");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("f"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
