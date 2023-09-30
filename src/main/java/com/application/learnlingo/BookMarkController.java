package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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

//    public static JFXListView getListWords() {
//        return listWords;
//    }

    private void makeFadeOutToChangeWord() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(container.getCenter());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> setChangeWord());
        fadeTransition.play();
    }

    @FXML
    public void handleButtonClickChangeWord() {
        makeFadeOutToChangeWord();
    }

    private void setChangeWord() {
        try {
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource("changeWordController.fxml")));
            Scene newScene = new Scene(secondView, 910, 600);
            Stage curStage = (Stage) container.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonClick() {
        makeFadeOut();
    }

    private void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(container.getCenter());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> setDictonaryMode());
        fadeTransition.play();
    }

    private void setDictonaryMode() {
        try {
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource("hello-view.fxml")));
            Scene newScene = new Scene(secondView, 910, 600);
            Stage curStage = (Stage) container.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonClickSetTranslation() {
        makeFadeOutToTranslation();
    }

    private void makeFadeOutToTranslation() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(container.getCenter());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> setTranslation());
        fadeTransition.play();
    }

    private void setTranslation() {
        try {
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource("TranslationController.fxml")));
            Scene newScene = new Scene(secondView, 910, 600);
            Stage curStage = (Stage) container.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteWord() {
        textfield1.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listWords.getItems().addAll("Hello", "World","Hello", "World","Hello", "World","Hello", "World","Hello", "World");

    }
}
