package com.application.learnlingo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.List;


public class AntonymController extends SynonymAndAntonym {

    public Button a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15;
    public Button[] buttons;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        buttons = new Button[]{a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15};

        for (int i = 0; i < 15; i++) {
            buttons[i].setVisible(false);
        }
//        checkMode1.setVisible(false);
//        checkMode2.setVisible(false);
//        checkMode3.setVisible(true);
        checkMode4.setVisible(true);

        synonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindSynonym.fxml", container));
    }

    public List<String> setListSyn(String s) {
        List<String> result = new ArrayList<>();
        try {
            String apiUrl = "https://api.datamuse.com/words?rel_ant=" + s;

            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            connection.disconnect();

            String[] words = response.toString().split("(?<=\\}),");
            for (String word : words) {
                String[] wordParts = word.split(":");
                if (wordParts.length >= 2) {
                    String cleanWord = wordParts[1].replaceAll("\"", "");
                    cleanWord = cleanWord.split(",")[0];
                    result.add(cleanWord);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void handleMouseClicked(MouseEvent mouseEvent) {
        String selectedWord = listWords.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            try {
                FileReader fr = new FileReader(historyTxt);
                BufferedReader input = new BufferedReader(fr);
                buttons = new Button[]{a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15};
                List<String> result = this.setListSyn(selectedWord);
                if (result.size() == 0) {
                    for (int i = 0; i < 15; i++) {
                        buttons[i].setVisible(false);
                    }
                } else {
                    for (int i = 0; i < 15; i++) {
                        if (i < result.size()) {
                            buttons[i].setVisible(true);
                            buttons[i].setText(result.get(i));
                        } else {
                            buttons[i].setVisible(false);
                        }
                    }
                }
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
