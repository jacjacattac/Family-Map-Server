package main.java.testCases;

import dao.DataAccessException;
import dao.*;
import database.Database;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.EventService;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDAOTest {
    private Database db;
    private Event bestEvent;
    private EventDAO eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        //db = new Database();
        // and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        // Here, we'll open the connection in preparation for the test case to use it
        //Connection conn = db.openConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        //eDao = new EventDAO();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        EventDAO.clear();
    }

//    @AfterEach
//    public void tearDown() {
//        // Here we close the connection to the database file, so it can be opened again later.
//        // We will set commit to false because we do not want to save the changes to the database
//        // between test cases.
//        //db.closeConnection(false);
//        db.closeConnection(false);
//    }

    @Test
    public void insertPass() throws DataAccessException, SQLException {
        // Start by inserting an event into the database.
        EventDAO.insert(bestEvent);
        // Let's use a find method to get the event that we just put in back out.
        Event compareTest = EventDAO.find(bestEvent.getEventID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException, SQLException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        EventDAO.insert(bestEvent);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> EventDAO.insert(bestEvent));
    }
    @Test
    public void findPass() throws DataAccessException, SQLException {
        EventDAO.insert(bestEvent);
        Event compareTest = EventDAO.find(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException, SQLException {
        // create a new event object and search for that
        EventDAO.insert(bestEvent);
        Event wrongEvent = new Event("bbbb", "Dave", "Dave123A",
                74.7f, 162.1f, "China", "Chengdu",
                "Walking_Around", 2004);

        assertNull(eDao.find(wrongEvent.getEventID()));
    }

    @Test
    public void findAllPass() throws DataAccessException, SQLException {
        //Create two more events and put them and best event into an array list
        ArrayList<Event> eventArray = new ArrayList<>();

        Event event1 = new Event("abc", "Gabby", "Gabby123A",
                74.7f, 162.1f, "China", "Chengdu",
                "Walking_Around", 2004);

        Event event2 = new Event("asdf", "Gale", "Gale123A",
                74.7f, 162.1f, "China", "Chengdu",
                "Walking_Around", 2004);

        Event event3 = new Event("bbbb", "Gale", "Gale123A",
                54.6f, 163.7f, "Australia", "Melbourne",
                "Biking", 1997);

        eventArray.add(bestEvent);
        // Not adding event1, because it is owned by a different user and should NOT be grabbed.
        eventArray.add(event2);
        eventArray.add(event3);
        //Add those events to the database
        EventDAO.insert(bestEvent);
        EventDAO.insert(event1);
        EventDAO.insert(event2);
        EventDAO.insert(event3);
        //Call find all events
        ArrayList<Event> allEvents = EventDAO.findAllEvents("Gale");
        //asssert equals with the array list
        assertEquals(eventArray, allEvents);
    }

    @Test
    public void findAllFail() throws SQLException, DataAccessException {
        ArrayList<Event> eventArray = new ArrayList<>();

        Event event1 = new Event("abc", "Gabby", "Gabby123A",
                74.7f, 162.1f, "China", "Chengdu",
                "Walking_Around", 2004);

        Event event2 = new Event("asdf", "Gale", "Gale123A",
                74.7f, 162.1f, "China", "Chengdu",
                "Walking_Around", 2004);

        Event event3 = new Event("bbbb", "Gale", "Gale123A",
                54.6f, 163.7f, "Australia", "Melbourne",
                "Biking", 1997);
        eventArray.add(bestEvent);
        // Not adding event1, because it is owned by a different user and should NOT be grabbed.
        eventArray.add(event2);
        eventArray.add(event3);

        EventDAO.insert(bestEvent);
        EventDAO.insert(event1);
        EventDAO.insert(event2);
        EventDAO.insert(event3);
        //Call find all events
        ArrayList<Event> allEvents = EventDAO.findAllEvents("FakeUsername");
        //asssert equals with the array list
        assertNotEquals(eventArray, allEvents);
    }

    @Test
    public void clearTest() throws DataAccessException, SQLException {
        EventDAO.insert(bestEvent);
        assertNotNull(EventDAO.find(bestEvent.getEventID()));

        EventDAO.clear();
        assertNull(EventDAO.find(bestEvent.getEventID()));
    }
    @Test
    public void clearFromEventPass() throws SQLException, DataAccessException {
        EventDAO.insert(bestEvent);
        assertNotNull(EventDAO.find(bestEvent.getEventID()));
        EventDAO.clearEventInfoFromUser(bestEvent.getAssociatedUsername());
        assertNull(EventDAO.find(bestEvent.getEventID()));
    }
    @Test
    public void clearFromEventFail() throws SQLException, DataAccessException {
        Event event1 = new Event("abc", "Gabby", "Gabby123A",
                74.7f, 162.1f, "China", "Chengdu",
                "Walking_Around", 2004);
        EventDAO.insert(event1);
        EventDAO.insert(bestEvent);
        EventDAO.clearEventInfoFromUser(event1.getAssociatedUsername());
        assertNotNull(EventDAO.find(bestEvent.getEventID()));
    }

}