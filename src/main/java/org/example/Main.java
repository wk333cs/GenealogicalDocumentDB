package org.example;
import java.sql.*;
import java.util.Scanner;


public class Main {
    private static final String DB_URL = "jdbc:sqlite:gen.db";

    public static void main(String[] args) throws SQLException{
        createTable();

            //Placeholder for adding documents
//            addProfile("profile1");
//            addDocument( 1, "Hilary", "Okulicki", 'd', 1905, "Kutno","Kutno" ,  null, "ffm", "lubił koty");

    }

    public static Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");

        }
        return conn;
    }

    public static void createTable() throws SQLException {
        String profiles = """
               
                CREATE TABLE IF NOT EXISTS profiles(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               profileName TEXT UNIQUE NOT NULL
                                          );
                """;

        String docs = """
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
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(profiles);
            stmt.execute(docs);
        }
    }


    public static void addProfile(String profileName) throws SQLException {
        String check = "SELECT 1 FROM profiles WHERE profileName = ? LIMIT 1";
        try(Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(check)){
            ps.setString(1,profileName);

            try (ResultSet rs = ps.executeQuery()) { //checks if profile is unique
                if (rs.next()) {
                System.out.println("Profile exists already"); //placeholder, normally method to send error message
                } else {
                    String sql = "INSERT INTO profiles(profileName) VALUES (?)";
                    try {
                        conn.setAutoCommit(false);
                        try (PreparedStatement ps2 = conn.prepareStatement(sql)) {
                            ps2.setString(1, profileName);
                            ps2.executeUpdate();
                        }
                        conn.commit();
                    } catch (SQLException e) {
                        try {
                            conn.rollback();
                        } catch (SQLException ignored) {}

                    } finally {
                        try {
                            conn.setAutoCommit(true);
                        } catch (SQLException ignored) {}
                    }
                }
            }

        }



    }

    public static void addDocument(int profile, String name, String surname, char type, int year, String parish, String city, String village, String branch, String info) throws SQLException {
        String sql = "INSERT INTO documents(profileID, name, surname, type, year, parish, city, village, branch, info, isPinned) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, false)";
        try(Connection conn = connect()){
            try {
                conn.setAutoCommit(false);
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, profile);
                    ps.setString(2, name);
                    ps.setString(3, surname);
                    ps.setString(4, String.valueOf(type));
                    ps.setInt(5, year);
                    ps.setString(6, parish);
                    ps.setString(7, city);
                    ps.setString(8, village);
                    ps.setString(9, branch);
                    ps.setString(10, info);

                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ignored) {
                }

            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ignored) {}
            }
        }
    }

    public static void displayDocument(int id) throws SQLException{}
}