package org.example.controllers;
import animatefx.animation.FadeIn;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.entities.Event;
import org.example.services.ServiceEvent;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static java.sql.Date.valueOf;

public class AjoutEventController implements Initializable {
    @FXML
    private Pane AjoutEvenement;

    @FXML
    private AnchorPane Ajouter_Pane;

    @FXML
    private JFXButton Ajouter;

    @FXML
    private JFXButton BTNSignIn;

    @FXML
    private JFXButton Browse;


    @FXML
    private JFXButton btnReset;


    @FXML
    private TextField Capacite;

    @FXML
    private DatePicker Date_deb;

    @FXML
    private DatePicker Date_fin;

    @FXML
    private Hyperlink ForgotPass;

    @FXML
    private ImageView Image;

    @FXML
    private TextField NomEvent;

    @FXML
    private TextField Organisateur;

    @FXML
    private TextField Type;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnReduce;


    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private Pane paneLogin;

    @FXML
    private Pane paneSignUp;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField txtusername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image.setImage(new Image("file:///C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/noImage.png"));
    }
    private final ServiceEvent se = new ServiceEvent();

    private Connection con;
    private Statement ste;

    private boolean update;

    String query = null;
    Event event;

    int EventId;

    String s = "file:///C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/noImage.png"  ;
    String p;

    //String K = pa

    @FXML
    void ajout_evenement(ActionEvent event) throws SQLException, ClassNotFoundException {
        ChronoLocalDate today = LocalDate.now();
        System.out.println(today);

        try {
            if (NomEvent.getText().isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.WARNING);
                alerte.setTitle("Warning");
                alerte.setContentText("Veuillez saisir un nom d'événement.");
                alerte.show();

            } else if (Organisateur.getText().isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.WARNING);
                alerte.setTitle("Warning");
                alerte.setContentText("Veuillez saisir le nom de l'organisateur.");
                alerte.show();

            } else if (!isValidPositiveInteger(Capacite.getText()) || Capacite.getText().isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.WARNING);
                alerte.setTitle("Warning");
                alerte.setContentText("Veuillez saisir une capacité valide ");
                alerte.show();

                //JOptionPane.showMessageDialog(null, "champ vide ");
            } else if (Type.getText().isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.WARNING);
                alerte.setTitle("Warning");
                alerte.setContentText("Veuillez saisir le type de l'événement.");
                alerte.show();

            }
            else if (Date_deb.getValue() == null || Date_fin.getValue() == null ) {
                Alert alerte = new Alert(Alert.AlertType.WARNING);
                alerte.setTitle("Warning");
                alerte.setContentText("Veuillez Saisir les dates");
                alerte.show();

            }
            else if (Date_deb.getValue().isBefore(today) || Date_fin.getValue().isBefore(today) ) {
                Alert alerte = new Alert(Alert.AlertType.WARNING);
                alerte.setTitle("Warning");
                alerte.setContentText("Veuillez Saisir des dates dès la date d'aujourd'hui");
                alerte.show();

            }
            else if (Date_deb.getValue().isAfter(Date_fin.getValue())) {
                Alert alerte = new Alert(Alert.AlertType.WARNING);
                alerte.setTitle("Warning");
                alerte.setContentText("La date de début ne peut pas être après la date de fin.");
                alerte.show();

            } else {

                if (update == true) {


                    se.modifier(new Event(Idev, (NomEvent.getText()), Type.getText(), Organisateur.getText(), valueOf(Date_deb.getValue()), valueOf(Date_fin.getValue()), Integer.valueOf(Capacite.getText()), null, s));
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/ListEvent2.fxml"));
                    try {
                        Parent parent = loader.load();
                        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentStage.setScene(new Scene(parent));
                    } catch (IOException ex) {
                        System.out.println("Erreur: " + ex.getMessage());
                    }
                }else {

                   // InputStream ig = new FileInputStream(new File(p));
                    se.ajouter(new Event((NomEvent.getText()), Type.getText(), Organisateur.getText(), valueOf(Date_deb.getValue()), valueOf(Date_fin.getValue()), Integer.parseInt(Capacite.getText()), null, s));
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/ListEvent2.fxml"));
                    Parent parent = loader.load();
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(new Scene(parent));
                    NomEvent.setText("");
                    Organisateur.setText("");
                    Type.setText("");
                    Capacite.setText("");
                    Image.setImage(new Image("file:///C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/noImage.png"));
                }

            }
        } catch (SQLException e) {

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidPositiveInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0; // Ensure it's a positive integer
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    void reset(ActionEvent event) {
        NomEvent.setText("");
        Organisateur.setText("");
        Type.setText("");
        Capacite.setText("");
        Image.setImage(new Image("file:///C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/noImage.png"));

    }

    @FXML
    void RecupererImage(ActionEvent event) throws SQLException {
        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showOpenDialog(null);

        if(f != null)
        {
            System.out.println("Selected file est "+f.getAbsolutePath());
            String path = f.getAbsolutePath();
            Image.setImage(new Image("file:///"+path));
            s = "file:///"+path;
            p = f.getAbsolutePath();
        }else {
            List<Event> Events = se.afficher();
            ObservableList<Event> observableList = FXCollections.observableList(Events);

            int i = 0;
            boolean conditionMet = false;
            Event evinfo = new Event();


            while (i < observableList.size() && !conditionMet) {
                if (observableList.get(i).getIdEvent()==Idev) {
                    conditionMet = true;
                    evinfo = observableList.get(i);
                }
                i++;
            }
            System.out.println("Voici "+evinfo);

            s = evinfo.getPath();

        }

    }

    int Idev;
    void setTextField(String nomEvent, String type,String organisateur,  int capacite,  java.util.Date date_deb, java.util.Date date_fin, int idEvent , String pathrecupere) {

        Idev = idEvent;
        NomEvent.setText(nomEvent);
        Organisateur.setText(organisateur);
        Capacite.setText(String.valueOf(capacite));
        Type.setText(type);
        Date_deb.setValue(valueOf(String.valueOf(date_deb)).toLocalDate());
        Date_fin.setValue(valueOf(String.valueOf(date_fin)).toLocalDate());
        Image.setImage(new Image(pathrecupere));
        System.out.println("voici image"+"file:///"+pathrecupere);
    }






    void setUpdate(boolean b) {
        this.update = b;

    }


    @FXML
    void RetournerAJout(MouseEvent event) {
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



    }

}
