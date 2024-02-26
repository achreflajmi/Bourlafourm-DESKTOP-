package org.example.controllers;
import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.entities.Event;
import org.example.services.ServiceEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherEventController2 implements Initializable {
    private final ServiceEvent se = new ServiceEvent();
    public AnchorPane AdminDashBoardPane;

    @FXML
    private AnchorPane Event_pane;

    @FXML
    private AnchorPane Reservation_pane;

    @FXML
    private GridPane reservation_gridpane;

    @FXML
    private ScrollPane reservation_scrollPane;

    @FXML
    private Button btnLogout;

    int cap;

    @FXML
    private TableColumn<?, ?> cd_Date_Deb;

    @FXML
    private TableColumn<?, ?> Path_Col;

    @FXML
    private TableColumn<?, ?> cd_Date_Fin;

    @FXML
    private TableColumn<?, ?> Image_Col;

    @FXML
    private TableColumn<?, ?> cd_Organisateur;

    @FXML
    private TableColumn<?, ?> cd_nom;

    @FXML
    private TableColumn<?, ?> cd_type;

    @FXML
    private TableColumn<Event, String> cd_action;

    @FXML
    private TableColumn<?, ?> cd_id;

    @FXML
    private TableView<Event> tableEvenement;

    @FXML
    private ScrollPane scroll_back;

    @FXML
    private GridPane grid_back;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MenuDisplayCarte();
            afficherReservation();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void SupprimerEvent(ActionEvent event) {


        Event ev = tableEvenement.getSelectionModel().getSelectedItem();

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Voulez vouz vraiment supprimer cet evenement ?");
            alert.setContentText(ev.toString());

            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {

                    se.supprimer(ev.getIdEvent());
                    Alert alertSuces = new Alert(Alert.AlertType.CONFIRMATION);
                    alertSuces.setTitle("Succes");
                    alertSuces.setContentText("Bien supprimé");
                    alertSuces.showAndWait();


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
    void Deconnecter(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Index.fxml"));
        try {
            Parent parent = loader.load();

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Replace the current scene with the new one
            currentStage.setScene(new Scene(parent));
        } catch (IOException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    @FXML
    void AjouterEvent(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AjoutEvent.fxml"));
        try {
            Parent parent = loader.load();

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Replace the current scene with the new one
            currentStage.setScene(new Scene(parent));
        } catch (IOException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }

    }




    public ObservableList<Event> EventGetData() throws SQLException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);
        return observableList;

    }

    public void MenuDisplayCarte() throws SQLException, IOException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);


        System.out.println("Voici Capacite "+Events.stream().toList().get(2).getCapacite());
        //carteList.clear();
        //carteList.addAll(observableList);


        int row=0;
        int column=0;

        grid_back.getRowConstraints().clear();
        grid_back.getColumnConstraints().clear();



        for (int q=0; q<observableList.size();q++)
        {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/views/CarteEventBack.fxml"));
            AnchorPane pane = load.load();
            //Event event = load.getController();
            CarteEventBackController cardE=load.getController();
            cardE.setEvent(observableList.get(q));
            if (column==2){
                column=0;
                row+=1;
            }
            grid_back.add(pane,column++,row);
            GridPane.setMargin(pane,new Insets(16));


        }
    }

    @FXML
    void toEvenement(ActionEvent event) {

        Event_pane.toFront();
        new FadeIn(Event_pane).play();
        Reservation_pane.toBack();

    }

    @FXML
    void toReservation(ActionEvent event) throws SQLException, IOException {
        Reservation_pane.toFront();
        new FadeIn(Reservation_pane).play();
        Event_pane.toBack();

    }


    public void afficherReservation() throws IOException, SQLException {
        List<Event> Events = se.afficherEvnbPlace();
        ObservableList<Event> observableList = FXCollections.observableList(Events);


        System.out.println("Voici Capacite "+Events.stream().toList().get(2).getCapacite());
        //carteList.clear();
        //carteList.addAll(observableList);


        int row=0;
        int column=0;

        reservation_gridpane.getRowConstraints().clear();
        reservation_gridpane.getColumnConstraints().clear();



        for (int q=0; q<observableList.size();q++)
        {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/views/CarteReservationBack.fxml"));
            AnchorPane pane = load.load();
            //Event event = load.getController();
            CarteReservationController cardE=load.getController();
            cardE.setInfoEvent(observableList.get(q));
            if (column==2){
                column=0;
                row+=1;
            }
            reservation_gridpane.add(pane,column++,row);
            reservation_gridpane.setMargin(pane,new Insets(16));

        }
    }

}














