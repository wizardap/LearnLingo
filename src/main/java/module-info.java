module com.application.learnlingo {
    requires java.sql;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires freetts;
    requires com.jfoenix;
    opens com.application.learnlingo to javafx.fxml;
    exports com.application.learnlingo;
}