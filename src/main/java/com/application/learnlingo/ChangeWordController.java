package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChangeWordController extends GeneralController {

    private static final StringBuilder DEFAULT_HTML_CONTENT_FILEPATH =
            new StringBuilder("src/main/resources/com/application/learnlingo/database/defaultHTMLEditor.txt");
    private static String defaultHTMLContent;
    @FXML
    private Button resetButton;
    @FXML
    private Button searchWordButton;
    private boolean isLookedUp;
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
    private Word currentSearchWord;
    private static boolean showFoundWord;

    private static void loadDefaultHTMLContent() {
        FileReader fr = null;
        try {
            fr = new FileReader(DEFAULT_HTML_CONTENT_FILEPATH.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fr != null;
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            defaultHTMLContent = line;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(Objects.requireNonNull(getClass().getResource("image/bg3.jpg")).toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        center.setBackground(background);
        d1.setSelected(true);
        if (!changeL) {
            changeMode();
        }
        d1.setOnAction(event -> {
            d2.setSelected(!d1.isSelected());
            changeMode();
            isLookedUp=false;
        });

        d2.setOnAction(event -> {
            d1.setSelected(!d2.isSelected());
            changeMode();
            isLookedUp=false;
        });
        textfield1.setOnKeyTyped(e->{
            isLookedUp = false;
        });
        loadDefaultHTMLContent();
        contentHTMLEditor.setHtmlText(defaultHTMLContent);
    }

    @Override
    public void changeMode() {
        super.changeMode();
    }

    @FXML
    public void deleteContentSearch() {
        textfield1.setText("");
        isLookedUp=false;
    }

    @FXML
    public void addWordToDatabase() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.YES, ButtonType.NO);
        alert.setContentText("Are you sure that you want to add this word to this dictionary?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.YES) {
                String contentSearchWord = textfield1.getText().toLowerCase();
                if (!contentSearchWord.isEmpty() && !currentDictionary.contain(contentSearchWord)) {
                    Word modifiedWord = new Word(contentSearchWord,contentHTMLEditor.getHtmlText(),false);
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setHeaderText("'" +
                            contentSearchWord +
                            "'" +
                            " has been added to this dictionary!");
                    success.showAndWait();
                    currentDictionary.insertWord(modifiedWord);
                }
                else {
                    Alert failed = new Alert(Alert.AlertType.ERROR);
                    failed.setHeaderText("'" +
                            contentSearchWord +
                            "'" +
                            " already exists in the dictionary.\n");
                    failed.setContentText("You should edit the definition of this word and save it!");
                    failed.showAndWait();

                }
            }
        }
    }

    @FXML
    public void deleteWordFromDatabase() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.YES, ButtonType.NO);
        alert.setContentText("Are you sure that you want to add this word to this dictionary?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.YES) {
                String contentSearchWord = textfield1.getText().toLowerCase();
                if (!contentSearchWord.isEmpty() && currentDictionary.contain(contentSearchWord)) {
                   currentDictionary.deleteWord(contentSearchWord);
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setHeaderText("'" +
                            contentSearchWord +
                            "'" +
                            " has been deleted to this dictionary!");
                    success.showAndWait();
                    contentHTMLEditor.setHtmlText(defaultHTMLContent);
                }
                else {
                    Alert failed = new Alert(Alert.AlertType.ERROR);
                    failed.setHeaderText("'" +
                            contentSearchWord +
                            "'" +
                            " doesn't exists in the dictionary.\n");
                    failed.setContentText("You should check your typo or make sure that this word is available!");
                    failed.showAndWait();

                }
            }
        }
    }

    @FXML
    public void searchWordExist(ActionEvent actionEvent) {
        String searchWord = textfield1.getText().toLowerCase();
        isLookedUp = true;
        if (searchWord.isEmpty() || !currentDictionary.contain(searchWord)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("'" +
                    searchWord +
                    "'" +
                    " isn't exist in this dictionary!\n" +
                    "If you need to add a new word, please insert content.\n" +
                    "Otherwise, you need to check your typo and search again.");
            alert.showAndWait();
            contentHTMLEditor.setHtmlText(defaultHTMLContent);
        } else {
            currentSearchWord = currentDictionary.getWordInformation(searchWord);
            contentHTMLEditor.setHtmlText(currentSearchWord.getHtml());
        }
    }

    @FXML
    public void saveWordIntoDatabase(MouseEvent mouseEvent) {
        String contentSearchWord = textfield1.getText().toLowerCase();
        if (!contentSearchWord.isEmpty() && currentDictionary.contain(contentSearchWord)) {
            if (!isLookedUp){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please click search button to show the definition before save the word!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.YES, ButtonType.NO);
                alert.setContentText("Are you sure that you want to save the definition of this word?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == ButtonType.YES) {
                        Word modifiedWord = currentSearchWord;
                        modifiedWord.setHtml(contentHTMLEditor.getHtmlText());
                        currentDictionary.modifyWord(modifiedWord);
                    }
                }
            }
        } else {
            if (contentSearchWord.isEmpty()) contentSearchWord="This word on the search field";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR: Can not save into dictionary!");
            alert.setContentText(contentSearchWord +
                    " does not exist in this dictionary!\n" +
                    "Please sure that your word is available in this dictionary!");
            alert.showAndWait();
        }
    }

    @FXML
    public void resetToDefaultContent(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "WARNING", ButtonType.YES, ButtonType.NO);
        alert.setContentText("Are you sure you want to refresh the content?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.YES) {
               if (isLookedUp){
                   contentHTMLEditor.setHtmlText(currentSearchWord.getHtml());
               }
               else contentHTMLEditor.setHtmlText(defaultHTMLContent);
            }
        }
    }
}
