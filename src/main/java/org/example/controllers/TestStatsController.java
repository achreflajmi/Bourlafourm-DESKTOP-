package org.example.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestStatsController implements Initializable {

    @FXML
    private BorderPane borderPane;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            borderPane.setCenter(buildPieChart());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void HandleSowPieChart() throws SQLException {

        borderPane.setCenter(buildPieChart());    }

    @FXML
    private void HandleShowBarChart() {

        borderPane.setCenter(buildBarChart());
    }
    private BarChart<String, Integer> StatLoading;

    private BarChart buildBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Les évenements avec les plus grandes capacitées");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("# of developers x 1000");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL = "Select NomEvent, Capacite FROM  event ORDER BY NomEvent";
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Capacité par évenement");
        BarChart barChart = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            stmt = conn.createStatement();
            try {
                rs = conn.createStatement().executeQuery(SQL);
                while (rs.next()) {
                    series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
                }
                barChart = new BarChart(xAxis, yAxis);

                barChart.getData().add(series);
            } catch (Exception e) {
                System.out.println("probleme");
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


        return barChart;
    }



    ObservableList<PieChart.Data> PieChartData;
    ArrayList<Integer> cell = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    Connection conn;
    PreparedStatement pre;
    ResultSet rs;



    private PieChart buildPieChart() throws SQLException {

        PieChartData = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
            Statement stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        pre = conn.prepareStatement("Select NomEvent, Capacite FROM event ");
        rs = pre.executeQuery();
        while (rs.next()) {
            PieChartData.add(new PieChart.Data(rs.getString("NomEvent"), rs.getInt("Capacite")));

            name.add(rs.getString("NomEvent"));
            cell.add(rs.getInt("Capacite"));
        }

        ContextMenu contextMenu = new ContextMenu(); //create context menu
        MenuItem miSwitchToBarChart = new MenuItem("Switch to Bar Chart");
        contextMenu.getItems().add(miSwitchToBarChart);

        PieChart pieChart = new PieChart(PieChartData); //Creating a Pie chart

        //Add event handler to display context menu
        pieChart.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            contextMenu.show(pieChart, event.getScreenX(), event.getScreenY());
                        }
                    }
                });


        //Before Java 8
        //Add event handler to change chart type (anonymous inner class)
        miSwitchToBarChart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                borderPane.setCenter(buildBarChart());
            }
        });


        //Java 8 and newer (lambda expression)
        miSwitchToBarChart.setOnAction(event -> { borderPane.setCenter(buildBarChart()); });


        return pieChart;
    }

    /**
     *
     */
    @FXML
    private void HandleClose() {
        System.exit(0);
    }


    /**
     *
     */
    @FXML
    private void HandleUpdateData() {
        Node node = borderPane.getCenter();

        if (node instanceof PieChart)
        {
            PieChart pc = (PieChart) node;
            double value = pc.getData().get(2).getPieValue();
            pc.getData().get(2).setPieValue(value * 1.10);
            createToolTips(pc);
        }
    }


    /**
     * Creates tooltips for all data entries
     * @param pc
     */
    private void createToolTips(PieChart pc) {

        for (PieChart.Data data: pc.getData()) {
            String msg = Double.toString(data.getPieValue());

            Tooltip tp = new Tooltip(msg);
            tp.setShowDelay(Duration.seconds(0));

            Tooltip.install(data.getNode(), tp);

            //update tooltip data when changed
            data.pieValueProperty().addListener((observable, oldValue, newValue) ->
            {
                tp.setText(newValue.toString());
            });
        }
    }

}
