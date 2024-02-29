package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.services.ServiceCoord;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.SQLException;

public class UpdateFormController {
    private final ServiceCoord ps = new ServiceCoord();
    @FXML
    private TextField agefx;
    @FXML
    private TextField poidsfx;

    @FXML
    private TextField taillefx;




    @FXML
    void handleSubmitt(ActionEvent event) {
        try {
            int coordinateIdToUpdate = 1;


            int newAge = Integer.parseInt(agefx.getText());
            double newPoids = Double.parseDouble(poidsfx.getText());
            double newTaille = Double.parseDouble(taillefx.getText());


            if (newAge < 0) {
                showAlert("Invalid Age", "Age must be a positive integer.");
                return;
            }

            if (newPoids < 0) {
                showAlert("Invalid Weight", "Weight must be a positive number.");
                return;
            }

            if (newTaille < 0 || newTaille > 3) {
                showAlert("Invalid Height", "Please enter a positive number in meters (0<height<3).");
                return;
            }


            ps.modifierCoordonneesById(coordinateIdToUpdate, newAge, newPoids, newTaille);



            showSuccessAlert("Success", "Information updated successfully!");
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values.");
        } catch (SQLException e) {
            showAlert("Error", "An error occurred while updating coordinates.");
        }
    }
    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }




}
