package org.example.controllers;

import com.jfoenix.controls.JFXButton;
import com.pdfjet.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.entities.Event;
import org.example.services.ServiceEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CarteEventBackController implements Initializable {
    private final ServiceEvent se = new ServiceEvent();
    @FXML
    private AnchorPane EventCarte;

    @FXML
    private Label Event_capacite;

    @FXML
    private Label Event_date;

    @FXML
    private Label Event_date_fin;

    @FXML
    private Label Event_organisateur;

    @FXML
    private ImageView Event_image;

    @FXML
    private Label Event_nom;

    @FXML
    private JFXButton Modfifier_BTN;

    @FXML
    private JFXButton Supprimer_BTN;

    @FXML
    private Label id_Event;


    private Event ev;

    public void setEvent(Event event) {

        System.out.println(event);
        id_Event.setText(String.valueOf(event.getIdEvent()));
        Event_nom.setText(event.getNomEvent());
        Event_capacite.setText(String.valueOf(event.getCapacite()));
        Event_organisateur.setText(event.getOrganisateur());
        if(event.getPath()==null){

            String path="file:///C:/Users/melek/IdeaProjects/BourLaFourme/src/main/resources/Icons/Unimage.png";
            Image image=new Image(path,190,94,false,true);
            Event_image.setImage(image);
        }
        else{
            Image image=new Image(event.getPath(),490,395,false,true);
            Event_image.setImage(image);

        }
        Event_date.setText(String.valueOf(event.getdate_Date_deb()));
        Event_date_fin.setText(String.valueOf(event.getdate_Date_fin()));
    }

    @FXML
    void Supprimer_Event(ActionEvent event) {
        //System.out.println(ev);
        System.out.println(id_Event.getText());
        System.out.println(Integer.parseInt(id_Event.getText()));

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Voulez vouz vraiment supprimer cet evenement ?");
            //alert.setContentText(ev.toString());

            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {

                    se.supprimer(Integer.parseInt(id_Event.getText()));
                    Alert alertSuces = new Alert(Alert.AlertType.CONFIRMATION);
                    alertSuces.setTitle("Succes");
                    alertSuces.setContentText("Bien supprimé");
                    alertSuces.showAndWait();



                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/ListEvent2.fxml"));
                    try {
                        Parent parent = loader.load();

                        // Get the current stage from the event source
                        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        // Replace the current scene with the new one
                        currentStage.setScene(new Scene(parent));
                    } catch (IOException ex) {
                        System.out.println("Erreur: " + ex.getMessage());
                    }






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



    @FXML
    void Modifier_Event(ActionEvent event) throws SQLException {

        List<Event> Events = se.afficher();
        ObservableList<Event> observableList = FXCollections.observableList(Events);

        int i = 0;
        boolean conditionMet = false;
        Event evrecupere = new Event();


        while (i < observableList.size() && !conditionMet) {
            if (observableList.get(i).getIdEvent()==Integer.parseInt(id_Event.getText())) {
                conditionMet = true;
                evrecupere = observableList.get(i);
            }
            i++;
        }

        System.out.println(evrecupere.toString());

        //Event ev = tableEvenement.getSelectionModel().getSelectedItem();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the current stage
        currentStage.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AjoutEvent.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("ereur");
        }

        AjoutEventController ajoutEventController = loader.getController();
        ajoutEventController.setUpdate(true);


        ajoutEventController.setTextField( evrecupere.getNomEvent(), evrecupere.getOrganisateur()  , evrecupere.getType(),+evrecupere.getCapacite(),
                evrecupere.getdate_Date_deb(), evrecupere.getdate_Date_fin(),evrecupere.getIdEvent() , evrecupere.getPath() );


        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
/*
    private void PDF(ActionEvent event) throws FileNotFoundException, IOException, Exception {
        File out = new File("ListeEvenement.pdf");
        try (FileOutputStream fos = new FileOutputStream(out)) {
            PDF pdf = new PDF(fos);
            Page page = new Page(pdf, A4.PORTRAIT);
            Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
            Font f2 = new Font(pdf, CoreFont.HELVETICA);


            Table table = new Table();

            List<List<Cell>> tableData = new ArrayList<>();
            List<Cell> tableRow = new ArrayList<>();

            Cell cell = new Cell(f1,c_id1.getText() );
            tableRow.add(cell);

            cell = new Cell(f1,c_nom1.getText() );
            tableRow.add(cell);

            cell = new Cell(f1,c_type1.getText() );
            tableRow.add(cell);

            cell = new Cell(f1,c_Organisateur1.getText() );
            tableRow.add(cell);

            cell = new Cell(f1,c_Date_Deb1.getText() );
            tableRow.add(cell);

            cell = new Cell(f1,c_Date_Fin1.getText() );
            tableRow.add(cell);

            tableData.add(tableRow);

            List<Event> items = tableEvenements1.getItems();
            for(Event eve :items){
                Cell IDEvenement = new Cell(f2, eve.getIdEvenement());
                Cell NomEvenement = new Cell(f2, eve.getNomEvenement());
                Cell OrganisateurEvenement = new Cell(f2, eve.getOrganisateur());
                Cell TypeEvenement = new Cell(f2, eve.getType());
                Cell DateDebEvenement = new Cell(f2, eve.getDate_Debut().toString());
                Cell DateFinEvenement = new Cell(f2, eve.getDate_Fin().toString());

                tableRow =new ArrayList<>();
                tableRow.add(IDEvenement);
                tableRow.add(NomEvenement);
                tableRow.add(OrganisateurEvenement);
                tableRow.add(TypeEvenement);
                tableRow.add(DateDebEvenement);
                tableRow.add(DateFinEvenement);
                tableData.add(tableRow);
            }
            table.setData(tableData);
            table.setPosition(40f, 60f);
            table.setColumnWidth(0, 50f);
            table.setColumnWidth(1, 60f);
            table.setColumnWidth(2, 80f);
            table.setColumnWidth(3, 100f);
            table.setColumnWidth(4, 110f);
            table.setColumnWidth(5, 110f);
            while(true){
                table.drawOn(page);
                if(!table.hasMoreData()){
                    table.resetRenderedPagesCount();
                    break;

                }
                page = new Page(pdf, A4.PORTRAIT);
            }
            pdf.flush();
           // JOptionPane.showMessageDialog(null, "PDF enregistrÃ© sous le path "+out.getAbsolutePath());
            System.out.println("Saved to " +out.getAbsolutePath());
        }

    }


*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
