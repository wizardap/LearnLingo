package com.application.learnlingo.Controller;

import com.application.learnlingo.Model.TextToSpeech;
import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                new Image(getClass().getResource(IMAGE_PATH + "bg3.jpg").toString(), 910, 600, false, true),
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
        if (changeL) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Bookmark");
            alert.setContentText("Do you want to delete Bookmark?");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                List<String> bookmarkList = new ArrayList<>();
                bookmarkList.addAll(evDict.exportBookmarkList());
                for (String bookmarkWord : bookmarkList) {
                    evDict.unsetBookmark(bookmarkWord);
                }
                bookmarkList.clear();
                bookmarkList.addAll(veDict.exportBookmarkList());
                for (String bookmarkWord : bookmarkList) {
                    veDict.unsetBookmark(bookmarkWord);
                }

                bookmarkList.clear();
                bookmarkList.addAll(veDict.exportBookmarkList());
                for (String bookmarkWord : bookmarkList) {
                    veDict.unsetBookmark(bookmarkWord);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xóa Bookmark");
            alert.setContentText("Bạn có chắc là muốn xóa Bookmark không?");

            ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                List<String> bookmarkList = new ArrayList<>();
                bookmarkList.addAll(evDict.exportBookmarkList());
                for (String bookmarkWord : bookmarkList) {
                    evDict.unsetBookmark(bookmarkWord);
                }
                bookmarkList.clear();
                bookmarkList.addAll(veDict.exportBookmarkList());
                for (String bookmarkWord : bookmarkList) {
                    veDict.unsetBookmark(bookmarkWord);
                }

                bookmarkList.clear();
                bookmarkList.addAll(veDict.exportBookmarkList());
                for (String bookmarkWord : bookmarkList) {
                    veDict.unsetBookmark(bookmarkWord);
                }
            }
        }
    }

    @FXML
    public void resetDefault() {
        if (changeL) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reset Default");
            alert.setContentText("Do you want reset to default settings?");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                evDict.resetData();
                veDict.resetData();
                slider.setValue(50);
                DictionaryController.speedRate = calculateSpeedRate(50);
                TextTwistGame.reset();
                FunnyQuizGame.reset();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quay về mặc định");
            alert.setContentText("Bạn có muốn quay về cài đặt mặc định không?");

            ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                evDict.resetData();
                veDict.resetData();
                slider.setValue(50);
                TextTwistGame.reset();
                FunnyQuizGame.reset();
                DictionaryController.speedRate = calculateSpeedRate(50);
            }
        }
    }

    @FXML
    public void sendFeedback() {
        if (changeL) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thank you!");
            alert.setContentText("Thank for giving us feedbacks");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cảm ơn bạn!");
            alert.setContentText("Cảm ơn vì đã gửi cho chúng tôi phản hồi");
            alert.showAndWait();
        }
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
            changeDictionary.getChildren().removeAll(british, vn, changeModeButton);
            changeDictionary.getChildren().addAll(vn, changeModeButton, british);
            lb1.setText("Xóa Bookmark");
            lb2.setText("Reset về ban đầu");
            lb3.setText("Tốc độ nói");
            lb4.setText("Phản hồi");
            lb5.setText("Chậm      Vừa         Nhanh");
            dictionaryButton.setText("Từ điển");
            translationButton.setText("Dịch câu");
            synonymButton.setText("Đồng nghĩa");
            antonymButton.setText("Trái nghĩa");
            searchButton.setText("TRANG CHỦ");
            changeDictionaryButton.setText("THÊM/XÓA");
            gameButton.setText("TRÒ CHƠI");
            bookmarkButton.setText("TỪ ĐÃ LƯU");
            settingsButton.setText("CÀI ĐẶT");
        } else {
            currentDictionary = evDict;
            changeDictionary.getChildren().removeAll(vn, british, changeModeButton);
            changeDictionary.getChildren().addAll(british, changeModeButton, vn);
            lb1.setText("Delete BookMark");
            lb2.setText("Reset Default");
            lb3.setText("Voice Speed");
            lb4.setText("Feedbacks");
            lb5.setText("Slow      Average      Fast");
            dictionaryButton.setText("Dictionary");
            translationButton.setText("Translation");
            synonymButton.setText("Synonyms");
            antonymButton.setText("Antonyms");
            searchButton.setText("HOME");
            changeDictionaryButton.setText("ADD/DELETE");
            gameButton.setText("GAME");
            bookmarkButton.setText("BOOKMARK");
            settingsButton.setText("SETTINGS");
        }
        changeL = !changeL;
    }
}
