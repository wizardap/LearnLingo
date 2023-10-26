package com.application.learnlingo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class GameController extends GeneralController {

    @FXML
    private Label timerLabel;

    private int seconds = 120;


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

    private boolean checkMenuGame = false;

    @FXML
    private VBox menuGame;

    @FXML
    private Button menuButton;

    private MediaPlayer mediaPlayer;

    private Media sound;

    @FXML
    private ImageView music;

    @FXML
    private Button btnmusic;

    @FXML
    private ImageView volume;

    @FXML
    private Button btnvolume;

    private boolean checkAudio = true;

    private boolean checkVolume = true;

    @FXML
    private VBox htp;

    @FXML
    private VBox credit;

    @FXML
    private Button start;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        credit.setVisible(false);
        htp.setVisible(false);
        Image image = new Image(getClass().getResource("image/music.png").toString());
        music = new ImageView(image);
        music.setFitWidth(21);
        music.setFitHeight(21);
        btnmusic.setGraphic(music);
        Image image2 = new Image(getClass().getResource("image/volumegame.png").toString());
        volume = new ImageView(image2);
        volume.setFitWidth(23);
        volume.setFitHeight(23);
        btnvolume.setGraphic(volume);
        sound = new Media(getClass().getResource("audio/soundGame3.mp3").toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        menuGame.setVisible(false);
        menuGame.setTranslateX(600);
        left.setVisible(false);
        left.setTranslateX(-100);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("image/bg3.jpg").toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        center.setBackground(background);
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
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                if (child.getId() == null || !child.getId().equals("left")) {
                    child.setOpacity(0.8);
                } else {
                    child.setOpacity(1);
                }
            }
        } else {
            slide.setToX(-100);
            slide.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                child.setOpacity(1);
            }
        }
    }

    @FXML
    public void setMenuGame() {
        checkMenuGame = !checkMenuGame;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menuGame);
        if (checkMenuGame) {
            menuGame.setVisible(true);
            slide.setToX(0);
            slide.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                if (child.getId() == null || !child.getId().equals("menuGame")) {
                    child.setOpacity(0.8);
                } else {
                    child.setOpacity(1);
                }
            }
        } else {
            slide.setToX(600);
            slide.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                child.setOpacity(1);
            }
        }
    }

    @FXML
    public void stopVolume() {
        if (checkVolume) {
            Image image = new Image(getClass().getResource("image/stopvolumegame.png").toString());
            volume = new ImageView(image);
            volume.setFitWidth(23);
            volume.setFitHeight(23);
            btnvolume.setGraphic(volume);
        } else {
            Image image = new Image(getClass().getResource("image/volumegame.png").toString());
            volume = new ImageView(image);
            volume.setFitWidth(23);
            volume.setFitHeight(23);
            btnvolume.setGraphic(volume);
        }
        checkVolume = !checkVolume;
    }

    @FXML
    public void stopMusic() {
        if (checkAudio) {
            mediaPlayer.stop();
            Image image = new Image(getClass().getResource("image/stopmusic.png").toString());
            music = new ImageView(image);
            music.setFitWidth(21);
            music.setFitHeight(21);
            btnmusic.setGraphic(music);
        } else {
            mediaPlayer.play();
            Image image = new Image(getClass().getResource("image/music.png").toString());
            music = new ImageView(image);
            music.setFitWidth(21);
            music.setFitHeight(21);
            btnmusic.setGraphic(music);
        }
        checkAudio = !checkAudio;
    }

    @FXML
    public void setClick() {
        Media soundClick = new Media(getClass().getResource("audio/audio_button_click_sound.wav").toString());
        MediaPlayer click = new MediaPlayer(soundClick);
        click.play();
    }

    @FXML
    public void startGame() {
        mediaPlayer.play();
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

    @FXML
    public void setHowToPlay() {
        htp.setOpacity(1);
        htp.setVisible(true);
        menuGame.setVisible(false);
    }

    @FXML
    public void setCredit() {
        credit.setOpacity(1);
        credit.setVisible(true);
        menuGame.setVisible(false);
    }

    @FXML
    public void setAgainMenu() {
        htp.setVisible(false);
        menuGame.setVisible(true);
    }

    @FXML
    public void setAgainMenu1() {
        credit.setVisible(false);
        menuGame.setVisible(true);
    }

}
