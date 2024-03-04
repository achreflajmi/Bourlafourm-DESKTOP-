package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import tn.esprit.entities.Video;

import java.io.File;

public class VideoItemController {

    @FXML
    private MediaView videomedia;

    @FXML
    private Label title;

    public void setVideoData(Video video) {
        title.setText(video.getTitle());
        Media media = new Media(new File(video.getFile_path()).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        videomedia.setMediaPlayer(mediaPlayer);


    }

    public static void setControllerForGridPane(GridPane gridPane, VideoItemController controller) {
                gridPane.setUserData(controller);
            }
        }



