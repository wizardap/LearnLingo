package com.application.learnlingo;

import java.net.URL;
import java.util.ResourceBundle;

public class SynonymController extends SynonymAndAntonym {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        checkMode1.setVisible(false);
        checkMode2.setVisible(false);
        checkMode3.setVisible(true);
        checkMode4.setVisible(false);
        synonym.setStyle("-fx-background-color: #dddddd; -fx-min-width: 85;");
        antonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container));
    }

}
