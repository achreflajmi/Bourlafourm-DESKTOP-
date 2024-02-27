package tn.esprit.test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainFX extends Application {


    @Override
        public void start(Stage primaryStage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutKine.fxml"));
            /**FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierKine.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerKine.fxml"));**/
            Parent root = loader.load();
            Scene scene = new Scene(root);
            /**primaryStage.setTitle("ajouter un nouveau cabinet ");**/
            /**primaryStage.setTitle("supprimer un nouveau cabinet ");**/
            primaryStage.setTitle("modifier un cabinet ");
            primaryStage.setScene(scene);
            primaryStage.show();

        }

}

