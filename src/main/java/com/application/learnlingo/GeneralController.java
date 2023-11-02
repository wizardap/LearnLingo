package com.application.learnlingo;

import com.jfoenix.controls.JFXListView;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralController implements Initializable {

    @FXML
    protected Button settings;
    @FXML
    protected Button speakUS;
    @FXML
    protected Button speakUK;
    @FXML
    protected Button speakVN;
    @FXML
    protected static boolean changeL = true;
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
    protected Button find;
    @FXML
    protected JFXListView<String> listWords;
    @FXML
    protected HBox function;
    @FXML
    protected Button deleteWord;
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
    private final static String DATABASE_PATH = "./src/main/resources/com/application/learnlingo/database/";
    private final static String DATABASE_NAME = "dict_hh.db";

    protected static DictDMBS evDict = new DictDMBS(DATABASE_PATH,DATABASE_NAME, "AV","defaultAV");
    protected static DictDMBS veDict = new DictDMBS(DATABASE_PATH,DATABASE_NAME, "VA","defaultVA");
    protected static DictDMBS currentDictionary= evDict;

    private static final String FEEDBACK_TXT_PATH
            = "./src/main/resources/com/application/learnlingo/database/feedbacks.txt";
    protected static File feedbackTxt = new File(FEEDBACK_TXT_PATH);
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
    public void handleMouseClicked(MouseEvent mouseEvent) {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            String meaningHTMLString = currentDictionary.getWordInformation(selectedWord).getHtml();
            webEngine.loadContent(meaningHTMLString);
            if (changeL){
                speakUS.setVisible(true);
                speakUK.setVisible(true);
                speakVN.setVisible(false);
            }
            else {
                speakVN.setVisible(true);
                speakUS.setVisible(false);
                speakUK.setVisible(false);
            }
            bookmark.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkMode1.setVisible(false);
        checkMode2.setVisible(false);
        checkMode3.setVisible(false);
        checkMode4.setVisible(false);
        game.setOnAction(e -> {
            AnimationChangeScene.handleButtonClick("gameController.fxml", container);
            if (!changeL){
                changeMode();
            }
        });
        add.setOnAction(e -> {
            AnimationChangeScene.handleButtonClick("changeWordController.fxml", container);
            if (!changeL){
                changeMode();
            }
        });
        search.setOnAction(e -> {
            AnimationChangeScene.handleButtonClick("hello-view.fxml", container);
            if (!changeL){
                changeMode();
            }
        });
        history.setOnAction(e -> {
            AnimationChangeScene.handleButtonClick("BookMark.fxml", container);
            if (!changeL){
                changeMode();
            }
        });
        settings.setOnAction(e -> {
            AnimationChangeScene.handleButtonClick("Settings.fxml", container);
            if (!changeL){
                changeMode();
            }
        });
        tudien.setOnAction(e -> {
            AnimationChangeScene.handleButtonClick("hello-view.fxml", container);
            if (!changeL){
                changeMode();
            }
        });
        dich.setOnAction(e -> {
            AnimationChangeScene.handleButtonClick("TranslationController.fxml", container);
            if (!changeL){
                changeMode();
            }
        });
        synonym.setOnAction(e -> {
            AnimationChangeScene.handleButtonClick("FindSynonym.fxml", container);
            if (!changeL){
                changeMode();
            }
        });
        antonym.setOnAction(e -> {
            AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container);
            if (!changeL){
                changeMode();
            }
        });
    }

    @FXML
    public void setMenu() {
        checkMenuBar = !checkMenuBar;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(left);
        if (!checkMenuBar) {
            left.setVisible(true);
            left.setPrefWidth(100);
            slide.setToX(0);
            slide.play();
        } else {
            slide.setToX(-100);
            slide.play();
        }
    }
    @FXML
    public void changeMode() {
        if (changeL) {
            currentDictionary = veDict;
            changeDictionary.getChildren().removeAll(british, vn, change);
            changeDictionary.getChildren().addAll(vn, change, british);
            tudien.setText("Từ điển");
            dich.setText("Dịch câu");
            synonym.setText("Đồng nghĩa");
            antonym.setText("Trái nghĩa");
        } else {
            currentDictionary = evDict;
            changeDictionary.getChildren().removeAll(vn, british, change);
            changeDictionary.getChildren().addAll(british, change, vn);
            tudien.setText("Dictionary");
            dich.setText("Translation");
            synonym.setText("Synonyms");
            antonym.setText("Antonyms");
        }
        changeL=!changeL;
    }

}

