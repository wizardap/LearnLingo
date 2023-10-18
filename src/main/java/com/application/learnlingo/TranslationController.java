package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.ResourceBundle;


public class TranslationController implements Initializable {

    @FXML
    private Button search;

    @FXML
    private Button add;

    @FXML
    private Button history;

    @FXML
    private Button settings;

    @FXML
    private Button tudien;

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
    private Button game;

    @FXML
    private BorderPane container;

    @FXML
    private Button menu;

    private boolean checkMenuBar = false;

    @FXML
    private Label warning;

    @FXML
    private Button chooseFile;

    public void changeLanguage() {
        if (changeL == true) {
            lang1.setText("Tiếng Việt");
            lang2.setText("Tiếng Anh");
            chooseFile.setText("Chọn ảnh");
            warning.setText("Vui lòng nhập không quá 150 ký tự");
            changeL = false;
        } else {
            lang1.setText("English");
            lang2.setText("Vietnamese");
            chooseFile.setText("Choose image");
            warning.setText("Enter no more than 150 characters");
            changeL = true;
        }
    }


    @FXML
    public void text_to_speech1() {
        if(changeL) {
            TextToSpeech pronouce = new TextToSpeech(tx1.getText(),"hl=en-us","Mike","0");
        } else {
            TextToSpeech pronouce = new TextToSpeech(tx1.getText(),"hl=vi-vn","Chi","0");
        }
    }

    @FXML
    public void text_to_speech2() {
        if(changeL) {
            SettingsController set = new SettingsController();
            TextToSpeech pronouce = new TextToSpeech(tx2.getText(),"hl=vi-vn","Chi","0");
        } else {
            SettingsController set = new SettingsController();
            TextToSpeech pronouce = new TextToSpeech(tx2.getText(),"hl=en-us","Mike","0");
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
        if (!changeL) {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        left.setVisible(false);
        left.setTranslateX(-100);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        center.setBackground(background);
        add.setOnAction(e -> AnimationChangeScene.handleButtonClick("changeWordController.fxml", container));
        search.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        settings.setOnAction(e -> AnimationChangeScene.handleButtonClick("Settings.fxml", container));
        tudien.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        game.setOnAction(e -> AnimationChangeScene.handleButtonClick("gameController.fxml", container));
        tx1.setWrapText(true);
        tx2.setWrapText(true);
    }

    @FXML
    public void setMenu() {
        checkMenuBar = !checkMenuBar;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(left);
        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(0.4));
        slide2.setNode(center);
        if (checkMenuBar) {
            left.setVisible(true);
            left.setPrefWidth(100);
            slide.setToX(0);
            slide.play();
            slide2.setToX(40);
            slide2.play();
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
            slide2.setToX(0);
            slide2.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                child.setOpacity(1);
            }
        }
    }

    @FXML
    public void chooseFileToTranslate() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.jpg"));
        File initialDirectory = new File("imageToChoose");
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.setTitle("Open Resource File");
        java.io.File file = fileChooser.showOpenDialog(center.getScene().getWindow());
        if (file != null) {
            Tesseract tesseract = new Tesseract();
            tesseract.setTessVariable("debug_file", "/dev/null");
            if (!changeL) {
                tesseract.setLanguage("vie");
            } else {
                tesseract.setLanguage("eng");
            }
            try {
                tesseract.setDatapath("Tess4J/tessdata");
                String text = tesseract.doOCR(new File(file.getAbsolutePath())).replaceAll("\n", " ").replaceAll("\r", "");
                tx1.setText(text);
            } catch (TesseractException e) {
                e.printStackTrace();
            }
        }
    }
}
