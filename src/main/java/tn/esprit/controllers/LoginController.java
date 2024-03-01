package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label LoginToRegister;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private TextField registerEmail;

    @FXML
    private Label loginForgotPassword;

    @FXML
    private ComboBox<String> registerRoleBox;

    @FXML
    private TextField loginEmail;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button LoginBtn;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerRoleBox.getItems().addAll("Sportif", "Admin", "Coach", "Nutritionist");

        // Ajouter un gestionnaire d'événements pour le clic sur "Forgot password"
        loginForgotPassword.setOnMouseClicked(event -> System.out.println("Forgot password clicked"));

        // Ajouter un gestionnaire d'événements pour le clic sur "LoginToRegister"
        LoginToRegister.setOnMouseClicked(event -> navigateToRegister(event));
    }

    @FXML
    private void handleLogin() {
        String email = loginEmail.getText();
        String password = loginPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Please enter email and password");
            return;
        }

        // Vérification des informations de connexion
        if (isValidLogin(email, password)) {
            // Naviguer vers le tableau de bord approprié en fonction du rôle de l'utilisateur
            User user = getUserByEmail(email);
            if (user != null) {
                switch (user.getRole_user()) {
                    case Sportif:
                        loadDashboard("/SportifProfile.fxml");
                        break;
                    case Admin:
                        loadDashboard("/UsersTable.fxml");
                        break;
                    case Coach:
                        loadDashboard("/CoachProfile.fxml");
                        break;
                    case Nutritionist:
                        loadDashboard("/NutritionistProfile.fxml");
                        break;
                    default:
                        showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid role");
                }
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid email or password");
        }
    }

    private boolean isValidLogin(String email, String password) {
        try {
            ServiceUser serviceUser = new ServiceUser();
            List<User> users = serviceUser.afficher();
            Optional<User> matchingUser = users.stream()
                    .filter(user -> user.getEmail_user().equals(email))
                    .findFirst();

            if (matchingUser.isPresent()) {
                // Si l'utilisateur existe, obtenez le hachage du mot de passe stocké
                User user = matchingUser.get();
                String hashedPasswordFromDB = user.getPassword_user();

                // Hasher le mot de passe saisi
                String hashedPasswordEntered = hashPassword(password);

                // Comparer les hachages
                return hashedPasswordEntered.equals(hashedPasswordFromDB);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "SQL Error", "Error accessing database");
        }
        return false;
    }

    private User getUserByEmail(String email) {
        try {
            ServiceUser serviceUser = new ServiceUser();
            List<User> users = serviceUser.afficher();
            Optional<User> matchingUser = users.stream()
                    .filter(user -> user.getEmail_user().equals(email))
                    .findFirst();

            return matchingUser.orElse(null);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "SQL Error", "Error accessing database");
            return null;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadDashboard(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) LoginBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading dashboard");
        }
    }

    @FXML
    private void navigateToRegister(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
            // Gérer l'erreur de chargement de la vue RegisterForm.fxml
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Gérer l'erreur NoSuchAlgorithmException
            return null;
        }
    }
}
