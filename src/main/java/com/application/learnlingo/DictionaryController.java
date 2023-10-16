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

public class DictionaryController extends GeneralController implements Initializable {

    @FXML
    private Button settings;
    @FXML
    private Button speakUS;
    @FXML
    private Button speakUK;
    static Boolean b = true;
    @FXML
    static boolean changeL = true;
    @FXML
    private Button bookmark;
    @FXML
    private Button add;
    @FXML
    private VBox left;
    @FXML
    private WebView webView;
    @FXML
    private WebEngine webEngine;
    @FXML
    private AnchorPane center;
    @FXML
    private Button find;
    @FXML
    private JFXListView<String> listWords;
    @FXML
    private HBox function;
    @FXML
    private Button deleteWord;
    @FXML
    private TextField textfield;
    @FXML
    private Button history;
    @FXML
    private Button change;
    @FXML
    private ImageView british;
    @FXML
    private ImageView vn;
    @FXML
    private HBox changeDictionary;
    @FXML
    static boolean isUKFlagVisible = true;
    @FXML
    private JFXButton tudien;
    @FXML
    private JFXButton dich;
    @FXML
    private JFXButton synonym;
    @FXML
    private JFXButton antonym;
    @FXML
    private BorderPane container;
    @FXML
    private Button search;

    @FXML
    private Button game;

    @FXML
    private Button menu;

    private boolean checkMenuBar = false;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}