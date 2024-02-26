package tn.esprit.services;

import tn.esprit.entities.Coord;
import tn.esprit.entities.Exercice;
import tn.esprit.entities.Sexe;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceExercice implements IService<Exercice> {
    private Connection connection;
    public ServiceExercice() {
        connection = MyDataBase.getInstance().getConnection();
    }


    //----------------------  Ajout --------------------------------

    @Override
    public void ajouter(Exercice exercice) throws SQLException {
        String sql= "INSERT INTO exercices (nom ,description,image,nbr_rep) VALUES ('"+exercice.getNom()+"','"+exercice.getDescription()+"','"+exercice.getImage()+"','"+exercice.getNbr_rep()+"')";
        Statement statement= connection.createStatement();
        statement.executeUpdate(sql);
    }
    //----------------------  modif  --------------------------------


    @Override
    public void modifier(Exercice exercice) throws SQLException {
        String sql = " update exercices set nom  =? ,description = ?,  image=? , nbr_rep=?  where id =? ";
        PreparedStatement PreparedStatement = connection.prepareStatement(sql);

        PreparedStatement.setString(1, exercice.getNom());
        PreparedStatement.setString(2, exercice.getDescription());
        PreparedStatement.setString(3, exercice.getImage());
        PreparedStatement.setInt(4, exercice.getNbr_rep());

        PreparedStatement.setInt(5, exercice.getId());
        PreparedStatement.executeUpdate();
    }

    //----------------------  supp  --------------------------------


    @Override
    public void supprimer(int id) throws SQLException {
        String sql="delete from exercices where id=?";
        PreparedStatement PreparedStatement = connection.prepareStatement(sql);
        PreparedStatement.setInt(1, id);
        PreparedStatement.executeUpdate();
    }

    //----------------------  Aff  --------------------------------


    @Override
    public List<Exercice> afficher() throws SQLException {
        List<Exercice> exercices = new ArrayList<>();
        String sql = "SELECT * FROM exercices";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Exercice e = new Exercice();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setDescription(rs.getString("description"));
                e.setImage(rs.getString("image"));
                e.setNbr_rep(rs.getInt("nbr_rep"));

                exercices.add(e);
            }
        }
        return exercices;
    }
}
