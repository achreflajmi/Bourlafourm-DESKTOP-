package tn.esprit.services;

import tn.esprit.entities.Video;
import tn.esprit.util.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceVideo {
    private Connection connection;

    public ServiceVideo() {connection = MyDataBase.getInstance().getConnection();}

    public void AjoutVid(Video video){
        String sql = "INSERT INTO videos (title, file_path) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, video.getTitle());
            preparedStatement.setString(2, video.getFile_path());
            preparedStatement.executeUpdate();
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Video> Afficher() {
        List<Video> videos = new ArrayList<>();

        String sql = "SELECT * FROM videos";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Video video = new Video();
                video.setId_user(resultSet.getInt("id"));
                video.setTitle(resultSet.getString("title"));
                video.setFile_path(resultSet.getString("file_path"));
                // Set other properties as needed
                videos.add(video);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching videos from the database: " + e.getMessage(), e);
        }

        return videos;
    }

}
