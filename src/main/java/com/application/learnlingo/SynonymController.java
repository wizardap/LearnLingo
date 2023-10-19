package com.application.learnlingo;

import java.net.URL;
import java.util.ResourceBundle;

public class SynonymController extends SynonymAndAntonym {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        synonym.setStyle("-fx-background-color: #FEC400; -fx-min-width: 85;");
        antonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container));
    }
}
