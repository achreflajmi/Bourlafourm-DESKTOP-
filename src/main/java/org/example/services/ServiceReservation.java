package org.example.services;

import javafx.scene.control.Alert;
import org.example.util.MyDataBase;
import  org.example.entities.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ServiceReservation implements Iservice<Reservation>{
    private Connection connection;

    public ServiceReservation(){
        connection= MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        try {
            String sql = "insert into reservation set  nom_res_event= ?, nbr_place_reserv= ? , Email= ?, id_reser_event=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reservation.getNom_rese_event());
            preparedStatement.setInt(2, reservation.getNbr_place_reserv());
            preparedStatement.setString(3, reservation.getEmail());
            preparedStatement.setInt(4, reservation.getId_reser_event());
            //preparedStatement.setInt(9, event.getIdEvent());

            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("reservation bien ajouté");
            alert.show();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Non ajoute");
            alert.setContentText(e.getMessage());
            alert.show();
        }


    }


    public void MiseAJour(int nbreservation, int idevenement) {
        try {
        String sql = "UPDATE event SET Capacite = Capacite - '"+nbreservation+"' WHERE idEvent  = '"+idevenement+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //initialize(null, null)
        }catch(Exception e){
            System.out.println("ereur");;
        }
    }


    public void MiseAJourNbPlace(int nbreservation, int idevenement) {
        try {
            String sql = "UPDATE event SET nb_place_res = nb_place_res + '"+nbreservation+"' WHERE idEvent  = '"+idevenement+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //initialize(null, null)
        }catch(Exception e){
            System.out.println("ereur");;
        }
    }









    @Override
    public void modifier(Reservation reservation) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<Reservation> afficher() throws SQLException {
        return null;
    }

}
