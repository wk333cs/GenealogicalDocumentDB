package org.example.gendatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBManager {

        private static final String DB_URL = "jdbc:sqlite:gen.db";

        public static void main(String[] args) throws SQLException {
            createTable();
            FilterParameters test= new FilterParameters();


        test.addCity("ASDA");
      search(test, 1);

            //displayDocument(1);
            // addDocument( 2, "Henryk", "Nowak", 'd', 1780, "Kutno","Kutno" ,  null, "ffm", "lubił koty");
            //editDocument( 1, "Hilary", "Okulicki", 'd', 1905, "Kutno","Kutno" ,  null, "ffm", "lubił koty");

            //Placeholder for adding documents
            //addProfile("profile1","#3238a8");
            //addProfile("profile2","#a87d32");
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
               profileID INTEGER PRIMARY KEY AUTOINCREMENT,
               profileName TEXT UNIQUE NOT NULL,
               profileColor TEXT UNIQUE NOT NULL                                    
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
                FOREIGN KEY (profileID) REFERENCES profiles(profileID)
                );
                 """;
            try (Connection conn = connect();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(profiles);
                stmt.execute(docs);
            }
        }


        public static void addProfile(String profileName, String profileColor) throws SQLException {
            String check = "SELECT 1 FROM profiles WHERE profileName = ? LIMIT 1";
            try(Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(check)){
                ps.setString(1,profileName);

                try (ResultSet rs = ps.executeQuery()) { //checks if profile is unique
                    if (rs.next()) {
                        System.out.println("Profile exists already"); //placeholder, normally method to send error message
                    } else {
                        String sql = "INSERT INTO profiles(profileName, profileColor) VALUES (?,?)";
                        try (PreparedStatement ps2 = conn.prepareStatement(sql)) {
                            ps2.setString(1, profileName);
                            ps2.setString(2,profileColor);
                            ps2.executeUpdate();
                        }
                    }
                }

            }



        }

        public static void addDocument(DocumentParameters dp) throws SQLException {
            String sql = "INSERT INTO documents(profileID, name, surname, type, year, parish, city, village, branch, info, isPinned) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, false)";
            try(Connection conn = connect()){
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, dp.profile);
                    ps.setString(2, dp.name);
                    ps.setString(3, dp.surname);
                    ps.setString(4, String.valueOf(dp.type));
                    ps.setInt(5, dp.year);
                    ps.setString(6, dp.parish);
                    ps.setString(7, dp.city);
                    ps.setString(8, dp.village);
                    ps.setString(9,dp. branch);
                    ps.setString(10, dp.info);

                    ps.executeUpdate();
                }
            }
        }

        public static void displayDocument(int id) throws SQLException{
            String sql = "SELECT * FROM documents JOIN profiles USING (profileID) WHERE id =? ";
            try(Connection conn = connect()){
                try (PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setInt(1, id);

                    ResultSet rs = ps.executeQuery();
                    if(rs.next()) {
                        //placeholder, should return the querry so that it can be displayed
                        System.out.println(rs.getInt("id"));
                        System.out.println(rs.getInt("year"));
                        System.out.println(rs.getString("profileName"));
                    }
                }
            }
        }

        public static void editDocument(DocumentParameters dp) throws SQLException{
            String sql = "UPDATE documents SET (name, surname, type, year, parish, city, village, branch, info) = (?,?,?,?,?,?,?,?,?) WHERE id=?";
            try(Connection conn = connect()){
                try(PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setString(1, dp.name);
                    ps.setString(2, dp.surname);
                    ps.setString(3, String.valueOf(dp.type));
                    ps.setInt(4, dp.year);
                    ps.setString(5, dp.parish);
                    ps.setString(6, dp.city);
                    ps.setString(7, dp.village);
                    ps.setString(8, dp.branch);
                    ps.setString(9, dp.info);
                    ps.setInt(10, dp.id);

                    ps.executeUpdate();
                }
            }

        }

        public static List<forDisplay> search(FilterParameters fp, int profileId) throws SQLException {
            List<forDisplay> searchResults = new ArrayList<>();
            String nameHolder;
            String surnameHolder;
            String typeHolder;
            String parishHolder;
            String cityHolder;
            String villageHolder;
            String branchHolder;

            //name
            if(!fp.getName().isEmpty()) {
                nameHolder = String.join(" OR ", Collections.nCopies(fp.getName().size(), "name LIKE ?"));
            } else {
                nameHolder = "1=1";
            }
            //surname
            if(!fp.getSurname().isEmpty()) {
                surnameHolder = String.join(" OR ", Collections.nCopies(fp.getSurname().size(), "surname LIKE ?"));
            } else {
                surnameHolder = "1=1";
            }
            //type*
            if(!fp.getType().isEmpty()) {
                typeHolder = String.join(" OR ", Collections.nCopies(fp.getType().size(), "type = ?"));
            } else {
                typeHolder = "1=1";
            }
            //parish
            if(!fp.getParish().isEmpty()) {
                parishHolder = String.join(" OR ", Collections.nCopies(fp.getParish().size(), "parish LIKE ?"));
            } else {
                parishHolder = "1=1";
            }
            //city
            if(!fp.getCity().isEmpty()) {
                cityHolder = String.join(" OR ", Collections.nCopies(fp.getCity().size(), "city LIKE ?"));
            } else {
                cityHolder = "1=1";
            }
            //village
            if(!fp.getVillage().isEmpty()) {
                villageHolder = String.join(" OR ", Collections.nCopies(fp.getVillage().size(), "village LIKE ?"));
            } else {
                villageHolder = "1=1";
            }
            //branch*
            if(!fp.getBranch().isEmpty()) {
                branchHolder = String.join(" OR ", Collections.nCopies(fp.getBranch().size(), "branch = ?"));
            } else {
                branchHolder = "1=1";
            }


            //Since I want to use LIKE here, I'll have to use OR instead of IN
            String sql = "SELECT * FROM documents WHERE (profileID = ?) AND (year BETWEEN ? AND ?) AND (" + nameHolder + ") AND (" + surnameHolder + ") AND (" + typeHolder + ") AND (" + parishHolder +") AND (" + cityHolder +") AND  (" + villageHolder +") AND  (" + branchHolder +")";
            try (Connection conn = connect()) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, profileId);
                    ps.setInt(2, fp.getFirstYear());
                    ps.setInt(3, fp.getLastYear());
                    int i =4;

                    for (String name : fp.getName()) {
                        ps.setString(i++, "%" + name + "%");

                    }


                    for (String surname : fp.getSurname()) {
                        ps.setString(i++, "%" + surname + "%");

                    }


                    for (String type : fp.getType()) {
                        ps.setString(i++, type);

                    }


                    for (String parish : fp.getParish()) {
                        ps.setString(i++, "%" + parish + "%");

                    }


                    for (String city : fp.getCity()) {
                        ps.setString(i++, "%" + city + "%");

                    }


                    for (String village : fp.getVillage()) {
                        ps.setString(i++, "%" + village + "%");

                    }


                    for (String branch : fp.getBranch()) {
                        ps.setString(i++, branch);

                    }


                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        String name = rs.getString("name");
                        String surname = rs.getString("surname");
                        String type= rs.getString("type");
                        //change type
                        int year = rs.getInt("year");
                        String parish =rs.getString("parish");
                        String city = rs.getString("city");
                        String village =rs.getString("village");
                        String branch = rs.getString("branch");
                        searchResults.add(new forDisplay (name, surname, type, year, parish, city, village, branch));
                    }
                    return searchResults;

                }


            }
        }


        public static void pinClicked(int id) throws SQLException {
            String sql = "SELECT isPinned FROM documents WHERE id=?";
            String sqlSetTrue = "Update documents SET isPinned = true WHERE id=?";
            String sqlSetFalse = "Update documents SET isPinned = false WHERE id=?";
            try (Connection conn = connect()) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);

                    try(ResultSet rs = ps.executeQuery()){
                        if (rs.next()){
                            if(rs.getBoolean("isPinned")){ // was pinned before being clicked
                                try(PreparedStatement ps2 = conn.prepareStatement(sqlSetFalse)){
                                    ps2.setInt(1, id);
                                    ps2.executeUpdate();
                                }

                            } else { //was not pinned
                                try(PreparedStatement ps2 = conn.prepareStatement(sqlSetTrue)){
                                    ps2.setInt(1, id);
                                    ps2.executeUpdate();
                                }

                            }
                        }
                    }

                }

            }
        }


}
