package com.application.learnlingo;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController extends GeneralController {

    @FXML
    protected ImageView music;

    @FXML
    protected ImageView volume;

    @FXML
    protected Button btnmusic;

    @FXML
    protected Button btnvolume;

    protected boolean checkAudio = true;

    protected boolean checkVolume = true;

    protected boolean checkMenuGame = false;

    @FXML
    private Button textTwist;

    @FXML
    private Button funnyQuiz;

    @FXML
    protected VBox menuGame;

    @FXML
    protected VBox credit;

    @FXML
    protected VBox htp;

    @FXML
    protected VBox highscore;

    protected AudioClip click;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        click = new AudioClip(getClass().getResource("audio/click.wav").toString());
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("image/bg3.jpg")).toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        center.setBackground(background);
    }

    @FXML
    public void setTextTwistGame() {
        AnimationChangeScene.handleButtonClick("TextTwistGame.fxml", container);
    }

    @FXML
    public void setFunnyQuizGame() {
        AnimationChangeScene.handleButtonClick("FunnyQuiz.fxml", container);
    }

    @FXML
    public void setMenuGame() {
        if (checkVolume) {
            click.play();
        }
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
            slide.setToX(620);
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

    public void stopMusic() {
        if (checkAudio) {
            Image image = new Image(getClass().getResource("image/stopmusic.png").toString());
            music = new ImageView(image);
            music.setFitWidth(21);
            music.setFitHeight(21);
            btnmusic.setGraphic(music);
            musicGame.stop();
        } else {
            Image image = new Image(getClass().getResource("image/music.png").toString());
            music = new ImageView(image);
            music.setFitWidth(21);
            music.setFitHeight(21);
            btnmusic.setGraphic(music);
            musicGame.play();
        }
        checkAudio = !checkAudio;
    }

    public void setHowToPlay() {
        if (checkVolume) {
            click.play();
        }
        htp.setOpacity(1);
        htp.setVisible(true);
        menuGame.setVisible(false);
    }

    public void setAgainMenu() {
        if (checkVolume) {
            click.play();
        }
        htp.setVisible(false);
        menuGame.setVisible(true);
    }

    @FXML
    public void setAgainMenu1() {
        if (checkVolume) {
            click.play();
        }
        credit.setVisible(false);
        menuGame.setVisible(true);
    }

    @FXML
    public void setAgainMenu2() {
        if (checkVolume) {
            click.play();
        }
        highscore.setVisible(false);
        menuGame.setVisible(true);
    }


    @FXML
    public void setCredit() {
        if (checkVolume) {
            click.play();
        }
        credit.setOpacity(1);
        credit.setVisible(true);
        menuGame.setVisible(false);
    }


    @FXML
    public void setMenuInGame() {
        checkMenuBar = !checkMenuBar;
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(left);
        if (checkMenuBar) {
            left.setVisible(true);
            left.setPrefWidth(99);
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
            slide.setToX(-99);
            slide.play();
            for (Node child : ((AnchorPane) container.getCenter()).getChildren()) {
                child.setOpacity(1);
            }
        }
    }

}
