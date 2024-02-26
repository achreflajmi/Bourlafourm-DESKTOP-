package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import tn.esprit.services.ServiceUser;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField fxEmail;

    @FXML
    private PasswordField fxPassword;

    @FXML
    private ChoiceBox<String> fxRole;

    @FXML
    private CheckBox saveMeCB;

    @FXML
    private Button loginBtn;

    @FXML
    private Label registerLabel;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    void handleLogin(ActionEvent event) {
        String email = fxEmail.getText();
        String password = fxPassword.getText();
        String role = fxRole.getValue();

        // Vérifier les informations d'identification de l'utilisateur
        ServiceUser userService = new ServiceUser();
        boolean isValidCredentials = userService.checkCredentials(email, password, role);

        if (isValidCredentials) {
            // Rediriger l'utilisateur vers la page principale de l'application (Index.fxml)
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Index.fxml"));
                Parent root = loader.load();

                // Récupérer le contrôleur de la vue de l'index
                IndexController indexController = loader.getController();

                // Récupérer la scène actuelle
                Scene scene = fxEmail.getScene();

                // Remplacer la scène actuelle par la scène de l'index
                scene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur si les informations d'identification sont incorrectes
            showAlert("Invalid email or password.");
        }
    }

    private void redirectToDashboard(String role) {
        try {
            String dashboardFXML = null;

            // Déterminez le fichier FXML du tableau de bord en fonction du rôle de l'utilisateur
            switch (role) {
                case "admin":
                    dashboardFXML = "AdminDashboard.fxml";
                    break;
                case "coach":
                    dashboardFXML = "CoachProfile.fxml";
                    break;
                case "nutritionniste":
                    dashboardFXML = "NutritionistProfile.fxml";
                    break;
                case "sportif":
                    dashboardFXML = "SportifProfile.fxml";
                    break;
                default:
                    showAlert("Invalid role.");
                    return;
            }

            // Chargez le fichier FXML du tableau de bord correspondant
            FXMLLoader loader = new FXMLLoader(getClass().getResource(dashboardFXML));
            Parent root = loader.load();

            // Obtenez la scène actuelle et définissez la nouvelle scène avec le tableau de bord chargé
            Scene scene = loginBtn.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert("Error loading dashboard.");
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Autres méthodes pour gérer les actions supplémentaires (inscription, réinitialisation de mot de passe, etc.)
}
