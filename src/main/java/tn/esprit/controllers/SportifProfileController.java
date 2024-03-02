package tn.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.ServiceUser;
import tn.esprit.util.MyDataBase;
import tn.esprit.services.SessionManager;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class SportifProfileController implements Initializable {

    @FXML
    private Button logoutBtn;

    @FXML
    private TextField profilePassword;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField profileFirstName;

    @FXML
    private Button packsBtn;

    @FXML
    private Button complaintsBtn;

    @FXML
    private Button newstandBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button eventsBtn;

    @FXML
    private TextField profileEmail;

    @FXML
    private Button shopBtn;

    @FXML
    private Button profileBtn;

    @FXML
    private TextField profileWeight;

    @FXML
    private TextField profileLastName;

    @FXML
    private TextField profileSize;

    @FXML
    private Button homeBtn;
    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Obtenez l'utilisateur actuellement connecté
        String loggedInUserEmail = SessionManager.getCurrentUserEmail();
        User currentUser = serviceUser.getUserByEmail(loggedInUserEmail);

        // Remplissez les champs de profil avec les données de l'utilisateur
        if (currentUser != null) {
            profileFirstName.setText(currentUser.getNom_user());
            profileLastName.setText(currentUser.getPrenom_user());
            profileEmail.setText(currentUser.getEmail_user());
            profilePassword.setText(currentUser.getPassword_user());
            if (currentUser.getPoids_sportif() != null) {
                profileSize.setText(String.valueOf(currentUser.getPoids_sportif()));
            }
            if (currentUser.getTaille_sportif() != null) {
                profileWeight.setText(String.valueOf(currentUser.getTaille_sportif()));
            }
        }
    }
    private void initializeUserProfile() {
        // Récupérer les données du sportif connecté à partir du service utilisateur
        User currentUser = serviceUser.getCurrentUser();
        if (currentUser != null) {
            // Remplir les champs de profil avec les données récupérées
            profileFirstName.setText(currentUser.getPrenom_user());
            profileLastName.setText(currentUser.getNom_user());
            profileEmail.setText(currentUser.getEmail_user());
            profilePassword.setText(currentUser.getPassword_user());
            profileSize.setText(String.valueOf(currentUser.getTaille_sportif()));
            profileWeight.setText(String.valueOf(currentUser.getPoids_sportif()));
        }
    }

    @FXML
    void navigateToHome(ActionEvent event) {
        navigateToView("Index.fxml", event);
    }


    @FXML
    void navigateToComplaints(ActionEvent event) {
        navigateToView("ComplaintsForm.fxml", event);
    }

    @FXML
    void navigateToShop(ActionEvent event) {
        navigateToView("Index.fxml", event);
    }

    @FXML
    void navigateToEvents(ActionEvent event) {
        navigateToView("Index.fxml", event);
    }

    @FXML
    void navigateToPacks(ActionEvent event) {
        navigateToView("Index.fxml", event);
    }

    @FXML
    void navigateToNewstand(ActionEvent event) {
        navigateToView("Index.fxml", event);

    }
    private void navigateToView(String viewName, ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(viewName));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Fermer la fenêtre actuelle si nécessaire
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void updateProfile(ActionEvent event) throws SQLException {
        try {String firstName = profileFirstName.getText();
        String lastName = profileLastName.getText();
        String email = profileEmail.getText();
        String password = profilePassword.getText();
        Double size = Double.parseDouble(profileSize.getText());
        Double weight = Double.parseDouble(profileWeight.getText());

        User updatedUser = new User();
        updatedUser.setPrenom_user(firstName);
        updatedUser.setNom_user(lastName);
        updatedUser.setEmail_user(email);
        updatedUser.setPassword_user(password);
        updatedUser.setTaille_sportif(size);
        updatedUser.setPoids_user(weight);
        boolean success = serviceUser.modifier(updatedUser);
        if (success) {
            showAlert("Success", "Profile updated successfully.");
        } else {
            showAlert("Error", "Failed to update profile.");
        }}catch (SQLException e) {
            e.printStackTrace(); // Gérer l'erreur SQLException ici (affichage du message d'erreur ou autre action appropriée)
        }
    }

    @FXML
    void deleteProfile(ActionEvent event) {
        boolean confirmDeletion = showConfirmationAlert("Confirmation", "Are you sure you want to delete your account?");
        if (confirmDeletion) {
            String loggedInUserEmail = SessionManager.getCurrentLoggedInUserEmail();
            User currentUser = serviceUser.getUserByEmail(loggedInUserEmail);
            if (currentUser != null) {
                int userId = currentUser.getId_user(); // Assurez-vous que userId contient l'ID de l'utilisateur à supprimer
                try {
                    serviceUser.supprimer(userId); // Appel de la méthode pour supprimer l'utilisateur
                    showAlert("Success", "Account deleted successfully.");
                    // Implémentez la logique pour naviguer vers la page de connexion ou effectuer d'autres actions après la suppression
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "An error occurred while deleting the account.");
                }
            } else {
                showAlert("Error", "User not found.");
            }
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Ajoutez un bouton d'annulation avec le libellé "Cancel"
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().add(cancelButton);

        // Afficher l'alerte et récupérer la réponse
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

}

