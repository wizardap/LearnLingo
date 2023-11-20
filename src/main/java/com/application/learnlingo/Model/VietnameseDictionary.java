package com.application.learnlingo.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VietnameseDictionary<T extends Word> extends DatabaseDictionary<T> {
    public VietnameseDictionary(String dbPath, String dbName, String tableName, String defaultTableName) {
        super(dbPath, dbName, tableName, defaultTableName);
    }

    @Override
    public T getWordInformation(String searchWord) {
        T checkWord = cache.get(searchWord);
        if (checkWord == null) {
            String sql = new StringBuilder()
                    .append("SELECT DISTINCT word,html,isBookmarked FROM ")
                    .append(tableName)
                    .append(" WHERE word = ?;")
                    .toString();
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, searchWord);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String word = rs.getString("word");
                    String html = rs.getString("html");
                    boolean isBookmarked = rs.getInt("isBookmarked") == 0;
                    T foundWord = (T) new Word(word, html, isBookmarked);
                    cache.put(searchWord, foundWord);
                    return foundWord;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
            return null;
        }
        return checkWord;
    }

    @Override
    public void insertWord(T newWord) {
        if (prefixTree.contain(newWord.getWord())) {
            throw new IllegalArgumentException("Error: Duplicated the word!");
        }
        String sql = "INSERT INTO " + tableName + " (word,html,isBookmarked) VALUES(?,?,?);";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newWord.getWord());
            pstmt.setString(2, newWord.getHtml());
            pstmt.setInt(3, newWord.isBookmarked() ? 1 : 0);
            pstmt.executeUpdate();
            prefixTree.put(newWord.getWord());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Couldn't insert word to database!");
        }
    }

}
