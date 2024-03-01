package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import tn.esprit.entities.Role_user;
import tn.esprit.entities.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static tn.esprit.entities.Role_user.Sportif;

public class RegisterController implements Initializable {

    @FXML
    private AnchorPane loginForm;

    @FXML
    private Label RegisterToLogin;

    @FXML
    private Button RegisterBtn;

    @FXML
    private ImageView logo;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private TextField registerEmail;

    @FXML
    private TextField registerFirstName;

    @FXML
    private TextField registerLastName;

    @FXML
    private ComboBox<Role_user> registerRole;
    @FXML
    private TextField registerWeight;
    @FXML
    private TextField registerSize;


    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<Role_user> roleOptions = FXCollections.observableArrayList(Role_user.Sportif,Role_user.Nutritionist,Role_user.Coach);
        registerRole.setItems(roleOptions);

        registerRole.setOnAction(event -> {
            Role_user selectedRole = registerRole.getValue();
            if (selectedRole != null) {
                System.out.println("Selected role: " + selectedRole);
                // You can perform further actions based on the selected role here
            }
            if (selectedRole.equals(Role_user.Sportif)){ // <-- Problematic comparison
                registerWeight.setVisible(true);
                registerSize.setVisible(true);
            }

            if (selectedRole.equals(Role_user.Coach)) {
                registerWeight.setVisible(false);
                registerSize.setVisible(false);
            }
            if (selectedRole.equals(Role_user.Nutritionist)){

                registerWeight.setVisible(false);
                registerSize.setVisible(false);
            }

        });

    }

    @FXML
    public void handleRegister(MouseEvent event) {
        Role_user sportif = Sportif;
        Role_user coach = Role_user.Coach;
        Role_user nutritionist = Role_user.Nutritionist;

        // Récupérer les valeurs des champs
        String nom_user = registerLastName.getText();
        String prenom_user = registerFirstName.getText();
        String email_user = registerEmail.getText();
        String password_user = registerPassword.getText();
        String taille_sportif =registerSize.getText();
        String poids_sportif = registerWeight.getText();
        Role_user Role = registerRole.getValue();

        if (nom_user.isEmpty() || prenom_user.isEmpty() || email_user.isEmpty() || password_user.isEmpty() || (Role == Sportif && (taille_sportif.isEmpty() || poids_sportif.isEmpty()))) {
            showAlert("You have to enter all your credentials.");
            return;
        }
        // Vérifier si le rôle a été sélectionné
        if (Role == null) {
            showAlert("You have to select your role");
            return;
        }

        // Input validation for name
        if (!nom_user.matches("[a-zA-Z]+(\\s[a-zA-Z]+)?")) {
            showAlert("Your last name must contain only letters and at most one space.");
            return;
        }

        // Input validation for surname
        if (!prenom_user.matches("[a-zA-Z]+(\\s[a-zA-Z]+)?")) {
            showAlert("Your first name should contain only letters and at most one space.");
            return;
        }

        // Input validation for email
        if (!email_user.matches("[a-zA-Z][a-zA-Z0-9._]*@[gmail|outlook|yahoo|esprit]+\\.(com|tn)")) {
            showAlert("Your Email should have a valid structure.");
            return;
        }

        // Input validation for password
        if (password_user.length() < 8) {
            showAlert("Password should have at least 8 characters");
            return;
        }

        if (Character.isDigit(password_user.charAt(0))) {
            showAlert("Password must not start with a number");
            return;
        }

        if (!password_user.matches(".*[@*_].*")) {
            showAlert("Password must have at least one special character ( _ or *) ");
            return;
        }

        if (!password_user.matches(".*[A-Z].*")) {
            showAlert("Password must have at least one Uppercase letter");
            return;
        }

        //if (!password_user.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@*_])[A-Za-z0-9@*_]{8,23}$")) {
        //   showAlert("Password should contain at least one uppercase letter, one digit, and one special character");
        //  return;
        //  }

        // Input validation for weight and height (if applicable)
        if (Role == sportif) {
            if (!isValidDouble(taille_sportif) || !isValidDouble(poids_sportif)) {
                showAlert("Weight and height should be valid numeric values.");
                return;
            }
        }


        // Créer un nouvel utilisateur en fonction du rôle sélectionné
        User user = null;
        try {
            if (Role == sportif) {
                if (nom_user.isEmpty() || prenom_user.isEmpty() || email_user.isEmpty() || password_user.isEmpty() || taille_sportif.isEmpty() || poids_sportif.isEmpty()) {
                    showAlert("Veuillez remplir tous les champs.");
                    return;
                }
                double poids = Double.parseDouble(poids_sportif);
                double taille = Double.parseDouble(taille_sportif);
                user = new User(nom_user, prenom_user, email_user, password_user, poids, taille, false,Role);
            } else if (Role == coach || Role == nutritionist) {
                if (nom_user.isEmpty() || prenom_user.isEmpty() || email_user.isEmpty() || password_user.isEmpty()) {
                    showAlert("Veuillez remplir tous les champs.");
                    return;
                }
                user = new User(nom_user, prenom_user, email_user, password_user, false, Role);
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
                navigateToLogin(event);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

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
    void navigateToLogin(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginForm.fxml"));
            Parent root = loader.load();

            // Obtenez la scène actuelle à partir du bouton de registre
            Scene currentScene = RegisterBtn.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            // Créez une nouvelle scène pour le formulaire de connexion
            Scene scene = new Scene(root);

            // Afficher la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
