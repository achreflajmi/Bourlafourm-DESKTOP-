package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.Role_user;
import tn.esprit.entities.User;
import tn.esprit.services.ServiceUser;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static tn.esprit.entities.Role_user.Sportif;


public class SignupController implements Initializable {
    private Role_user Role;

    @FXML
    private ComboBox<Role_user> role;


    @FXML
    private ImageView fxLogo;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField fxSize;

    @FXML
    private TextField fxWeight;

    @FXML
    private TextField fxPrenom;

    @FXML
    private PasswordField fxPassword;

    @FXML
    private RadioButton sportifRadioBtn;

    @FXML
    private TextField fxEmail;

    @FXML
    private Label loginLabel;

    @FXML
    private RadioButton conseillerRadioBtn;

    @FXML
    private TextField fxNom;


    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxWeight.setVisible(false);
        fxSize.setVisible(false);
        Role_user sportif = Sportif;
        Role_user coach = Role_user.Coach;
        Role_user nutritionist = Role_user.Nutritionist;
        ObservableList<Role_user> roleOptions = FXCollections.observableArrayList(Role_user.values());
        role.setItems(roleOptions);

        // Add event handler for ComboBox selection
        role.setOnAction(event -> {
            Role_user selectedRole = role.getValue();
            if (selectedRole != null) {
                System.out.println("Selected role: " + selectedRole);
                // You can perform further actions based on the selected role here
            }
          if (selectedRole==sportif) {
               // System.out.println("ani houni");
                fxWeight.setVisible(true);
                fxSize.setVisible(true);
            }
            if (selectedRole==coach) {
               // System.out.println("ani houni");
                fxWeight.setVisible(false);
                fxSize.setVisible(false);
            }
            if (selectedRole==nutritionist) {
               // System.out.println("ani houni");
                fxWeight.setVisible(false);
                fxSize.setVisible(false);
            }

        });
    }





    // Méthode appelée lors du clic sur le bouton Register
    @FXML
    public void Register(ActionEvent event) {
        Role_user sportif = Sportif;
        Role_user coach = Role_user.Coach;
        Role_user nutritionist = Role_user.Nutritionist;

        // Récupérer les valeurs des champs
        String nom_user = fxNom.getText();
        String prenom_user = fxPrenom.getText();
        String email_user = fxEmail.getText();
        String password_user = fxPassword.getText();
        String taille_sportif = fxSize.getText();
        String poids_sportif = fxWeight.getText();
        Role_user Rolee = role.getValue();

        // Vérifier si le rôle a été sélectionné
        if (Rolee == null) {
            showAlert("You have to select your role");
            return;
        }

        // Input validation for name
        if (!nom_user.matches("[a-zA-Z]+(\\s[a-zA-Z]+)?")) {
            showAlert("Nom should contain only letters and at most one space.");
            return;
        }

        // Input validation for surname
        if (!prenom_user.matches("[a-zA-Z]+(\\s[a-zA-Z]+)?")) {
            showAlert("Prenom should contain only letters and at most one space.");
            return;
        }

        // Input validation for email
        if (!email_user.matches("[a-zA-Z][a-zA-Z0-9._]*@[gmail|outlook|yahoo]+\\.com")) {
            showAlert("Email should have a valid structure.");
            return;
        }

        // Input validation for password
        if (!password_user.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@*_])[A-Za-z0-9@*_]{8,23}$")) {
            showAlert("Password should have a length between 8 and 23 characters and contain at least one uppercase letter, one digit, and one special character");
            return;
        }

        // Input validation for weight and height (if applicable)
        if (Rolee == sportif) {
            if (!isValidDouble(taille_sportif) || !isValidDouble(poids_sportif)) {
                showAlert("Weight and height should be valid numeric values.");
                return;
            }
        }


        // Créer un nouvel utilisateur en fonction du rôle sélectionné
        User user = null;
        try {
            if (Rolee == sportif) {
                if (nom_user.isEmpty() || prenom_user.isEmpty() || email_user.isEmpty() || password_user.isEmpty() || taille_sportif.isEmpty() || poids_sportif.isEmpty()) {
                    showAlert("Veuillez remplir tous les champs.");
                    return;
                }
                double poids = Double.parseDouble(poids_sportif);
                double taille = Double.parseDouble(taille_sportif);
                user = new User(nom_user, prenom_user, email_user, password_user, false, Rolee, poids, taille);
            } else if (Rolee == coach || Rolee == nutritionist) {
                if (nom_user.isEmpty() || prenom_user.isEmpty() || email_user.isEmpty() || password_user.isEmpty()) {
                    showAlert("Veuillez remplir tous les champs.");
                    return;
                }
                user = new User(nom_user, prenom_user, email_user, password_user, false, Rolee);
            }
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer des valeurs numériques valides pour le poids et la taille.");
            return;
        }

        if (user != null) {
            System.out.println("New User added: " + user.toString());
            try {
                ServiceUser sr=new ServiceUser();
                sr.ajouter(user);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Méthode utilitaire pour vérifier si une chaîne est un nombre décimal valide
    private boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @FXML
    void handleLoginLabelClicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/Login.fxml")));
            Stage stage = (Stage) loginLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'erreur de chargement du fichier login.fxml
        }
    }



}
