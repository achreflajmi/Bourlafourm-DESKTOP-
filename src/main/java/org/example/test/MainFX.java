package org.example.test;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/views/ListEventFront.fxml"));
        Parent root = fxmlLoader.load();

        // Create a scene with transparent fill
        Scene scene = new Scene(root, 1366, 768);
        scene.setFill(null);

        // Apply CSS to the scene to make it transparent with rounded corners and drop shadow effect

        stage.setScene(scene);
        stage.setTitle("Bour La Fourm");
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}



