package tn.esprit.test;

import tn.esprit.entities.Coord;
import tn.esprit.entities.Regime;
import tn.esprit.entities.Sexe;
import tn.esprit.services.ServiceCoord;
import tn.esprit.services.ServiceExercice;
import tn.esprit.services.ServiceRegime;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ServiceRegime sr = new ServiceRegime();
        ServiceExercice se = new ServiceExercice();

        //---------------------- Ajout Regime --------------------------------


       /*
        try {
            sr.ajouter(new Regime("petitdej","colpdej","dej","col","din"));
            System.out.println("ajouter");
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        */

        //---------------------- Modif Regime --------------------------------

       /*
        try {
            sr.modifier(new Regime(1,"alo","hi","test","ndj","jnf"));
            System.out.println("modifier");
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        */

        //---------------------- Aff Regime --------------------------------

        /*
        try {

          System.out.println(sr.afficher());
       } catch (SQLException e) {
           System.out.println(e.getMessage());;
        }
         */
        try {

            System.out.println(se.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }


        //---------------------- Supp Regime --------------------------------

        /*
        try {
            sr.supprimer(4);
            System.out.println("Supprimer");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
         */


        ServiceCoord sc = new ServiceCoord();

        //---------------------- Ajout Coordonnées --------------------------------


       /*
        try {
            sc.ajouter(new Coord(23,Sexe.homme,80,170,29));
            System.out.println("ajouter");
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        */


        //---------------------- Modif Coordonnées --------------------------------


       /*
        try {
            sc.modifier(new Coord(1,Sexe.homme,40,90,190,27));
            System.out.println("modifier");
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        */


        //---------------------- Aff Coordonnées --------------------------------


        /*
        try {

            System.out.println(sc.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
         */


        //---------------------- Supp Coordonnées --------------------------------


        /*
        try {
            sc.supprimer(4);
            System.out.println("Supprimer");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
         */

        //---------------------- displaybyID(1) --------------------------------

/*
        try {
            ServiceCoord serviceCoord = new ServiceCoord();
            int idToDisplay = 1;
            serviceCoord.afficherCoordonneesParId(idToDisplay);
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
    }
}