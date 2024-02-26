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
import javafx.stage.StageStyle;
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

public class CarteMesReservationsController implements Initializable {

    @FXML
    private JFXButton Event_annuler;
    @FXML
    private AnchorPane EventCarte;

    @FXML
    private Label Event_capacite;

    @FXML
    private Label Event_date;

    @FXML
    private Label Event_date_fin;

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

    public void setREservation(Reservation reservation){
        iD_reservation.setText(String.valueOf(reservation.getId_reservation()));
    }



    public void setEvent(Event event) {

        System.out.println(event);


        front_id.setText(String.valueOf(event.getIdEvent()));
        Event_nom.setText(event.getNomEvent());
        Event_capacite.setText(String.valueOf(event.getCapacite()));
        if(event.getPath()==null){

            String path="file:///C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/Unimage.png";
            Image image=new Image(path,190,94,false,true);
            Event_image.setImage(image);
        }
        else{
            Image image=new Image(event.getPath(),490,395,false,true);
            Event_image.setImage(image);

        }
        Event_date.setText(String.valueOf(event.getdate_Date_deb()));
        Event_date_fin.setText(String.valueOf(event.getdate_Date_fin()));

    }





    ServiceReservation sr=new ServiceReservation();
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

                    sr.supprimer(Integer.parseInt(iD_reservation.getText()));
                    Alert alertSuces = new Alert(Alert.AlertType.CONFIRMATION);
                    alertSuces.setTitle("Succes");
                    alertSuces.setContentText("Bien supprimé");
                    alertSuces.showAndWait();



                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/ListEvent2.fxml"));
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
ServiceEvent se=new ServiceEvent();
    @FXML
    void Information(MouseEvent event) throws SQLException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);

        int i = 0;
        boolean conditionMet = false;
        Event evinfo = new Event();


        while (i < observableList.size() && !conditionMet) {
            if (observableList.get(i).getIdEvent()==Integer.parseInt(front_id.getText())) {
                conditionMet = true;
                evinfo = observableList.get(i);
            }
            i++;
        }

        System.out.println(evinfo.toString());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/CarteInfo.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("ereur");
        }
        CarteInfoController carteInfoController = loader.getController();


        carteInfoController.setInfoEvent( evinfo.getNomEvent(), evinfo.getOrganisateur() ,evinfo.getCapacite(),
                evinfo.getdate_Date_deb(), evinfo.getdate_Date_fin(),evinfo.getIdEvent() , evinfo.getPath() );
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();


    }

    @FXML
    void Informer(MouseEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
