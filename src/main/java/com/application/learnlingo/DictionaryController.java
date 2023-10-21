package com.application.learnlingo;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DictionaryController extends GeneralController {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tudien.setStyle("-fx-background-color: #FEC400; -fx-min-width: 85;");
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
        center.setStyle("-fx-background-color: #F4F4F4");
        left.setVisible(false);
        left.setTranslateX(-100);
        bookmark.setVisible(false);
        speakUK.setVisible(false);
        speakUS.setVisible(false);
        settings.setOnAction(e -> AnimationChangeScene.handleButtonClick("Settings.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        add.setOnAction(e -> AnimationChangeScene.handleButtonClick("changeWordController.fxml", container));
        dich.setOnAction(e -> AnimationChangeScene.handleButtonClick("TranslationController.fxml", container));
        game.setOnAction(e -> AnimationChangeScene.handleButtonClick("gameController.fxml", container));
        synonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindSynonym.fxml", container));
        antonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container));
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
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        TextToSpeech pronouce = new TextToSpeech(evDict.getWordInformation(selectedWord).getWord(), "hl=en-us", "Mike", Integer.toString(speedRate));
    }

    @FXML
    public void speakWordUK() {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        TextToSpeech pronouce = new TextToSpeech(evDict.getWordInformation(selectedWord).getWord(), "hl=en-gb", "LiLy", Integer.toString(speedRate));
    }

    @FXML
    public void setMenu() {
        checkMenuBar = !checkMenuBar;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(left);
        if (checkMenuBar) {
            left.setVisible(true);
            left.setPrefWidth(100);
            slide.setToX(0);
            slide.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                if (child.getId() == null || !child.getId().equals("left")) {
                    child.setOpacity(0.8);
                } else {
                    child.setOpacity(1);
                }
            }
        } else {
            slide.setToX(-100);
            slide.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                child.setOpacity(1);
            }
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


    }

    private ObservableList<String> suggestionSearchList(String text) {
        if (isUKFlagVisible) {
            return evDict.exportSuggestionList(text);
        }
        return veDict.exportSuggestionList(text);
    }

    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        listWords.getItems().clear();

        if (!textfield.getText().isEmpty()) {
            listWords.getItems().addAll(suggestionSearchList(textfield.getText()));
        }
        else {
            listWords.getItems().addAll(recentlySearchList());
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
            //
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


    private static class IconAndFontListCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                String[] lines = item.split("\n");

                HBox hbox = new HBox();

                ImageView iconImageView = new ImageView(new Image(getClass().getResource("image/clock.png").toString()));
                iconImageView.setFitHeight(16);
                iconImageView.setFitWidth(16);
                hbox.getChildren().add(iconImageView);

                VBox vBox = new VBox();
                for (int i = 0; i < 3; i++) {
                    Label text = new Label(lines[i]);
                    if (i == 1) {
                        text.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
                    } else {
                        text.setStyle("-fx-font-size: 12; -fx-text-fill: white;");

                    }
                    vBox.getChildren().add(text);
                }
                hbox.setSpacing(5);

                hbox.setSpacing(15);

                hbox.getChildren().add(vBox);
                setGraphic(hbox);
            }
        }
    }

}