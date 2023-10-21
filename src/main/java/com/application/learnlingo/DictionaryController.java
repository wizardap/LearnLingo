package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DictionaryController extends GeneralController {
    public static int speedRate;
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

    @FXML
    private void deleteSearch() {
        listWords.getItems().clear();
        textfield.setText("");
    }

    @FXML
    public void saveWordInBookMark() {

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
                for (int i = 0; i < 1; i++) {
                    Label text = new Label(lines[i]);
                    if (i == 0) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listWords.getItems().addAll("Hello", "World", "Hello", "World","Hello");
        listWords.setCellFactory(param -> new IconAndFontListCell());
        tudien.setStyle("-fx-background-color: #dddddd; -fx-min-width: 85;");
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
//        center.setStyle("-fx-background-color: #1d2a57");
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
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {

    }

    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            String meaningHTMLString = "";
            if (isUKFlagVisible) {
                meaningHTMLString = evDict.getWordInformation(selectedWord).getHtml();
            } else meaningHTMLString = veDict.getWordInformation(selectedWord).getHtml();
            webEngine.loadContent(meaningHTMLString);
            speakUS.setVisible(true);
            speakUK.setVisible(true);
            bookmark.setVisible(true);
        }
    }
    @FXML
    public void speakWordUS(){
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        TextToSpeech pronouce = new TextToSpeech(evDict.getWordInformation(selectedWord).getWord(),"hl=en-us","Mike",Integer.toString(speedRate));
    }
    @FXML
    public void speakWordUK(){
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        TextToSpeech pronouce = new TextToSpeech(evDict.getWordInformation(selectedWord).getWord(),"hl=en-gb","LiLy",Integer.toString(speedRate));
    }

    @FXML
    public void setMenu() {
        checkMenuBar = !checkMenuBar;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(left);
        if (!checkMenuBar) {
            left.setVisible(true);
            left.setPrefWidth(100);
            slide.setToX(0);
            slide.play();
        } else {
            slide.setToX(-100);
            slide.play();
        }
    }
}