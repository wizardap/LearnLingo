package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DictionaryController extends GeneralController implements Initializable {

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
            listWords.getItems().clear();
        } else {
            changeDictionary.getChildren().removeAll(vn, british, change);
            changeDictionary.getChildren().addAll(british, change, vn);
            tudien.setText("Dictionary");
            dich.setText("Translation");
            synonym.setText("Synonyms");
            antonym.setText("Antonyms");
            listWords.getItems().clear();
        }
        isUKFlagVisible = !isUKFlagVisible;
    }

    @FXML
    public void handleButtonClick() {
        makeFadeOutToTranslation();
    }

    private void makeFadeOutToTranslation() {
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
                ImageView iconImageView = new ImageView(new Image("C:\\IdeaProjects\\LearnLingo\\src\\main\\resources\\com\\application\\learnlingo\\image\\clock.png")); // Đường dẫn đến biểu tượng
                iconImageView.setFitHeight(16); // Thiết lập kích thước cho biểu tượng
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
                hbox.getChildren().add(vBox);
                setGraphic(hbox);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        listWords.getItems().addAll("Hello\nĐộng từ\nXin chào","Hello\nĐộng từ\nXin chào","Hello\nĐộng từ\nXin chào","Hello\nĐộng từ\nXin chào","Hello\nĐộng từ\nXin chào");
//        listWords.setCellFactory(param -> new IconAndFontListCell());
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
        }
    }
}