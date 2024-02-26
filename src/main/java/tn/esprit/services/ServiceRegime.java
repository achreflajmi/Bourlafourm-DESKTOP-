package tn.esprit.services;

import tn.esprit.entities.Coord;
import tn.esprit.entities.Regime;
import tn.esprit.entities.Sexe;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRegime implements IService<Regime>{
    private Connection connection;
    public ServiceRegime(){
        connection = MyDataBase.getInstance().getConnection();
    }



    @Override
    public void ajouter(Regime regime) throws SQLException {
        String sql= "INSERT INTO regime (petitdej, colpdej,  dej,  coldej,  diner) VALUES ('"+regime.getPetitdej()+"','"+regime.getColpdej()+"','"+regime.getDej()+"','"+regime.getColdej()+"','"+regime.getDiner()+"')";
        Statement statement= connection.createStatement();
        statement.executeUpdate(sql);

    }


    @Override
    public void modifier(Regime regime) throws SQLException {
        String sql="update regime set petitdej=?, colpdej=?,  dej=?,  coldej=?,  diner=? where id =?";
        PreparedStatement PreparedStatement = connection.prepareStatement(sql);
        PreparedStatement.setString(1, regime.getPetitdej());
        PreparedStatement.setString(2, regime.getColpdej());
        PreparedStatement.setString(3, regime.getDej());
        PreparedStatement.setString(4, regime.getColdej());
        PreparedStatement.setString(5, regime.getDiner());
        PreparedStatement.setInt(6, regime.getId());
        PreparedStatement.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql="delete from regime where id=?";
        PreparedStatement PreparedStatement = connection.prepareStatement(sql);
        PreparedStatement.setInt(1, id);
        PreparedStatement.executeUpdate();

    }

    @Override
    public List<Regime> afficher() throws SQLException {
        List<Regime> regime = new ArrayList<>();
        String sql = "select * from regime";
        Statement statement= connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Regime r = new Regime();
            r.setPetitdej(rs.getString("petitdej"));
            r.setColpdej(rs.getString("colpdej"));
            r.setDej(rs.getString("dej"));
            r.setColdej(rs.getString("coldej"));
            r.setDiner(rs.getString("diner"));
            regime.add(r);
        }
        return regime;
    }

    public Regime getRegimeById(int id) throws SQLException {
        try {
            String sql = "SELECT * FROM regime WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Regime r = new Regime();
                r.setId(rs.getInt("id"));
                r.setPetitdej(rs.getString("petitdej"));
                r.setColpdej(rs.getString("colpdej"));
                r.setDej(rs.getString("dej"));
                r.setColdej(rs.getString("Coldej"));
                r.setDiner(rs.getString("Diner"));
                return r;
            } else {
                return null;
            }
        }catch (SQLException e) {
                e.printStackTrace();
                return null;
        }

    }
    public void afficherRegimeParId(int id) throws SQLException {
        Regime regime = getRegimeById(id);

        if (regime != null) {
            System.out.println("Regime pour l'ID " + id + ":");
            System.out.println("Petit dej: " + regime.getPetitdej());
            System.out.println("Collation: " + regime.getColpdej());
            System.out.println("Dejeuner: " + regime.getDej());
            System.out.println("Collation: " + regime.getColdej());
            System.out.println("Diner: " + regime.getDiner());
        } else {
            System.out.println("Aucun Regime trouvée pour l'ID " + id);
        }
    }

    public void modifierRegimeById(int id, String newBreakfest, String newSnack, String newLunch, String newSnackb, String newDinner) throws SQLException {
        Regime regime = getRegimeById(id);
        if (regime != null) {
            regime.setPetitdej(newBreakfest);
            regime.setColpdej(newSnack);
            regime.setDej(newLunch);
            regime.setColdej(newSnack);
            regime.setDiner(newDinner);
            modifier(regime);
        }
    }

}
