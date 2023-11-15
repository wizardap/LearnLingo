package com.application.learnlingo;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FunnyQuizGame extends GameController implements Game {


    @FXML
    private VBox credit;

    @FXML
    private VBox htp;
    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void notice(boolean isWon) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
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
        credit.setVisible(false);
        htp.setVisible(false);
        menuGame.setVisible(false);
        menuGame.setTranslateX(620);
        left.setVisible(false);
        left.setTranslateX(-99.5);
    }
}
