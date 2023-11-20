package com.application.learnlingo.Controller;

import com.application.learnlingo.Model.TextToSpeech;
import com.application.learnlingo.Model.WordOfTheDay;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class SynonymAndAntonym extends GeneralController {
    public Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15;
    public Button[] buttons;
    @FXML
    private AnchorPane confirmAdd;
    @FXML
    private WebView wordOfDayWebView;

    private WebEngine webEngine;

    public void setBut(String s, Button x) {
        buttons = new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15};
        List<String> result = this.setList(x.getText(), s);
        if (result.isEmpty()) {
            if (Objects.equals(s, "syn")) buttons[0].setText("There is no synonym for this word.");
            else buttons[0].setText("There is no antonym for this word.");
            for (int i = 1; i < 15; i++) {
                buttons[i].setVisible(false);
            }
        } else {
            for (int i = 0; i < 15; i++) {
                if (i < result.size()) {
                    buttons[i].setVisible(true);
                    buttons[i].setText(result.get(i));
                } else {
                    buttons[i].setVisible(false);
                }
            }
        }
    }

    public void buttonClicked(MouseEvent mouseEvent, String s) {
        Button clickedButton = (Button) mouseEvent.getSource();
        String txt = clickedButton.getText();
        setBut(s, clickedButton);
        textfield.setText(txt);
        listWords.getItems().removeAll(listWords.getItems());
        listWords.getItems().addAll(suggestionSearchList(txt));
        displayListWord();
    }

    public void changeMode() {
        if (changeL) {
            changeDictionary.getChildren().removeAll(british, vn, changeModeButton);
            changeDictionary.getChildren().addAll(vn, changeModeButton, british);
            dictionaryButton.setText("Từ điển");
            translationButton.setText("Dịch câu");
            synonymButton.setText("Đồng nghĩa");
            antonymButton.setText("Trái nghĩa");
        } else {
            changeDictionary.getChildren().removeAll(vn, british, changeModeButton);
            changeDictionary.getChildren().addAll(british, changeModeButton, vn);
            dictionaryButton.setText("Dictionary");
            translationButton.setText("Translation");
            synonymButton.setText("Synonyms");
            antonymButton.setText("Antonyms");
        }
        changeL = !changeL;
    }

    @FXML
    public void deleteSearch() {
        textfield.setText("");
        listWords.getItems().clear();
    }


    public List<String> setList(String s, String a) {
        List<String> result = new ArrayList<>();
        try {
            String apiUrl = "https://api.datamuse.com/words?rel_" + a + "=" + s.replaceAll(" ", "+");

            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            connection.disconnect();

            String[] words = response.toString().split("(?<=\\}),");
            for (String word : words) {
                String[] wordParts = word.split(":");
                if (wordParts.length >= 2) {
                    String cleanWord = wordParts[1].replaceAll("\"", "");
                    cleanWord = cleanWord.split(",")[0];
                    result.add(cleanWord);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void handleMouseClicked(String s) {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            buttons = new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15};
            List<String> result = this.setList(selectedWord, s);
            if (result.isEmpty()) {
                if (Objects.equals(s, "syn")) buttons[0].setText("There is no synonym for this word.");
                else buttons[0].setText("There is no antonym for this word.");
                for (int i = 1; i < 15; i++) {
                    buttons[i].setVisible(false);
                }
            } else {
                for (int i = 0; i < 15; i++) {
                    if (i < result.size()) {
                        buttons[i].setVisible(true);
                        buttons[i].setText(result.get(i));
                    } else {
                        buttons[i].setVisible(false);
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        changeL = true;
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
        currentDictionary = evDict;
        listWords.setVisible(false);
        listWords.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        DictionaryController.checkStyle = false;
        listWords.setCellFactory(param -> new DictionaryController.IconAndFontListCell());
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + "bg3.jpg")).toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        center.setBackground(background);
        webEngine = wordOfDayWebView.getEngine();
        webEngine.loadContent(WordOfTheDay.getDefinition());
        showBookmarkButton(true);
    }

    @FXML
    public void speakWordUS() {
        String line = WordOfTheDay.getWordToday();
        TextToSpeech pronounce = new TextToSpeech(evDict.getWordInformation(line).getWord(), "hl=en-us", "Mike", Integer.toString(DictionaryController.speedRate));
    }

    @FXML
    public void speakWordUK() {
        String line = WordOfTheDay.getWordToday();
        TextToSpeech pronounce = new TextToSpeech(evDict.getWordInformation(line).getWord(), "hl=en-gb", "LiLy", Integer.toString(DictionaryController.speedRate));
    }

    @FXML
    public void saveWordInBookMark() {
        confirmAdd.setVisible(true);
        if (evDict.contain(WordOfTheDay.getWordToday())) {
            btnYes.setOnAction(ev -> {
                if (evDict.isBookmarked(WordOfTheDay.getWordToday())) {
                    evDict.unsetBookmark(WordOfTheDay.getWordToday());
                } else {
                    evDict.setBookmark(WordOfTheDay.getWordToday());
                }
                confirmAdd.setVisible(false);
                showBookmarkButton(true);
            });
            btnNo.setOnAction(ev -> confirmAdd.setVisible(false));
        } else if (veDict.contain(WordOfTheDay.getWordToday())) {
            btnYes.setOnAction(ev -> {
                if (veDict.isBookmarked(WordOfTheDay.getWordToday())) {
                    veDict.unsetBookmark(WordOfTheDay.getWordToday());
                } else {
                    veDict.setBookmark(WordOfTheDay.getWordToday());
                }
                confirmAdd.setVisible(false);
                showBookmarkButton(true);
            });
            btnNo.setOnAction(ev -> confirmAdd.setVisible(false));
        }

    }

    private void showBookmarkButton(boolean b) {
        bookmark.setVisible(b);
        if (currentDictionary.isBookmarked(WordOfTheDay.getWordToday())) {
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

    @FXML
    public void handleClickOnSearch(String s) {
        if (listWords.getItems().contains(textfield.getText())) {
            listWords.getSelectionModel().select(textfield.getText());
            handleMouseClicked(s);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("The word you are finding don't exist, try to find again");
            alert.showAndWait();
        }
    }

    @FXML
    public void handlePressEnterInTextField(KeyEvent event, String s) {
        if (event.getCode() == KeyCode.ENTER) {
            handleClickOnSearch(s);
        }
    }

    public abstract void buttonClicked(MouseEvent mouseEvent);
}

