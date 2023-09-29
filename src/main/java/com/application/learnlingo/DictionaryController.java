package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {

    @FXML
    private VBox left;

    @FXML
    private WebView wv;

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
    static Boolean b = true;
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
    private boolean isUKFlagVisible = true;
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
    static boolean changeL = true;
    @FXML
    private Button search;
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
    public void handleButtonClick() {
        makeFadeOut();
    }

    private void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(container.getCenter());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> setTranslation());
        fadeTransition.play();
    }

    private void setTranslation() {
        try {
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource("TranslationController.fxml")));
            Scene newScene = new Scene(secondView, 910, 600);
            Stage curStage = (Stage) container.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listWords.getItems().addAll("Hello", "World","Hello", "World","Hello", "World","Hello", "World","Hello", "World");
    }
}