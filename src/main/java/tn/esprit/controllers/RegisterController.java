package tn.esprit.controllers;

import java.awt.event.ActionEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.entities.Role_user;
import tn.esprit.entities.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private AnchorPane loginForm;

    @FXML
    private Label RegisterToLogin;

    @FXML
    private Button RegisterBtn;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Role_user> roleOptions = FXCollections.observableArrayList(Role_user.Sportif, Role_user.Coach, Role_user.Nutritionist);
        registerRole.setItems(roleOptions);

        registerRole.setOnAction(event -> {
            Role_user selectedRole = registerRole.getValue();
            if (selectedRole != null && selectedRole == Role_user.Sportif) {
                registerWeight.setVisible(true);
                registerSize.setVisible(true);
            } else {
                registerWeight.setVisible(false);
                registerSize.setVisible(false);
            }
        });
    }

    @FXML
    public void handleRegister(MouseEvent event) {
        // Validation des champs
        String nom_user = registerLastName.getText();
        String prenom_user = registerFirstName.getText();
        String email_user = registerEmail.getText();
        String password_user = registerPassword.getText();
        String taille_sportif = registerSize.getText();
        String poids_sportif = registerWeight.getText();
        Role_user Role = registerRole.getValue();

        // Vérification des champs vides
        if (nom_user.isEmpty() || prenom_user.isEmpty() || email_user.isEmpty() || password_user.isEmpty() ||
                (Role == Role_user.Sportif && (taille_sportif.isEmpty() || poids_sportif.isEmpty()))) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Validation de l'email, du mot de passe, etc. (restez conforme à votre logique)

        // Hashage du mot de passe
        String hashedPassword = hashPassword(password_user);

        // Création de l'utilisateur
        User user = createUser(Role, nom_user, prenom_user, email_user, hashedPassword, taille_sportif, poids_sportif);

        if (user != null) {
            System.out.println("New User added: " + user.toString());
            try {
                ServiceUser sr = new ServiceUser();
                sr.ajouter(user);
                navigateToLogin(event);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User createUser(Role_user Role, String nom_user, String prenom_user, String email_user,
                            String hashedPassword, String taille_sportif, String poids_sportif) {
        try {
            switch (Role) {
                case Sportif:
                    double poids = Double.parseDouble(poids_sportif);
                    double taille = Double.parseDouble(taille_sportif);
                    return new User(nom_user, prenom_user, email_user, hashedPassword, poids, taille, false, Role);
                case Coach:
                case Nutritionist:
                    return new User(nom_user, prenom_user, email_user, hashedPassword, false, Role);
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer des valeurs numériques valides pour le poids et la taille.");
            return null;
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void navigateToLogin(MouseEvent event) {
        try {
            System.out.println("Done");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Index.fxml"));
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
