package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tn.esprit.entities.Role_user;
import tn.esprit.entities.User;

import java.awt.event.ActionEvent;

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
    private TableColumn<User, Integer> tvId;

    @FXML
    private Button GrowthBtn;

    @FXML
    private TableColumn<User, String> tvLastName;

    @FXML
    private TableColumn<User, String> tvReport;

    @FXML
    private TableColumn<User , Role_user> tvRole;

    @FXML
    private Button UsersBtn;

    @FXML
    private TableColumn<User, String> tvEmail;

    @FXML
    private TableView<User> UsersTableView;

    @FXML
    void navigateToHome(ActionEvent event) {

    }

    @FXML
    void navigateToUsers(ActionEvent event) {

    }

    @FXML
    void navigateToGrowth(ActionEvent event) {

    }

    @FXML
    void navigateToReports(ActionEvent event) {

    }

    @FXML
    void navigateToProductsManag(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

}
