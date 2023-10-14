package com.application.learnlingo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Game implements Initializable {

    @FXML
    private AnchorPane center;

    @FXML
    private BorderPane container;

    @FXML
    private Label timerLabel;

    private int seconds = 120;

    @FXML
    private Button search;

    @FXML
    private Button add;

    @FXML
    private Button history;

    @FXML
    private Button settings;

    @FXML
    private Button tudien;

    @FXML
    private Button dich;

    @FXML
    private Button start;

    @FXML
    private Label s1;

    @FXML
    private Label s2;

    @FXML
    private Label s3;

    @FXML
    private Label s4;

    @FXML
    private Label s5;

    @FXML
    private Label s6;

    @FXML
    private Label s7;

    @FXML
    private Button l1;

    @FXML
    private Button l2;

    @FXML
    private Button l3;

    @FXML
    private Button l4;

    @FXML
    private Button l5;

    @FXML
    private Button l6;

    @FXML
    private Button l7;

    @FXML
    private Button clear;

    @FXML
    private Button last;

    @FXML
    private Button twist;

    @FXML
    private Button enter;

    @FXML
    private VBox left;

    @FXML
    private Button menu;

    private boolean checkMenuBar = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        left.setVisible(false);
        left.setTranslateX(-100);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        center.setBackground(background);
        add.setOnAction(e -> AnimationChangeScene.handleButtonClick("changeWordController.fxml", container));
        search.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        history.setOnAction(e -> AnimationChangeScene.handleButtonClick("BookMark.fxml", container));
        settings.setOnAction(e -> AnimationChangeScene.handleButtonClick("Settings.fxml", container));
        tudien.setOnAction(e -> AnimationChangeScene.handleButtonClick("hello-view.fxml", container));
        dich.setOnAction(e -> AnimationChangeScene.handleButtonClick("TranslationController.fxml", container));

    }

    @FXML
    public void setMenu() {
        checkMenuBar = !checkMenuBar;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(left);
        if (checkMenuBar) {
            left.setVisible(true);
            left.setPrefWidth(100);
            slide.setToX(0);
            slide.play();
        } else {
            slide.setToX(-100);
            slide.play();
        }
    }

    @FXML
    public void startGame() {
        start.setVisible(false);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event1 -> {
                    seconds--;
                    timerLabel.setText("TIME                " + seconds);
                })
        );

        List<Label> listLabelToSet = new ArrayList<>();
        List<Label> listLabelLastAgain = new ArrayList<>();
        List<Button> listButtonToClick = new ArrayList<>();
        List<Button> listButtonToVisibleAgain = new ArrayList<>();

        listLabelToSet.add(s1);
        listLabelToSet.add(s2);
        listLabelToSet.add(s3);
        listLabelToSet.add(s4);
        listLabelToSet.add(s5);
        listLabelToSet.add(s6);
        listLabelToSet.add(s7);
        listButtonToClick.add(l1);
        listButtonToClick.add(l2);
        listButtonToClick.add(l3);
        listButtonToClick.add(l4);
        listButtonToClick.add(l5);
        listButtonToClick.add(l6);
        listButtonToClick.add(l7);
        timeline.setCycleCount(Timeline.INDEFINITE);

        for (Button btn : listButtonToClick) {
            btn.setOnAction(e -> {
                for (Label label : listLabelToSet) {
                    if (label.getText().equals("")) {
                        label.setText(btn.getText());
                        btn.setVisible(false);
                        listButtonToClick.remove(btn);
                        listLabelLastAgain.add(label);
                        listButtonToVisibleAgain.add(btn);
                        break;
                    }
                }
            });
        }


        clear.setOnAction(e -> {
            for (Label label : listLabelToSet) {
                if (!label.getText().equals("")) {
                    label.setText("");
                    for (int i = 0; i < listButtonToVisibleAgain.size(); i++) {
                        listButtonToVisibleAgain.get(i).setVisible(true);
                        listButtonToClick.add(listButtonToVisibleAgain.get(i));
                        listButtonToVisibleAgain.remove(i);
                        i--;
                    }
                }
            }
            listButtonToVisibleAgain.clear();
        });

        last.setOnAction(e -> {
            if (listButtonToVisibleAgain.size() >= 1) {
                listButtonToVisibleAgain.get(listButtonToVisibleAgain.size() - 1).setVisible(true);
                listButtonToClick.add(listButtonToVisibleAgain.get(listButtonToVisibleAgain.size() - 1));
                listButtonToVisibleAgain.remove(listButtonToVisibleAgain.size() - 1);
            }

            if (listLabelLastAgain.size() >= 1) {
                listLabelLastAgain.get(listLabelLastAgain.size() - 1).setText("");
                listLabelLastAgain.remove(listLabelLastAgain.size() - 1);
            }
        });

        twist.setOnAction(e -> {
            List<Button> copyList = new ArrayList<>(listButtonToClick);
            Collections.shuffle(copyList);
            String[] a = new String[copyList.size()];
            for (int i = 0; i < copyList.size(); i++) {
                a[i] = copyList.get(i).getText();
            }
            for (int i = 0; i < copyList.size(); i++) {
                listButtonToClick.get(i).setText(a[i]);
            }
        });

        // Xử lý answer ở đây nhé
        enter.setOnAction(e -> {
            String ans = "";
            for (Label x : listLabelToSet) {
                ans += x.getText();
            }
            System.out.println(ans);
        });

        timeline.play();

    }


    // Function to check user's answer
//    @FXML
//    public void userClickEnter() {
//
//    }
}
