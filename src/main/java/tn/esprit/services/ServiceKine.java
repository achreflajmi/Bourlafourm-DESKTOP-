package tn.esprit.services;

import tn.esprit.entities.Kine;
import tn.esprit.util.MyDataBase;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceKine implements Iservice<Kine>{

    private Connection connection;

    public ServiceKine() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Kine kine) throws SQLException {
        String sql = "INSERT INTO kine (nomcab, nomkine, adresse) VALUES ('"+ kine.getNomcab()+"','"+kine.getNomkine()+"','"+kine.getAdresse()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
    @Override
    public void modifier(Kine kine) throws SQLException {
        String sql = "Update kine set nomcab = ?, nomkine = ? , adresse= ? where id_kine = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        ((PreparedStatement) preparedStatement).setString(1,kine.getNomcab());
        preparedStatement.setString(2, kine.getNomkine());
        preparedStatement.setString(3,kine.getAdresse());
        preparedStatement.setInt(4,kine.getId_kine());
        preparedStatement.executeUpdate();
    }
    @Override
    public void supprimer(int id_kine) throws SQLException {
        String sql= "delete from kine where id_kine = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,id_kine);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Kine> recuperer() throws SQLException {
            List<Kine> kines = new ArrayList<>();
            String sql = "Select * from kine";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                Kine k= new Kine();
                k.setId_kine(rs.getInt("id_kine"));
                k.setNomcab(rs.getString("nomcab"));
                k.setNomkine(rs.getString("nomkine"));
                k.setAdresse(rs.getString("adresse"));
                kines.add(k);
            }
            return kines;
        }
    }



