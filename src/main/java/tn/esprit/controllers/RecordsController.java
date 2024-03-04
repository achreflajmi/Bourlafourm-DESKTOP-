package tn.esprit.controllers;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Coord;
import tn.esprit.entities.Exercice;
import tn.esprit.entities.Regime;
import tn.esprit.services.ServiceCoord;
import tn.esprit.services.ServiceExercice;
import tn.esprit.services.ServiceRegime;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class RecordsController implements Initializable {
    ServiceExercice se = new ServiceExercice();
    ServiceCoord sc = new ServiceCoord();
    ServiceRegime sr = new ServiceRegime();
    @FXML
    private TextField SearchBar;

    @FXML
    private ListView<Object> listFx; // Change 'Object' to the actual type of your data

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Assuming you have a method to retrieve each type of data
            List<Coord> coordList = sc.afficher();
            List<Regime> regimeList = sr.afficher();
            List<Exercice> exerciceList = se.afficher();

            // Convert the lists to ObservableLists
            ObservableList<Object> combinedList = FXCollections.observableArrayList();
            combinedList.addAll(coordList);
            combinedList.addAll(regimeList);
            combinedList.addAll(exerciceList);

            // Wrap the combinedList in a FilteredList
            FilteredList<Object> filteredList = new FilteredList<>(combinedList, p -> true);

            // Bind the FilteredList to the ListView
            listFx.setItems(filteredList);

            // Set the custom cell factory for multiline text
            listFx.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Object> call(ListView<Object> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Object item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                // Customize the display for each type (Coord, Regime, Exercice)
                                if (item instanceof Coord) {
                                    Coord coord = (Coord) item;
                                    setText("Coordonnées: Sexe: " + coord.getSexe() + "\nAge: " + coord.getAge() +
                                            "\nPoids: " + coord.getPoids() + "\nTaille: " + coord.getTaille());
                                } else if (item instanceof Regime) {
                                    Regime regime = (Regime) item;
                                    setText("Diets: Petit-dej: " + regime.getPetitdej() + "\nColpdej: " +
                                            regime.getColpdej() + "\nDej: " + regime.getDej() +
                                            "\nColdej: " + regime.getColdej() + "\nDiner: " + regime.getDiner());
                                } else if (item instanceof Exercice) {
                                    Exercice exercice = (Exercice) item;
                                    setText("Exercices: Nom: " + exercice.getNom() + "\nDescription: " + exercice.getDescription() +
                                            "\nImage: " + exercice.getImage() + "\nNombre de répétitions: " + exercice.getNbr_rep());
                                }
                            }
                        }
                    };
                }
            });

            // Add a listener to the search bar
            SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(object -> {
                    // If the search bar is empty, show all items
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Check the type of the object and filter accordingly
                    if (object instanceof Coord) {
                        return "Coordonnées".toLowerCase().contains(newValue.toLowerCase());
                    } else if (object instanceof Regime) {
                        return "Diets".toLowerCase().contains(newValue.toLowerCase());
                    } else if (object instanceof Exercice) {
                        return "Exercices".toLowerCase().contains(newValue.toLowerCase());
                    }

                    return false;
                });
            });

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

}
