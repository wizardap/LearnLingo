package com.application.learnlingo;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TextToSpeech {
    String WordToSpeech;
    String language;
    public TextToSpeech(String Word, String language) {
        this.WordToSpeech = Word;
        this.language = language;
        speak();
    }

    public void speak() {
        String apiKey = "f9bbafb560a64ab78feda826087cc584";

        String wordToRead = WordToSpeech;

        try {
            String apiUrl = "http://api.voicerss.org/?";
            String apiKeyParam = "key=" + URLEncoder.encode(apiKey, "UTF-8");
            String textParam = "src=" + URLEncoder.encode(wordToRead, "UTF-8");
            String langParam = language;

            URL url = new URL(apiUrl + apiKeyParam + "&" + textParam + "&" + langParam);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

                Thread.sleep(clip.getMicrosecondLength() / 1000);

                audioInputStream.close();
                bufferedInputStream.close();
                inputStream.close();
            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}