package com.application.learnlingo;

public abstract class Dictionary<T extends Word> {
    public abstract String getDefinition(String word);
    public abstract void insertWord(T word);
    public abstract void deleteWord(String word);
    public abstract void modifyWord(T word);
    public abstract T getWordInformation(String searchWord);
}
