package tn.esprit.services;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public interface Iservice <T> {
    void ajouter (T t) throws SQLException;
    void modifier (T t) throws SQLException;
    void supprimer(int id_kine) throws SQLException;
    List<T> recuperer ()throws SQLException;
}
