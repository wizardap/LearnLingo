package com.application.learnlingo.Controller;

import com.application.learnlingo.Model.TextToSpeech;
import com.application.learnlingo.Model.WordOfTheDay;
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

    public static int speedRate;
    protected static boolean checkStyle = false;
    private static String selectedWord;
    @FXML
    protected AnchorPane introduction;
    @FXML
    protected Button btnYes;
    @FXML
    protected Button btnNo;
    @FXML
    protected AnchorPane confirmAdd;
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
        showBookmarkButton(ready);
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
        confirmAdd.setVisible(false);
        checkStyle = false;
        listWords.setVisible(false);
        checkMode1.setVisible(true);
        webEngine = webView.getEngine();
        showBookmarkButton(false);
        speakUK.setVisible(false);
        speakUS.setVisible(false);
        speakVN.setVisible(false);
        listWords.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        loadWordOfTheDay(true);

    }

    @FXML
    public void speakWordVN() {
        String line = selectedWord;
        TextToSpeech pronounce = new TextToSpeech(veDict.getWordInformation(line).getWord(), "hl=vi-vn", "Chi", Integer.toString(speedRate));
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
        if (evDict.contain(selectedWord)) {
            btnYes.setOnAction(ev -> {
                if (evDict.isBookmarked(selectedWord)) {
                    evDict.unsetBookmark(selectedWord);
                } else {
                    evDict.setBookmark(selectedWord);
                }
                confirmAdd.setVisible(false);
                showBookmarkButton(true);
            });
            btnNo.setOnAction(ev -> confirmAdd.setVisible(false));
        } else if (veDict.contain(selectedWord)) {
            btnYes.setOnAction(ev -> {
                if (veDict.isBookmarked(selectedWord)) {
                    veDict.unsetBookmark(selectedWord);
                } else {
                    veDict.setBookmark(selectedWord);
                }
                confirmAdd.setVisible(false);
                showBookmarkButton(true);
            });
            btnNo.setOnAction(ev -> confirmAdd.setVisible(false));
        }
    }

    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        introduction.setVisible(false);
        displayExtensionButton(false);
        listWords.getItems().clear();
        if (!textfield.getText().isEmpty()) {
            loadWordOfTheDay(false);
            listWords.getItems().addAll(suggestionSearchList(textfield.getText()));
            checkStyle = false;
        } else {
            loadWordOfTheDay(true);
            listWords.getItems().addAll(currentDictionary.exportHistoryList());
            checkStyle = true;
        }
        listWords.setCellFactory(param -> new IconAndFontListCell());
        displayListWord();
    }

    protected void loadWordOfTheDay(boolean show) {
        if (!changeL) {
            wordOfDay.setText("Từ của ngày");
        } else {
            wordOfDay.setText("Word of the day");
        }
        if (show) {
            webEngine.loadContent(WordOfTheDay.getDefinition());
            selectedWord = WordOfTheDay.getWordToday();
        }
        speakUS.setVisible(show);
        speakUK.setVisible(show);
        showBookmarkButton(show);
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
            loadWordOfTheDay(true);
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
            if (changeL) {
                speakUS.setVisible(true);
                speakUK.setVisible(true);
                speakVN.setVisible(false);
            } else {
                speakVN.setVisible(true);
                speakUS.setVisible(false);
                speakUK.setVisible(false);
            }
            showBookmarkButton(true);
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
    public void handlePressEnterInTextField(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleClickOnSearch();
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
            if (changeL) {
                speakUS.setVisible(true);
                speakUK.setVisible(true);
                speakVN.setVisible(false);
            } else {
                speakVN.setVisible(true);
                speakUS.setVisible(false);
                speakUK.setVisible(false);
            }
            showBookmarkButton(true);
        }
    }

    public void handleKeyEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleMouseClicked();
        }
    }

    @Override
    public void changeMode() {
        super.changeMode();
        confirmAdd.setVisible(false);
        webEngine.loadContent(WordOfTheDay.getDefinition());
        listWords.getItems().clear();
        listWords.getItems().addAll(currentDictionary.exportHistoryList());
        displayListWord();
        textfield.clear();
        loadWordOfTheDay(true);
    }

    protected void showBookmarkButton(boolean show) {
        bookmark.setVisible(show);
        if (selectedWord != null && !selectedWord.isEmpty()) {
            if (currentDictionary.isBookmarked(selectedWord)) {
                if (changeL) {
                    bookmark.setText("Saved");
                    label.setText("Do you want to unsaved this word?");
                    btnYes.setText("Yes");
                    btnNo.setText("No");
                } else {
                    bookmark.setText("Đã lưu");
                    label.setText("Bạn có muốn không lưu từ này không?");
                    btnYes.setText("Có");
                    btnNo.setText("Không");
                }
            } else {
                if (changeL) {
                    bookmark.setText("Save");
                    label.setText("Do you want to save this word?");
                    btnYes.setText("Yes");
                    btnNo.setText("No");
                } else {
                    bookmark.setText("Lưu");
                    label.setText("Bạn có muốn lưu từ này không?");
                    btnYes.setText("Có");
                    btnNo.setText("Không");
                }
            }
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
                    iconImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + "clock.png")).toString()));
                else
                    iconImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + "find1.png")).toString()));
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

    public static class IconAndFontListCellInBookMark extends ListCell<String> {
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
                    iconImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + "bm.png")).toString()));
                else
                    iconImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + "find1.png")).toString()));
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