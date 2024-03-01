package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.entities.Role_user;
import tn.esprit.entities.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Charger le formulaire d'inscription (RegisterForm.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UsersTable.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Admin Dashboard");
            primaryStage.show();

            // Créer l'utilisateur Admin et l'ajouter à la base de données
            createAndAddAdminUserToDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAndAddAdminUserToDatabase() {
        // Définir les informations de l'utilisateur Admin
        String adminEmail = "oumasaadi1@example.com";
        String adminPassword = "Ouma_123123"; // Mot de passe en texte brut pour cet exemple
        Role_user adminRole = Role_user.Admin;

        // Hashage du mot de passe
        String hashedAdminPassword = hashPassword(adminPassword);

        // Créer l'utilisateur Admin
        User adminUser = new User("Saadi", "Oumaima", adminEmail, hashedAdminPassword, false, adminRole);

        // Ajouter l'utilisateur Admin à la base de données
        try {
            ServiceUser userService = new ServiceUser();
            userService.ajouter(adminUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(password.getBytes());

            // Convertir le hash en une chaîne hexadécimale
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
}
