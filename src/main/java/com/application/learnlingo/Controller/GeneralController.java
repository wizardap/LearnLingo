package com.application.learnlingo.Controller;

import com.application.learnlingo.Model.*;
import com.jfoenix.controls.JFXListView;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GeneralController implements Initializable {
    public static final String VIEW_PATH = "/com/application/learnlingo/View/";
    public static final String AUDIO_PATH = "/com/application/learnlingo/audio/";
    public static final String IMAGE_PATH = "/com/application/learnlingo/image/";

    private final static String DATABASE_PATH = "./src/main/resources/com/application/learnlingo/database/";
    private final static String DATABASE_NAME = "dict_hh.db";
    private static final String FEEDBACK_TXT_PATH
            = "./src/main/resources/com/application/learnlingo/database/feedbacks.txt";
    public static DatabaseDictionary<Word> evDict = new EnglishDictionary<>(DATABASE_PATH, DATABASE_NAME, "AV", "defaultAV");
    public static DatabaseDictionary<Word> currentDictionary = evDict;
    public static DatabaseDictionary<Word> veDict = new VietnameseDictionary<>(DATABASE_PATH, DATABASE_NAME, "VA", "defaultVA");
    @FXML
    protected static boolean changeL = true;
    protected static File feedbackTxt = new File(FEEDBACK_TXT_PATH);
    @FXML
    protected Button btnYes;

    @FXML
    protected Button btnNo;

    @FXML
    protected Button settingsButton;
    @FXML
    protected Button speakUS;
    @FXML
    protected Button speakUK;
    @FXML
    protected Button speakVN;
    @FXML
    protected Button bookmark;
    @FXML
    protected Button changeDictionaryButton;
    @FXML
    protected VBox left;
    @FXML
    protected WebView webView;
    @FXML
    protected WebEngine webEngine;
    @FXML
    protected AnchorPane center;
    @FXML
    protected Label label;

    @FXML
    protected Button find;
    @FXML
    protected JFXListView<String> listWords;
    @FXML
    protected HBox function;
    @FXML
    protected TextField textfield;
    @FXML
    protected Button bookmarkButton;
    @FXML
    protected Button changeModeButton;
    @FXML
    protected ImageView british;
    @FXML
    protected ImageView vn;
    @FXML
    protected HBox changeDictionary;

    @FXML
    protected Button dictionaryButton;
    @FXML
    protected Button translationButton;
    @FXML
    protected Button synonymButton;
    @FXML
    protected Button antonymButton;
    @FXML
    protected BorderPane container;
    @FXML
    protected Button searchButton;
    @FXML
    protected Button gameButton;
    @FXML
    protected Button menu;
    @FXML
    protected ImageView checkMode1;
    @FXML
    protected ImageView checkMode2;
    @FXML
    protected ImageView checkMode3;
    @FXML
    protected ImageView checkMode4;
    protected boolean checkMenuBar = false;

    protected AudioClip musicGame = new AudioClip(getClass().getResource(AUDIO_PATH + "soundGame.mp3").toString());

    @FXML
    protected Label wordOfDay;

    protected List<Button> listButton = new ArrayList<>();

    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        listWords.getItems().clear();
        if (!textfield.getText().isEmpty()) {
            listWords.setVisible(true);
            listWords.getItems().addAll(suggestionSearchList(textfield.getText()));
            int s = listWords.getItems().size();
            if (s < 23)
                listWords.setPrefHeight(3 + 24 * s);
            else
                listWords.setPrefHeight(550);
        } else {
            listWords.setVisible(false);
        }
    }

    public ObservableList<String> suggestionSearchList(String text) {
        return FXCollections.observableList(currentDictionary.exportSuggestionList(text));
    }


    @FXML
    public void handleMouseClicked() {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            String meaningHTMLString = currentDictionary.getWordInformation(selectedWord).getHtml();
            webEngine.loadContent(meaningHTMLString);
            if (changeL) {
                speakUS.setVisible(true);
                speakUK.setVisible(true);
                speakVN.setVisible(false);
            } else {
                speakVN.setVisible(true);
                speakUS.setVisible(false);
                speakUK.setVisible(false);
            }
            bookmark.setVisible(true);
        }
    }

    public void handleKeyEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleMouseClicked();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textfield.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                listWords.getSelectionModel().selectFirst();
                listWords.requestFocus();
            }
        });

        musicGame.setCycleCount(-1);
        musicGame.stop();
        if (!changeL) {
            currentDictionary = veDict;
            changeDictionary.getChildren().removeAll(british, vn, changeModeButton);
            changeDictionary.getChildren().addAll(vn, changeModeButton, british);
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
        checkMode1.setVisible(false);
        checkMode2.setVisible(false);
        checkMode3.setVisible(false);
        checkMode4.setVisible(false);
        gameButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH + "Game.fxml", container));
        changeDictionaryButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH + "changeWordController.fxml", container));
        searchButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH + "Dictionary.fxml", container));
        bookmarkButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH + "BookMark.fxml", container));
        settingsButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH + "Settings.fxml", container));
        dictionaryButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH + "Dictionary.fxml", container));
        translationButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH + "TranslationController.fxml", container));
        synonymButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH + "FindSynonym.fxml", container));
        antonymButton.setOnAction(e -> AnimationChangeScene.handleButtonClick(VIEW_PATH + "FindAntonym.fxml", container));
        listButton.add(dictionaryButton);
        listButton.add(translationButton);
        listButton.add(synonymButton);
        listButton.add(antonymButton);
        listButton.add(searchButton);
        listButton.add(changeDictionaryButton);
        listButton.add(gameButton);
        listButton.add(bookmarkButton);
        listButton.add(settingsButton);
        DatabaseManager.connectingToDatabase(DATABASE_PATH + DATABASE_NAME);
    }

    @FXML
    public void setMenu() {
        checkMenuBar = !checkMenuBar;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(left);
        if (!checkMenuBar) {
            left.setVisible(true);
            left.setPrefWidth(99);
            slide.setToX(0);
            slide.play();
        } else {
            slide.setToX(-99.5);
            slide.play();
        }
    }

    public void changeMode2() {
        if (changeL) {
            currentDictionary = veDict;
            changeDictionary.getChildren().removeAll(british, vn, changeModeButton);
            changeDictionary.getChildren().addAll(vn, changeModeButton, british);
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

    @FXML
    public void changeMode() {
        if (changeL) {
            currentDictionary = veDict;
            changeDictionary.getChildren().removeAll(british, vn, changeModeButton);
            changeDictionary.getChildren().addAll(vn, changeModeButton, british);
            bookmark.setText("Thêm từ này");
            label.setText("Bạn muốn thêm từ này vào Bookmark không");
            btnYes.setText("Có");
            btnNo.setText("Không");
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
            bookmark.setText("Add to word list");
            label.setText("Do you want to add this word in Bookmark");
            btnYes.setText("Yes");
            btnNo.setText("No");
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
        listWords.getItems().clear();
    }

    protected void displayListWord() {
        int k = listWords.getItems().size();
        if (k == 0) {
            listWords.setVisible(false);
        } else {
            listWords.setVisible(true);
            if (k < 23)
                listWords.setPrefHeight(3 + 24 * k);
            else
                listWords.setPrefHeight(550);
        }
        changeL = !changeL;
    }
}

