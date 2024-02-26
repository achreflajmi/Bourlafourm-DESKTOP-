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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.entities.Event;
import org.example.services.ServiceEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CarteEventController implements Initializable {

    @FXML
    private AnchorPane EventCarte;

    @FXML
    private Label Event_capacite;

    @FXML
    private Label Event_date;

    @FXML
    private ImageView Event_image;

    @FXML
    private Label Event_nom;

    @FXML
    private JFXButton Event_reserv;

    @FXML
    private Label Event_date_fin;

    @FXML
    private Label front_id;

    private Event event;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        Alert alerte = new Alert(Alert.AlertType.WARNING);
        alerte.setTitle("Warning");
        alerte.setContentText("Veuillez saisir le nom de l'organisateur.");
        alerte.show();
    }


    @FXML
    void Event_reserver_btn(ActionEvent event) throws SQLException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);

        int i = 0;
        boolean conditionMet = false;
        Event evrecupere = new Event();


        while (i < observableList.size() && !conditionMet) {
            if (observableList.get(i).getIdEvent()==Integer.parseInt(front_id.getText())) {
                conditionMet = true;
                evrecupere = observableList.get(i);
            }
            i++;
        }

        System.out.println(evrecupere.toString());

        //Event ev = tableEvenement.getSelectionModel().getSelectedItem();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the current stage
        currentStage.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AjoutReservation.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("ereur");
        }

        AjoutReservationController ajoutReservationController = loader.getController();



        ajoutReservationController.setTextFieldReservation(evrecupere.getIdEvent() , evrecupere.getNomEvent(),evrecupere.getCapacite());
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }





}