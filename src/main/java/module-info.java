module Try {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javax.persistence;
    requires javafx.media;
    requires  pdfjet;
    requires twilio;

    opens tn.esprit.test to javafx.fxml;
    opens tn.esprit.controllers to javafx.fxml;

    exports tn.esprit.controllers;
    exports tn.esprit.test;
}