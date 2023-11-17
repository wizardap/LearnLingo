package com.application.learnlingo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
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


public class TranslationController extends GeneralController {

    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;

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
    private TextArea tx1;
    @FXML
    private TextArea tx2;

    private boolean checkMenuBar = false;

    @FXML
    private Label warning;

    @FXML
    private Button chooseFile;

    @FXML
    private Label charCountLabel;

    private boolean changeModeTrans;

    public void changeLanguage() {
        if (changeModeTrans) {
            lang1.setText("Tiếng Việt");
            lang2.setText("Tiếng Anh");
            chooseFile.setText("Chọn ảnh");
            warning.setText("Vui lòng nhập không quá 600 ký tự");
        } else {
            lang1.setText("English");
            lang2.setText("Vietnamese");
            chooseFile.setText("Choose image");
            warning.setText("Enter no more than 600 characters");
        }
        changeModeTrans = !changeModeTrans;
    }


    @FXML
    public void text_to_speech1() {
        if(changeModeTrans) {
            TextToSpeech pronouce = new TextToSpeech(tx1.getText(),"hl=en-us","Mike","0");
        } else {
            TextToSpeech pronouce = new TextToSpeech(tx1.getText(),"hl=vi-vn","Chi","0");
        }
    }

    @FXML
    public void text_to_speech2() {
        if(changeModeTrans) {
            SettingsController set = new SettingsController();
            TextToSpeech pronouce = new TextToSpeech(tx2.getText(),"hl=vi-vn","Chi","0");
        } else {
            SettingsController set = new SettingsController();
            TextToSpeech pronouce = new TextToSpeech(tx2.getText(),"hl=en-us","Mike","0");
        }
    }

    @FXML
    public String translate(String langFrom, String langTo, String text) throws IOException {
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
        if (!changeModeTrans) {
            String trans = translate("vi", "en", text).replaceAll("&#39;", "'");;
            tx2.setText(trans);
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
        super.initialize(url, resourceBundle);
        checkMode2.setVisible(true);
        changeModeTrans = changeL;
        if (!changeL) {
            lang1.setText("Tiếng Việt");
            lang2.setText("Tiếng Anh");
            chooseFile.setText("Chọn ảnh");
            warning.setText("Vui lòng nhập không quá 600 ký tự");
        } else {
            lang1.setText("English");
            lang2.setText("Vietnamese");
            chooseFile.setText("Choose image");
            warning.setText("Enter no more than 600 characters");
        }
        tx1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int charCount = newValue.length();
                if (charCount > 600) {
                    charCountLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
                charCountLabel.setText(charCount + "/600");
            }
        });
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("image/bg3.jpg")).toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        center.setBackground(background);
        tx1.setWrapText(true);
        tx2.setWrapText(true);
    }

    public void chooseFileToTranslate() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.png"));
        File initialDirectory;
        if (changeModeTrans) {
            initialDirectory = new File("imageToChooseInEnglish");
        } else {
            initialDirectory = new File("imageToChooseInVietnamese");
        }
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.setTitle("Open Resource File");
        java.io.File file = fileChooser.showOpenDialog(center.getScene().getWindow());
        if (file != null) {
            Tesseract tesseract = new Tesseract();
            tesseract.setTessVariable("debug_file", "/dev/null");
            if (!changeModeTrans) {
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
