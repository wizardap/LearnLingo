package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeWordController extends GeneralController {

    private static StringBuilder DEFAULT_HTML_CONTENT_FILEPATH =
            new StringBuilder("src/main/resources/com/application/learnlingo/database/defaultHTMLEditor.txt");

    @FXML
    private Button resetButton;
    @FXML
    private Button searchWordButton;
    @FXML
    private HTMLEditor contentHTMLEditor;
    @FXML
    private Button deleteContentButton;

    @FXML
    private TextField textfield1;

    @FXML
    private CheckBox d1;

    @FXML
    private CheckBox d2;

    @FXML
    private JFXButton addWordButton;

    @FXML
    private JFXButton deleteWordButton;

    private static String defaultHTMLContent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        center.setBackground(background);
        d1.setSelected(true);
        currentDictionary = evDict;
        d1.setOnAction(event -> {
            if (d1.isSelected()) {
                currentDictionary = evDict;
                d2.setSelected(false);
            }
        });

        d2.setOnAction(event -> {
            if (d2.isSelected()) {
                currentDictionary = veDict;
                d1.setSelected(false);
            }
        });
        loadDefaultHTMLContent();
        contentHTMLEditor.setHtmlText(defaultHTMLContent);
    }
    private static void loadDefaultHTMLContent(){
        FileReader fr = null;
        try {
            fr = new FileReader(DEFAULT_HTML_CONTENT_FILEPATH.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line=null;
        while(true){
            try {
                if (!((line=br.readLine())!=null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            defaultHTMLContent = line;
        }

    }

    @FXML
    public void deleteContentSearch() {
        textfield1.setText("");
    }

    @FXML
    public void addWordToDatabase() {
        //To do
    }

    @FXML
    public void deleteWordFromDatabase() {
        //To do
    }
    @FXML
    public void searchWordExist(ActionEvent actionEvent) {
        String searchWord = textfield1.getText();
        if (searchWord.isEmpty() || !currentDictionary.contain(searchWord)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText(new StringBuilder().append(searchWord)
                    .append(" isn\'t exist in this dictionary!\n")
                    .append("If you need to add a new word, please insert content!.\n")
                    .append("Otherwise, you need to check your typo and search again!")
                    .toString());
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText(new StringBuilder()
                    .append(searchWord)
                    .append(" is exist in this dictionary.\n")
                    .append("Now you can change the content or delete this word!\n")
                    .toString());
            alert.showAndWait();
            contentHTMLEditor.setHtmlText(currentDictionary.getWordInformation(searchWord).getHtml());
        }
    }
}
