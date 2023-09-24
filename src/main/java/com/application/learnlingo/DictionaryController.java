package com.application.learnlingo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DictionaryController {
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
    public void changeMode() {
        change.setOnAction(event -> {
            if (isUKFlagVisible) {
                changeDictionary.getChildren().removeAll(british, vn, change);
                changeDictionary.getChildren().addAll(vn, change, british);
            } else {
                changeDictionary.getChildren().removeAll(vn, british, change);
                changeDictionary.getChildren().addAll(british, change, vn);
            }
            isUKFlagVisible = !isUKFlagVisible;
        });
    }
}