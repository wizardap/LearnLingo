package com.application.learnlingo.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class DatabaseDictionary<T extends Word> extends Dictionary<T> {
    public static Connection connection;
    protected final String dbPath;
    protected final String dbName;
    protected final String tableName;
    protected String defaultTableName;
    protected HistorySearch historySearch;
    protected DatabaseBookmark bookmark;

    protected Trie prefixTree;
    protected HashMap<String, T> cache;


    public DatabaseDictionary(String dbPath, String dbName, String tableName, String defaultTableName) {
        this.dbPath = dbPath;
        this.dbName = dbName;
        this.tableName = tableName;
        this.defaultTableName = defaultTableName;
        this.prefixTree = new Trie();
        this.historySearch = new HistorySearch(new StringBuilder()
                .append(dbPath)
                .append("history")
                .append(tableName)
                .append(".txt")
                .toString());
        connection = DatabaseManager.getConnection(dbPath + dbName);
        this.bookmark = new DatabaseBookmark(tableName);
        cache = new HashMap<>();
        importWordListFromDatabase();
    }


    private void importWordListFromDatabase() {
        String sql = new StringBuilder().append("SELECT DISTINCT word FROM ").append(tableName).toString();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                prefixTree.put(rs.getString("word"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public abstract T getWordInformation(String searchWord);

    public ObservableList<String> exportSuggestionList(String prefixString) {

        return FXCollections.observableList(prefixTree.getPrefixStringList(prefixString));
    }

    public ObservableList<String> exportBookmarkSuggestionList(String prefixString) {
        return FXCollections.observableList(bookmark.exportPrefixStringList(prefixString));
    }

    @Override
    public void deleteWord(String opWord) {
        String sql = "DELETE FROM " + tableName + " WHERE word=?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, opWord);
            pstmt.executeUpdate();
            prefixTree.remove(opWord);
            bookmark.remove(opWord);
            historySearch.removeFromHistory(opWord);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public abstract void insertWord(T newWord);

    public void modifyWord(Word opWord) {
        String sql = "UPDATE " + tableName + " SET word = ?, html = ?, isBookmarked = ? WHERE word = ?;";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, opWord.getWord());
            pstmt.setString(2, opWord.getHtml());
            pstmt.setInt(3, opWord.isBookmarked() ? 1 : 0);
            pstmt.setString(4, opWord.getWord());
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
        bookmark.clear();
        cache.clear();
    }

    public List<String> exportHistoryList() {
        return historySearch.exportHistoryList();
    }

    public void insertToHistoryList(String word) {
        historySearch.insertToHistory(word);
    }

    public void setBookmark(String word) {
        bookmark.add(word);
    }

    public void unsetBookmark(String word) {
        bookmark.remove(word);
    }

    public List<String> exportBookmarkList() {
        return bookmark.exportData();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseDictionary<T> databaseDictionary = (DatabaseDictionary<T>) o;
        return Objects.equals(dbPath, databaseDictionary.dbPath)
                && Objects.equals(dbName, databaseDictionary.dbName)
                && Objects.equals(tableName, databaseDictionary.tableName)
                && Objects.equals(defaultTableName, databaseDictionary.defaultTableName)
                && Objects.equals(historySearch, databaseDictionary.historySearch)
                && Objects.equals(prefixTree, databaseDictionary.prefixTree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbPath,
                dbName,
                tableName,
                defaultTableName,
                connection,
                historySearch,
                prefixTree);
    }

    public String getHistoryString(int index) {
        return historySearch.getHistoryIndex(index);
    }

    public boolean isBookmarked(String word) {
        return bookmark.isBookmarked(word);
    }

    public boolean contain(String word) {
        return prefixTree.contain(word);
    }


    @Override
    public String getDefinition(String word) {
        return getWordInformation(word).getHtml();
    }

    public String getRandomWord() {
        String sql = new StringBuilder()
                .append("SELECT DISTINCT word FROM ")
                .append(tableName)
                .append(" ORDER BY RANDOM() LIMIT 1;")
                .toString();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return rs.getString("word");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}