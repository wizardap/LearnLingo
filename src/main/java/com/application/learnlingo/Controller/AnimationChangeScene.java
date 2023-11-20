package com.application.learnlingo.Controller;

import com.application.learnlingo.DictionaryApplication;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnimationChangeScene {

    public static List<Double> lst = new ArrayList<>();

    private static void makeFadeOutToScene(String scene, BorderPane container) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(container.getCenter());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> loadScene(scene, container));
        fadeTransition.play();
    }

    @FXML
    public static void handleButtonClick(String scene, BorderPane container) {
        makeFadeOutToScene(scene, container);
    }

    private static void loadScene(String scene, BorderPane container) {
        try {
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource(scene)));
            Scene newScene = new Scene(secondView, 910, 600);
            Stage curStage = (Stage) container.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
