package tn.esprit.test;

import tn.esprit.entities.User;
import tn.esprit.entities.Role_user;
import tn.esprit.entities.Recclamation;
import tn.esprit.services.ServiceUser;

import java.sql.SQLException;

import static tn.esprit.entities.Role_user.Sportif;

public class Main {
    public static void main(String[] args) {
        ServiceUser ss = new ServiceUser();
/*
        try{
            ss.ajouter(new User("Saadi","Oumaima", "ouou@gmail.com", "Ou12345_", Sportif, 170,75));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
*/

        try {
            ss.modifier(new User(5,"H.A","Hadil","Hadil@gmail.com","Hadil1234_",true,Role_user.Coach));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
/*
        try {
            ss.supprimer(3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
*/
        try {
            System.out.println(ss.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }





    }
}