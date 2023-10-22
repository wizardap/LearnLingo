package com.application.learnlingo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SynonymController extends SynonymAndAntonym {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        synonym.setStyle("-fx-background-color: #dddddd; -fx-min-width: 85;");
        antonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container));
    }

}
