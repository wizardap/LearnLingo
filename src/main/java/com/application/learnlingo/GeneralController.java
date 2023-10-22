package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralController implements Initializable {

    @FXML
    protected Button settings;
    @FXML
    protected Button speakUS;
    @FXML
    protected Button speakUK;
    static Boolean b = true;
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
    static boolean isUKFlagVisible = true;
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

    protected boolean checkMenuBar = false;
    private final static String DEFAULT_DICT_DBMS_PATH
            = "./src/main/resources/com/application/learnlingo/database/dict_hh.db";

    protected static DictDMBS evDict = new DictDMBS(DEFAULT_DICT_DBMS_PATH, "av");
    protected static DictDMBS veDict = new DictDMBS(DEFAULT_DICT_DBMS_PATH, "va");

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
        if (isUKFlagVisible) {
            return evDict.exportSuggestionList(text);
        }
        return veDict.exportSuggestionList(text);
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {

    }

    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            String meaningHTMLString = "";
            if (isUKFlagVisible) {
                meaningHTMLString = evDict.getWordInformation(selectedWord).getHtml();
            } else meaningHTMLString = veDict.getWordInformation(selectedWord).getHtml();
            webEngine.loadContent(meaningHTMLString);
            speakUS.setVisible(true);
            speakUK.setVisible(true);
            bookmark.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
