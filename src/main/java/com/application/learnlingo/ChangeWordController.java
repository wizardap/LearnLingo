package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeWordController extends GeneralController {

    @FXML
    private Button deleteWord1;

    @FXML
    private TextField textfield1;

    @FXML
    private CheckBox d1;

    @FXML
    private CheckBox d2;

    @FXML
    private JFXButton addw;

    @FXML
    private JFXButton delw;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        center.setBackground(background);
        d1.setOnAction(event -> {
            if (d1.isSelected()) {
                d2.setSelected(false);
            }
        });

        d2.setOnAction(event -> {
            if (d2.isSelected()) {
                d1.setSelected(false);
            }
        });
    }

    @FXML
    public void delete() {
        textfield1.setText("");
    }

    @FXML
    public void addWord() {
        //To do
    }

    @FXML
    public void deleteWord() {
        //To do
    }

}
