package com.application.learnlingo;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeWordController implements Initializable {

    @FXML
    private JFXButton b1;

    @FXML
    private JFXButton b2;

    @FXML
    private JFXButton b3;

    @FXML
    private JFXButton b4;

    @FXML
    private JFXButton b5;

    @FXML
    private JFXButton b6;

    private static void animation(JFXButton button, int x, int time, String message) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(time));
        transition.setNode(button);
        transition.setToY(x);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
        button.setOnAction(e -> {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(message);
            alert1.show();
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animation(b1, -200, 2, "Have a good day <3");
        animation(b2, 200, 2, "You are so amazing <3");
        animation(b3, -200, 2, "Wish you everything <3");
        animation(b4, -200, 2, "Thank for using our app <3");
        animation(b5, 200, 2, "You are so beautiful <3");
        animation(b6, 200, 2, "Hope you feel happy <3");
    }
}
