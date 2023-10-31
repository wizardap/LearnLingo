package com.application.learnlingo;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileWriter;
import java.io.IOException;
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
    @FXML
    private Button resetButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
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

    @FXML
    public void deleteBookmark() {
    }

    @FXML
    public void resetDefault() {
        evDict.resetData();
        veDict.resetData();
        slider.setValue(50);
        DictionaryController.speedRate = calculateSpeedRate(50);
    }

    @FXML
    public void sendFeedback() {
        try {
            FileWriter fw = new FileWriter(feedbackTxt, true);
            String fb = feedback.getText();
            fw.write("+) " + fb + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
