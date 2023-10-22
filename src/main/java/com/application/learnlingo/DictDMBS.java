package com.application.learnlingo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DictDMBS {
    private String dbpath;
    private String tableName;
    private String defaultTableName;
    private Connection connection;


    public DictDMBS(String dbpath, String tableName) {
        this.dbpath = dbpath;
        this.tableName = tableName;
        connection = this.connecting();
    }

    public DictDMBS(String dbpath, String tableName, String defaultTableName) {
        this.dbpath = dbpath;
        this.tableName = tableName;
        this.defaultTableName = defaultTableName;
        connection = this.connecting();
    }

    public Connection connecting() {
        String tmp = "jdbc:sqlite:";
        String url = tmp.concat(dbpath);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public Word getWordInformation(String searchWord) {
        String sql = "SELECT word,html,description,pronounce FROM "
                +tableName
                +" WHERE word = ?;";
        try  {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,searchWord);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String word = rs.getString("word");
                String html = rs.getString("html");
                String description = rs.getString("description");
                String pronounce = rs.getString("pronounce");
                return new Word(word, description, html, pronounce);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return new Word();
    }

    ObservableList<String> exportSuggestionList(String prefixString) {
        List<String> filterList = new ArrayList<String>();
        String sql = "SELECT word FROM " + tableName
                + " WHERE word LIKE \'" + prefixString
                + "%\';";
        try (Connection conn = this.connecting();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String word = rs.getString("word");
                filterList.add(word);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return FXCollections.observableList(filterList);
    }
    public void removeWord(String opWord){
       String sql = "DELETE FROM "+tableName+" WHERE word=?;";
       try {
           PreparedStatement pstmt = connection.prepareStatement(sql);
           pstmt.setString(1,opWord);
           pstmt.executeUpdate();

       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
    }
    public void insertWord(Word newWord){

    }
    public void copyDataFromDefaultTable() {
        String sql = "INSERT or IGNORE INTO " + tableName + " SELECT * FROM " + defaultTableName + ";";
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
    }


}
