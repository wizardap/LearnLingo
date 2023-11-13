package com.application.learnlingo;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    @FXML
    private Label lb1;

    @FXML
    private Label lb2;

    @FXML
    private Label lb3;

    @FXML
    private Label lb4;

    @FXML
    private Label lb5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if (!changeL) {
            lb1.setText("Xóa Bookmark");
            lb2.setText("Reset về ban đầu");
            lb3.setText("Tốc độ nói");
            lb4.setText("Phản hồi");
            lb5.setText("Chậm      Vừa         Nhanh");
        } else {
            lb1.setText("Delete BookMark");
            lb2.setText("Reset Default");
            lb3.setText("Voice Speed");
            lb4.setText("Feedbacks");
            lb5.setText("Slow      Average      Fast");
        }
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
        if (!changeL) {
            String selectedWord = "Anh yêu em";
            TextToSpeech pronounce = new TextToSpeech(selectedWord, "hl=vi-vn", "Chi", Integer.toString(DictionaryController.speedRate));
        } else {
            String selectedWord = "I love you";
            TextToSpeech pronouce = new TextToSpeech(selectedWord, "hl=en-us", "Mike", Integer.toString(DictionaryController.speedRate));
        }
    }

    @FXML
    public void deleteBookmark() {
        List<String> bookmarkList = new ArrayList<>();
        bookmarkList.addAll(evDict.exportBookmarkList());
        for (String bookmarkWord: bookmarkList){
            evDict.unsetBookmark(bookmarkWord);
        }

        bookmarkList.clear();
        bookmarkList.addAll(veDict.exportBookmarkList());
        for (String bookmarkWord: bookmarkList){
            veDict.unsetBookmark(bookmarkWord);
        }

        bookmarkList.clear();
        bookmarkList.addAll(veDict.exportBookmarkList());
        for (String bookmarkWord: bookmarkList){
            veDict.unsetBookmark(bookmarkWord);
        }

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

    @Override
    public void changeMode2() {
        if (changeL) {
            currentDictionary = veDict;
            changeDictionary.getChildren().removeAll(british, vn, change);
            changeDictionary.getChildren().addAll(vn, change, british);
            lb1.setText("Xóa Bookmark");
            lb2.setText("Reset về ban đầu");
            lb3.setText("Tốc độ nói");
            lb4.setText("Phản hồi");
            lb5.setText("Chậm      Vừa         Nhanh");
            tudien.setText("Từ điển");
            dich.setText("Dịch câu");
            synonym.setText("Đồng nghĩa");
            antonym.setText("Trái nghĩa");
            search.setText("TRANG CHỦ");
            add.setText("THÊM/XÓA");
            game.setText("TRÒ CHƠI");
            history.setText("TỪ ĐÃ LƯU");
            settings.setText("CÀI ĐẶT");
        } else {
            currentDictionary = evDict;
            changeDictionary.getChildren().removeAll(vn, british, change);
            changeDictionary.getChildren().addAll(british, change, vn);
            lb1.setText("Delete BookMark");
            lb2.setText("Reset Default");
            lb3.setText("Voice Speed");
            lb4.setText("Feedbacks");
            lb5.setText("Slow      Average      Fast");
            tudien.setText("Dictionary");
            dich.setText("Translation");
            synonym.setText("Synonyms");
            antonym.setText("Antonyms");
            search.setText("HOME");
            add.setText("ADD/DELETE");
            game.setText("GAME");
            history.setText("BOOKMARK");
            settings.setText("SETTINGS");
        }
        changeL=!changeL;
    }
}
