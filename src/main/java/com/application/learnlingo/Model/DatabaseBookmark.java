package com.application.learnlingo.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.application.learnlingo.Model.DatabaseDictionary.connection;

public class DatabaseBookmark implements Bookmark {
    protected String tableName;
    protected List<String> bookmarkList;
    protected Trie prefixTree;

    public DatabaseBookmark(String tableName) {
        this.tableName = tableName;
        prefixTree = new Trie();
        bookmarkList = new ArrayList<>();
        importData();
    }

    @Override
    public void importData() {
        String sql = "SELECT DISTINCT word FROM " + tableName + " WHERE isBookmarked = 1;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String word = rs.getString("word");
                bookmarkList.add(word);
                prefixTree.put(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Couldn't import bookmark list!");
        }
    }

    @Override
    public List<String> exportData() {
        return bookmarkList;
    }

    @Override
    public void add(String word) {
        if (!bookmarkList.contains(word)) {
            String sql = "UPDATE " + tableName + " SET isBookmarked = 1 WHERE word = ?;";

            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, word);
                pstmt.executeUpdate();
                bookmarkList.add(0, word);
                prefixTree.put(word);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: Couldn't set word into bookmark list!");
            }
        }
    }

    @Override
    public void remove(String word) {
        if (bookmarkList.contains(word)) {
            String sql = "UPDATE " + tableName + " SET isBookmarked = 0 WHERE word = ?;";
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, word);
                pstmt.executeUpdate();

                bookmarkList.removeIf(e -> e.equals(word));
                prefixTree.remove(word);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: Couldn't unset word into bookmark list!");
            }
        }
    }

    @Override
    public void clear() {
        for (String s : bookmarkList) {
            prefixTree.remove(s);
        }
        bookmarkList.clear();

    }

    protected List<String> exportPrefixStringList(String prefix) {
        return prefixTree.getPrefixStringList(prefix);
    }

    public boolean isBookmarked(String word) {
        return bookmarkList.contains(word);
    }
}
