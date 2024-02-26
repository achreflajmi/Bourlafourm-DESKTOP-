package org.example.controllers;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.entities.Event;

import java.net.URL;
import java.util.ResourceBundle;

import static java.sql.Date.valueOf;

public class CarteInfoController implements Initializable {
    @FXML
    private AnchorPane Info_pane;

    @FXML
    private Label info_cap;

    @FXML
    private Label info_date_deb;

    @FXML
    private Label info_date_fin;

    @FXML
    private ImageView info_image;

    @FXML
    private Label info_nom_ev;

    @FXML
    private Label info_org;

    void setInfoEvent(String nomEvent,String organisateur,  int capacite,  java.util.Date date_deb, java.util.Date date_fin, int idEvent , String pathrecupere) {

        new FadeIn(Info_pane).play();
        info_nom_ev.setText(nomEvent);
        info_org.setText(organisateur);
        info_cap.setText(String.valueOf(capacite));

        info_date_deb.setText((String.valueOf(date_deb)));
        info_date_fin.setText((String.valueOf(date_fin)));
        info_image.setImage(new Image(pathrecupere));
        System.out.println("voici image"+"file:///"+pathrecupere);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
