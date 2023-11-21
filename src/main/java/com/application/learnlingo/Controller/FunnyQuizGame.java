package com.application.learnlingo.Controller;

import com.application.learnlingo.Model.DatabaseManager;
import com.application.learnlingo.Model.Game;
import com.application.learnlingo.Model.Quiz;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class FunnyQuizGame extends GameController implements Game {

    private static final String DB_PATH =
            "./src/main/resources/com/application/learnlingo/database/quiz.db";
    private static final String DEFAULT_HIGH_SCORE_FILE_PATH =
            "./src/main/resources/com/application/learnlingo/database/FunnyQuizGameHighScore.txt";
    private static final String IMAGE_FUNNYQUIZGAME_PATH =
            "/com/application/learnlingo/imageFunnyQuiz/";
    private static final int TIME = 15;
    private static final int DEFAULT_CORRECT_PENALTY = 10;
    private static int score = 0;
    private static int round = 1;
    @FXML
    public Button startButton;
    @FXML
    private Button answerA;
    @FXML
    private Button answerB;
    @FXML
    private Button answerC;
    @FXML
    private Button answerD;

    @FXML
    private Button btnmusic;
    @FXML
    private Button btnvolume;
    @FXML
    private BorderPane container;
    @FXML
    private VBox credit;

    @FXML
    private VBox htp;
    @FXML
    private ImageView illustrations;
    @FXML
    private VBox left;
    @FXML
    private VBox menuGame;
    @FXML
    private ImageView music;
    @FXML
    private Label questionLabel;
    @FXML
    private Label roundLabel;
    @FXML
    private Label scoreLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private VBox boxContainImage;
    @FXML
    private ImageView volume;
    private Timeline timeline;
    private List<Quiz> quizList;
    private List<Button> answerList;
    private boolean playing = false;

    @FXML
    private AnchorPane loseGame;

    @FXML
    private AnchorPane winGame;

    @FXML
    private Button yesWin;

    @FXML
    private Button noWin;

    @FXML
    private Button yesLose;

    @FXML
    private Button noLose;
    @FXML
    private Label highScoreLabel;

    private int highScore = 0;

    public static void reset() {
        FileWriter fw = null;
        try {
            fw = new FileWriter(DEFAULT_HIGH_SCORE_FILE_PATH);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        assert fw != null;
        java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
        try {
            bw.write("0");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
            fw.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromDatabase() {
        Connection connection = DatabaseManager.getConnection(DB_PATH);
        String sql = "SELECT * FROM Quiz";
        try {
            java.sql.Statement stmt = connection.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String question = rs.getString("question");
                String answerA = rs.getString("A");
                String answerB = rs.getString("B");
                String answerC = rs.getString("C");
                String answerD = rs.getString("D");
                String correctAnswer = rs.getString("Key");
                String imagePath = IMAGE_FUNNYQUIZGAME_PATH + rs.getString("ImagePath");
                List<String> answer = new ArrayList<>();
                answer.add(answerA);
                answer.add(answerB);
                answer.add(answerC);
                answer.add(answerD);
                String regex = "";
                if (question.contains("-")) {
                    regex = "(?=[-])";

                } else if (question.matches("[?:!]") || question.contains(":")) {
                    regex = "(?<=[?:!])";
                }
                if (!regex.isEmpty()) {
                    String[] temp = question.split(regex);
                    question = "";
                    for (int i = 0; i < temp.length; i++) {
                        question += temp[i] + "\n";
                    }
                }
                Quiz quiz = new Quiz(question, answer, correctAnswer, imagePath);
                quizList.add(quiz);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        timeline = new Timeline();
        quizList = new ArrayList<>();
        answerList = new ArrayList<>();
        answerList.add(answerA);
        answerList.add(answerB);
        answerList.add(answerC);
        answerList.add(answerD);
        score = 0;
        round = 1;
        loadDataFromDatabase();
        Collections.shuffle(quizList);
        highScoreLabel.setText(String.valueOf(highScore));
        yesLose.setOnMouseClicked(e1 -> {
            loseGame.setVisible(false);
            if (checkAudio) {
                musicGame.play();
            }
            init();
            startButton.getOnMouseClicked().handle(e1);
        });

        noLose.setOnMouseClicked(e1 -> {
            AnimationChangeScene.handleButtonClick(VIEW_PATH + "game.fxml", container);
        });
        yesWin.setOnMouseClicked(e1 -> {
            musicGame.play();
            winGame.setVisible(false);
            init();
            startButton.getOnMouseClicked().handle(e1);
        });

        noWin.setOnMouseClicked(e1 -> {
            AnimationChangeScene.handleButtonClick(VIEW_PATH + "game.fxml", container);
        });

    }

    @Override
    public void start() {
        startButton.setOnMouseClicked(e -> {
            checkStart = true;
            if (checkVolume) {
                click.play();
            }
            if (checkAudio && startButton.getText().equals("START HERE")) {
                musicGame.play();
            }
            playing = true;
            startButton.setVisible(false);
            answerList.forEach(button -> {
                button.getStyleClass().removeIf(eg -> eg.equals("correctAnswer"));
                button.getStyleClass().removeIf(eg -> eg.equals("wrongAnswer"));
            });
            if (startButton.getText().equals("NEXT")) {
                round++;
            } else if (startButton.getText().equals("RESTART")) {
                round = 1;
                score = 0;
                Collections.shuffle(quizList);
            }
            roundLabel.setText(String.valueOf(round));
            scoreLabel.setText(String.valueOf(score));
            Quiz quiz = quizList.get(round - 1);
            questionLabel.setText(quiz.getQuestion());
            List<String> answer = quiz.getAnswer();
            for (int i = 0; i < answer.size(); i++) {
                answerList.get(i).setText(answer.get(i));
            }
            setImage(quiz.getImagePath());
            timerLabel.setText(String.valueOf(TIME));
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
                int time = Integer.parseInt(timerLabel.getText());
                if (time > 0) {
                    time--;
                    timerLabel.setText(String.valueOf(time));
                } else {
                    timeline.stop();
                    playing = false;
                    loseGame.setVisible(true);
                    AudioClip loseGame = new AudioClip(TextTwistGame.class.getResource(AUDIO_PATH + "loseGame.mp3").toString());
                    if (checkVolume) {
                        loseGame.play();
                    }
                    musicGame.stop();
                    for (Button button : answerList) {
                        String answerText = answerList.get(quiz.getCorrectAnswer().charAt(0) - 'A').getText();
                        if (button.getText().equals(answerText)) {
                            button.getStyleClass().add("correctAnswer");
                        } else {
                            button.getStyleClass().add("wrongAnswer");
                            answerList.get(quiz.getCorrectAnswer().charAt(0) - 'A').getStyleClass().add("correctAnswer");
                            startButton.setText("RESTART");
                        }
                    }
                    AudioClip wrongAnswer = new AudioClip(getClass().getResource(AUDIO_PATH + "wrongAnswer.mp3").toString());
                    if (checkVolume) {
                        wrongAnswer.play();
                    }
                    notice(false);

                }
            }));
            timeline.play();
            answerList.forEach(button -> {
                button.setOnAction(event -> {
                    if (playing) {
                        if (checkVolume) {
                            click.play();
                        }
                        timeline.stop();
                        String answerText = answerList.get(quiz.getCorrectAnswer().charAt(0) - 'A').getText();
                        if (button.getText().equals(answerText)) {
                            AudioClip rightAnswer = new AudioClip(getClass().getResource(AUDIO_PATH + "rightAnswer.mp3").toString());
                            if (checkVolume) {
                                rightAnswer.play();
                            }
                            button.getStyleClass().add("correctAnswer");
                            score += DEFAULT_CORRECT_PENALTY;
                            scoreLabel.setText(String.valueOf(score));
                            if (score > highScore) {
                                highScore = score;
                                highScoreLabel.setText(String.valueOf(highScore));
                                FileWriter fw = null;
                                try {
                                    fw = new FileWriter(DEFAULT_HIGH_SCORE_FILE_PATH);
                                } catch (java.io.IOException e1) {
                                    e1.printStackTrace();
                                }
                                assert fw != null;
                                java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
                                try {
                                    bw.write(String.valueOf(highScore));
                                } catch (java.io.IOException e2) {
                                    e2.printStackTrace();
                                }
                                try {
                                    bw.close();
                                    fw.close();
                                } catch (java.io.IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (round == quizList.size()) {
                                notice(true);
                                round = 1;
                                score = 0;
                            } else {
                                startButton.setVisible(true);
                                startButton.setText("NEXT");
                            }
                        } else {
                            AudioClip wrongAnswer = new AudioClip(getClass().getResource(AUDIO_PATH + "wrongAnswer.mp3").toString());
                            if (checkVolume) {
                                wrongAnswer.play();
                            }
                            button.getStyleClass().add("wrongAnswer");
                            answerList.get(quiz.getCorrectAnswer().charAt(0) - 'A').getStyleClass().add("correctAnswer");
                            notice(false);
                        }
                        playing = false;
                    }
                });
            });

        });
    }

    @Override
    public void notice(boolean isWon) {
        if (isWon) {
            AudioClip winAll = new AudioClip(getClass().getResource(AUDIO_PATH + "winAll.mp3").toString());
            if (checkVolume) {
                winAll.play();
            }
            winGame.setVisible(true);
            musicGame.stop();
            startButton.setText("RESTART");
        } else {
            startButton.setText("RESTART");
            loseGame.setVisible(true);
            AudioClip loseGame = new AudioClip(TextTwistGame.class.getResource(AUDIO_PATH + "loseGame.mp3").toString());
            if (checkVolume) {
                loseGame.play();
            }
            musicGame.stop();
        }
    }

    @Override
    public void resume() {
        timeline.play();
    }

    @Override
    public void pause() {
        timeline.pause();
    }

    public void setImage(String path) {
        boxContainImage.getChildren().clear();
        String imageType = ".jpg";
        if (getClass().getResource(path + imageType) == null) {
            imageType = ".png";
        }
        String imagePath = path + imageType;
        Image image = new Image(getClass().getResource(imagePath).toString());
        illustrations.setImage(image);
        illustrations.setEffect(new DropShadow(20, Color.BLACK));
        boxContainImage.getChildren().add(illustrations);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        loseGame.setVisible(false);
        winGame.setVisible(false);
        Image image = new Image(getClass().getResource(IMAGE_PATH + "music.png").toString());
        music = new ImageView(image);
        music.setFitWidth(21);
        music.setFitHeight(21);
        btnmusic.setGraphic(music);
        Image image2 = new Image(getClass().getResource(IMAGE_PATH + "volumegame.png").toString());
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
        setImage(IMAGE_FUNNYQUIZGAME_PATH + "rome");
        listButton.forEach(button -> button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            timeline.stop();
        }));
        // Read high score
        FileReader fr = null;
        try {
            fr = new FileReader(DEFAULT_HIGH_SCORE_FILE_PATH);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fr != null;
        java.io.BufferedReader br = new java.io.BufferedReader(fr);
        String line = null;
        try {
            line = br.readLine();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        if (line != null) {
            highScore = Integer.parseInt(line);
        }
        init();
        start();
    }


}