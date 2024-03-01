package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {

    private final String URL = "jdbc:mysql://localhost:3306/gestionusersdb";
    private final String USER = "root";
    private final String PSW = "";

    private static Connection connection;
    private static MyDataBase instance;

    private MyDataBase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PSW);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            // Gérer l'exception de manière appropriée
        }
    }

    public static MyDataBase getInstance() {
        if (instance == null)
            instance = new MyDataBase();
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection fermée");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                // Gérer l'exception de manière appropriée
            }
        }
    }
}
