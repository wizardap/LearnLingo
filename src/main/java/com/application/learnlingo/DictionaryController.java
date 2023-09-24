package com.application.learnlingo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DictionaryController {
    @FXML
    static TextArea tx1 = new TextArea();
    @FXML
    static TextArea tx2 = new TextArea();
    static {
        tx1.setWrapText(true);
        tx2.setWrapText(true);
    }
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
    private Button tudien;
    @FXML
    private Button dich;
    @FXML
    private Button synonym;
    @FXML
    private Button antonym;
    @FXML
    private BorderPane container;
    @FXML
    static boolean changeL = true;
    @FXML
    public void changeMode() {
        if (isUKFlagVisible) {
            changeDictionary.getChildren().removeAll(british, vn, change);
            changeDictionary.getChildren().addAll(vn, change, british);
            tudien.setText("Từ điển");
            dich.setText("Dịch câu");
            synonym.setText("Từ đồng nghĩa");
            antonym.setText("Từ trái nghĩa");
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
    public void setTranslationMode() {
        VBox left = new VBox(10);
        VBox right = new VBox(10);
        VBox center = new VBox();
        Region space = new Region();
        space.setMinHeight(200);
        ImageView imageView = new ImageView(new Image("C:\\IdeaProjects\\LearnLingo\\src\\main\\resources\\com\\application\\learnlingo\\image\\change.png"));
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        Button changeMode = new Button("", imageView);
        changeMode.setMinWidth(30);
        changeMode.setMinHeight(30);
        center.getChildren().addAll(space, changeMode);
        Button lang1 = new Button("English");
        lang1.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffffff; " +
                "-fx-background-color: #1d2a57; -fx-font-weight: bold; " +
                "-fx-background-radius: 15; -fx-cursor: hand;");
        lang1.setOnMouseEntered(event -> {
            lang1.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffffff; " +
                    "-fx-background-color: #1d2a57; -fx-font-weight: bold; " +
                    "-fx-background-radius: 15; -fx-cursor: hand; -fx-opacity: 0.9");
        });
        lang1.setOnMouseExited(event -> {
            lang1.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffffff; " +
                    "-fx-background-color: #1d2a57; -fx-font-weight: bold; " +
                    "-fx-background-radius: 15; -fx-cursor: hand;");
        });

        Button lang2 = new Button("Vietnamese");
        lang2.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffffff; " +
                "-fx-background-color: #1d2a57; -fx-font-weight: bold; " +
                "-fx-background-radius: 15; -fx-cursor: hand;");
        lang2.setOnMouseEntered(event -> {
            lang2.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffffff; " +
                    "-fx-background-color: #1d2a57; -fx-font-weight: bold; " +
                    "-fx-background-radius: 15; -fx-cursor: hand; -fx-opacity: 0.9");
        });
        lang2.setOnMouseExited(event -> {
            lang2.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffffff; " +
                    "-fx-background-color: #1d2a57; -fx-font-weight: bold; " +
                    "-fx-background-radius: 15; -fx-cursor: hand;");
        });
        changeMode.setStyle("-fx-background-color: #1d2a57;");
        changeMode.setOnMouseEntered(event -> {
            changeMode.setStyle("-fx-background-color: #1d2a57; -fx-opacity: 0.9; -fx-cursor: hand");
        });
        changeMode.setOnMouseExited(event -> {
            changeMode.setStyle("-fx-background-color: #1d2a57;");
        });
        changeMode.setOnAction(e -> {
            if(changeL == true) {
                lang1.setText("Vietnamese");
                lang2.setText("English");
                changeL = false;
            } else {
                lang1.setText("English");
                lang2.setText("Vietnamese");
                changeL = true;
            }
        });
        ImageView speech1 = new ImageView(new Image("C:\\IdeaProjects\\LearnLingo\\src\\main\\resources\\com\\application\\learnlingo\\image\\volume.png"));
        speech1.setFitWidth(40);
        speech1.setFitHeight(40);
        speech1.setOnMouseEntered(e -> {
            speech1.setStyle("-fx-cursor: hand; -fx-opacity: 0.7");
        });
        speech1.setOnMouseExited(e -> {
            speech1.setStyle("-fx-cursor: hand;");
        });
        ImageView btn = new ImageView(new Image("C:\\IdeaProjects\\LearnLingo\\src\\main\\resources\\com\\application\\learnlingo\\image\\translate.png"));
        btn.setOnMouseEntered(e -> {
            btn.setStyle("-fx-cursor: hand; -fx-opacity: 0.8");
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle("-fx-cursor: hand;");
        });
        btn.setOnMouseClicked(e -> {
            try {
                translateE_to_V();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        Region space1 = new Region();
        space1.setMinWidth(290);
        btn.setFitWidth(70);
        btn.setFitHeight(40);
        HBox left_bottom = new HBox(speech1,space1, btn);
        HBox right_bottom = new HBox();
        tx1.setMinHeight(400);
        tx1.setMaxWidth(400);
        tx2.setMinHeight(400);
        tx2.setMaxWidth(400);
        left.getChildren().addAll(lang1, tx1, left_bottom);
        right.getChildren().addAll(lang2, tx2, right_bottom);
        HBox hbox = new HBox(left, center, right);
        container.setCenter(hbox);
        BorderPane.setMargin(container.getCenter(), new Insets(20, 0, 0, 10));
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
    public void translateE_to_V() throws IOException {
        String text = tx1.getText();
        if(changeL == false)
            tx2.setText(translate("vi", "en", text));
        else
            tx2.setText(translate("en", "vi", text));
    }
}