package com.application.learnlingo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookMarkController extends DictionaryController {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if (!changeL) {
            bookmark.setText("Xóa từ này");
            label.setText("Bạn muốn xóa từ này khỏi Bookmark không");
            btnYes.setText("Có");
            btnNo.setText("Không");
        } else {
            bookmark.setText("Delete this word");
            label.setText("Do you want to delete this word in Bookmark");
            btnYes.setText("Yes");
            btnNo.setText("No");
        }
        listWords.setVisible(true);
        center.setStyle("-fx-background-color: #F4F4F4");
        checkMode1.setVisible(false);
        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
        checkStyle = true;
        bookmark.setVisible(false);
        speakUK.setVisible(false);
        speakUS.setVisible(false);
        speakVN.setVisible(false);
        listWords.setCellFactory(param -> new DictionaryController.IconAndFontListCellInBookMark());
        displayListWord();
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
        confirmAdd.setVisible(true);
        btnYes.setOnAction(ev -> {
            String selectedWord = listWords.getSelectionModel().getSelectedItem();
            currentDictionary.unsetBookmark(selectedWord);
            listWords.getItems().removeIf(e -> e.equals(selectedWord));
            confirmAdd.setVisible(false);
            displayListWord();
        });
        btnNo.setOnAction(ev -> confirmAdd.setVisible(false));
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
            changeDictionary.getChildren().removeAll(british, vn, change);
            changeDictionary.getChildren().addAll(vn, change, british);
            bookmark.setText("Xóa từ này");
            label.setText("Bạn muốn xóa từ này khỏi Bookmark không");
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
            bookmark.setText("Delete this word");
            label.setText("Do you want to delete this word in Bookmark");
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
        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
        displayListWord();
    }

    @Override
    public void deleteSearch() {
        listWords.getItems().clear();
        textfield.setText("");
        checkStyle = true;
        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
        displayListWord();
    }

}