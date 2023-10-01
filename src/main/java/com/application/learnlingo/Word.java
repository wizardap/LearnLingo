package com.application.learnlingo;

import java.util.Objects;

public class Word {
    private String word;
    private String description;
    private String html;
    private String pronounce;

    public Word() {
        this.word = null;
        this.description = null;
        this.html = null;
        this.pronounce = null;
    }

    public Word(String word, String description, String html, String pronounce) {
        this.word = word;
        this.description = description;
        this.html = html;
        this.pronounce = pronounce;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public boolean isNotFound(){
        return this.word == null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) && Objects.equals(description, word1.description) && Objects.equals(html, word1.html) && Objects.equals(pronounce, word1.pronounce);
    }

}
