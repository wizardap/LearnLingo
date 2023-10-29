package com.application.learnlingo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DictionaryController extends GeneralController {

    @FXML
    private AnchorPane introduction;

    @FXML
    private Button btnYes;

    @FXML
    private Button btnNo;

    @FXML
    private AnchorPane confirmAdd;

    public static int speedRate;
    private DictionaryCache searchCache = new DictionaryCache();

    @FXML
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
        bookmark.setVisible(false);
        speakUK.setVisible(false);
        speakUS.setVisible(false);
        webEngine = webView.getEngine();
        listWords.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    private ObservableList<String> recentlySearchList() {
        List<String> historyList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(historyTxt);
            BufferedReader input = new BufferedReader(fr);
            String line;
            while ((line = input.readLine()) != null) {
                historyList.add(line);
            }
            input.close();
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return FXCollections.observableList(historyList);
    }

    @FXML
    public void speakWordUS() {
        try {
            FileReader fileReader = new FileReader(historyTxt);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                TextToSpeech pronouce = new TextToSpeech(evDict.getWordInformation(line).getWord(), "hl=en-us", "Mike", Integer.toString(speedRate));
                break;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void speakWordUK() {
        try {
            FileReader fileReader = new FileReader(historyTxt);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                TextToSpeech pronouce = new TextToSpeech(evDict.getWordInformation(line).getWord(), "hl=en-gb", "LiLy", Integer.toString(speedRate));
                break;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteSearch() {
        listWords.getItems().clear();
        textfield.setText("");
        listWords.getItems().addAll(recentlySearchList());
    }

    @FXML
    public void saveWordInBookMark() {
        confirmAdd.setVisible(true);
        btnYes.setOnAction(ev -> {
            confirmAdd.setVisible(false);
            try {
                List<String> bookmarkList = new ArrayList<>();
                boolean isBookmarked = false;
                String selectedWord = listWords.getSelectionModel().getSelectedItem();
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
        });
        btnNo.setOnAction(ev -> confirmAdd.setVisible(false));
    }

    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        introduction.setVisible(false);
        listWords.getItems().clear();
        if (!textfield.getText().isEmpty()) {
            listWords.setVisible(true);
            listWords.getItems().addAll(suggestionSearchList(textfield.getText()));
            int s = listWords.getItems().size();
            if (s < 23)
                listWords.setPrefHeight(3 + 24 * s);
            else
                listWords.setPrefHeight(550);
        }
        else {
            listWords.getItems().addAll(recentlySearchList());
            int k = listWords.getItems().size();
            if (k < 23)
                listWords.setPrefHeight(3 + 24 * k);
            else
                listWords.setPrefHeight(550);
        }
    }

    @FXML
    public void handleSearchMouseClicked(MouseEvent mouseEvent) {
        if (textfield.getText().isEmpty()) {
            listWords.getItems().addAll(recentlySearchList());
        }
    }

    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            String meaningHTMLString = "";
            meaningHTMLString = searchCache.getWordInformation(selectedWord).getHtml();
            webEngine.loadContent(meaningHTMLString);
            try {
                List<String> historyList = new ArrayList<>();
                FileReader fr = new FileReader(historyTxt);
                BufferedReader input = new BufferedReader(fr);
                String line;
                while ((line = input.readLine()) != null) {
                    historyList.add(line);
                }
                input.close();
                fr.close();
                FileWriter fw = new FileWriter(historyTxt);
                BufferedWriter output = new BufferedWriter(fw);
                output.write(selectedWord);
                output.newLine();

                for (String historyWord : historyList) {
                    if (!historyWord.equals(selectedWord)) {
                        output.write(historyWord);
                        output.newLine();
                    }
                }
                output.close();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            speakUS.setVisible(true);
            speakUK.setVisible(true);
            bookmark.setVisible(true);
        }
    }
}