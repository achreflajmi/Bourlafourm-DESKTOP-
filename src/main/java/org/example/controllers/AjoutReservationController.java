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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.entities.Event;
import org.example.entities.Reservation;
import org.example.services.ServiceReservation;
import javax.mail.Message;



import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.sql.Date.valueOf;

public class AjoutReservationController implements Initializable {




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private Pane AjoutRservation;

    @FXML
    private JFXButton Ajout_reservation;

    @FXML
    private JFXButton BTNSignIn;

    @FXML
    private TextField Email;

    @FXML
    private Hyperlink ForgotPass;

    @FXML
    private ImageView Image;

    @FXML
    private TextField Nom_Event_reserver;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnReduce;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private TextField nbr_place;

    @FXML
    private Pane paneLogin;

    @FXML
    private Pane paneSignUp;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField txtusername;

    @FXML
    private Label capacite_res;

    @FXML
    private Label id_evenement;

    @FXML
    void Retourner(MouseEvent event) {
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

    }

    ServiceReservation sr=new ServiceReservation();

    @FXML
    void ajouter_reservation(ActionEvent event) throws SQLException, IOException {
        String emailText = Email.getText();
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailText);

        if (Email.getText().isEmpty()) {
            Alert alerte = new Alert(Alert.AlertType.WARNING);
            alerte.setTitle("Warning");
            alerte.setContentText("Veuillez saisir votre mail.");
            alerte.show();
        } else if (!matcher.matches()) {
            Alert alerte = new Alert(Alert.AlertType.WARNING);
            alerte.setTitle("Warning");
            alerte.setContentText("Veuillez saisir un email valide.");
            alerte.show();
    } else if (!isValidPositiveInteger (nbr_place.getText())|| nbr_place.getText().isEmpty()) {
            Alert alerte = new Alert(Alert.AlertType.WARNING);
            alerte.setTitle("Warning");
            alerte.setContentText("Veuillez saisir nombre de place valide");
            alerte.show();
        }
            else if ((Integer.parseInt(nbr_place.getText()) > Integer.parseInt(capacite_res.getText()))) {
                Alert alerte = new Alert(Alert.AlertType.WARNING);
                alerte.setTitle("Warning");
                alerte.setContentText("Nombre de place réserve sup capacité");
                alerte.show();

        }else {
            System.out.println(Nom_Event_reserver.getText());
            sr.ajouter(new Reservation((Nom_Event_reserver.getText()), Integer.parseInt(nbr_place.getText()), Email.getText(), Integer.parseInt(id_evenement.getText())));
        sr.MiseAJour(Integer.parseInt(nbr_place.getText()), Integer.parseInt(id_evenement.getText()));
        sr.MiseAJourNbPlace(Integer.parseInt(nbr_place.getText()), Integer.parseInt(id_evenement.getText()));
        Send();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ListEventFront.fxml"));
        Parent parent = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(parent));


        }
    }

    @FXML
    void reset(ActionEvent event) {
        Email.setText("");
        nbr_place.setText("");

    }


    void setTextFieldReservation(int evId,String nomEvent,int capacite) {

        id_evenement.setText(String.valueOf(evId));
        Nom_Event_reserver.setText(nomEvent);
        capacite_res.setText(String.valueOf(capacite));
    }

    private boolean isValidPositiveInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0; // Ensure it's a positive integer
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void Send() throws IOException{
        String to =Email.getText();
        String from ="hamadimelek0@gmail.com";
        String host = "smtp.gmail.com";
        final String Username = "hamadimelek0@gmail.com";
        final String Password = "lratlnewgzgzydsz";


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.starttls.trust", "smtp.gmail.com");



        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,


                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Username, Password);
                    }
                });
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            message.setSubject("Reservation");
            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Cher Client,\n\n" +
                    "Nous vous remercions de votre réservation pour l'événement " + Nom_Event_reserver.getText() + ".\n" +
                    "Voici un récapitulatif de votre réservation :\n\n" +
                    "- Nom de l'événement : " + Nom_Event_reserver.getText() + "\n" +
                    "- Nombre de places réservées : " + nbr_place.getText() + "\n\n" +
                    "Nous avons hâte de vous accueillir à l'événement.\n\n" +
                    "Cordialement,\n" +
                    "[BourLaFourme]");
            MimeBodyPart pdfAttatchement = new MimeBodyPart();
            pdfAttatchement.attachFile("C:/Users/melek/Downloads/Reservation.png");
            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pdfAttatchement);
            message.setContent(emailContent);
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
