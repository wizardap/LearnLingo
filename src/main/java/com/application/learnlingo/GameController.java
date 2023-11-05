package com.application.learnlingo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class GameController extends GeneralController {
    private static final int DEFAULT_TIME_SECOND = 120;
    private static final double DEFAULT_BOX_HEIGHT = 64;
    private static double maxCharacter = 0;
    private static double maxWord = 0;
    private static List<List<String>> dataList = new ArrayList<>();
    @FXML
    private BorderPane container;
    @FXML
    private Button refresh;
    @FXML
    private Button clear;
    @FXML
    private HBox containerCharacterChoose;
    @FXML
    private HBox containerCharacterGuess;
    @FXML
    private HBox containerWords;
    @FXML
    private Button enter;
    @FXML
    private Button game;
    @FXML
    private HBox gameFunctions;
    @FXML
    private Button last;
    @FXML
    private Button menuButton;
    @FXML
    private Label roundLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Button start;
    @FXML
    private Label timerLabel;
    @FXML
    private Button twist;
    @FXML
    private VBox wordList1;
    @FXML
    private VBox wordList2;
    private boolean checkMenuGame = false;
    private List<String> columnGameTable = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        left.setVisible(false);
        left.setTranslateX(-100);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("image/bg3.jpg")).toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        center.setBackground(background);
        loadDataFromDatabase();
        timerLabel.setText(String.valueOf(GameUtils.seconds));
        start.setText("START HERE");
        initGame();
        GameUtils.numberOfTurn=1;
        GameUtils.score = 0;
    }

    private void loadDataFromDatabase() {
        String sqlGetColumnName = "SELECT name FROM PRAGMA_TABLE_INFO(\"Game\");";
        String sql = "SELECT * from Game";
        try {
            Statement stmt = DictDMBS.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetColumnName);
            while (rs.next()) {
                columnGameTable.add(rs.getString("name"));
            }
            System.out.println(columnGameTable.get(0));
            columnGameTable.remove(0);

            stmt = DictDMBS.connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                List<String> groupData = new ArrayList<>();
                for (String columnName : columnGameTable) {
                    String word = rs.getString(columnName);
                    groupData.add(word);
                    maxCharacter = Math.max(maxCharacter, word.length());
                }
                maxWord = Math.round((double)groupData.size() / 2);
                dataList.add(groupData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initGame(){
        GameUtils.init();
        wordList1.getChildren().clear();
        wordList2.getChildren().clear();
        containerCharacterChoose.getChildren().clear();
        containerCharacterGuess.getChildren().clear();
        timerLabel.setText(String.valueOf(GameUtils.seconds));
        GameUtils.isPlaying = true;
        roundLabel.setText(String.valueOf(GameUtils.numberOfTurn));
        scoreLabel.setText(String.valueOf(GameUtils.score));
    }
    private void createQuizz() {
        // Loading the character box of invisible character box to guess.
        GameUtils.dice();
        for (int i = 0; i < GameUtils.wordList.size(); i++) {
            String word = GameUtils.wordList.get(i);
            HBox wordHbox = new HBox();
            VBox vBox = (i * 2 < GameUtils.wordList.size() ? wordList1 : wordList2);
            double vBoxHeight = vBox.getHeight();
            double vBoxWidth = vBox.getWidth();
            double boxHeight = vBoxHeight / maxWord;
            double boxWidth = vBoxWidth / maxCharacter;
            for (char ch : word.toCharArray()) {
                Label boxLabel = GameUtils.getCharacterLabel("", "choose-square-character");
                boxLabel.setPrefSize(boxWidth, boxHeight);
                wordHbox.getChildren().add(boxLabel);
            }
            wordHbox.setPrefSize(vBoxWidth, vBoxHeight);
            GameUtils.wordListHbox.add(wordHbox);
            vBox.getChildren().add(wordHbox);
        }

        //Load the guess-square label
        for (char ch : GameUtils.mainWord.toCharArray()) {

            double boxHeight = DEFAULT_BOX_HEIGHT;
            double boxWidth = 0;
            try {
                boxWidth = DEFAULT_BOX_HEIGHT;
            } catch (ArithmeticException e) {
                e.printStackTrace();
            }
            Label boxLabel = GameUtils.getCharacterLabel("", "guess-square-character");
            boxLabel.setPrefSize(boxWidth, boxHeight);
            containerCharacterGuess.getChildren().add(boxLabel);
        }

        // Load the choose-round label
        GameUtils.shuffleMainWord();
        for (char ch : GameUtils.mainWord.toCharArray()) {
            double boxHeight = DEFAULT_BOX_HEIGHT;
            double boxWidth = 0;
            try {
                boxWidth = DEFAULT_BOX_HEIGHT;
            } catch (ArithmeticException e) {
                e.printStackTrace();
            }
            Label roundLabel = GameUtils.getCharacterLabel(ch, "choose-round-character");
            HBox boxLabel = new HBox();
            boxLabel.setPrefSize(boxWidth, boxHeight);
            roundLabel.setPrefSize(boxWidth - 10, boxHeight - 10);
            boxLabel.getChildren().add(roundLabel);
            boxLabel.setAlignment(Pos.CENTER);
            containerCharacterChoose.getChildren().add(boxLabel);
        }
    }

    private void play() {

        // Set on Click Action for Character label
        List<Node> chooseCharacterList = containerCharacterChoose.getChildren();
        List<Node> guessCharacterList = containerCharacterGuess.getChildren();
        SortedSet<Integer> emptyGuessLabel = new TreeSet<>();
        SortedSet<Integer> emptyChooseLabel = new TreeSet<>();
        SortedSet<Integer> remainChooseLabel = new TreeSet<>();
        SortedSet<Integer> remainGuessLabel = new TreeSet<>();
        StringBuilder lastWord = new StringBuilder();
        for (int i = 0; i < guessCharacterList.size(); i++) {
            emptyGuessLabel.add(i);
        }
        for (int i = 0; i < chooseCharacterList.size(); i++) {
            remainChooseLabel.add(i);
        }
        for (int i = 0; i < guessCharacterList.size(); i++) {
            Label guessCharacter = (Label) guessCharacterList.get(i);
            int finalI = i;
            guessCharacter.setOnMouseClicked(e -> {
                if (!guessCharacter.getText().isEmpty() && GameUtils.isPlaying) {
                    int firstEmptyChooseIndex = emptyChooseLabel.first();
                    HBox chooseCharacterBox = (HBox) chooseCharacterList.get(firstEmptyChooseIndex);
                    Label chooseCharacter = (Label) chooseCharacterBox.getChildren().get(0);
                    chooseCharacter.setText(guessCharacter.getText());
                    emptyChooseLabel.remove(firstEmptyChooseIndex);
                    remainChooseLabel.add(firstEmptyChooseIndex);
                    guessCharacter.setText("");
                    emptyGuessLabel.add(finalI);
                    remainGuessLabel.remove(finalI);
                }
            });
        }
        for (int i = 0; i < chooseCharacterList.size(); i++) {
            HBox chooseCharacterBox = (HBox) chooseCharacterList.get(i);
            Label chooseCharacter = (Label) chooseCharacterBox.getChildren().get(0);
            int finalI = i;
            chooseCharacter.setOnMouseClicked(e -> {
                if (!chooseCharacter.getText().isEmpty() && GameUtils.isPlaying) {
                    int firstEmptyGuessIndex = emptyGuessLabel.first();
                    Label guessCharacter = (Label) guessCharacterList.get(firstEmptyGuessIndex);
                    guessCharacter.setText(chooseCharacter.getText());
                    remainGuessLabel.add(firstEmptyGuessIndex);
                    emptyGuessLabel.remove(firstEmptyGuessIndex);
                    chooseCharacter.setText("");
                    emptyChooseLabel.add(finalI);
                    remainChooseLabel.remove(finalI);
                }
            });
        }

        // Set on Action with function

        enter.setOnAction(e -> {
            if (GameUtils.isPlaying) {
                lastWord.delete(0, lastWord.capacity());
                StringBuilder enterWord = new StringBuilder();
                for (int i = 0; i < chooseCharacterList.size(); i++) {
                    Label guessCharacter = (Label) guessCharacterList.get(i);
                    if (!guessCharacter.getText().isEmpty()) {
                        enterWord.append(guessCharacter.getText());
                        lastWord.append(guessCharacter.getText());
                    }
                }

                System.out.println(enterWord);
                System.out.println(GameUtils.wordList);
                int id = GameUtils.wordList.indexOf(enterWord.toString());
                if (id != -1) {
                    GameUtils.modifyScore(GameUtils.DEFAULT_SCORE_EACH_CORRECT_WORD);
                    System.out.println("Found!");
                    HBox wordHbox = GameUtils.wordListHbox.get(id);
                    for (int j = 0; j < wordHbox.getChildren().size(); j++) {
                        Label visibleCharacter = (Label) wordHbox.getChildren().get(j);
                        visibleCharacter.setText(String.valueOf(enterWord.charAt(j)));
                    }
                    while (!remainGuessLabel.isEmpty()) {
                        int finalI = remainGuessLabel.first();
                        Label guessCharacter = (Label) guessCharacterList.get(finalI);
                        int firstEmptyChooseIndex = emptyChooseLabel.first();
                        HBox chooseCharacterBox = (HBox) chooseCharacterList.get(firstEmptyChooseIndex);
                        Label chooseCharacter = (Label) chooseCharacterBox.getChildren().get(0);
                        chooseCharacter.setText(guessCharacter.getText());
                        emptyChooseLabel.remove(firstEmptyChooseIndex);
                        remainChooseLabel.add(firstEmptyChooseIndex);
                        guessCharacter.setText("");
                        emptyGuessLabel.add(finalI);
                        remainGuessLabel.remove(finalI);
                    }
                }
                else {
                    GameUtils.modifyScore(GameUtils.DEFAULT_SCORE_EACH_WRONG_WORD);
                }
                scoreLabel.setText(String.valueOf(GameUtils.score));
            }
        });

        clear.setOnAction(e -> {
            if (GameUtils.isPlaying) {
                for (int i = 0; i < chooseCharacterList.size(); i++) {
                    Label guessCharacter = (Label) guessCharacterList.get(i);
                    if (!guessCharacter.getText().isEmpty()) {
                        int firstEmptyChooseIndex = emptyChooseLabel.first();
                        HBox chooseCharacterBox = (HBox) chooseCharacterList.get(firstEmptyChooseIndex);
                        Label chooseCharacter = (Label) chooseCharacterBox.getChildren().get(0);
                        chooseCharacter.setText(guessCharacter.getText());
                        emptyChooseLabel.remove(firstEmptyChooseIndex);
                        remainChooseLabel.add(firstEmptyChooseIndex);
                        guessCharacter.setText("");
                        emptyGuessLabel.add(i);
                        remainGuessLabel.remove(i);
                    }
                }
            }
        });
        twist.setOnAction(e -> {
            if (GameUtils.isPlaying) {
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < chooseCharacterList.size(); i++) {
                    list.add(i);
                }
                List<String> shuffleList = new ArrayList<>();
                for (Integer id : remainChooseLabel) {
                    HBox chooseCharacterBox = (HBox) chooseCharacterList.get(id);
                    Label chooseCharacter = (Label) chooseCharacterBox.getChildren().get(0);
                    shuffleList.add(chooseCharacter.getText());
                    emptyChooseLabel.add(id);
                    chooseCharacter.setText("");
                }
                remainChooseLabel.clear();

                Collections.shuffle(list);
                for (int i = 0; i < shuffleList.size(); i++) {
                    int id = list.get(i);
                    HBox chooseCharacterBox = (HBox) chooseCharacterList.get(id);
                    Label chooseCharacter = (Label) chooseCharacterBox.getChildren().get(0);
                    chooseCharacter.setText(shuffleList.get(i));
                    emptyChooseLabel.remove(id);
                    remainChooseLabel.add(id);
                }

            }
        });
        last.setOnAction(e -> {
            if (GameUtils.isPlaying) {
                while (!remainGuessLabel.isEmpty()) {
                    int finalI = remainGuessLabel.first();
                    Label guessCharacter = (Label) guessCharacterList.get(finalI);
                    int firstEmptyChooseIndex = emptyChooseLabel.first();
                    HBox chooseCharacterBox = (HBox) chooseCharacterList.get(firstEmptyChooseIndex);
                    Label chooseCharacter = (Label) chooseCharacterBox.getChildren().get(0);
                    chooseCharacter.setText(guessCharacter.getText());
                    emptyChooseLabel.remove(firstEmptyChooseIndex);
                    remainChooseLabel.add(firstEmptyChooseIndex);
                    guessCharacter.setText("");
                    emptyGuessLabel.add(finalI);
                    remainGuessLabel.remove(finalI);
                }

                for (char ch : lastWord.toString().toCharArray()) {
                    for (int i = 0; i < chooseCharacterList.size(); i++) {
                        HBox chooseCharacterBox = (HBox) chooseCharacterList.get(i);
                        Label chooseCharacter = (Label) chooseCharacterBox.getChildren().get(0);
                        if (chooseCharacter.getText().equals(String.valueOf(ch))) {
                            int firstEmptyGuessIndex = emptyGuessLabel.first();
                            Label guessCharacter = (Label) guessCharacterList.get(firstEmptyGuessIndex);
                            guessCharacter.setText(chooseCharacter.getText());
                            remainGuessLabel.add(firstEmptyGuessIndex);
                            emptyGuessLabel.remove(firstEmptyGuessIndex);
                            chooseCharacter.setText("");
                            emptyChooseLabel.add(i);
                            remainChooseLabel.remove(i);
                            break;
                        }
                    }
                }
            }
        });


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

    }


    private void countdown(){
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event1 -> {
            GameUtils.seconds--;
            if (GameUtils.isPlaying) {
                timerLabel.setText(String.valueOf(GameUtils.seconds));
                GameUtils.checkStatus();
                if (GameUtils.seconds <= 0 || GameUtils.status) {
                    System.out.println(GameUtils.status);
                    GameUtils.isPlaying = false;
                    timeline.stop();
                    start.setVisible(true);
                    if (GameUtils.status){
                        start.setText("NEXT");
                       GameUtils.nextRound();
                        // Thông báo hay animation thắng ở đây
                    }
                    else {
                        start.setText("RESTART");
                        GameUtils.restart();
                        // Thông báo hay animation thua ở đây
                    }

                    if (GameUtils.numberOfTurn == dataList.size()){
                        // Thông báo hay animation phá đảo game ở đây
                    }
                }
            }
        }));

        timeline.play();
    }
    @FXML
    public void startGame() {
        initGame();
        start.setVisible(false);
        createQuizz();
        play();
        countdown();
    }

    private static class GameUtils {
        public static final int DEFAULT_SCORE_EACH_CORRECT_WORD = 10;
        public static final int DEFAULT_SCORE_EACH_WRONG_WORD = -5;
        public static boolean status = false;
        public static List<String> wordList = new ArrayList<>();
        public static List<HBox> wordListHbox = new ArrayList<>();
        public static String mainWord = "";
        public static boolean isPlaying = false;
        public static int numberOfTurn = 1;
        public static int seconds = DEFAULT_TIME_SECOND;
        public static int score = 0;

        public static void init() {
            isPlaying = false;
            mainWord = "";
            wordList.clear();
            status = false;
            wordListHbox.clear();
            seconds = DEFAULT_TIME_SECOND;
        }

        private static Label getCharacterLabel(char ch, String style) {
            Label label = new Label(String.valueOf(ch));
            label.getStyleClass().add(style);
            return label;
        }

        private static Label getCharacterLabel(String ch, String style) {
            Label label = new Label(ch);
            label.getStyleClass().add(style);
            return label;
        }

        public static boolean checkStatus() {
            for (int i = 0; i < GameUtils.wordListHbox.size(); i++) {
                StringBuilder word = new StringBuilder();
                HBox wordHbox = GameUtils.wordListHbox.get(i);
                for (Node character : wordHbox.getChildren()) {
                    Label ch = (Label) character;
                    word.append(ch.getText());
                }
                if (!word.toString().equals(GameUtils.wordList.get(i))) {
                    GameUtils.status = false;
                    return false;
                }
            }
            GameUtils.status = true;
            return true;

        }

        public static void dice() {
            mainWord = "";
            Random dice = new Random();
            wordList.addAll(dataList.get(dice.nextInt(dataList.size())));
            Collections.shuffle(wordList);
            System.out.println(wordList);
            for (String word : wordList) {
                if (word.length() > mainWord.length()) {
                    mainWord = word;
                }
            }

        }

        public static void shuffleMainWord() {
            List<Character> chList = new ArrayList<>();
            for (char ch : GameUtils.mainWord.toCharArray()) {
                chList.add(ch);
            }

            Collections.shuffle(chList);
            StringBuilder str = new StringBuilder();
            for (Character ch : chList) {
                str.append(ch);
            }
            mainWord = str.toString();
        }
        public static void restart(){
            score = 0;
            numberOfTurn = 1;
        }
        public static void nextRound(){
            numberOfTurn++;
        }
        public static void modifyScore(int quantity){
            score+=quantity;
            if (score<0){
                score = 0;
            }
        }
    }

}
