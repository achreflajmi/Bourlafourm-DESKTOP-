package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Exercice;
import tn.esprit.services.ServiceExercice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AffExerciceController {

    ServiceExercice ps = new ServiceExercice();
    private List<Exercice> recomended;


    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane exerciceContainer;

    public void initialize() throws SQLException, IOException {
        int column=0;
        int row=1;
        recomended = ps.afficher();
        System.out.println(recomended);
        for (Exercice exercice : recomended){

            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffExercice.fxml"));
            //Parent root = loader.load();
           /*
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Exercice.fxml"));
            Parent exerciceBox= fxmlLoader.load();
            ExerciceController ec = fxmlLoader.getController();
           ec.setData(exercice);

            if (column == 6){
                column = 0;
                ++row;
            }

            exerciceContainer.add(exerciceBox, column++,row);
            GridPane.setMargin(exerciceBox,new Insets(10));
            */
        }
    }


}
