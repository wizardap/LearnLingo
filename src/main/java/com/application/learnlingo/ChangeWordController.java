package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChangeWordController implements Initializable {

    @FXML
    private Button search;

    @FXML
    private Button dich;

    @FXML
    private Button history;

    @FXML
    private Button settings;

    @FXML
    private Button tudien;

    @FXML
    private BorderPane container;

    @FXML
    private AnchorPane center;

    @FXML
    private Button deleteWord1;

    @FXML
    private TextField textfield1;

    @FXML
    private Button game;

    @FXML
    private JFXCheckBox d1;

    @FXML
    private JFXCheckBox d2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        search.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        settings.setOnAction(e -> AnimationChangeScene.handleButtonClick("Settings.fxml", container));
        tudien.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        dich.setOnAction(e -> AnimationChangeScene.handleButtonClick("TranslationController.fxml", container));
        game.setOnAction(e -> AnimationChangeScene.handleButtonClick("gameController.fxml", container));
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
