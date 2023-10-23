package main.java.dao;

import main.java.database.Database;
import model.*;
import org.sqlite.SQLiteException;

import java.sql.*;
import java.util.ArrayList;

/**
 * Manipulates person data in the database
 */
public class PersonDAO {

    //private final Connection conn;

    //public PersonDAO(Connection conn) {
    //    this.conn = conn;
    //}

    /**
     * Adds a new person to the database table of persons
     * @param person
     */

    public static void insert(Person person) throws DataAccessException, SQLException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, " +
                "gender, fatherID, motherID, spouseID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        } finally {
            Database.closeConnection(conn, true);
        }

    }

    public static void update(String personID, Person person) throws DataAccessException, SQLException {
        String sql = "UPDATE Person SET personID = ?, associatedUsername = ?, firstName = ?, lastName = ?, " +
                "gender = ?, fatherID = ?, motherID = ?, spouseID = ? WHERE personID = ?";

        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.setString(9, personID);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        } finally {
            Database.closeConnection(conn, true);
        }

    }

    /**
     * Checks to see if a person already exists in the database table
     * @param personID
     * @return Person
     */

    public static Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        Connection conn = Database.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                stmt.close();
                Database.closeConnection(conn, true);
                return person;
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

    public static ArrayList<Person> findAllPersons(String username) throws DataAccessException {
        ArrayList<Person> allPersons = new ArrayList<>();
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try{
            Connection conn = Database.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
//                stmt.close();
//                Database.closeConnection(conn, true);
                allPersons.add(person);
            }
            stmt.close();
            Database.closeConnection(conn, true);
            return allPersons;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all persons in the database");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a person from the database table
     */

    public static void clear() throws DataAccessException {
        String sql = "DELETE FROM Person";
        try {
            Connection conn = Database.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
            Database.closeConnection(conn, true);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }

    }
    public static void clearPersonInfoFromUser(String userName) throws DataAccessException {
        String sql = "DELETE FROM Person WHERE associatedUsername = ?;";
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
