package main.java.dao;

import main.java.database.Database;
import main.java.model.AuthToken;
import main.java.model.Person;

import java.sql.*;

/**
 * Manipulates authtoken data in the database
 */

public class AuthTokenDAO {
//    private final Connection conn;
//
//    public AuthTokenDAO(Connection conn) {
//        this.conn = conn;
//    }

    /**
     * Adds a new authtoken to the database table
     * @param authtoken
     */

    public static void insert(AuthToken authtoken) throws DataAccessException {
        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES (?, ?)";
        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authtoken into the database");
        } finally {
            Database.closeConnection(conn, true);
        }
    }

    /**
     * checks to see if an authtoken already exists in the database
     * @param authtoken
     * @return
     */

    public static AuthToken find(String authtoken) throws DataAccessException{
        AuthToken at;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                at = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                stmt.close();
                Database.closeConnection(conn, true);
                return at;
            } else {
                stmt.close();
                Database.closeConnection(conn, true);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn, true);
            throw new DataAccessException("Error encountered while finding an auth token in the database");
        }
    }

    /**
     * Deletes an authtoken from the database table
     */

    public static void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtoken";
        try {
            Connection conn = Database.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
            Database.closeConnection(conn, true);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authtoken table");
        }
    }
    public static void clearAuthTokenInfoFromUser(String userName) throws DataAccessException {
        String sql = "DELETE FROM AuthToken WHERE username = ?;";
        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.executeUpdate();
            Database.closeConnection(conn, true);
        }
        catch (SQLException e){
            System.out.println(e);
            Database.closeConnection(conn, true);
        }
    }
}
