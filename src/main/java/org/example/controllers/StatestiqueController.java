package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatestiqueController implements Initializable {
    @FXML
    private BarChart<String, Integer> StatLoading;
    @FXML
    private NumberAxis Yaxis;
    @FXML
    private CategoryAxis Xaxis;
    @FXML
    private PieChart PieChartStat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Yaxis.setLabel("Capacité");
        try {
            //Xaxis.setLabel("Evenement");
            load();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StatestiqueController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StatestiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Loading();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PieChartStat.setData(PieChartData);

    }


    ObservableList<PieChart.Data> PieChartData;
    ArrayList<Integer> cell = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    Connection conn;
    PreparedStatement pre;
    ResultSet rs;

    public void load() throws ClassNotFoundException, SQLException {
        PieChartData = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            Statement stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pre = conn.prepareStatement("Select NomEvent, Capacite FROM event ");
        rs = pre.executeQuery();
        while (rs.next()) {
            PieChartData.add(new PieChart.Data(rs.getString("NomEvent"), rs.getInt("Capacite")));

            name.add(rs.getString("NomEvent"));
            cell.add(rs.getInt("Capacite"));
        }
    }

    public void Loading() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL = "Select NomEvent, Capacite FROM  event ORDER BY NomEvent";
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Capacité par évenement");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            stmt = conn.createStatement();
            try {
                rs = conn.createStatement().executeQuery(SQL);
                while (rs.next()) {
                    series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
                }
                StatLoading.getData().add(series);
            } catch (Exception e) {
                System.out.println("probleme");
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}