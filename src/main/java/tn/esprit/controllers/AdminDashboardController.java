package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Recclamation;
import tn.esprit.entities.User;
import tn.esprit.entities.Role_user;
import tn.esprit.entities.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminDashboardController {

    @FXML
    private Button ReportsBtn;

    @FXML
    private TableColumn<User, String> tvFirstName;

    @FXML
    private Button ProductManagBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button HomeBtn;

    @FXML
    private TableColumn<User, String> tvSendMail;

    @FXML
    private TableColumn<User , Integer> tvId;

    @FXML
    private Button GrowthBtn;

    @FXML
    private TableColumn<User , String> tvLastName;

    @FXML
    private TableColumn<Recclamation, String> tvReport;

    @FXML
    private TableColumn<User, Role_user> tvRole;

    @FXML
    private Button UsersBtn;

    @FXML
    private TableColumn<User, String> tvEmail;

    @FXML
    private TableView<User> UsersTableView;



    private ServiceUser userService;

    public AdminDashboardController() {
        userService = new ServiceUser();
    }

    @FXML
    void initialize() throws SQLException {
        // Configure les colonnes pour correspondre aux attributs de l'objet User
        tvId.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        tvFirstName.setCellValueFactory(new PropertyValueFactory<>("prenom_user"));
        tvLastName.setCellValueFactory(new PropertyValueFactory<>("nom_user"));
        tvEmail.setCellValueFactory(new PropertyValueFactory<>("email_user"));
        tvRole.setCellValueFactory(new PropertyValueFactory<>("role_user"));

        // Charger les données depuis la base de données et les afficher dans la TableView
        loadUsersData();
    }

    private void loadUsersData() throws SQLException {
        List<User> users = userService.getAllUsers();
        UsersTableView.getItems().addAll(users);
    }

    @FXML
    void handleLogout(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    void navigateToUsers(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface Users
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Users.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
