package org.example.controllers;



//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.example.entities.Event;
import org.example.services.ServiceEvent;

//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AfficherEventController implements Initializable {
    private final ServiceEvent se = new ServiceEvent();


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

    ObservableList<Event>  eventtList = FXCollections.observableArrayList();



    public void refreshTable() throws SQLException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);
        tableEvenement.setItems(observableList);

        cd_nom.setCellValueFactory(new PropertyValueFactory<>("NomEvent"));
        cd_Organisateur.setCellValueFactory(new PropertyValueFactory<>("Organisateur"));
        cd_Date_Deb.setCellValueFactory(new PropertyValueFactory<>("Date_deb"));
        cd_Date_Fin.setCellValueFactory(new PropertyValueFactory<>("Date_fin"));
        cd_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        cd_id.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        Image_Col.setCellValueFactory(new PropertyValueFactory<>("Image"));
        Path_Col.setCellValueFactory(new PropertyValueFactory<>("Path"));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {


            loadDate();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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


    private void loadDate() throws SQLException {

        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);
        tableEvenement.setItems(observableList);

        cd_nom.setCellValueFactory(new PropertyValueFactory<>("NomEvent"));
        cd_Organisateur.setCellValueFactory(new PropertyValueFactory<>("Organisateur"));
        cd_Date_Deb.setCellValueFactory(new PropertyValueFactory<>("Date_deb"));
        cd_Date_Fin.setCellValueFactory(new PropertyValueFactory<>("Date_fin"));
        cd_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        cd_id.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        Image_Col.setCellValueFactory(new PropertyValueFactory<>("Image"));
        Path_Col.setCellValueFactory(new PropertyValueFactory<>("Path"));

        Callback<TableColumn<Event, String>, TableCell<Event, String>> cellFoctory = (param) -> {
            // make cell containing buttons
            final TableCell<Event, String> cell = new TableCell<Event, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {


                        JFXButton deleteIcon = new JFXButton("Delete");
                        JFXButton editIcon = new JFXButton("Edit");

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                                        + "-fx-background-color:red;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                                        + "-fx-background-color: #557E86;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                Event ev = tableEvenement.getSelectionModel().getSelectedItem();
                                se.supprimer(ev.getIdEvent());
                                Alert alertSuces = new Alert(Alert.AlertType.CONFIRMATION);
                                alertSuces.setTitle("Succes");
                                alertSuces.setContentText("Bien supprimé");
                                alertSuces.showAndWait();
                                refreshTable();

                            } catch (SQLException ex) {
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setTitle("Error");
                                alertError.setContentText("Non supprimé");
                                alertError.showAndWait();
                            }


                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            Event ev = tableEvenement.getSelectionModel().getSelectedItem();
                            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                            // Close the current stage
                            currentStage.close();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/views/AjoutEvent.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                System.out.println("ereur");
                            }

                            AjoutEventController ajoutEventController = loader.getController();
                            ajoutEventController.setUpdate(true);



                            int i = 0;
                            boolean conditionMet = false;


                            while (i < observableList.size() && !conditionMet) {
                                if (observableList.get(i).getIdEvent()==ev.getIdEvent()) {
                                    conditionMet = true;
                                    cap = observableList.get(i).getCapacite();
                                }
                                i++;
                            }


                            ajoutEventController.setTextField( ev.getNomEvent(), ev.getOrganisateur()  , ev.getType(),+ev.getCapacite(),
                                    ev.getdate_Date_deb(), ev.getdate_Date_fin(),ev.getIdEvent() , ev.getPath());
                            Parent parent = loader.getRoot();
                            System.out.println(ev);
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

                        });
                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        cd_action.setCellFactory(cellFoctory);
        tableEvenement.setItems(observableList);
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

}














