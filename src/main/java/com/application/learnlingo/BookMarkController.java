package com.application.learnlingo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookMarkController extends DictionaryController {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if (!changeL) {
            bookmark.setText("Xóa từ này");
        } else {
            bookmark.setText("Delete this word");
        }
        listWords.setVisible(true);
        center.setStyle("-fx-background-color: #F4F4F4");
        checkMode1.setVisible(false);
        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
        checkStyle = true;
        listWords.setCellFactory(param -> new DictionaryController.IconAndFontListCell());
        int s = listWords.getItems().size();
        if (s < 23)
            listWords.setPrefHeight(3 + 24 * s);
        else
            listWords.setPrefHeight(550);
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
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
    }

    @Override
    public ObservableList<String> suggestionSearchList(String text) {
        List<String> suggestionList = currentDictionary.exportBookmarkSuggestionList(text);
        return FXCollections.observableList(suggestionList);
    }

    @Override
    public void handleKeyTyped(KeyEvent keyEvent) {
        listWords.getItems().clear();
        if (!textfield.getText().isEmpty()) {
            listWords.getItems().addAll(suggestionSearchList(textfield.getText()));
            checkStyle = false;
        } else {
            listWords.getItems().addAll(currentDictionary.exportBookmarkList());
            checkStyle = true;
        }
        listWords.setCellFactory(param -> new DictionaryController.IconAndFontListCell());
        displayListWord();
    }

    @Override
    public void handleSearchMouseClicked(MouseEvent mouseEvent) {
        super.handleSearchMouseClicked(mouseEvent);
    }

    @Override
    public void handleMouseClicked(MouseEvent mouseEvent) {
        super.handleMouseClicked(mouseEvent);
    }

    @Override
    public void changeMode() {
        super.changeMode();
        if (!changeL) {
            bookmark.setText("Xóa từ này");
        } else {
            bookmark.setText("Delete this word");
        }
        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
        displayListWord();
    }

    @Override
    public void deleteSearch() {
        listWords.getItems().clear();
        textfield.setText("");
        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
        displayListWord();
    }
}