package main.java.dao;

import main.java.database.Database;
import main.java.model.Person;
import main.java.model.User;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Manipulates user data in the database
 */

public class UserDAO {
//    private final Connection conn;
//
//    public UserDAO(Connection conn) {
//        this.conn = conn;
//    }

    /**
     * Adds a new user to the database table of users
     * @param user
     */

    public static void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO User (username, password, email, firstName, " +
                "lastName, gender, personID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        } finally {
            Database.closeConnection(conn, true);
        }

    }

    public static void update(User user) throws DataAccessException {
        String sql = "UPDATE User SET password = ?, email = ?, firstName = ?, " +
                "lastName = ?, gender = ?, personID = ? WHERE username = ?";
        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getPersonID());
            stmt.setString(7, user.getUsername());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while updating a user in the database");
        } finally {
            Database.closeConnection(conn, true);
        }

    }

    /**
     * Checks to see if a user already exists
     * @param username
     * @return
     */

    public static User find(String username) throws DataAccessException{
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = ?;";
        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                stmt.close();
                Database.closeConnection(conn, true);
                return user;
            } else {
                stmt.close();
                Database.closeConnection(conn, true);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn, true);
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * deletes a user from the database table
     */

    public static void clear() throws DataAccessException {
        String sql = "DELETE FROM User";
        try {
            Connection conn = Database.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
            Database.closeConnection(conn, true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }

    }
}

