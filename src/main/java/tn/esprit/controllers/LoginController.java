package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class LoginController {

    @FXML
    private Button registerBtn;

    @FXML
    private Label LoginToRegister;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private TextField registerEmail;

    @FXML
    private Label loginForgotPassword;

    @FXML
    private TextField registerFirstName;

    @FXML
    private ComboBox<?> registerRoleBox;

    @FXML
    private TextField loginEmail;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private Label RegisterToLogin;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button LoginBtn;

    @FXML
    private ImageView logo;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private TextField registerLastName;

    @FXML
    void handleLogin(ActionEvent event) {

    }

    @FXML
    void navigateToRegister(MouseEvent event) {

    }


}
