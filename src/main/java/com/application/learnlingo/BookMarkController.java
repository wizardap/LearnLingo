package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BookMarkController implements Initializable {

    @FXML
    private TextField textfield1;

    @FXML
    private BorderPane container;

    @FXML
    private JFXListView listWords;

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
    private Button game;

    @FXML
    private VBox left;

    @FXML
    private Button menu;

    private boolean checkMenuBar = false;

    @FXML
    private AnchorPane center;

    @FXML
    private AnchorPane subcenter;

    @FXML
    private WebView wv;

    @FXML
    private void deleteWord() {
        textfield1.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        center.setStyle("-fx-background-color: #F4F4F4");
        left.setVisible(false);
        left.setTranslateX(-100);
        add.setOnAction(e -> AnimationChangeScene.handleButtonClick("changeWordController.fxml", container));
        search.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        settings.setOnAction(e -> AnimationChangeScene.handleButtonClick("Settings.fxml", container));
        tudien.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        dich.setOnAction(e -> AnimationChangeScene.handleButtonClick("TranslationController.fxml", container));
        game.setOnAction(e -> AnimationChangeScene.handleButtonClick("gameController.fxml", container));
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
