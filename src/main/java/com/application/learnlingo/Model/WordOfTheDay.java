package com.application.learnlingo.Model;

import java.io.*;
import java.time.LocalDate;

import static com.application.learnlingo.Controller.GeneralController.evDict;

public class WordOfTheDay {
    private static final String WORD_OF_THE_DAY_PATH =
            "./src/main/resources/com/application/learnlingo/database/wordOfTheDay.txt";
    private static String wordToday;
    private static boolean loaded = false;
    private static String date;

    public static String getWordToday() {
        return wordToday;
    }

    public static void loadData() {
        File wordOfTheDayFile = new File(WORD_OF_THE_DAY_PATH);
        if (!wordOfTheDayFile.exists()) {
            try {
                if (!wordOfTheDayFile.createNewFile()) {
                    System.out.println(new StringBuilder().append("Couldn't create file ")
                            .append(WORD_OF_THE_DAY_PATH));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileReader fr = null;
        try {
            fr = new FileReader(WORD_OF_THE_DAY_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(new StringBuilder().append("Error: Can't read data from ")
                    .append(WORD_OF_THE_DAY_PATH));
        }
        assert fr != null;
        BufferedReader br = new BufferedReader(fr);
        String line = null;

        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(new StringBuilder().append("Error: Can't read data from ")
                    .append(WORD_OF_THE_DAY_PATH));
        }
        if (line != null) {
            String[] data = line.split(" ");
            date = data[0];
            wordToday = data[1];
        }
        try {
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(new StringBuilder().append("Error: Can't close ")
                    .append(WORD_OF_THE_DAY_PATH));

        }
    }

    public static String getDefinition() {
        if (!loaded) {
            loadData();
            loaded = true;
        }
        if (date == null || !date.equals(LocalDate.now().toString())) {
            date = LocalDate.now().toString();
            wordToday = evDict.getRandomWord();
            saveData();
        }
        return evDict.getDefinition(wordToday);
    }

    private static void saveData() {
        FileWriter fw = null;
        try {
            fw = new FileWriter(WORD_OF_THE_DAY_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(new StringBuilder().append("Error: Can't write data to ")
                    .append(WORD_OF_THE_DAY_PATH));
        }
        assert fw != null;
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(new StringBuilder().append(date).append(" ").append(wordToday).toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(new StringBuilder().append("Error: Can't write data to ")
                    .append(WORD_OF_THE_DAY_PATH));
        }
        try {
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(new StringBuilder().append("Error: Can't close ")
                    .append(WORD_OF_THE_DAY_PATH));
        }
    }
}
