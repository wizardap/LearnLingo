package com.application.learnlingo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DictionaryController extends GeneralController {

    protected static boolean checkStyle = false;

    @FXML
    protected AnchorPane introduction;

    public static int speedRate;

    @FXML
    protected Button btnYes;
    @FXML
    protected Button btnNo;
    @FXML
    protected AnchorPane confirmAdd;
    private static String selectedWord;
    @FXML
    private Label wordOfDay;

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

    protected void displayExtensionButton(boolean ready) {
        bookmark.setVisible(ready);
        speakUK.setVisible(ready);
        speakUS.setVisible(ready);
        speakVN.setVisible(ready);
        if (!ready) {
            webEngine.loadContent("");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if (!changeL) {
            bookmark.setText("Thêm từ này");
            label.setText("Bạn muốn thêm từ này vào Bookmark không");
            btnYes.setText("Có");
            btnNo.setText("Không");
        } else {
            bookmark.setText("Add to word list");
            label.setText("Do you want to add this word in Bookmark");
            btnYes.setText("Yes");
            btnNo.setText("No");
        }
        confirmAdd.setVisible(false);
        checkStyle = false;
        listWords.setVisible(false);
        checkMode1.setVisible(true);
        webEngine = webView.getEngine();
        bookmark.setVisible(false);
        speakUK.setVisible(false);
        speakUS.setVisible(false);
        speakVN.setVisible(false);
        listWords.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        loadWordOfTheDay(true);

    }
    @FXML
    public void speakWordVN() {
        String line = selectedWord;
        TextToSpeech pronounce = new TextToSpeech(veDict.getWordInformation(line).getWord(), "hl=vi-vn","Chi", Integer.toString(speedRate));
    }
    @FXML
    public void speakWordUS() {
        String line = selectedWord;
        TextToSpeech pronounce = new TextToSpeech(evDict.getWordInformation(line).getWord(), "hl=en-us", "Mike", Integer.toString(speedRate));
    }

    @FXML
    public void speakWordUK() {
        String line = selectedWord;
        TextToSpeech pronounce = new TextToSpeech(evDict.getWordInformation(line).getWord(), "hl=en-gb", "LiLy", Integer.toString(speedRate));
    }

    @FXML
    public void saveWordInBookMark() {
        confirmAdd.setVisible(true);
        btnYes.setOnAction(ev -> {
            confirmAdd.setVisible(false);
            if (evDict.contain(selectedWord)){
                evDict.setBookmark(selectedWord);
            }
            else{
                veDict.setBookmark(selectedWord);
            }
        });
        btnNo.setOnAction(ev -> {
            confirmAdd.setVisible(false);
            if (evDict.contain(selectedWord)){
                evDict.unsetBookmark(selectedWord);
            }
            else{
                veDict.unsetBookmark(selectedWord);
            }
        });
    }

    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        introduction.setVisible(false);
        displayExtensionButton(false);
        listWords.getItems().clear();
        if (!textfield.getText().isEmpty()) {
            listWords.getItems().addAll(suggestionSearchList(textfield.getText()));
            checkStyle = false;
        } else {
            listWords.getItems().addAll(currentDictionary.exportHistoryList());
            checkStyle = true;
        }
        listWords.setCellFactory(param -> new IconAndFontListCell());
        displayListWord();
    }

    private void loadWordOfTheDay(boolean show) {
        if (show) {
            webEngine.loadContent(WordOfTheDay.getDefinition());
            selectedWord = WordOfTheDay.getWordToday();
        }
        speakUS.setVisible(show);
        speakUK.setVisible(show);
        bookmark.setVisible(show);
        wordOfDay.setVisible(show);
    }

    @FXML
    public void handleSearchMouseClicked() {
        if (textfield.getText().isEmpty() && listWords.getItems().isEmpty()) {
            introduction.setVisible(false);
            checkStyle = true;
            listWords.setCellFactory(param -> new IconAndFontListCell());
            listWords.getItems().addAll(currentDictionary.exportHistoryList());
            displayListWord();
        }
    }

    @FXML
    public void deleteSearch() {
        listWords.getItems().clear();
        textfield.setText("");
        loadWordOfTheDay(true);
        checkStyle = true;
        listWords.getItems().addAll(currentDictionary.exportHistoryList());
        displayListWord();
    }

    @FXML
    public void handleClickOnSearch() {
        if (listWords.getItems().contains(textfield.getText())) {
            loadWordOfTheDay(false);
            selectedWord = textfield.getText();
            listWords.getSelectionModel().select(selectedWord);
            String meaningHTMLString = "";
            meaningHTMLString = currentDictionary.getDefinition(selectedWord);
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
        } else {
            if (changeL) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("The word you are finding don't exist, try to find again");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setContentText("Từ bạn tìm dường như không tồn tại, bạn thử tra lại đi");
                alert.showAndWait();
            }
        }
    }


    @FXML
    public void handleMouseClicked() {
        selectedWord = listWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            String meaningHTMLString = "";
            meaningHTMLString = currentDictionary.getDefinition(selectedWord);
            loadWordOfTheDay(false);
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

    public void handleKeyEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleMouseClicked();
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
                    iconImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("image/clock.png")).toString()));
                else
                    iconImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("image/find1.png")).toString()));
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

    @Override
    public void changeMode() {
        super.changeMode();
        confirmAdd.setVisible(false);
        displayExtensionButton(false);
        webEngine.loadContent("");
        listWords.getItems().clear();
        textfield.clear();
        loadWordOfTheDay(true);
    }
}