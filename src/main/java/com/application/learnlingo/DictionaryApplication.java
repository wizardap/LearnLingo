package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("gameController.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 910, 600);
        stage.setTitle("LearnLingo");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}