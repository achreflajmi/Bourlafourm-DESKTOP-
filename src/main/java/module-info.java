module bourlafourme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires AnimateFX;
    requires com.jfoenix;

    requires pdfjet;
    requires charm.glisten;
    requires java.mail;


    opens CSS to javafx.fxml;
    opens Icons to javafx.fxml;
    opens org.example.test to javafx.fxml;
    opens org.example.controllers to javafx.fxml;


    opens org.example.entities to javafx.base;



    exports org.example.test;
}