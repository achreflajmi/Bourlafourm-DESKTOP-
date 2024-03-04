package tn.esprit.services;

import tn.esprit.entities.Sexe;
import tn.esprit.util.MyDataBase;
import tn.esprit.entities.Coord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceCoord implements IService<Coord> {

    private Connection connection;
    public ServiceCoord(){
        connection = MyDataBase.getInstance().getConnection();
    }


    //----------------------  Ajout --------------------------------
    @Override
    public void ajouter(Coord coord) throws SQLException {
        String sql= "INSERT INTO coordonnees (age ,sexe,poids,taille,imc) VALUES ('"+coord.getAge()+"','"+coord.getSexe()+"','"+coord.getPoids()+"','"+coord.getTaille()+"','"+coord.getImc()+"')";
        Statement statement= connection.createStatement();
        statement.executeUpdate(sql);
    }

    //----------------------  Modif --------------------------------
    @Override
    public void modifier(Coord coordonnees) throws SQLException {
        String sql = " update coordonnees set sexe =? ,age = ?,  poids=? , taille=? , imc=? where id =? ";
        PreparedStatement PreparedStatement = connection.prepareStatement(sql);

        PreparedStatement.setString(1, String.valueOf(coordonnees.getSexe()));
        PreparedStatement.setInt(2, coordonnees.getAge());
        PreparedStatement.setDouble(3, coordonnees.getPoids());
        PreparedStatement.setDouble(4, coordonnees.getTaille());
        PreparedStatement.setDouble(5, coordonnees.getImc());
        PreparedStatement.setInt(6, coordonnees.getId());
        PreparedStatement.executeUpdate();
    }

    //----------------------  Supp  --------------------------------
    @Override
    public void supprimer(int id) throws SQLException {
        String sql="delete from coordonnees where id=?";
        PreparedStatement PreparedStatement = connection.prepareStatement(sql);
        PreparedStatement.setInt(1, id);
        PreparedStatement.executeUpdate();

    }

    //----------------------  Aff --------------------------------
    @Override
    public List<Coord> afficher() throws SQLException {
        List<Coord> coordonnees = new ArrayList<>();
        String sql = "select * from coordonnees";
        Statement statement= connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Coord c = new Coord();
            c.setSexe(Sexe.valueOf(rs.getString("sexe")));
            c.setAge(rs.getInt("age"));
            c.setPoids(rs.getFloat("poids"));
            c.setTaille(rs.getFloat("taille"));
            c.setImc(rs.getFloat("imc"));
            coordonnees.add(c);
        }
            return coordonnees;
        }

    //----------------------  Aff ById --------------------------------
    public Coord getCoordById(int id) throws SQLException {
        String sql = "SELECT * FROM coordonnees WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            Coord c = new Coord();
            c.setId(rs.getInt("id"));
            c.setSexe(Sexe.valueOf(rs.getString("sexe")));
            c.setAge(rs.getInt("age"));
            c.setPoids(rs.getDouble("poids"));
            c.setTaille(rs.getDouble("taille"));
            c.setImc(rs.getDouble("imc"));
            return c;
        } else {
            return null;
        }
    }
    public void afficherCoordonneesParId(int id) throws SQLException {
        Coord coordonnees = getCoordById(id);

        if (coordonnees != null) {
            System.out.println("Coordonnées pour l'ID " + id + ":");
            System.out.println("Sexe: " + coordonnees.getSexe());
            System.out.println("Age: " + coordonnees.getAge());
            System.out.println("Poids: " + coordonnees.getPoids());
            System.out.println("Taille: " + coordonnees.getTaille());
            System.out.println("IMC: " + coordonnees.getImc());
        } else {
            System.out.println("Aucune coordonnée trouvée pour l'ID " + id);
        }
    }

    //----------------------  modif ByID  --------------------------------

    /*public void modifierCoordonneesById(int id, Coord updatedCoord) throws SQLException {
        String sql = "UPDATE coordonnees SET sexe=?, age=?, poids=?, taille=?, imc=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(updatedCoord.getSexe()));
            preparedStatement.setInt(2, updatedCoord.getAge());
            preparedStatement.setDouble(3, updatedCoord.getPoids());
            preparedStatement.setDouble(4, updatedCoord.getTaille());
            preparedStatement.setDouble(5, updatedCoord.getImc());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
        }
    }

     */

    public void modifierCoordonneesById(int id, int newAge , double newPoids, double newTaille) throws SQLException {
        Coord coord = getCoordById(id);

        if (coord != null) {
            coord.setAge(newAge);
            coord.setPoids(newPoids);
            coord.setTaille(newTaille);


            double imc = calculateIMC(newPoids, newTaille);
            coord.setImc(imc);
            updateCoordonneesInDatabase(coord);
        }
    }


    private double calculateIMC(double poids, double taille) {
        return poids / (taille * taille);
    }


    private void updateCoordonneesInDatabase(Coord coord) {



        try {
            String query = "UPDATE coordonnees SET age= ?,  poids = ?, taille = ?, imc = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, coord.getAge());
                preparedStatement.setDouble(2, coord.getPoids());
                preparedStatement.setDouble(3, coord.getTaille());
                preparedStatement.setDouble(4, coord.getImc());
                preparedStatement.setInt(5, coord.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
