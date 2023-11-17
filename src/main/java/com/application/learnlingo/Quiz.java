package com.application.learnlingo;

import javafx.util.Pair;

import java.util.List;

public class Quiz {
    private List<String> answer;
    private String question;
    private String correctAnswer;
    private String ImagePath;

    public Quiz(String question, List<String> answer, String correctAnswer, String ImagePath) {
        this.question = question;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
        this.ImagePath = ImagePath;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
