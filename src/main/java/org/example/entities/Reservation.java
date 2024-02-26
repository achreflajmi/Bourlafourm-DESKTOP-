package org.example.entities;

public class Reservation {
    int id_reservation;
    String nom_rese_event;

    int nbr_place_reserv;
    String Email;
    int id_reser_event;


    public Reservation() {
    }

    public Reservation(int id_reservation, String nom_rese_event, int nbr_place_reserv, String email, int id_reser_event) {
        this.id_reservation = id_reservation;
        this.nom_rese_event = nom_rese_event;
        this.nbr_place_reserv = nbr_place_reserv;
        Email = email;
        this.id_reser_event = id_reser_event;
    }

    public Reservation(String nom_rese_event,int nbr_place_reserv, String email,  int id_reser_event) {
        this.nom_rese_event = nom_rese_event;
        this.nbr_place_reserv = nbr_place_reserv;
        Email = email;
        this.id_reser_event = id_reser_event;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public String getNom_rese_event() {
        return nom_rese_event;
    }

    public void setNom_rese_event(String nom_rese_event) {
        this.nom_rese_event = nom_rese_event;
    }

    public int getNbr_place_reserv() {
        return nbr_place_reserv;
    }

    public void setNbr_place_reserv(int nbr_place_reserv) {
        this.nbr_place_reserv = nbr_place_reserv;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getId_reser_event() {
        return id_reser_event;
    }

    public void setId_reser_event(int id_reser_event) {
        this.id_reser_event = id_reser_event;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_reservation +
                ", nom_rese_event='" + nom_rese_event + '\'' +
                ", nbr_place_reserv=" + nbr_place_reserv +
                ", Email='" + Email + '\'' +
                ", id_reser_event=" + id_reser_event +
                '}';
    }
}

