package com.application.learnlingo;

import java.net.URL;
import java.util.ResourceBundle;

public class AntonymController extends SynonymAndAntonym {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        antonym.setStyle("-fx-background-color: #1d2a57; -fx-min-width: 85; -fx-text-fill: white");
        synonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindSynonym.fxml", container));
    }
}
