module classes.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires passay;
    requires com.opencsv;
    requires AnimateFX;
    requires java.sql;
    requires mysql.connector.java;

    opens classes.project to javafx.fxml;
    exports classes.project;
}