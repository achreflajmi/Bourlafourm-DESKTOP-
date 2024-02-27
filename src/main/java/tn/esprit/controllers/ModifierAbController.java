package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.entities.Abonnement;
import tn.esprit.entities.Kine;
import tn.esprit.services.ServiceAbonnement;
import tn.esprit.services.ServiceKine;

import java.sql.SQLException;

public class ModifierAbController {
    private final ServiceAbonnement sa = new ServiceAbonnement();
    @FXML
    private TextField id_abTF;
    @FXML
    private TextField nomprenomTF;
    @FXML
    private TextField ageTF;
    @FXML
    private TextField id_kineTF;

    @FXML
    private ListView ListType<Type> ;
    @FXML
    private ListView ListSexe<Sexe> ;

    @FXML
    private Button btnmodifier;
    @FXML
    void modifier(ActionEvent event) {
        String stringid= id_abTF.getText();
        try {
            sa.modifier(new Abonnement(;Integer.parseInt(stringid), nomprenomTF.getText(), ageTF.getText(), ListSexe.getInputMethodRequests().getSelectedText()));
            System.out.println("updated");

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());;
        }
    }

}
