package com.application.learnlingo;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class SynonymController extends SynonymAndAntonym {

    public Button s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15;
    public Button[] buttons;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        buttons = new Button[]{s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15};

        checkMode1.setVisible(false);
        checkMode2.setVisible(false);
        checkMode3.setVisible(true);
        checkMode4.setVisible(false);
        antonym.setOnAction(e -> AnimationChangeScene.handleButtonClick("FindAntonym.fxml", container));
    }

    public List<String> setListSyn(String s) {
        List<String> result = new ArrayList<>();
        try {
            String apiUrl = "https://api.datamuse.com/words?rel_syn=" + s.replaceAll(" ", "+");

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
                buttons = new Button[]{s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15};
                List<String> result = this.setListSyn(selectedWord);
                if (result.size() == 0) {
                    buttons[0].setText("There is no synonym for this word.");
                    for (int i = 1; i < 15; i++) {
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
