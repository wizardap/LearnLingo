package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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


public class TranslationController implements Initializable{

    @FXML
    private JFXButton b1;

    @FXML
    private JFXButton b2;

    @FXML
    private JFXButton b3;

    @FXML
    private VBox left;

    @FXML
    private VBox center;

    @FXML
    private HBox function;

    @FXML
    private Button deleteWord;
    @FXML
    private TextField textfield;

    @FXML
    private ImageView translate;

    @FXML
    private ImageView speech;

    @FXML
    private JFXButton lang1;

    @FXML
    private JFXButton lang2;

    @FXML
    private Button changeMode;
    @FXML
    static Boolean changeL = true;
    @FXML
    static Boolean b = true;

    @FXML
    private TextArea tx1;
    @FXML
    private TextArea tx2;

    @FXML
    private Button search;

    @FXML
    private BorderPane container;

    private void makeFadeOutToBookMark() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(container.getCenter());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> setBookMark());
        fadeTransition.play();
    }

    @FXML
    public void handleButtonClickBookMark() {
        makeFadeOutToBookMark();
    }

    private void setBookMark() {
        try {
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource("BookMark.fxml")));
            Scene newScene = new Scene(secondView, 910, 600);
            Stage curStage = (Stage) container.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeFadeOutToChangeWord() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(container.getCenter());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> setChangeWord());
        fadeTransition.play();
    }

    @FXML
    public void handleButtonClickChangeWord() {
        makeFadeOutToChangeWord();
    }

    private void setChangeWord() {
        try {
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource("changeWordController.fxml")));
            Scene newScene = new Scene(secondView, 910, 600);
            Stage curStage = (Stage) container.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        fadeTransition.setOnFinished(e -> setDictonaryMode());
        fadeTransition.play();
    }

    private void setDictonaryMode() {
        try {
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource("hello-view.fxml")));
            Scene newScene = new Scene(secondView, 910, 600);
            Stage curStage = (Stage) container.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeLanguage() {
        if(changeL == true) {
            lang1.setText("Vietnamese");
            lang2.setText("English");
            changeL = false;
        } else {
            lang1.setText("English");
            lang2.setText("Vietnamese");
            changeL = true;
        }
    }


    @FXML
    public void text_to_speech1() {
        if(changeL) {
            TextToSpeech pronouce = new TextToSpeech(tx1.getText(),"hl=en-us");
        } else {
            TextToSpeech pronouce = new TextToSpeech(tx1.getText(),"hl=vi-vn");
        }
    }

    @FXML
    public void text_to_speech2() {
        if(changeL) {
            TextToSpeech pronouce = new TextToSpeech(tx2.getText(),"hl=vi-vn");
        } else {
            TextToSpeech pronouce = new TextToSpeech(tx2.getText(),"hl=en-us");
        }
    }

    @FXML
    public String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbzF2OvXi2Ka9v4aBMt-7L_TL9fCGfLpAaCqrFfibjNKi6baRPl7Qrlt_zakZH6Y3KE9ew/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    @FXML
    public void translateMini() throws IOException {
        String text = tx1.getText();
        if(!changeL) {
            tx2.setText(translate("vi", "en", text));
        } else {
            tx2.setText(translate("en", "vi", text));
        }
    }

    @FXML
    public void translateMode() {
        try {
            translateMini();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    private static void animation(JFXButton button, int x, int time, String message) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(time));
        transition.setNode(button);
        transition.setToY(x);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
        button.setOnAction(e -> {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(message);
            alert1.show();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
//        animation(b1, 150, 1, "Have a good day <3");
//        animation(b2, -150, 1, "You are so amazing <3");
//        animation(b3, 150, 1, "Wish you everything <3");
        center.setBackground(background);
        tx1.setWrapText(true);
        tx2.setWrapText(true);
    }
}
