package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
            try {
                // Check if videomedia and titleLabel are null
                if (videomedia == null || title == null) {
                    System.err.println("FXML elements not injected properly.");
                    return;
                }



                System.out.println("Video Title: " + video.getTitle());

                Media media = new Media(new File(video.getFile_path()).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);

                videomedia.setMediaPlayer(mediaPlayer);

                title.setText(video.getTitle());

                mediaPlayer.play();
            }catch (Exception e) {
                    System.err.println("Error setting video data: " + e.getMessage());
                    e.printStackTrace();
                }
        }


}
