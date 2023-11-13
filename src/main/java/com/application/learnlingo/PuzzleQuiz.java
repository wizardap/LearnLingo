package com.application.learnlingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleQuiz {
    private List<String> quizList;
    private String mainWord;

    public PuzzleQuiz(List<String> quizList) {
        this.quizList = quizList;
        this.mainWord = "";
        dice();
    }
    public void dice(){
        Collections.shuffle(quizList);
//        System.out.println(quizList);
        for (String word : quizList) {
            if (word.length() > mainWord.length()) {
                mainWord = word;
            }
        }
    }

    public List<String> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<String> quizList) {
        this.quizList = quizList;
    }

    public String getMainWord() {
        return mainWord;
    }

    public void setMainWord(String mainWord) {
        this.mainWord = mainWord;
    }

    public String getShuffleMainWord(){
        List<Character> chList = new ArrayList<>();
        for (char ch :mainWord.toCharArray()) {
            chList.add(ch);
        }

        Collections.shuffle(chList);
        StringBuilder str = new StringBuilder();
        for (Character ch : chList) {
            str.append(ch);
        }
        return str.toString();
    }
}
