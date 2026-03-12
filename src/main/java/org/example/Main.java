package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {
    private static final String DB_URL = "jdbc:sqlite:gen.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement s = conn.createStatement();
            createProfTable(conn);
            createDocTable(conn);


        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

    }
    public static void createProfTable(Connection conn) throws SQLException {
        String sql = """
               CREATE TABLE IF NOT EXISTS profiles(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               profileName TEXT NOT NULL
             
             );
                """;
        try(Statement stnt = conn.createStatement()) {
            stnt.execute(sql);
        }
    }
    public static void createDocTable(Connection conn) throws SQLException{
        String sql = """
                CREATE TABLE IF NOT EXISTS documents (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                profileID INTEGER NOT NULL,
                name TEXT NOT NULL,
                surname TEXT NOT NULL,
                type VARCHAR2 (1) NOT NULL,
                year INTEGER NOT NULL,
                parish TEXT NOT NULL,
                city TEXT NULL,
                village TEXT NULL,
                branch TEXT NOT NULL,
                info TEXT NULL,
                isPinned BOOLEAN NOT NULL,
                FOREIGN KEY (profileID) REFERENCES profiles(id)
                );
                """;
        try(Statement stnt = conn.createStatement()) {
            stnt.execute(sql);
        }
    }
}