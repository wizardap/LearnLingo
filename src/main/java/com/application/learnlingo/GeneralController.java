package com.application.learnlingo;

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
import java.util.ResourceBundle;

public class GeneralController implements Initializable {

    private final static String DATABASE_PATH = "./src/main/resources/com/application/learnlingo/database/";
    private final static String DATABASE_NAME = "dict_hh.db";
    private static final String FEEDBACK_TXT_PATH
            = "./src/main/resources/com/application/learnlingo/database/feedbacks.txt";
    @FXML
    protected static boolean changeL = true;
    protected static DatabaseDictionary<Word> evDict = new EnglishDictionary(DATABASE_PATH, DATABASE_NAME, "AV", "defaultAV");
    protected static DatabaseDictionary<Word> currentDictionary = evDict;
    protected static DatabaseDictionary<Word> veDict = new VietnameseDictionary(DATABASE_PATH, DATABASE_NAME, "VA", "defaultVA");
    protected static File feedbackTxt = new File(FEEDBACK_TXT_PATH);
    protected static WordOfTheDay wordOfTheDay = new WordOfTheDay();
    @FXML
    protected Button btnYes;

    @FXML
    protected Button btnNo;

    @FXML
    protected Button settings;
    @FXML
    protected Button speakUS;
    @FXML
    protected Button speakUK;
    @FXML
    protected Button speakVN;
    @FXML
    protected Button bookmark;
    @FXML
    protected Button add;
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
    protected Button history;
    @FXML
    protected Button change;
    @FXML
    protected ImageView british;
    @FXML
    protected ImageView vn;
    @FXML
    protected HBox changeDictionary;

    @FXML
    protected Button tudien;
    @FXML
    protected Button dich;
    @FXML
    protected Button synonym;
    @FXML
    protected Button antonym;
    @FXML
    protected BorderPane container;
    @FXML
    protected Button search;
    @FXML
    protected Button game;
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

    protected AudioClip musicGame = new AudioClip(TextTwistGame.class.getResource("audio/soundGame.mp3").toString());

    @FXML
    protected Label wordOfDay;

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
            changeDictionary.getChildren().removeAll(british, vn, change);
            changeDictionary.getChildren().addAll(vn, change, british);
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
        checkMode1.setVisible(false);
        checkMode2.setVisible(false);
        checkMode3.setVisible(false);
        checkMode4.setVisible(false);
        game.setOnAction(e -> AnimationChangeScene.handleButtonClick("Game.fxml", container));
        add.setOnAction(e -> AnimationChangeScene.handleButtonClick("changeWordController.fxml", container));
        search.setOnAction(e -> AnimationChangeScene.handleButtonClick("Dictionary.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        settings.setOnAction(e -> AnimationChangeScene.handleButtonClick("Settings.fxml", container));
        tudien.setOnAction(e -> AnimationChangeScene.handleButtonClick("Dictionary.fxml", container));
        dich.setOnAction(e -> AnimationChangeScene.handleButtonClick("TranslationController.fxml", container));
        synonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindSynonym.fxml", container));
        antonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container));
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
                changeDictionary.getChildren().removeAll(british, vn, change);
                changeDictionary.getChildren().addAll(vn, change, british);
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
            changeL = !changeL;
        }

    @FXML
    public void changeMode () {
        if (changeL) {
            currentDictionary = veDict;
            changeDictionary.getChildren().removeAll(british, vn, change);
            changeDictionary.getChildren().addAll(vn, change, british);
            bookmark.setText("Thêm từ này");
            label.setText("Bạn muốn thêm từ này vào Bookmark không");
            btnYes.setText("Có");
            btnNo.setText("Không");
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
            bookmark.setText("Add to word list");
            label.setText("Do you want to add this word in Bookmark");
            btnYes.setText("Yes");
            btnNo.setText("No");
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

