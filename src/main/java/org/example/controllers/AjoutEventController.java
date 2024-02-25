package org.example.controllers;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.entities.Event;
import org.example.services.ServiceEvent;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.sql.Date.valueOf;

public class AjoutEventController implements Initializable {
    @FXML
    private Pane AjoutEvenement;

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
    }
    private final ServiceEvent se = new ServiceEvent();

    private Connection con;
    private Statement ste;

    private boolean update;

    String query = null;
    Event event;

    int EventId;

    String s;
    String p;

    @FXML
    void ajout_evenement(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            if ( NomEvent.getText().isEmpty() || Organisateur.getText().isEmpty() || Type.getText().isEmpty()) {
                Alert alerte = new Alert(Alert.AlertType.WARNING);
                alerte.setTitle("Warning");
                alerte.setContentText("Chmps vide");
                alerte.show();
                //JOptionPane.showMessageDialog(null, "champ vide ");
            } else {

                if (update == true) {

                    se.modifier(new Event(Idev, (NomEvent.getText()), Type.getText(), Organisateur.getText(), valueOf(Date_deb.getValue()), valueOf(Date_fin.getValue()), Integer.valueOf(Capacite.getText()), null, s));
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/ListEvent.fxml"));
                    try {
                        Parent parent = loader.load();
                        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentStage.setScene(new Scene(parent));
                    } catch (IOException ex) {
                        System.out.println("Erreur: " + ex.getMessage());
                    }
                }else {

                    InputStream ig = new FileInputStream(new File(p));
                    se.ajouter(new Event((NomEvent.getText()), Type.getText(), Organisateur.getText(), valueOf(Date_deb.getValue()), valueOf(Date_fin.getValue()), Integer.parseInt(Capacite.getText()), null, s));
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/ListEvent.fxml"));
                    Parent parent = loader.load();
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(new Scene(parent));
                    NomEvent.setText("");
                    Organisateur.setText("");
                    Type.setText("");
                    Capacite.setText("");
                    Image.setImage(new Image("file:///C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/Unimage.png"));
                }

            }
        } catch (SQLException e) {

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void reset(ActionEvent event) {


        NomEvent.setText("");
        Organisateur.setText("");
        Type.setText("");
        Capacite.setText("");
        Image.setImage(new Image("file:///C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/Unimage.png"));

    }

    @FXML
    void RecupererImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showOpenDialog(null);

        if(f != null)
        {
            System.out.println("Selected file est "+f.getAbsolutePath());
            String path = f.getAbsolutePath();
            Image.setImage(new Image("file:///"+path));
            s = "file:///"+path;
            p = f.getAbsolutePath();
        }

    }

    int Idev;
    void setTextField(String nomEvent, String type,String organisateur,  int capacite,  java.util.Date date_deb, java.util.Date date_fin, int idEvent) {

        Idev = idEvent;
        NomEvent.setText(nomEvent);
        Organisateur.setText(organisateur);
        Capacite.setText(String.valueOf(capacite));
        Type.setText(type);
        Date_deb.setValue(valueOf(String.valueOf(date_deb)).toLocalDate());
        Date_fin.setValue(valueOf(String.valueOf(date_fin)).toLocalDate());

    }

    void setUpdate(boolean b) {
        this.update = b;

    }


    @FXML
    void RetournerAJout(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ListEvent.fxml"));
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
