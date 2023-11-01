package com.application.learnlingo;

import java.util.Objects;

public class Word {
    private String word;
    private String html;
    private boolean bookmarked;

    public Word() {
        this.word = null;
        this.html = null;
        this.bookmarked = false;
    }

    public Word(String word, String html, boolean bookmarked) {
        this.word = word;
        this.html = html;
        this.bookmarked = bookmarked;
    }

    public Word(String word, String html) {
        this.word = word;
        this.html = html;
        this.bookmarked = false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return bookmarked == word1.bookmarked && Objects.equals(word, word1.word) && Objects.equals(html, word1.html);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, html, bookmarked);
    }

}
