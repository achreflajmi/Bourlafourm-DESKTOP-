package org.example.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.entities.Event;
import org.example.entities.Reservation;
import org.example.services.ServiceEvent;
import org.example.services.ServiceReservation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListDesReservationsController implements Initializable {

    @FXML
    private AnchorPane EventCarte;

    @FXML
    private JFXButton Event_annuler;

    @FXML
    private Label Event_capacite;

    @FXML
    private ImageView Event_image;

    @FXML
    private Label Event_nom;

    @FXML
    private ImageView btnReduce;

    @FXML
    private Label front_id;

    @FXML
    private Label iD_reservation;
    @FXML
    private Label id_ev_res;

    ServiceEvent se = new ServiceEvent();

    public void setReservation(Reservation reservation) throws SQLException {
        Event_nom.setText(reservation.getNom_rese_event());
        Event_capacite.setText(String.valueOf(reservation.getNbr_place_reserv()));
        iD_reservation.setText(String.valueOf(reservation.getId_reservation()));
        id_ev_res.setText(String.valueOf(reservation.getId_reser_event()));


        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);

        int i = 0;
        boolean conditionMet = false;
        Event evinfo = new Event();


        while (i < observableList.size() && !conditionMet) {
            if (observableList.get(i).getIdEvent()==reservation.getId_reser_event()) {
                conditionMet = true;
                evinfo = observableList.get(i);
            }
            i++;
        }
        System.out.println("Voici "+evinfo);


        Event_image.setImage(new Image(evinfo.getPath()));
    }


ServiceReservation sr =new ServiceReservation();
    @FXML
    void Event_annuler_btn(ActionEvent event) {
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Voulez vouz vraiment supprimer cet evenement ?");
            //alert.setContentText(ev.toString());

            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    sr.CapacitePlus(Integer.parseInt(Event_capacite.getText()),Integer.parseInt(id_ev_res.getText()));
                    sr.nombrePlaceMoins(Integer.parseInt(Event_capacite.getText()),Integer.parseInt(id_ev_res.getText()));

                    sr.supprimer(Integer.parseInt(iD_reservation.getText()));
                    Alert alertSuces = new Alert(Alert.AlertType.CONFIRMATION);
                    alertSuces.setTitle("Succes");
                    alertSuces.setContentText("Bien supprimé");
                    alertSuces.showAndWait();



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






                } catch (Exception e) {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Error");
                    alertError.setContentText("Non supprimé");
                    alertError.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println("erreur");
            ;
        }




    }

    @FXML
    void Information(MouseEvent event) {


    }

    @FXML
    void Informer(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
