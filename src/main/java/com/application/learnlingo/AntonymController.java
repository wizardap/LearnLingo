package com.application.learnlingo;

import java.net.URL;
import java.util.ResourceBundle;

public class AntonymController extends SynonymAndAntonym {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        checkMode1.setVisible(false);
        checkMode2.setVisible(false);
        checkMode3.setVisible(false);
        checkMode4.setVisible(true);
        antonym.setStyle("-fx-background-color: #dddddd; -fx-min-width: 85;");
        synonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindSynonym.fxml", container));
    }
}
