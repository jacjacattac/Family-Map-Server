package main.java.testCases;

import database.Database;
import model.Event;
import model.Person;
import model.User;
import dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserDAOTest {
    private Database db;
    private User bestUser;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        //db = new Database();
        // and a new event with random data
        bestUser = new User("SillyGoose", "password123", "yahoo@gmail.com",
                "Rebecca", "White", "F", "Rebecca_1234");

        // Here, we'll open the connection in preparation for the test case to use it
        //Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        //uDao = new UserDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        UserDAO.clear();
    }

 //   @AfterEach
//    public void tearDown() {
//        // Here we close the connection to the database file, so it can be opened again later.
//        // We will set commit to false because we do not want to save the changes to the database
//        // between test cases.
//        db.closeConnection(false);
//    }

    @Test
    public void insertPass() throws DataAccessException {
        // Start by inserting an event into the database.
        UserDAO.insert(bestUser);
        // Let's use a find method to get the event that we just put in back out.
        User compareTest = UserDAO.find(bestUser.getUsername());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        UserDAO.insert(bestUser);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> UserDAO.insert(bestUser));
    }
    @Test
    public void findPass() throws DataAccessException {
        UserDAO.insert(bestUser);
        User compareTest = UserDAO.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        // create a new event object and search for that
        UserDAO.insert(bestUser);
        User wrongUser = new User("username123", "password145", "gmail@yahoo.com",
                "Jack", "Black", "M", "Jack1234");

        assertNull(UserDAO.find(wrongUser.getUsername()));
    }

    @Test
    public void updatePass() throws DataAccessException, SQLException {
        User original = new User("username123", "password145", "gmail@yahoo.com",
                "Jack", "Black", "M", "Jack1234");
        User updated = new User("username123", "password145", "gmail@yahoo.com",
                "Jack", "Black", "M", "Jack4321");

        UserDAO.insert(original);
        assertEquals(original.getPersonID(), UserDAO.find(original.getUsername()).getPersonID());

        UserDAO.update(updated);
        assertEquals(updated.getPersonID(), UserDAO.find(original.getUsername()).getPersonID());

        changeElsewhere(updated);
        assertEquals("Jack5555", updated.getPersonID());
    }

    public void changeElsewhere(User user) {
        user.setPersonID("Jack5555");
    }

    @Test
    public void clearTest() throws DataAccessException {
        UserDAO.insert(bestUser);
        assertNotNull(UserDAO.find(bestUser.getUsername()));

        UserDAO.clear();
        assertNull(UserDAO.find(bestUser.getUsername()));
    }
    @Test
    public void updateFail() throws DataAccessException {
        User original = new User("Different", "password145", "gmail@yahoo.com",
                "Jack", "Black", "M", "Jack1234");
        User updated = new User("username123", "password145", "gmail@yahoo.com",
                "Jack", "Black", "M", "Jack4321");

        UserDAO.insert(original);
        assertEquals(original.getPersonID(), UserDAO.find(original.getUsername()).getPersonID());

        UserDAO.update(updated);
        assertNotEquals(updated.getPersonID(), UserDAO.find(original.getUsername()).getPersonID());
    }
}