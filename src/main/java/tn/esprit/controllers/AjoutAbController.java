package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.entities.Abonnement;
import tn.esprit.entities.Kine;
import tn.esprit.entities.Sexe;
import tn.esprit.entities.Type;
import tn.esprit.services.ServiceAbonnement;
import tn.esprit.services.ServiceKine;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjoutAbController implements Initializable {
/**
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListSexe.getItems().addAll(Sexe.values());
        ListType.getItems().addAll(Type.values());
        ListSexe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sexe>() {
            @Override
            public void changed(ObservableValue<? extends Sexe> observableValue, Sexe sexe, Sexe t1) {

            }
        });

        ListSexe.getSelectionModel().selectedItemProperty().addListener();

    }
**/
    private ListView<Type> ListType;
    private ListView<Sexe> ListSexe;
    private final ServiceAbonnement sa = new ServiceAbonnement();
    @FXML
    private TextField nomprenomTF;
    @FXML
    private TextField ageTF;
    @FXML
    private TextField idkineTF;

    @FXML
    private Button btnajouter;

    public void ajouter(ActionEvent actionEvent) {
        try {
            sa.ajouter(new Abonnement(nomprenomTF.getText(),ListType.getSelectionModel().getSelectedItem(), ListSexe.getSelectionModel().getSelectedItem(), ageTF.getText(), idkineTF.getText()));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

}
}
