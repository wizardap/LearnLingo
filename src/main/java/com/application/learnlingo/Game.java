package com.application.learnlingo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Game implements Initializable {

    @FXML
    private AnchorPane center;

    @FXML
    private BorderPane container;

    @FXML
    private Label timerLabel;

    private int seconds = 120;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        center.setBackground(background);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        seconds--;
                        timerLabel.setText("TIME                " + seconds);
                    }
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }
}
