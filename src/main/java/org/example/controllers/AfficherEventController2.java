package org.example.controllers;
import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.pdfjet.*;
import com.pdfjet.Cell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.entities.Event;
import org.example.services.ServiceEvent;


import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;





public class AfficherEventController2 implements Initializable {
    private final ServiceEvent se = new ServiceEvent();
    public AnchorPane AdminDashBoardPane;


    @FXML
    private com.gluonhq.charm.glisten.control.TextField searchByName;

    @FXML
    private JFXButton Stats;



    @FXML
    private AnchorPane Event_pane;

    @FXML
    private AnchorPane Reservation_pane;

    @FXML
    private GridPane reservation_gridpane;

    @FXML
    private ScrollPane reservation_scrollPane;

    @FXML
    private Button btnLogout;

    int cap;


    @FXML
    private ScrollPane scroll_back;

    @FXML
    private GridPane grid_back;

    @FXML
    private TableColumn<?, ?> cd_Date_Deb;

    @FXML
    private TableColumn<?, ?> cd_Date_Fin;

    @FXML
    private TableColumn<?, ?> cd_Organisateur;

    @FXML
    private TableColumn<?, ?> cd_id;

    @FXML
    private TableColumn<?, ?> cd_nom;

    @FXML
    private TableColumn<?, ?> cd_type;

    @FXML
    private TableView<Event> tableEvenement;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MenuDisplayCarte();
            afficherReservation();
            affichetableau();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

/*
    @FXML
    void SupprimerEvent(ActionEvent event) {


        Event ev = tableEvenement.getSelectionModel().getSelectedItem();

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Voulez vouz vraiment supprimer cet evenement ?");
            alert.setContentText(ev.toString());

            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {

                    se.supprimer(ev.getIdEvent());
                    Alert alertSuces = new Alert(Alert.AlertType.CONFIRMATION);
                    alertSuces.setTitle("Succes");
                    alertSuces.setContentText("Bien supprimé");
                    alertSuces.showAndWait();


                } catch (Exception e) {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Error");
                    alertError.setContentText("Non supprimé");
                    alertError.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println("erreur");
            ;
        }
    }
*/

    @FXML
    void Deconnecter(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Index.fxml"));
        try {
            Parent parent = loader.load();

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Replace the current scene with the new one
            currentStage.setScene(new Scene(parent));
        } catch (IOException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    @FXML
    void AjouterEvent(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AjoutEvent.fxml"));
        try {
            Parent parent = loader.load();

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Replace the current scene with the new one
            currentStage.setScene(new Scene(parent));
        } catch (IOException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }

    }




    public ObservableList<Event> EventGetData() throws SQLException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);
        return observableList;

    }

    public ObservableList<Event> affichetableau() throws SQLException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);
        tableEvenement.setItems(observableList);

        cd_nom.setCellValueFactory(new PropertyValueFactory<>("NomEvent"));
        cd_Organisateur.setCellValueFactory(new PropertyValueFactory<>("Organisateur"));
        cd_Date_Deb.setCellValueFactory(new PropertyValueFactory<>("Date_deb"));
        cd_Date_Fin.setCellValueFactory(new PropertyValueFactory<>("Date_fin"));
        cd_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        cd_id.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        return observableList;
    }

    public void MenuDisplayCarte() throws SQLException, IOException {
        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);


        //System.out.println("Voici Capacite "+Events.stream().toList().get(2).getCapacite());
        //carteList.clear();
        //carteList.addAll(observableList);


        int row=0;
        int column=0;

        grid_back.getRowConstraints().clear();
        grid_back.getColumnConstraints().clear();



        for (int q=0; q<observableList.size();q++)
        {

            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/views/CarteEventBack.fxml"));
            AnchorPane pane = load.load();
            //Event event = load.getController();
            CarteEventBackController cardE=load.getController();
            cardE.setEvent(observableList.get(q));
            if (column==2){
                column=0;
                row+=1;
            }
            grid_back.add(pane,column++,row);
            GridPane.setMargin(pane,new Insets(16));


        }
    }

    @FXML
    void toEvenement(ActionEvent event) {

        Event_pane.toFront();
        new FadeIn(Event_pane).play();
        Reservation_pane.toBack();

    }

    @FXML
    void toReservation(ActionEvent event) throws SQLException, IOException {
        Reservation_pane.toFront();
        new FadeIn(Reservation_pane).play();
        Event_pane.toBack();
    }


    public void afficherReservation() throws IOException, SQLException {
        List<Event> Events = se.afficherEvnbPlace();
        ObservableList<Event> observableList = FXCollections.observableList(Events);


        System.out.println("Voici Capacite "+Events.stream().toList().get(2).getCapacite());
        //carteList.clear();
        //carteList.addAll(observableList);


        int row=0;
        int column=0;

        reservation_gridpane.getRowConstraints().clear();
        reservation_gridpane.getColumnConstraints().clear();



        for (int q=0; q<observableList.size();q++)
        {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/views/CarteReservationBack.fxml"));
            AnchorPane pane = load.load();
            //Event event = load.getController();
            CarteReservationController cardE=load.getController();
            cardE.setInfoEvent(observableList.get(q));
            if (column==2){
                column=0;
                row+=1;
            }
            reservation_gridpane.add(pane,column++,row);
            reservation_gridpane.setMargin(pane,new Insets(16));

        }
    }


    @FXML
    public void PDF(ActionEvent actionEvent) throws FileNotFoundException {
        File out = new File("ListeEvenement.pdf");
        try (FileOutputStream fos = new FileOutputStream(out)) {
            PDF pdf = new PDF(fos);
            Page page = new Page(pdf, A4.PORTRAIT);
            Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
            Font f2 = new Font(pdf, CoreFont.HELVETICA);
            Font titleFont = new Font(pdf, CoreFont.HELVETICA_BOLD);
            Font normalFont = new Font(pdf, CoreFont.HELVETICA);
            Font headerFont = new Font(pdf, CoreFont.HELVETICA_BOLD_OBLIQUE);
            TextLine p=new TextLine(f1, "\n");

            Paragraph onesp = new Paragraph(p);

            // Title
            TextLine title = new TextLine(titleFont, "Liste des Événements \n");
            title.setFontSize(16f);
            title.setPosition(40f, 40f);
            title.drawOn(page);

// Add a line below the title and center it
            float titleWidth = title.getWidth();
            float titleHeight = title.getHeight();
            float lineY = 40f + titleHeight + 5f; // Adjust the vertical position as needed
            float lineLength = 550f; // Adjust the length of the line as needed
            float lineStartX = (20f) ;
            float lineEndX = lineStartX + lineLength;

            page.setPenWidth(1f);
            page.moveTo(lineStartX, lineY);
            page.lineTo(lineEndX, lineY);
            page.strokePath();

            TextLine date = new TextLine(normalFont, "Date: " + LocalDate.now());
            date.setPosition(page.getWidth() - 140f, 40f);
            date.drawOn(page);

            Table table = new Table();




// Set the y-coordinate for the table at the bottom of the page

            List<List<com.pdfjet.Cell>> tableData = new ArrayList<>();

            List<com.pdfjet.Cell> headerRow = new ArrayList<>();

            // Add header cells
            com.pdfjet.Cell headerCell = new com.pdfjet.Cell(f1, "ID");
            headerCell.setFont(headerFont);
            headerCell.setFgColor(Color.white);
            headerCell.setBgColor(Color.darkgray);
            //headerRow.add(headerCell);

            headerCell = new com.pdfjet.Cell(f1, "Nom");
            headerCell.setFont(headerFont);
            headerCell.setFgColor(Color.white);
            headerCell.setBgColor(Color.darkgray);
            headerRow.add(headerCell);

            headerCell = new com.pdfjet.Cell(f1, "Organisateur");
            headerCell.setFont(headerFont);
            headerCell.setFgColor(Color.white);
            headerCell.setBgColor(Color.darkgray);
            headerRow.add(headerCell);

            headerCell = new com.pdfjet.Cell(f1, "Capacité");
            headerCell.setFont(headerFont);
            headerCell.setFgColor(Color.white);
            headerCell.setBgColor(Color.darkgray);
            headerRow.add(headerCell);

            headerCell = new com.pdfjet.Cell(f1, "Date_debut");
            headerCell.setFont(headerFont);
            headerCell.setFgColor(Color.white);
            headerCell.setBgColor(Color.darkgray);
            headerRow.add(headerCell);


            headerCell = new com.pdfjet.Cell(f1, "Date_fin");
            headerCell.setFont(headerFont);
            headerCell.setFgColor(Color.white);
            headerCell.setBgColor(Color.darkgray);
            headerRow.add(headerCell);




            tableData.add(headerRow);

            List<Event> items = tableEvenement.getItems();
            System.out.println(tableEvenement);
            for (Event eve : items) {
                List<com.pdfjet.Cell> dataRow = new ArrayList<>();

                //com.pdfjet.Cell IDEvenement = new com.pdfjet.Cell(f2,String.valueOf(eve.getIdEvent()));
                com.pdfjet.Cell NomEvenement = new com.pdfjet.Cell(f2,eve.getNomEvent());
                com.pdfjet.Cell OrganisateurEvenement = new com.pdfjet.Cell(f2,eve.getOrganisateur());
                com.pdfjet.Cell CapcitEvenement = new com.pdfjet.Cell(f2,String.valueOf(eve.getCapacite()));
                com.pdfjet.Cell Date_dedEvenement = new com.pdfjet.Cell(f2,String.valueOf(eve.getdate_Date_deb()));
                com.pdfjet.Cell Date_finEvenement = new com.pdfjet.Cell(f2,String.valueOf(eve.getDate_fin()));





                ArrayList<Cell> tableRow = new ArrayList<>();
                //tableRow.add(IDEvenement);
                tableRow.add(NomEvenement);
                tableRow.add(OrganisateurEvenement);
                tableRow.add(CapcitEvenement);
                tableRow.add(Date_dedEvenement);
                tableRow.add(Date_finEvenement);



                tableData.add(tableRow);
            }

            table.setData(tableData);
            table.setPosition(40f, 60f);
            //table.setColumnWidth(0, 50f);
            table.setColumnWidth(0, 150f);
            table.setColumnWidth(1, 80f);
            table.setColumnWidth(2, 70f);
            table.setColumnWidth(3, 90f);
            table.setColumnWidth(4, 90f);
            table.setPosition( 50f, 110f);


            while (true) {
                table.drawOn(page);
                if (!table.hasMoreData()) {
                    table.resetRenderedPagesCount();
                    break;
                }
                page = new Page(pdf, A4.PORTRAIT);
            }

            TextLine title1 = new TextLine(titleFont, "Pour nous contacter !\n");
            title1.setFontSize(12f);
            title1.setPosition(50f, 300f);
            title1.drawOn(page);

            TextLine title2 = new TextLine(f2, "+216 99 666 444\n");
            title2.setFontSize(10f);
            title2.setPosition(50f, 320f);
            title2.drawOn(page);

            TextLine title3 = new TextLine(titleFont, "Veuillez consulter notre page web\n");
            title3.setFontSize(12f);
            title3.setPosition(50f, 350f);
            title3.drawOn(page);

            TextLine title4 = new TextLine(f2, "www.BourLaFourme.com\n");
            title4.setFontSize(10f);
            title4.setPosition(50f, 370f);
            title4.drawOn(page);


            //System.out.println(System.getProperty("user.dir"));

            Image image = new Image(
                    pdf,
                    new FileInputStream(new File("C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/BourLaFourm.png")),
                    ImageType.PNG
            );

            // Positionner l'image et spécifier sa taille
            image.setPosition(100f, 140f);



            // Dessiner l'image sur la page
            image.drawOn(page);





            pdf.flush();
            Alert alertSuces = new Alert(Alert.AlertType.CONFIRMATION);
            alertSuces.setTitle("Succes");
            alertSuces.setContentText("PDF bien enregistré");
            alertSuces.showAndWait();

            System.out.println("Saved to " + out.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    void Stats(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/TestStatas.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("ereur");
        }
        TestStatsController testStatsController = loader.getController();


        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

    }

}

















