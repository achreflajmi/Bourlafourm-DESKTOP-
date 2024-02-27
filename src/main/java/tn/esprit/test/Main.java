package tn.esprit.test;

import tn.esprit.entities.Kine;
import tn.esprit.entities.Kine;
import tn.esprit.services.ServiceKine;
import tn.esprit.util.MyDataBase;

import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ServiceKine sk =new ServiceKine();
        try {
            sk.ajouter(new Kine("centre","jamal","mourouj"));
            sk.ajouter(new Kine("centre1","ali","fouchena"));
            System.out.println("kine ajouté");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        try {
            sk.modifier(new Kine(1,"clinique","achre","korba"));
            System.out.println("updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        /**try{
            sk.supprimer(1);
            sk.supprimer(2);
            sk.supprimer(3);
            sk.supprimer(5);
            sk.supprimer(6);
            System.out.println("deleted");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try{
            System.out.println(sk.recuperer());
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }**/


    }
    }
