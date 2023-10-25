package com.application.learnlingo;

import com.jfoenix.controls.JFXSlider;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends GeneralController {

    @FXML
    private TextArea feedback;

    @FXML
    private JFXSlider slider;

    private boolean checkMenuBar = false;

    private double speedVoice = 0;

    @FXML
    private ImageView speak;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkMode1.setVisible(false);
        checkMode2.setVisible(false);
        checkMode3.setVisible(false);
        checkMode4.setVisible(false);
        left.setVisible(false);
        left.setTranslateX(-100);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        add.setOnAction(e -> AnimationChangeScene.handleButtonClick("changeWordController.fxml", container));
        search.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        tudien.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        dich.setOnAction(e -> AnimationChangeScene.handleButtonClick("TranslationController.fxml", container));
        game.setOnAction(e -> AnimationChangeScene.handleButtonClick("gameController.fxml", container));
        synonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindSynonym.fxml", container));
        antonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container));
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int newSliderValue = newValue.intValue();
            DictionaryController.speedRate = calculateSpeedRate(newSliderValue);
            slider.setValue((double) newValue);
            AnimationChangeScene.lst.add((double) newValue);
        });
        if (!AnimationChangeScene.lst.isEmpty()) {
            slider.setValue(AnimationChangeScene.lst.get(AnimationChangeScene.lst.size() - 1));
        } else {
            slider.setValue(50);
        }
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
    private int calculateSpeedRate(int sliderValue) {
        if (sliderValue == 50) {
            return 0;
        } else if (sliderValue < 50) {
            return (int) (-(50 - sliderValue) / 5);
        } else {
            return (int) ((sliderValue - 50) / 5);
        }
    }

    @FXML
    public void testVoiceSpeed() {
        String selectedWord = "Contribution";
        TextToSpeech pronouce = new TextToSpeech(selectedWord,"hl=en-us","Mike", Integer.toString(DictionaryController.speedRate));
    }

}
