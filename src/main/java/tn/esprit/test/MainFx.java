package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.controllers.AjoutCoordController;

public class MainFx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PatientRecord.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root,800 , 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("BoUr la FoUrm");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }



        //----------------------  Regime --------------------------------

        /*
        AjoutRegimeController controller = fxmlLoader.getController();
         */


        //----------------------  Coordonnées --------------------------------



}
