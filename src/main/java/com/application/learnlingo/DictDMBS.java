package com.application.learnlingo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DictDMBS {
    private String dbPath;
    private String dbName;
    private String tableName;
    private String defaultTableName;
    private static Connection connection;
    private HistorySearch historySearch;
    private List<String> bookmarkList;
    private Trie prefixTree;
    private Trie prefixBookmarkTree;


    public DictDMBS(String dbPath, String dbName, String tableName, String defaultTableName) {
        this.dbPath = dbPath;
        this.dbName = dbName;
        this.tableName = tableName;
        this.defaultTableName = defaultTableName;
        this.prefixTree = new Trie();
        this.prefixBookmarkTree = new Trie();
        this.historySearch = new HistorySearch(new StringBuilder()
                .append(dbPath)
                .append("history")
                .append(tableName)
                .append(".txt")
                .toString());
        this.bookmarkList = new ArrayList<>();
        this.connection = this.connectingToDatabase();
        importBookmarkListFromDatabase();
        importWordListFromDatabase();
    }

    public DictDMBS(String dbPath, String dbName, String tableName) {
        this.dbPath = dbPath;
        this.dbName = dbName;
        this.tableName = tableName;
        this.historySearch = new HistorySearch(new StringBuilder()
                .append(dbPath)
                .append("history")
                .append(tableName)
                .append(".txt")
                .toString());
        this.bookmarkList = new ArrayList<>();
        this.connection = this.connectingToDatabase();
        this.prefixTree = new Trie();
        this.prefixBookmarkTree = new Trie();
        importBookmarkListFromDatabase();
        importWordListFromDatabase();
    }

    private void importWordListFromDatabase() {
        String sql = new StringBuilder().append("SELECT DISTINCT word FROM ").append(tableName).toString();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                prefixTree.put(rs.getString("word"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection connectingToDatabase() {
        String url = new StringBuilder()
                .append("jdbc:sqlite:")
                .append(dbPath)
                .append(dbName)
                .toString();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public Word getWordInformation(String searchWord) {
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
                return new Word(word, html, isBookmarked);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return new Word();
    }

    ObservableList<String> exportSuggestionList(String prefixString) {

        return FXCollections.observableList(prefixTree.getPrefixStringList(prefixString));
    }
    ObservableList<String> exportBookmarkSuggestionList(String prefixString){
        return FXCollections.observableList(prefixBookmarkTree.getPrefixStringList(prefixString));
    }

    public void removeWord(String opWord) {
        String sql = "DELETE FROM " + tableName + " WHERE word=?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, opWord);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertWord(Word newWord) {
        if (prefixTree.contain(newWord.getWord())) {
            throw new IllegalArgumentException("Error: Duplicated the word!");
        }
        String sql = "INSERT INTO " + tableName + " (word,html,isBookmarked) VALUES(?,?,?);";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
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

    public void modifyWord(Word opWord) {
        String sql = "UPDATE " + tableName + " SET html = ? WHERE word = ?;";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, opWord.getHtml());
            pstmt.setString(2, opWord.getWord());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Couldn't modify word in database!");
        }
    }


    public void copyDataFromDefaultTable() {
        String sql = new StringBuilder()
                .append("INSERT or IGNORE INTO ")
                .append(tableName)
                .append(" SELECT * FROM ")
                .append(defaultTableName)
                .append(";").toString();
        try {
            Connection conn = connection;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAllData() {
        String sql = "DELETE FROM " + tableName + ";";
        try {
            Connection conn = connection;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void resetData() {
        deleteAllData();
        copyDataFromDefaultTable();
        historySearch.clearHistory();
        bookmarkList.clear();
    }

    public List<String> exportHistoryList() {
        return historySearch.exportHistoryList();
    }

    public void insertToHistoryList(String word) {
        historySearch.insertToHistory(word);
    }

    public void setBookmark(String word) {
        String sql = "UPDATE " + tableName + " SET isBookmarked = 1 WHERE word = ?;";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, word);
            pstmt.executeUpdate();
            if (!bookmarkList.contains(word)) {
                bookmarkList.add(0,word);
                prefixBookmarkTree.put(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Couldn't set word into bookmark list!");
        }
    }

    public void unsetBookmark(String word) {
        String sql = "UPDATE " + tableName + " SET isBookmarked = 0 WHERE word = ?;";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, word);
            pstmt.executeUpdate();
            if (bookmarkList.contains(word)) {
                bookmarkList.removeIf(e -> e.equals(word));
                prefixBookmarkTree.remove(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Couldn't unset word into bookmark list!");
        }
    }

    public List<String> exportBookmarkList() {
        return bookmarkList;
    }

    private void importBookmarkListFromDatabase() {
        String sql = "SELECT DISTINCT word FROM " + tableName + " WHERE isBookmarked = 1;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String word = rs.getString("word");
                bookmarkList.add(word);
                prefixBookmarkTree.put(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Couldn't import bookmark list!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictDMBS dictDMBS = (DictDMBS) o;
        return Objects.equals(dbPath, dictDMBS.dbPath) && Objects.equals(dbName, dictDMBS.dbName) && Objects.equals(tableName, dictDMBS.tableName) && Objects.equals(defaultTableName, dictDMBS.defaultTableName) && Objects.equals(connection, dictDMBS.connection) && Objects.equals(historySearch, dictDMBS.historySearch) && Objects.equals(bookmarkList, dictDMBS.bookmarkList) && Objects.equals(prefixTree, dictDMBS.prefixTree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbPath, dbName, tableName, defaultTableName, connection, historySearch, bookmarkList, prefixTree);
    }
    public String getHistoryString(int index){
        return historySearch.getHistoryIndex(index);
    }
    public boolean contain(String word){
        return prefixTree.contain(word);
    }
}
