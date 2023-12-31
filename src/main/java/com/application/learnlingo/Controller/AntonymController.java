package com.application.learnlingo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.util.ResourceBundle;


public class AntonymController extends SynonymAndAntonym {
    @Override
    public void handleMouseClicked() {
        super.handleMouseClicked("ant");
    }
    @Override
    public void buttonClicked(MouseEvent mouseEvent){
        super.buttonClicked(mouseEvent, "ant");
    }

    @FXML
    public void handleClickOnSearch() {super.handleClickOnSearch("ant");}

    @FXML
    public void handlePressEnterInTextField(KeyEvent event) {super.handlePressEnterInTextField(event, "ant");}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        buttons = new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15};

        synonymButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH+"FindSynonym.fxml", container));
    }

}
