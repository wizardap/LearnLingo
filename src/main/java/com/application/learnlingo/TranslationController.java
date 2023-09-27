package com.application.learnlingo;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
    private ImageView translate;

    @FXML
    private ImageView speech;

    @FXML
    private Button lang1;

    @FXML
    private Button lang2;

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
    private BorderPane container;
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
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource("hello-view.fxml")));
            Scene newScene = new Scene(secondView, 910, 600);
            FadeTransition fadeTransition1 = new FadeTransition();
            fadeTransition1.setDuration(Duration.millis(500));
            fadeTransition1.setNode(secondView.getCenter());
            fadeTransition1.setFromValue(0.0);
            fadeTransition1.setToValue(1.0);
            fadeTransition1.play();
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
        if(!changeL)
            tx2.setText(translate("vi", "en", text));
        else
            tx2.setText(translate("en", "vi", text));
    }

    @FXML
    public void translateMode() {
        try {
            translateMini();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void text_to_speech() {
        TextToSpeech pronouce = new TextToSpeech(tx1.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tx1.setWrapText(true);
        tx2.setWrapText(true);
    }
}
