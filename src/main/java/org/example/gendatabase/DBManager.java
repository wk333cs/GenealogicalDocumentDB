package org.example.gendatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

 class DBManager {
    // communication protocol (JDBC), database driver(SQLite) and database file identifier (gen.db) are specified
        private static final String DB_URL = "jdbc:sqlite:gen.db";

        private static Connection connect() throws SQLException {
            Connection conn = DriverManager.getConnection(DB_URL); // API call that performs session initialization
            try (Statement stmt = conn.createStatement()) {
                //as in SQLite by default foreign keys are disabled, referential integrity is enforced
                stmt.execute("PRAGMA foreign_keys = ON");

            }
            return conn;
        }

        protected static void createTable() throws SQLException {
            //if such tables are not already established in the database, they are created
            String profiles = """
               
                CREATE TABLE IF NOT EXISTS profiles(
               profileID INTEGER PRIMARY KEY AUTOINCREMENT,
               profileName TEXT NOT NULL,
               profileColor TEXT NOT NULL                                    
                                          );
                """;

            String docs = """
                CREATE TABLE IF NOT EXISTS documents (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                profileID INTEGER NOT NULL,
                name TEXT NOT NULL,
                surname TEXT NOT NULL,
                type TEXT NOT NULL,
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
            try (Connection conn = connect(); // new connection object is created using connect() method
                 Statement stmt = conn.createStatement()) {
                stmt.execute(profiles);
                stmt.execute(docs);
            }
        }


        protected static void addProfile(String profileName, String profileColor) throws SQLException {

            try(Connection conn = connect()){
                String sql = "INSERT INTO profiles(profileName, profileColor) VALUES (?,?)";
                try (PreparedStatement ps1 = conn.prepareStatement(sql)) {
                    ps1.setString(1, profileName);
                    ps1.setString(2,profileColor);
                    ps1.executeUpdate();
                }

            }

        }
        protected static void editProfile(String name, String color, int id) throws SQLException{
            try(Connection conn = connect()){
                String sql = "UPDATE profiles SET(profileName, profileColor) = (?,?) WHERE profileID = ? ";
                try (PreparedStatement ps1 = conn.prepareStatement(sql)) {
                    ps1.setString(1, name);
                    ps1.setString(2,color);
                    ps1.setInt(3,id);
                    ps1.executeUpdate();
                }

            }
        }
        protected static List<ProfileParameters> getProfiles() throws SQLException{
            List<ProfileParameters> allProfiles = new ArrayList<>();
            String sql= "SELECT * FROM profiles";
             try(Connection conn = connect()){
                 try (PreparedStatement ps = conn.prepareStatement(sql)) {
                     ResultSet rs = ps.executeQuery();
                     while(rs.next()){
                         int id = rs.getInt("profileID");
                         String name = rs.getString("profileName");
                         String color = rs.getString("profileColor");
                         allProfiles.add(new ProfileParameters(id,name,color));
                     }

                 }

            }
             return allProfiles;
        }


        protected static void addDocument(forDisplay fd) throws SQLException {
            String sql = "INSERT INTO documents(profileID, name, surname, type, year, parish, city, village, branch, info, isPinned) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try(Connection conn = connect()){
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, fd.getProfile()); // ? are replaced by the chosen datatype
                    ps.setString(2, fd.getName()); //parametrization ensures literal value inputs, preventing outside modifications of the SQL query
                    ps.setString(3, fd.getSurname());
                    ps.setString(4, fd.getType());
                    ps.setInt(5, fd.getYear());
                    ps.setString(6, fd.getParish());
                    ps.setString(7, fd.getCity());
                    ps.setString(8, fd.getVillage());
                    ps.setString(9,fd.getBranch());
                    ps.setString(10, fd.getInfo());
                    ps.setBoolean(11,fd.getIsPinned());

                    ps.executeUpdate();
                }
            }
        }


        protected static void editDocument(forDisplay fd) throws SQLException{
            String sql = "UPDATE documents SET (name, surname, type, year, parish, city, village, branch, info) = (?,?,?,?,?,?,?,?,?) WHERE (id=?) AND (profileID = ?)";
            try(Connection conn = connect()){
                try(PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setString(1, fd.getName());
                    ps.setString(2, fd.getSurname());
                    ps.setString(3, fd.getType());
                    ps.setInt(4, fd.getYear());
                    ps.setString(5, fd.getParish());
                    ps.setString(6, fd.getCity());
                    ps.setString(7, fd.getVillage());
                    ps.setString(8, fd.getBranch());
                    ps.setString(9, fd.getInfo());
                    ps.setInt(10, fd.getId());
                    ps.setInt(11, fd.getProfile());

                    ps.executeUpdate();
                }
            }

        }

        protected static List<forDisplay> search(FilterParameters fp, int profileId) throws SQLException {
            List<forDisplay> searchResults = new ArrayList<>();
            String nameHolder;
            String surnameHolder;
            String typeHolder;
            String parishHolder;
            String cityHolder;
            String villageHolder;
            String branchHolder;
            //creates a string including every inputted:
            //name
            if(!fp.getName().isEmpty()) {
                //the use of LIKE allows the user to search by incomplete words and phrases
                nameHolder = String.join(" OR ", Collections.nCopies(fp.getName().size(), "name LIKE ?"));
            } else {
                nameHolder = "1=1"; // a true statement used in case no name is inputted as the search parameter
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


            //All documents matching the query are selected
            // The result set is the intersection of all conditions for every parameter, hence if the parameter holder holds 1=1, it will not impact the search results in any way
            String sql = "SELECT * FROM documents WHERE (profileID = ?) AND (year BETWEEN ? AND ?) AND (" + nameHolder + ") AND (" + surnameHolder + ") AND (" + typeHolder + ") AND (" + parishHolder +") AND (" + cityHolder +") AND  (" + villageHolder +") AND  (" + branchHolder +")";
            try (Connection conn = connect()) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    //static parameters are set first by replacing the question marks within the SQL statement
                    ps.setInt(1, profileId);
                    ps.setInt(2, fp.getFirstYear());
                    ps.setInt(3, fp.getLastYear());
                    int i =4;
                    //dynamically changing filter parameters are set analogically through for loops
                    for (String name : fp.getName()) {
                        ps.setString(i++, "%" + name + "%"); // use of % allows for the phrase to be found no matter its position

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
                    while(rs.next()){ //the parameters of each search result are transported to be displayed through an array list of forDisplay objects
                        int id= rs.getInt("id");
                        String name = rs.getString("name");
                        String surname = rs.getString("surname");
                        String type= rs.getString("type");
                        int year = rs.getInt("year");
                        String parish =rs.getString("parish");
                        String city = rs.getString("city");
                        String village =rs.getString("village");
                        String branch = rs.getString("branch");
                        String info = rs.getString("info");
                        boolean isPinned= rs.getBoolean("isPinned");
                        searchResults.add(new forDisplay (id,name, surname, type, year, parish, city, village, branch, info, profileId, isPinned));
                    }
                    return searchResults;

                }


            }
        }

    protected static List<forDisplay> showPinned( int profileId) throws SQLException {
        List<forDisplay> allPinned = new ArrayList<>();
        String sql ="SELECT * FROM documents WHERE profileID=? AND isPinned";
        try (Connection conn = connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, profileId);

                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String type = rs.getString("type");
                    int year = rs.getInt("year");
                    String parish = rs.getString("parish");
                    String city = rs.getString("city");
                    String village = rs.getString("village");
                    String branch = rs.getString("branch");
                    String info = rs.getString("info");
                    allPinned.add(new forDisplay(id, name, surname, type, year, parish, city, village, branch, info, profileId, true));
                }

            }
        }
        return allPinned;

    }


        protected static void pinClicked(forDisplay fd) throws SQLException {

            String sql = "Update documents SET isPinned = ? WHERE id=? AND profileID=?";

            try (Connection conn = connect()) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setBoolean(1, fd.getIsPinned());
                    ps.setInt(2, fd.getId());
                    ps.setInt(3,fd.getProfile());

                    ps.executeUpdate();

                }

            }
        }


}
