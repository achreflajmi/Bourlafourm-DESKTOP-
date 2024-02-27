package tn.esprit.service;
import tn.esprit.entities.Categorie;
import tn.esprit.entities.Panier;
import tn.esprit.entities.Produit;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServicePanier implements IService<Panier> {
    private Connection connection;

    public ServicePanier() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Panier panier) throws SQLException {
        String sql = "INSERT INTO panier (id_user, total_panier, id_prod, quantite_panier) VALUES ('"+panier.getId_user()+"', '"+panier.getTotal_panier()+"', '"+panier.getId_prod()+"', '"+panier.getQuantite_panier()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifier(Panier panier) throws SQLException {
        String sql = "update panier set  id_user = ?, total_panier = ?, id_prod = ?, quantite_panier = ? where id_panier = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, panier.getId_user());
        preparedStatement.setDouble(2, panier.getTotal_panier());
        preparedStatement.setInt(3, panier.getId_prod());
        preparedStatement.setInt(4, panier.getQuantite_panier());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Panier> supprimer(int id_panier) throws SQLException {
        String sql = "delete from panier where id_panier = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id_panier);
        preparedStatement.executeUpdate();
        return null;
    }

    @Override
    public List<Panier> recuperer() throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String sql ="Select * from panier";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Panier pa = new Panier();
            pa.setId_panier(rs.getInt("id_panier"));
            pa.setId_user(rs.getInt("id_user"));
            pa.setTotal_panier(rs.getDouble("total_panier"));
            pa.setId_prod(rs.getInt("id_prod"));
            pa.setQuantite_panier(rs.getInt("quantite_panier"));
            paniers.add(pa);
        }
        return paniers;
    }

    @Override
    public Categorie getById(int id) throws SQLException {
        return null;
    }

    @Override
    public Categorie getCategorieByCategorieId(int CategorieId) throws SQLException {
        return null;
    }

    @Override
    public Produit getProduitByProduitId(int ProduitId) throws SQLException {
        return null;
    }

    @Override
    public Produit getByIdP(int id) throws SQLException {
        return null;
    }
    public int fetchProduitByInfo(String nomProd, double prixProd, String imageProd) throws SQLException {

        String query = "SELECT id_prod FROM produit WHERE nom_prod = ?, prix_prod = ?, image_prod = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nomProd);
            statement.setDouble(2, prixProd);
            statement.setString(1, imageProd);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_prod");
                }
            }
        }
        return -1;
    }
}
