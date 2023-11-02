package com.application.learnlingo;

import javafx.fxml.FXML;
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

    protected static boolean checkStyle = false;

    @FXML
    private AnchorPane introduction;

    public static int speedRate;
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
        speakVN.setVisible(ready);
        if (ready == false) {
            webEngine.loadContent("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if (!changeL) {
            bookmark.setText("Thêm từ này");
        } else {
            bookmark.setText("Add to word list");
        }
        checkStyle = false;
        listWords.setVisible(false);
        checkMode1.setVisible(true);
        webEngine = webView.getEngine();
        bookmark.setVisible(false);
        speakUK.setVisible(false);
        speakUS.setVisible(false);
        speakVN.setVisible(false);
        listWords.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }
    @FXML
    public void speakWordVN() {
        String line = listWords.getSelectionModel().getSelectedItem();
        TextToSpeech pronounce = new TextToSpeech(veDict.getWordInformation(line).getWord(), "hl=vi-vn","Chi", Integer.toString(speedRate));
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
    }

    @FXML
    public void saveWordInBookMark() {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        currentDictionary.setBookmark(selectedWord);
    }

    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        introduction.setVisible(false);
        displayExtensionButton(false);
        listWords.getItems().clear();
        if (!textfield.getText().isEmpty()) {
            listWords.getItems().addAll(suggestionSearchList(textfield.getText().toLowerCase()));
            checkStyle = false;
        } else {
            listWords.getItems().addAll(currentDictionary.exportHistoryList());
            checkStyle = true;
        }
        listWords.setCellFactory(param -> new IconAndFontListCell());
        displayListWord();
    }

    @FXML
    public void handleSearchMouseClicked(MouseEvent mouseEvent) {
        if (textfield.getText().isEmpty() && listWords.getItems().isEmpty()) {
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

    public static class IconAndFontListCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                String[] lines = item.split("\n");

                HBox hbox = new HBox();
                ImageView iconImageView;
                if (checkStyle)
                    iconImageView = new ImageView(new Image(getClass().getResource("image/clock.png").toString()));
                else
                    iconImageView = new ImageView(new Image(getClass().getResource("image/find1.png").toString()));
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