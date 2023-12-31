package com.application.learnlingo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class SynonymController extends SynonymAndAntonym {

    @Override
    public void handleMouseClicked() {
        super.handleMouseClicked("syn");
    }

    @Override
    public void buttonClicked(MouseEvent mouseEvent) {
        super.buttonClicked(mouseEvent, "syn");
    }

    @FXML
    public void handleClickOnSearch() {
        super.handleClickOnSearch("syn");
    }

    @FXML
    public void handlePressEnterInTextField(KeyEvent event) {
        super.handlePressEnterInTextField(event, "syn");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        buttons = new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15};

        checkMode1.setVisible(false);
        checkMode2.setVisible(false);
        checkMode3.setVisible(true);
        checkMode4.setVisible(false);
        antonymButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH+"FindAntonym.fxml", container));
    }


}
