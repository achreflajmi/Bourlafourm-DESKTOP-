package tn.esprit.services;

import tn.esprit.entities.Abonnement;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAbonnement implements Iservice<Abonnement>{

    private Connection connection;

    public ServiceAbonnement() {
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Abonnement abonnement) throws SQLException {
        String sql = "INSERT INTO abonnement (nomprenom,type,sexe,age,id_kine) VALUES ('"+ abonnement.getNomprenom() +"','"+abonnement.getType()+"','"+abonnement.getSexe()+"','"+abonnement.getAge()+"','"+abonnement.getId_kine()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifier(Abonnement abonnement) throws SQLException {
        String sql = "Update abonnement set nomprenom = ?, type = ? , sexe= ?, age= ?, id_kine= ? where id_ab = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        ((PreparedStatement) preparedStatement).setString(1,abonnement.getNomprenom());
        preparedStatement.getResultSetType();
        preparedStatement.setString(3, String.valueOf(abonnement.getSexe()));
        preparedStatement.setInt(4,abonnement.getAge());
        preparedStatement.setInt(5,abonnement.getId_kine());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id_ab) throws SQLException {
        String sql= "delete from abonnement where id_ab = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,id_ab);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Abonnement> recuperer() throws SQLException {
        List<Abonnement> abonnements = new ArrayList<>();
        String sql = "Select * from abonnement";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Abonnement a= new Abonnement();
            a.setId_ab(rs.getInt("id_ab"));
            a.setNomprenom(rs.getString("nomp&renom"));
            a.setNomprenom(rs.getString("type"));
            a.setNomprenom(rs.getString("sexe"));
            a.setAge(rs.getInt("age"));
            a.setId_kine(rs.getInt("id_kine"));

            abonnements.add(a);
        }
        return abonnements;
    }
    }

