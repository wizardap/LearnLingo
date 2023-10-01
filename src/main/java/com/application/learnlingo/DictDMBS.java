package com.application.learnlingo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DictDMBS {
    private String dbpath;
    private String tableName;

    public DictDMBS(String dbpath, String tableName) {
        this.dbpath = dbpath;
        this.tableName = tableName;

    }

    public Connection connecting(){
        String url = "jdbc:sqlite:"+dbpath;
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public Word getWordInformation(String searchWord){
        String sql = "SELECT word,html,description,pronounce FROM "
                        +tableName
                        +" WHERE word=\'"+searchWord+"\';";
        try(Connection conn = this.connecting();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()){
                String word = rs.getString("word");
                String html = rs.getString("html");
                String description = rs.getString("description");
                String pronounce = rs.getString("pronounce");
                return new Word(word,description,html,pronounce);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return new Word();
    }
    ObservableList<String> exportSuggestionList(String prefixString){
        List<String> filterList = new ArrayList<String>();
        String sql = "SELECT word FROM "+tableName
                        +" WHERE word LIKE \'"+prefixString
                        +"%\';";
        try(Connection conn = this.connecting();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()){
                String word = rs.getString("word");
                filterList.add(word);
            }
           // System.out.println("End.");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return FXCollections.observableList(filterList);
    }



}
