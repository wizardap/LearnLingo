package com.application.learnlingo;


import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class BookMarkController extends GeneralController {

    @FXML
    private WebView wv;

    @FXML
    private TextField textfield1;

    @FXML
    private AnchorPane subcenter;

    @FXML
    private void deleteWord() {
        textfield1.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        left.setVisible(false);
        left.setTranslateX(-100);
        center.setStyle("-fx-background-color: #F4F4F4");
        add.setOnAction(e -> AnimationChangeScene.handleButtonClick("changeWordController.fxml", container));
        search.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        settings.setOnAction(e -> AnimationChangeScene.handleButtonClick("Settings.fxml", container));
        tudien.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        dich.setOnAction(e -> AnimationChangeScene.handleButtonClick("TranslationController.fxml", container));
        game.setOnAction(e -> AnimationChangeScene.handleButtonClick("gameController.fxml", container));
        synonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindSynonym.fxml", container));
        antonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container));
        listWords.getItems().addAll("Hello", "World", "Hello", "World", "Hello", "World", "Hello", "World", "Hello", "World");

    }

    @FXML
    public void setMenu() {
        checkMenuBar = !checkMenuBar;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(left);
        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(0.4));
        slide2.setNode(subcenter);
        if (checkMenuBar) {
            left.setVisible(true);
            left.setPrefWidth(100);
            subcenter.setPrefWidth(559);
            slide.setToX(0);
            slide.play();
            slide2.setToX(50);
            slide2.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                if (child.getId() == null || !child.getId().equals("left")) {
                    child.setOpacity(0.8);
                } else {
                    child.setOpacity(1);
                }
            }
        } else {
            wv.setPrefWidth(659);
            slide.setToX(-100);
            slide.play();
            slide2.setToX(0);
            slide2.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                child.setOpacity(1);
            }
        }
    }
}
