package com.application.learnlingo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BookMarkController extends GeneralController {

    private static class IconAndFontListCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                String[] lines = item.split("\n");

                HBox hbox = new HBox();

                ImageView iconImageView = new ImageView(new Image(getClass().getResource("image/deleteWordBM.png").toString()));
                iconImageView.setFitHeight(16);
                iconImageView.setFitWidth(16);

                VBox vBox = new VBox();
                vBox.setPrefWidth(170);
                for (int i = 0; i < 1; i++) {
                    Label text = new Label(lines[i]);
                    if (i == 0) {
                        text.setStyle("-fx-font-size: 14; -fx-text-fill: white;");
                    } else {
                        text.setStyle("-fx-font-size: 12; -fx-text-fill: white;");
                    }
                    vBox.getChildren().add(text);
                }
                hbox.setSpacing(5);

                hbox.setSpacing(15);

                hbox.getChildren().addAll(vBox, iconImageView);
                setGraphic(hbox);
            }
        }
    }

    @FXML
    private void deleteWord() {
        textfield.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        center.setStyle("-fx-background-color: #F4F4F4");
        listWords.setCellFactory(param -> new IconAndFontListCell());
        listWords.getItems().addAll("Hello", "World", "Hello", "World", "Hello", "World", "Hello", "World", "Hello", "World");
    }

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
}
