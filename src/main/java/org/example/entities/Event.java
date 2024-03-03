package org.example.entities;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;

public class Event {
    int idEvent;
    String NomEvent;
    String Type;
    String Organisateur;
    Date Date_deb;
    Date Date_fin;
    int Capacite;
    Blob Image;
    String Path;
    int nb_place_res;

    public Event(int idEvent, String nomEvent, String type, String organisateur, Date date_deb, Date date_fin, int capacite, Blob image, String path, int nb_place_res) {
        this.idEvent = idEvent;
        NomEvent = nomEvent;
        Type = type;
        Organisateur = organisateur;
        Date_deb = date_deb;
        Date_fin = date_fin;
        Capacite = capacite;
        Image = image;
        Path = path;
        this.nb_place_res = nb_place_res;
    }

    public Event(int idEvent, String nomEvent, String type, String organisateur, Date date_deb, Date date_fin, int Capacite, Blob image, String path) {
        this.idEvent = idEvent;
        this.NomEvent = nomEvent;
        this.Type = type;
        this.Organisateur = organisateur;
        this.Date_deb = date_deb;
        this.Date_fin = date_fin;
        this.Capacite = Capacite;
        this.Image = image;
        this.Path = path;
    }

  //  public Event(int idEvent, String nomEvent, int capacite,Date date_deb,String path){
    //    this.idEvent=idEvent;
      //  this.NomEvent= nomEvent;
  //      this.Capacite=capacite;
  //      this.Date_deb=date_deb;
  //      this.Path=path;
   // }

    public Event(String nomEvent, String type, String organisateur, Date date_deb, Date date_fin, int Capacite, Blob image, String path) {
        NomEvent = nomEvent;
        Type = type;
        Organisateur = organisateur;
        Date_deb = date_deb;
        Date_fin = date_fin;
        this.Capacite = Capacite;
        Image = image;
        Path = path;
    }

    public Event() {

    }


    public int getNb_place_res() {
        return nb_place_res;
    }

    public void setNb_place_res(int nb_place_res) {
        this.nb_place_res = nb_place_res;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getNomEvent() {
        return NomEvent;
    }

    public void setNomEvent(String nomEvent) {
        NomEvent = nomEvent;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getOrganisateur() {
        return Organisateur;
    }

    public void setOrganisateur(String organisateur) {
        Organisateur = organisateur;
    }

    public String getDate_deb() {
        return String.valueOf(Date_deb);
    }

    public void setDate_deb(Date date_deb) {
        Date_deb = date_deb;
    }

    public String getDate_fin() {
        return String.valueOf(Date_fin);
    }


    public Date getdate_Date_fin() {
        return (Date_fin);
    }

    public Date getdate_Date_deb() {
        return (Date_deb);
    }



    public void setDate_fin(Date date_fin) {
        Date_fin = date_fin;
    }




    public int getCapacite() {
        return Capacite;
    }


    public void setCapacite(int capacite) {
        Capacite = capacite;
    }

    public Blob getImage() {
        return Image;
    }

    public void setImage(Blob image) {
        Image = image;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }


    @Override
    public String toString() {
        return "Event{" +
                "idEvent=" + idEvent +
                ", NomEvent='" + NomEvent + '\'' +
                ", Type='" + Type + '\'' +
                ", Organisateur='" + Organisateur + '\'' +
                ", Date_deb=" + Date_deb +
                ", Date_fin=" + Date_fin +
                ", Capacite=" + Capacite +
                ", Image=" + Image +
                ", Path='" + Path + '\'' +
                ", nb_place_res=" + nb_place_res +
                '}';
    }

}
