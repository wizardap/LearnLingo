package com.application.learnlingo;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SynonymAndAntonym extends GeneralController {

    public void changeMode() {
        if (isUKFlagVisible) {
            changeDictionary.getChildren().removeAll(british, vn, change);
            changeDictionary.getChildren().addAll(vn, change, british);
            tudien.setText("Từ điển");
            dich.setText("Dịch câu");
            synonym.setText("Đồng nghĩa");
            antonym.setText("Trái nghĩa");
        } else {
            changeDictionary.getChildren().removeAll(vn, british, change);
            changeDictionary.getChildren().addAll(british, change, vn);
            tudien.setText("Dictionary");
            dich.setText("Translation");
            synonym.setText("Synonyms");
            antonym.setText("Antonyms");
        }
        isUKFlagVisible = !isUKFlagVisible;
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

    public void handleMouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listWords.setVisible(false);
        listWords.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listWords.setCellFactory(param -> new DictionaryController.IconAndFontListCell());
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
        settings.setOnAction(e -> AnimationChangeScene.handleButtonClick("Settings.fxml", container));
    }

}

