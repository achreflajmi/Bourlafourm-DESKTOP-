package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.Exercice;
import tn.esprit.entities.Regime;
import tn.esprit.services.ServiceExercice;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciceController implements Initializable {
    @FXML
    private ImageView imgf;

    @FXML
    private Label descf;

    @FXML
    private Label nomf;

    @FXML
    private Label nbrf;
private Exercice exercice;
    int column=0;
    int row=1;

    public void setData(Exercice exercice){
        this.exercice=exercice;
        System.out.println(exercice);
        //Image image= new Image(getClass().getResourceAsStream(exercice.getImage()));
       // imgf.setImage(image);
        //nomf.setText(exercice.getNom());
      //  descf.setText(exercice.getDescription());
       // nbrf.setText(String.valueOf(exercice.getNbr_rep()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Exercice.fxml"));
        try {
            AnchorPane exerciceBox= fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExerciceController ec = fxmlLoader.getController();
        ec.setData(exercice);

        if (column == 6) {
            column = 0;
            ++row;

        }}
}
