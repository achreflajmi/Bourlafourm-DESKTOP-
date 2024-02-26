package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.entities.Event;

import java.net.URL;
import java.util.ResourceBundle;

public class CarteReservationController implements Initializable {

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

    @FXML
    private Label nbr_place_res;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    void setInfoEvent(Event event) {

        info_nom_ev.setText(event.getNomEvent());
        info_org.setText(event.getOrganisateur());
        info_cap.setText(String.valueOf(event.getCapacite()));
        info_date_deb.setText((String.valueOf(event.getdate_Date_deb())));
        info_date_fin.setText((String.valueOf(event.getDate_fin())));
        info_image.setImage(new Image(event.getPath()));
        //System.out.println("voici image"+"file:///"+pathrecupere);
        nbr_place_res.setText(String.valueOf(event.getNb_place_res()));
    }



}
