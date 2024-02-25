package org.example.test;

import org.example.entities.Event;
import org.example.services.ServiceEvent;
import org.example.util.MyDataBase;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        System.out.println(MyDataBase.getInstance());
        System.out.println(MyDataBase.getInstance());

        ServiceEvent se = new ServiceEvent();

        Date DateFin = new Date(2012,01,30);
        Date DateDeb = new Date(2010,02,31);

        LocalDate localDateDeb = DateDeb.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateFin = DateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        System.out.println("C est la date fin"+DateFin);
        System.out.println("C est la date Deb"+DateDeb);

        try {
            se.ajouter(new Event("ll", "bb", "ahmed",  DateFin, DateDeb, 10, null, "kk"));
            System.out.println("mrigl");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            se.modifier(new Event(1,"tomorrowLand", "festival", "ahmed",  DateFin, DateDeb, 10, null, "kk"));
            System.out.println("tbaddel");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }








    }
}