package com.application.learnlingo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryController extends GeneralController {

    public static int speedRate;
    @FXML
    protected Button btnYes;
    @FXML
    protected Button btnNo;
    @FXML
    protected AnchorPane confirmAdd;
    @FXML
    private Label rw9;
    @FXML
    private Label rw10;
    @FXML
    private Label rw11;
    @FXML
    private Label rw12;
    @FXML
    private Label rw13;
    @FXML
    private Label rw14;
    @FXML
    private Label rw15;
    @FXML
    private Label rw16;
    private DictionaryCache searchCache = new DictionaryCache();

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
    }

    public void displayExtensionButton(boolean ready) {
        bookmark.setVisible(ready);
        speakUK.setVisible(ready);
        speakUS.setVisible(ready);
        if (ready == false) {
            webEngine.loadContent("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        confirmAdd.setVisible(false);
        listWords.setCellFactory(param -> new IconAndFontListCell());
        listWords.setVisible(false);
        checkMode1.setVisible(true);
        if (!SettingsController.changeL) {
            changeDictionary.getChildren().removeAll(british, vn, change);
            changeDictionary.getChildren().addAll(vn, change, british);
            tudien.setText("Từ điển");
            dich.setText("Dịch câu");
            synonym.setText("Đồng nghĩa");
            antonym.setText("Trái nghĩa");
        } else {
            changeDictionary.getChildren().removeAll(vn, british, change);
            changeDictionary.getChildren().addAll(british, change, vn);
            tudien.setText("Dictionary");
            dich.setText("Translation");
            synonym.setText("Synonyms");
            antonym.setText("Antonyms");
        }
        webEngine = webView.getEngine();
        bookmark.setVisible(false);
        speakUK.setVisible(false);
        speakUS.setVisible(false);
        listWords.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    @FXML
    public void speakWordUS() {
        String line = currentDictionary.getHistoryString(0);
        TextToSpeech pronounce = new TextToSpeech(evDict.getWordInformation(line).getWord(), "hl=en-us", "Mike", Integer.toString(speedRate));
    }

    @FXML
    public void speakWordUK() {
        String line = currentDictionary.getHistoryString(0);
        TextToSpeech pronounce = new TextToSpeech(evDict.getWordInformation(line).getWord(), "hl=en-gb", "LiLy", Integer.toString(speedRate));

    }

    @FXML
    public void deleteSearch() {
        listWords.getItems().clear();
        textfield.setText("");
        listWords.getItems().addAll(currentDictionary.exportHistoryList());
        displayListWord();
        displayExtensionButton(false);
    }

    @FXML
    public void saveWordInBookMark() {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        confirmAdd.setVisible(true);
        btnYes.setOnAction(ev -> {
            confirmAdd.setVisible(false);
            currentDictionary.setBookmark(selectedWord);
        });
        btnNo.setOnAction(ev -> {
            confirmAdd.setVisible(false);
            currentDictionary.unsetBookmark(selectedWord);
        });
    }

    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        listWords.getItems().clear();
        if (!textfield.getText().isEmpty()) {
            listWords.getItems().addAll(suggestionSearchList(textfield.getText()));

        } else {
            listWords.getItems().addAll(currentDictionary.exportHistoryList());
        }
        displayListWord();
    }

    @FXML
    public void handleSearchMouseClicked(MouseEvent mouseEvent) {
        if (textfield.getText().isEmpty()) {
            listWords.getItems().addAll(currentDictionary.exportHistoryList());
            displayListWord();
        }
    }

    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            String meaningHTMLString = "";
            meaningHTMLString = searchCache.getWordInformation(selectedWord).getHtml();
            webEngine.loadContent(meaningHTMLString);
            currentDictionary.insertToHistoryList(selectedWord);
            speakUS.setVisible(true);
            speakUK.setVisible(true);
            bookmark.setVisible(true);
        }
    }

    public static class IconAndFontListCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                String[] lines = item.split("\n");

                HBox hbox = new HBox();
                ImageView iconImageView = new ImageView(new Image(getClass().getResource("image/clock.png").toString()));
                iconImageView.setFitHeight(15);
                iconImageView.setFitWidth(15);
                hbox.getChildren().add(iconImageView);

                VBox vBox = new VBox();
                for (int i = 0; i < 1; i++) {
                    Label text = new Label(lines[i]);
                    if (i == 0) {
                        text.setStyle("-fx-font-size: 12; -fx-text-fill: white;");
                    } else {
                        text.setStyle("-fx-font-size: 12; -fx-text-fill: white;");
                    }
                    vBox.getChildren().add(text);
                }
                hbox.setSpacing(5);
                hbox.getChildren().add(vBox);
                setGraphic(hbox);
            }
        }
    }
}