package com.application.learnlingo;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SynonymAndAntonym implements Initializable {

    @FXML
    protected BorderPane container;

    @FXML
    protected AnchorPane center;

    @FXML
    protected Button menu;

    protected boolean checkMenuBar = true;

    @FXML
    protected VBox left;

    @FXML
    protected Button search;

    @FXML
    protected Button add;

    @FXML
    protected Button history;

    @FXML
    protected Button settings;

    @FXML
    protected Button tudien;

    @FXML
    protected Button dich;

    @FXML
    protected Button game;

    @FXML
    protected Button antonym;

    @FXML
    protected Button synonym;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        left.setVisible(false);
        left.setTranslateX(-100);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        center.setBackground(background);
        add.setOnAction(e -> AnimationChangeScene.handleButtonClick("changeWordController.fxml", container));
        search.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        tudien.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        dich.setOnAction(e -> AnimationChangeScene.handleButtonClick("TranslationController.fxml", container));
        game.setOnAction(e -> AnimationChangeScene.handleButtonClick("gameController.fxml", container));
    }

}

