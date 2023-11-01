package com.application.learnlingo;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class SynonymController extends SynonymAndAntonym {

    @Override
    public void handleMouseClicked(MouseEvent mouseEvent) {
        super.handleMouseClicked(mouseEvent, "syn");
    }

    @Override
    public void buttonClicked(MouseEvent mouseEvent) {
        super.buttonClicked(mouseEvent, "syn");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        buttons = new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15};

        checkMode1.setVisible(false);
        checkMode2.setVisible(false);
        checkMode3.setVisible(true);
        checkMode4.setVisible(false);
        antonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container));
    }


}
