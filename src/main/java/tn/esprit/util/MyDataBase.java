package tn.esprit.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {

    private final String URL = "jdbc:mysql://localhost:3306/esm l base";
    private final String USER = "root";
    private final String PSW = "";
    private Connection connection;
    public MyDataBase() {
        try{
            connection = DriverManager.getConnection(URL, USER, PSW);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
