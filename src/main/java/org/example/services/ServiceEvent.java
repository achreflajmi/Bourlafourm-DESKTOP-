package org.example.services;

import javafx.scene.control.Alert;
import org.example.entities.Event;
import org.example.util.MyDataBase;
import java.sql.*;


import java.util.ArrayList;
import java.util.List;

public class ServiceEvent implements Iservice <Event>{

    private Connection connection;

    public ServiceEvent(){
        connection= MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Event event) throws SQLException {
        try {
            String sql = "insert into event set NomEvent = ?, Type= ? , organisateur= ?, Date_deb= ? , Date_fin= ?, Capacite= ? , Image= ? , Path= ? ";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1, event.getNomEvent());
            preparedStatement.setString(2, event.getType());
            preparedStatement.setString(3,event.getOrganisateur());
            preparedStatement.setString(4,  event.getDate_deb());
            preparedStatement.setString(5,  event.getDate_fin());
            preparedStatement.setInt(6, event.getCapacite());
            preparedStatement.setBlob(7, event.getImage());
            preparedStatement.setString(8, event.getPath());
            //preparedStatement.setInt(9, event.getIdEvent());

            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("bien ajouté");
            alert.show();

        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Non ajoute");
            alert.setContentText(e.getMessage());
            alert.show();

        }



    }

    @Override
    public void modifier(Event event) throws SQLException {
        String sql = "Update event set NomEvent = ?, Type= ? , organisateur= ?, Date_deb= ? , Date_fin= ?, Capacite= ? , Image= ? , Path= ? where idEvent = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1, event.getNomEvent());
        preparedStatement.setString(2, event.getType());
        preparedStatement.setString(3,event.getOrganisateur());
        preparedStatement.setString(4,  event.getDate_deb());
        preparedStatement.setString(5,  event.getDate_fin());
        preparedStatement.setInt(6, event.getCapacite());
        preparedStatement.setBlob(7, event.getImage());
        preparedStatement.setString(8, event.getPath());
        preparedStatement.setInt(9, event.getIdEvent());


        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int idEvent) throws SQLException {

        String sql= "delete from event where idEvent = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,idEvent);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Event> afficher() throws SQLException {
        List<Event> events= new ArrayList<>();
        String sql = "select * from event";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Event e = new Event();

            e.setNomEvent(rs.getString("NomEvent"));
            e.setOrganisateur(rs.getString("Organisateur"));
            e.setDate_deb(rs.getDate("Date_deb"));
            e.setDate_fin(rs.getDate("Date_fin"));
            e.setType(rs.getString("Type"));
            e.setIdEvent(rs.getInt("idEvent"));
            //e.setCapacite(rs.getInt("Capacite"));
            e.setImage(rs.getBlob("Image"));
            e.setPath(rs.getString("Path"));
            events.add(e);
        }
        return events;
    }
}
