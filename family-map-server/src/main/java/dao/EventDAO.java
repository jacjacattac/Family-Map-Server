package main.java.dao;

import main.java.database.Database;
import model.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

/**
 * Manipulates event data in the database
 */
public class EventDAO {
    //private final Connection conn;


    /**
     * inserts a new event into the database table
     * @param event
     * @throws DataAccessException
     */

    public static void insert(Event event) throws DataAccessException, SQLException{

        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        Connection conn = Database.openConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        } finally {
            Database.closeConnection(conn, true);
        }
    }

    /**
     * Checks to see if an event already exists in the database table
     * @param eventID
     * @return
     * @throws DataAccessException
     */
    public static Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE EventID = ?;";
        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                stmt.close();
                Database.closeConnection(conn, true);
                return event;
            } else {
                stmt.close();
                Database.closeConnection(conn, true);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn,true);
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }
    public static ArrayList <Event> findAllEvents(String username) throws DataAccessException {
        ArrayList<Event> allEvents = new ArrayList<>();
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?;";
        try{
            Connection conn = Database.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                //stmt.close();
                //Database.closeConnection(conn, true);
                allEvents.add(event);
            }
            stmt.close();
            Database.closeConnection(conn, true);
            return allEvents;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all events in the database");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes an event from the database table
     * @throws DataAccessException
     */
    public static void clear() throws DataAccessException {
        String sql = "DELETE FROM Event";
        try {
            Connection conn = Database.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
            Database.closeConnection(conn, true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public static void clearEventInfoFromUser(String userName) throws DataAccessException {
        String sql = "DELETE FROM Event WHERE associatedUsername = ?;";
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