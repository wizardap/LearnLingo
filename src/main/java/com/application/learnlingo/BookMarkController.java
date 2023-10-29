package com.application.learnlingo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookMarkController extends DictionaryController {

    private static class IconAndFontListCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                String[] lines = item.split("\n");

                HBox hbox = new HBox();

                ImageView iconImageView = new ImageView(new Image(getClass().getResource("image/deleteWordBM.png").toString()));
                iconImageView.setFitHeight(16);
                iconImageView.setFitWidth(16);

                VBox vBox = new VBox();
                vBox.setPrefWidth(170);
                for (int i = 0; i < 1; i++) {
                    Label text = new Label(lines[i]);
                    if (i == 0) {
                        text.setStyle("-fx-font-size: 14; -fx-text-fill: white;");
                    } else {
                        text.setStyle("-fx-font-size: 12; -fx-text-fill: white;");
                    }
                    vBox.getChildren().add(text);
                }
                hbox.setSpacing(5);

                hbox.setSpacing(15);

                hbox.getChildren().addAll(vBox, iconImageView);
                setGraphic(hbox);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        listWords.setVisible(true);
        center.setStyle("-fx-background-color: #F4F4F4");
        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
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
    public void saveWordInBookMark() {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        confirmAdd.setVisible(true);
        btnYes.setOnAction(ev -> {
            confirmAdd.setVisible(false);
            currentDictionary.setBookmark(selectedWord);
            if (!listWords.getItems().contains(selectedWord)){
                listWords.getItems().add(selectedWord);
                displayListWord();
            }
        });
        btnNo.setOnAction(ev -> {
            confirmAdd.setVisible(false);
            currentDictionary.unsetBookmark(selectedWord);
            listWords.getItems().removeIf(e -> e.equals(selectedWord));
            displayListWord();
        });
    }

    @Override
    public ObservableList<String> suggestionSearchList(String text) {
        List<String> suggestionList = currentDictionary.exportBookmarkList();
        return FXCollections.observableList(suggestionList);
    }

    @Override
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
            listWords.getItems().addAll(currentDictionary.exportBookmarkList());
            int k = listWords.getItems().size();
            if (k == 0) {
                listWords.setVisible(false);
            } else if (k < 23)
                listWords.setPrefHeight(3 + 24 * k);
            else
                listWords.setPrefHeight(550);
        }
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
        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
    }

    @Override
    public void deleteSearch() {
        listWords.getItems().clear();
        textfield.setText("");

        listWords.getItems().addAll(currentDictionary.exportBookmarkList());
        int k = listWords.getItems().size();
        if (k == 0) {
            listWords.setVisible(false);
        } else if (k < 23)
            listWords.setPrefHeight(3 + 24 * k);
        else
            listWords.setPrefHeight(550);
    }
}
