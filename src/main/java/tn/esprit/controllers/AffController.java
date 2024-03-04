package tn.esprit.controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.pdfjet.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.Coord;
import tn.esprit.entities.Exercice;
import tn.esprit.entities.Regime;
import tn.esprit.entities.Video;
import tn.esprit.services.ServiceCoord;
import tn.esprit.services.ServiceExercice;
import tn.esprit.services.ServiceRegime;
import tn.esprit.services.ServiceVideo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AffController {
    @FXML
    private Button dietBT,detailsBT,sportBT,upeBT, pdf;
    @FXML
    private VBox vBox;
    @FXML
    private GridPane gridP;
    @FXML
    private Label sexeLabel,imcLabel,ageLabel,dejL,poidsLabel,coldejL,dinerL,tailleLabel,pettidejL,ColpdejL;

    @FXML
    private Label nameLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Label nbrLabel;
    @FXML
    private ImageView imageViewl;
   // @FXML
   //    private WebView recaptchaWebView;
    private final int refreshIntervalInSeconds = 5; // Refresh every 5 seconds
    private Timeline refreshTimeline;
    VideoItemController controller = new VideoItemController();

    public void initialize() throws SQLException, IOException {

        serviceCoord = new ServiceCoord();
        serviceRegime = new ServiceRegime();
        serviceExercice = new ServiceExercice();
        serviceVideo = new ServiceVideo();

      //  AfficherVideos();
        afficherCoordonneesParId(1);
        afficherRegimeParId(1);
        //afficherParIdExercice(1);
        // Set up the refresh timeline
        setupRefreshTimeline();
    }

    private ServiceCoord serviceCoord;
    private ServiceRegime serviceRegime;
    private ServiceExercice serviceExercice;
    private ServiceVideo serviceVideo;



    //----------------------  Affichage  --------------------------------

   /*
    private void afficherParIdExercice(int id) throws SQLException {

            Exercice exercices = serviceExercice.getExerciceById(id);
        if (exercices != null) {


            nameLabel.setText("Nom: " + exercices.getNom());
            descLabel.setText("Description: " + exercices.getDescription());
            nbrLabel.setText("nbr repitition: " + exercices.getNbr_rep());
            Image image = new Image(exercices.getImage());

           imageViewl.setImage(image);

        }
    }
    */

    @FXML
    private GridPane gridPa;


    private  void AfficherVideos(){


           List<Video> videos = serviceVideo.Afficher();

       if (gridPa != null) {
               int rowIndex = 0;
               for (Video video : videos) {
                   try {

                       FXMLLoader loader = new FXMLLoader(getClass().getResource("/video_item.fxml"));
                       Parent videoItem = loader.load();
                       System.out.println("FXML Loaded Successfully"); // Add this line

                       // Get the controller
                       VideoItemController videoItemController = loader.getController();
                       System.out.println("Controller: " + videoItemController); // Add this line

                       // Set the exercise data in the controller
                       videoItemController.setVideoData(video);

                       // Add the exerciceItem to the GridPane
                       gridPa.add(videoItem, 0, rowIndex);

                       // Adjust the row constraints
                       RowConstraints rowConstraints = new RowConstraints();
                       gridPa.getRowConstraints().add(rowConstraints);

                       rowIndex++;
                   } catch (IOException e) {
                       System.err.println("Error loading ExerciceItem.fxml: " + e.getMessage());
                       e.printStackTrace();
                   }
               }
           } else {
               System.err.println("GridPane is not initialized.");
           }
       }


    private void afficherRegimeParId(int id) {
        try {

            Regime regime = serviceRegime.getRegimeById(id);

            if (regime != null) {
                pettidejL.setText("Petit Déjeuner: " + regime.getPetitdej());
                ColpdejL.setText("Collation Petit Déjeuner: " + regime.getColpdej());
                dejL.setText("Déjeuner: " + regime.getDej());
                coldejL.setText("Collation Déjeuner: " + regime.getColdej());
                dinerL.setText("Dîner: " + regime.getDiner());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void afficherCoordonneesParId(int id) {
        try {
            Coord coordonnees = serviceCoord.getCoordById(id);


            if (coordonnees != null) {


                sexeLabel.setText("Sexe: " + coordonnees.getSexe());
                ageLabel.setText("Age: " + coordonnees.getAge());
                poidsLabel.setText("Poids: " + coordonnees.getPoids());
                tailleLabel.setText("Taille: " + coordonnees.getTaille());
                imcLabel.setText("IMC: " + coordonnees.getImc());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //----------------------  Modif --------------------------------
    @FXML
    void UpdateExercice(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateExercice.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update DIET Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleUpdateRegime(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateRegime.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update DIET Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void handleUpdateButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateForm.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //----------------------  Ajout  --------------------------------

    @FXML
    void AjoutExercice(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutExercice.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Exercice Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    @FXML
    void AjoutRegime(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutR.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add DIET Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    @FXML
    void AjoutCoord(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCoord.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add DETAILS Form");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }}




    private void setupRefreshTimeline() {
        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(refreshIntervalInSeconds), this::refreshData));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    private void refreshData(ActionEvent event) {

        afficherCoordonneesParId(1);
        afficherRegimeParId(1);
        //afficherParIdExercice(1);
    }

//--------------------------------------- Video ----------------------------------------------
    public void AddVid(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddVideo.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Video");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRefreshTimeline() {
        refreshTimeline.stop();
    }
}
