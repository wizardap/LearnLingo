package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
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
    private ListView<String> listWord;
    static Boolean b = true;
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
    private JFXButton change;
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
    public void handleButtonClick() {
        makeFadeOut();
    }

    private void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(container.getCenter());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> {
            setTranslation();
        });
        fadeTransition.play();
    }

    private void setTranslation() {
        try {
            BorderPane secondView = (BorderPane) FXMLLoader.load(Objects.requireNonNull(DictionaryApplication.class.getResource("TranslationController.fxml")));
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

    @FXML
    public void setTranslationMode() {
        FadeTransition animation3 = new FadeTransition(Duration.seconds(2), dich);
        animation3.setFromValue(0.0);
        animation3.setToValue(1.0);
        animation3.play();
        b = false;
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
        speech1.setOnMouseClicked(e -> text_to_speech());
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
        Label warning = new Label("Vui lòng nhập không quá 150 ký tự!!!");
        warning.setStyle("-fx-background-color: #1d2a57; -fx-text-fill: #ffffff;" +
                " -fx-font-weight: bold; -fx-font-size: 15;" +
                "-fx-padding: 10, 10, 10, 10; -fx-background-radius: 20;");
        HBox left_bottom = new HBox(speech1, warning , btn);
        left_bottom.setSpacing(3);
        HBox right_bottom = new HBox();
        tx1.setMinHeight(250);
        tx1.setMaxWidth(400);
        tx2.setMinHeight(250);
        tx2.setMaxWidth(400);
        left.getChildren().addAll(lang1, tx1, left_bottom);
        right.getChildren().addAll(lang2, tx2, right_bottom);
        HBox hbox = new HBox(left, center, right);
        ImageView img1 = new ImageView("C:\\IdeaProjects\\LearnLingo\\src\\main\\resources\\com\\application\\learnlingo\\image\\img1.png");
        img1.setFitWidth(275);
        img1.setFitHeight(140);
        ImageView img2 = new ImageView("C:\\IdeaProjects\\LearnLingo\\src\\main\\resources\\com\\application\\learnlingo\\image\\img2.png");
        img2.setFitWidth(275);
        img2.setFitHeight(140);
        ImageView img3 = new ImageView("C:\\IdeaProjects\\LearnLingo\\src\\main\\resources\\com\\application\\learnlingo\\image\\img3.png");
        img3.setFitWidth(275);
        img3.setFitHeight(140);

        img2.setStyle("-fx-background-radius: 20");
        img3.setStyle("-fx-background-radius: 20");
        HBox img_container = new HBox(img1, img2, img3);
        img_container.setSpacing(10);
        VBox main_container = new VBox(hbox, img_container);
        main_container.setSpacing(20);
        if(b == false) {
            container.setCenter(main_container);
            BorderPane.setMargin(container.getCenter(), new Insets(20, 0, 0, 10));
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
    public void translateE_to_V() throws IOException {
        String text = tx1.getText();
        if(changeL == false)
            tx2.setText(translate("vi", "en", text));
        else
            tx2.setText(translate("en", "vi", text));
    }

    public void text_to_speech() {
        TextToSpeech pronouce = new TextToSpeech(tx1.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listWord.getItems().add("Heheheh");
    }
}