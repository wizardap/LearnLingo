package com.application.learnlingo;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button search;

    @FXML
    private Button add;

    @FXML
    private Button history;

    @FXML
    private Button settings;

    @FXML
    private Button tudien;

    @FXML
    private Button dich;

    @FXML
    private TextArea feedback;

    @FXML
    private JFXSlider slider;

    @FXML
    private AnchorPane center;

    @FXML
    private BorderPane container;

    @FXML
    private Button game;

    @FXML
    private VBox left;

    @FXML
    private Button menu;

    private boolean checkMenuBar = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        left.setVisible(false);
        left.setTranslateX(-100);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        add.setOnAction(e -> AnimationChangeScene.handleButtonClick("changeWordController.fxml", container));
        search.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        tudien.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        dich.setOnAction(e -> AnimationChangeScene.handleButtonClick("TranslationController.fxml", container));
        game.setOnAction(e -> AnimationChangeScene.handleButtonClick("gameController.fxml", container));
        center.setBackground(background);
        feedback.setWrapText(true);

    }

    @FXML
    public void setMenu() {
        checkMenuBar = !checkMenuBar;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(left);
        if (checkMenuBar) {
            left.setVisible(true);
            left.setPrefWidth(100);
            slide.setToX(0);
            slide.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                if (child.getId() == null || !child.getId().equals("left")) {
                    child.setOpacity(0.8);
                } else {
                    child.setOpacity(1);
                }
            }
        } else {
            slide.setToX(-100);
            slide.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                child.setOpacity(1);
            }
        }
    }
}
