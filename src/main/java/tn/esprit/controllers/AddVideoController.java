package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Video;
import tn.esprit.services.ServiceVideo;

import java.io.File;

public class AddVideoController {

    ServiceVideo sv = new ServiceVideo();
    @FXML
    private TextField titleVid;


    @FXML
    private void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Video File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Video video = new Video();
            video.setId_user(1);
            video.setTitle(titleVid.getText());
            video.setFile_path(selectedFile.getAbsolutePath());
            sv.AjoutVid(video);
            showAlert("Upload Complete", "Video uploaded successfully.");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            showAlert("Error", "No file selected.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
