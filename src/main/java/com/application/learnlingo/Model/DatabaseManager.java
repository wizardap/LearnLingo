package com.application.learnlingo.Model;

import java.sql.Connection;

public class DatabaseManager {
    private static Connection connection;

    public static void connectingToDatabase(String dbPath) {
        String url = new StringBuilder()
                .append("jdbc:sqlite:")
                .append(dbPath)
                .toString();
        try {
            connection = java.sql.DriverManager.getConnection(url);
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection(String dbPath) {
        connectingToDatabase(dbPath);
        return connection;
    }
}
