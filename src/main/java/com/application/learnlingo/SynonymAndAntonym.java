package com.application.learnlingo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

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

    public void setBut(String s, Button x){
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
    public void buttonClicked(MouseEvent mouseEvent,String s) {
        Button clickedButton = (Button) mouseEvent.getSource();
        setBut(s, clickedButton);
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

    public void handleMouseClicked(MouseEvent mouseEvent, String s) {
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
        changeDictionary.getChildren().removeAll(vn, british, change);
        changeDictionary.getChildren().addAll(british, change, vn);
        tudien.setText("Dictionary");
        dich.setText("Translation");
        synonym.setText("Synonyms");
        antonym.setText("Antonyms");
        currentDictionary = evDict;
        listWords.setVisible(false);
        listWords.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listWords.setCellFactory(param -> new DictionaryController.IconAndFontListCell());
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        center.setBackground(background);
    }

    @FXML
    public void speakWordUS() {
        TextToSpeech pronouce = new TextToSpeech(evDict.getWordInformation("enormity").getWord(), "hl=en-us", "Mike", Integer.toString(DictionaryController.speedRate));
    }

    @FXML
    public void speakWordUK() {
        TextToSpeech pronouce = new TextToSpeech(evDict.getWordInformation("enormity").getWord(), "hl=en-gb", "LiLy", Integer.toString(DictionaryController.speedRate));
    }

    @FXML
    public void saveWordInBookMark() {
        String selectedWord = "enormity";
        currentDictionary.setBookmark(selectedWord);
    }

    public abstract void buttonClicked(MouseEvent mouseEvent);
}

