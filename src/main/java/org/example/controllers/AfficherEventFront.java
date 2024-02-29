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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.entities.Event;
import org.example.entities.Reservation;
import org.example.services.ServiceEvent;
import org.example.services.ServiceReservation;
import org.example.util.MyDataBase;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AfficherEventFront implements Initializable {




    @FXML
    private AnchorPane AdminDashBoardPane;

    @FXML
    private Button btnArticles;

    @FXML
    private Button btnClients;



    @FXML
    private GridPane event_grip;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnDelete;

    @FXML
    private Button btnEvent;

    @FXML
    private Button btnExercices;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMentors;

    @FXML
    private Button btnRecettes;

    @FXML
    private ImageView btnReduce;

    @FXML
    private AnchorPane event_form;


    @FXML
    private ScrollPane event_scrollpane;

    @FXML
    private Label lbltitle;

    @FXML
    private Pane pnlStatus;

    @FXML
    private AnchorPane Event_Front;

    @FXML
    private GridPane MesEvenements_GridPane;

    @FXML
    private ScrollPane MesEvenements_ScrollPane;

    @FXML
    private AnchorPane Mes_Event_Front;

    private Connection connection;

    public void ServiceEvent(){
        connection= MyDataBase.getInstance().getConnection();
    }



    ServiceEvent se=new ServiceEvent();
    public ObservableList<Event> EventGetData() throws SQLException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);
        return observableList;

    }

    public void MenuDisplayCarte() throws SQLException, IOException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);


        System.out.println(observableList);
        //carteList.clear();
        //carteList.addAll(observableList);


        int row=0;
        int column=0;

        event_grip.getRowConstraints().clear();
        event_grip.getColumnConstraints().clear();



        for (int q=0; q<observableList.size();q++)
        {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/views/CarteEvent.fxml"));
            AnchorPane pane = load.load();
            //Event event = load.getController();
            CarteEventController cardE=load.getController();
            cardE.setEvent(observableList.get(q));
            if (column==2){
                column=0;
                row+=1;
            }
            event_grip.add(pane,column++,row);
            GridPane.setMargin(pane,new Insets(16));


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
    void toEventFront(ActionEvent event) {
        Event_Front.toFront();
        new FadeIn(Event_Front).play();

    }

    @FXML
    void toMesEvenementsFront(ActionEvent event) {
        Mes_Event_Front.toFront();
        new FadeIn(Mes_Event_Front).play();

    }

    ServiceReservation sr = new ServiceReservation();
    public void MesafficherReservation() throws IOException, SQLException {
        List<Reservation> Reservations = sr.afficher();
        ObservableList<Reservation> observableList = FXCollections.observableList(Reservations);

        //carteList.clear();
        //carteList.addAll(observableList);


        int row = 0;
        int column = 0;

        MesEvenements_GridPane.getRowConstraints().clear();
        MesEvenements_GridPane.getColumnConstraints().clear();


        for (int q = 0; q < observableList.size(); q++) {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/views/ListDesReservations.fxml"));
            AnchorPane pane = load.load();
            //Event event = load.getController();
            ListDesReservationsController cardE = load.getController();
            cardE.setReservation(observableList.get(q));
            if (column == 2) {
                column = 0;
                row += 1;
            }
            MesEvenements_GridPane.add(pane, column++, row);
            MesEvenements_GridPane.setMargin(pane, new Insets(16));


        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MenuDisplayCarte();
            MesafficherReservation();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
