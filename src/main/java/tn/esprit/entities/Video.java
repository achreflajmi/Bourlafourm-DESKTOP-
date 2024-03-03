package tn.esprit.entities;

import java.util.Date;

public class Video {
    int id_user;

    String title;
    String file_path;
    Date upload_date;


    @Override
    public String toString() {
        return "Video{" +
                "id_user=" + id_user +
                ", title='" + title + '\'' +
                ", file_path='" + file_path + '\'' +
                ", upload_date=" + upload_date +
                '}';
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public Video(String file_path, Date upload_date) {
        this.file_path = file_path;
        this.upload_date = upload_date;
    }

    public Video(int id_user, String file_path, Date upload_date) {
        this.id_user = id_user;
        this.file_path = file_path;
        this.upload_date = upload_date;
    }

    public Video() {
    }

    public Video(int id_user, String title, String file_path, Date upload_date) {
        this.id_user = id_user;
        this.title = title;
        this.file_path = file_path;
        this.upload_date = upload_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Video(String title, String file_path, Date upload_date) {
        this.title = title;
        this.file_path = file_path;
        this.upload_date = upload_date;
    }
}
