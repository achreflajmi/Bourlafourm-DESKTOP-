package tn.esprit.services;


import tn.esprit.entities.Role_user;
import tn.esprit.entities.User;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {
    private Connection connection;
    public ServiceUser(){
        connection = MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "INSERT INTO `user`(`nom_user`, `prenom_user`, `email_user`, `password_user`, `role_user`, `poids_sportif`, `taille_sportif`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getNom_user());
            preparedStatement.setString(2, user.getPrenom_user());
            preparedStatement.setString(3, user.getEmail_user());
            preparedStatement.setString(4, user.getPassword_user());
            preparedStatement.setString(5, user.getRole_user().toString());

            // Vérifier si le rôle de l'utilisateur est un sportif
            if (user.getRole_user().equals(Role_user.Sportif)) {
                // Si oui, ajouter également le poids et la taille
                preparedStatement.setDouble(6, user.getPoids_sportif());
                preparedStatement.setDouble(7, user.getTaille_sportif());
            } else {
                // Sinon, définir le poids et la taille sur null
                preparedStatement.setNull(6, Types.DOUBLE);
                preparedStatement.setNull(7, Types.DOUBLE);
            }

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(User user) throws SQLException {
        String sql = "UPDATE `user` SET `nom_user` = ?, `prenom_user` = ?, `email_user` = ?, `password_user` = ?, `role_user` = ?, `poids_sportif` = ?, `taille_sportif` = ? WHERE `id_user` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getNom_user());
            preparedStatement.setString(2, user.getPrenom_user());
            preparedStatement.setString(3, user.getEmail_user());
            preparedStatement.setString(4, user.getPassword_user());
            preparedStatement.setString(5, user.getRole_user().toString());

            // Vérifier si poids_sportif est null
            if (user.getPoids_sportif() != null) {
                preparedStatement.setDouble(6, user.getPoids_sportif());
            } else {
                preparedStatement.setNull(6, Types.DOUBLE);
            }

            // Vérifier si taille_sportif est null
            if (user.getTaille_sportif() != null) {
                preparedStatement.setDouble(7, user.getTaille_sportif());
            } else {
                preparedStatement.setNull(7, Types.DOUBLE);
            }

            preparedStatement.setInt(8, user.getId_user());
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM `user` WHERE `id_user` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<User> afficher() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User u = new User();
                u.setId_user(rs.getInt("id_user"));
                u.setNom_user(rs.getString("nom_user"));
                u.setPrenom_user(rs.getString("prenom_user"));
                u.setEmail_user(rs.getString("email_user"));
                u.setPassword_user(rs.getString("password_user"));
                u.setRole_user(Role_user.valueOf(rs.getString("role_user")));
                // Vérifier si les colonnes poids_sportif et taille_sportif sont null dans la base de données
                if (rs.getObject("poids_sportif") != null) {
                    u.setPoids_sportif(rs.getDouble("poids_sportif"));
                }
                if (rs.getObject("taille_sportif") != null) {
                    u.setTaille_sportif(rs.getDouble("taille_sportif"));
                }
                users.add(u);
            }
        }
        return users;
    }

    public User getUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM user WHERE email_user = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId_user(rs.getInt("id_user"));
                user.setNom_user(rs.getString("nom_user"));
                user.setPrenom_user(rs.getString("prenom_user"));
                user.setEmail_user(rs.getString("email_user"));
                user.setPassword_user(rs.getString("password_user"));
                user.setRole_user(Role_user.valueOf(rs.getString("role_user")));
                // Vérifier si les colonnes poids_sportif et taille_sportif sont null dans la base de données
                if (rs.getObject("poids_sportif") != null) {
                    user.setPoids_sportif(rs.getDouble("poids_sportif"));
                }
                if (rs.getObject("taille_sportif") != null) {
                    user.setTaille_sportif(rs.getDouble("taille_sportif"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }



}

