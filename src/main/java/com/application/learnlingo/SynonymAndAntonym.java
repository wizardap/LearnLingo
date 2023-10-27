package com.application.learnlingo;

import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SynonymAndAntonym extends GeneralController {

    public void changeMode() {
        if (isUKFlagVisible) {
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
        isUKFlagVisible = !isUKFlagVisible;
    }

    public void handleMouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        listWords.setVisible(false);
        listWords.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listWords.setCellFactory(param -> new DictionaryController.IconAndFontListCell());
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
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
        try {
            List<String> bookmarkList = new ArrayList<>();
            boolean isBookmarked = false;
            String selectedWord = "enormity";
            FileReader fr = new FileReader(bookmarkTxt);
            BufferedReader input = new BufferedReader(fr);
            String line;
            while ((line = input.readLine()) != null) {
                if (line.equals(selectedWord)) {
                    isBookmarked = true;
                }
                bookmarkList.add(line);
            }
            input.close();
            fr.close();
            FileWriter fw = new FileWriter(bookmarkTxt);
            BufferedWriter output = new BufferedWriter(fw);
            if (!isBookmarked) {
                output.write(selectedWord);
                output.newLine();
            }
            for (String bookmarkedWord : bookmarkList) {
                output.write(bookmarkedWord);
                output.newLine();
            }
            output.close();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

