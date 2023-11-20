package com.application.learnlingo.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookMarkController extends DictionaryController {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        listWords.setVisible(true);
        center.setStyle("-fx-background-color: #F4F4F4");
        checkMode1.setVisible(false);
        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
        checkStyle = true;
        showBookmarkButton(false);
        speakUK.setVisible(false);
        speakUS.setVisible(false);
        speakVN.setVisible(false);
        listWords.setCellFactory(param -> new DictionaryController.IconAndFontListCellInBookMark());
        displayListWord();
        loadWordOfTheDay(true);
    }

    @Override
    public void speakWordUS() {
        super.speakWordUS();
    }

    @Override
    public void speakWordUK() {
        super.speakWordUK();
    }

    @Override
    public void speakWordVN() {
        super.speakWordVN();
    }

    public void deleteWordInBookMark() {
        saveWordInBookMark();
        EventHandler<ActionEvent> current = btnYes.getOnAction();
        btnYes.setOnAction(e->{
            current.handle(e);
            listWords.getItems().clear();
            listWords.getItems().addAll(currentDictionary.exportBookmarkList());
            displayListWord();
        });
    }

    @Override
    public ObservableList<String> suggestionSearchList(String text) {
        List<String> suggestionList = currentDictionary.exportBookmarkSuggestionList(text);
        return FXCollections.observableList(suggestionList);
    }

    @Override
    public void handleKeyTyped(KeyEvent keyEvent) {
        listWords.getItems().clear();
        displayExtensionButton(false);
        if (!textfield.getText().isEmpty()) {
            loadWordOfTheDay(false);
            listWords.getItems().addAll(suggestionSearchList(textfield.getText()));
            checkStyle = false;
        } else {
            loadWordOfTheDay(true);
            listWords.getItems().addAll(currentDictionary.exportBookmarkList());
            checkStyle = true;
        }
        listWords.setCellFactory(param -> new DictionaryController.IconAndFontListCellInBookMark());
        displayListWord();
    }


    @Override
    public void handleMouseClicked() {
        super.handleMouseClicked();
    }

    @Override
    public void changeMode() {
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
        listWords.getItems().clear();
        listWords.getItems().addAll(suggestionSearchList(textfield.getText()));
        displayListWord();
        loadWordOfTheDay(true);
    }

    @Override
    public void deleteSearch() {
        listWords.getItems().clear();
        textfield.setText("");
        listWords.getItems().addAll(suggestionSearchList(textfield.getText()));
        checkStyle = true;
        displayListWord();
        loadWordOfTheDay(true);
    }

}