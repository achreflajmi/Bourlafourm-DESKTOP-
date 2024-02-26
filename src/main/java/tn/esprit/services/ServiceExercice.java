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

    public void modifierExercicesById(int id, String newname, String newdesc, String path1, int newnbr) throws SQLException {
        Exercice exercice = getExerciceById(id);

        if (exercice != null) {
            exercice.setNom(newname);
            exercice.setDescription(newdesc);
            exercice.setImage(path1);
            exercice.setNbr_rep(newnbr);
            updateExercicesInDatabase(exercice);
        }
    }

    private void updateExercicesInDatabase(Exercice exercice) {
        try {
            String query = "UPDATE exercices SET nom= ?,  description = ?, image = ?, nbr_rep = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, exercice.getNom());
                preparedStatement.setString(2, exercice.getDescription());
                preparedStatement.setString(3, exercice.getImage());
                preparedStatement.setInt(4, exercice.getNbr_rep());
                preparedStatement.setInt(5, exercice.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Exercice getExerciceById(int id) {
        String sql = "SELECT * FROM exercices WHERE id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet rs = null;
        try {
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (rs.next()) {
                Exercice c = new Exercice();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setDescription(rs.getString("description"));
                c.setImage(rs.getString("image"));
                c.setNbr_rep(rs.getInt("nbr_rep"));

                return c;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
