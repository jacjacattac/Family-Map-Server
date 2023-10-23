package main.java.testCases;

import database.Database;
import model.Event;
import model.*;
import dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonDAOTest {
    //private Database db;
    private Person bestPerson;
    //private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        //db = new Database();
        // and a new event with random data
        bestPerson = new Person("Dave_123A", "Dave123A", "Dave",
                "Smith", "M", "John_1234", "Ashley_1234",
                "Frida_123");

        // Here, we'll open the connection in preparation for the test case to use it
//        Connection conn = db.getConnection();
//        //Then we pass that connection to the EventDAO, so it can access the database.
//        pDao = new PersonDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        PersonDAO.clear();
    }

//    @AfterEach
//    public void tearDown() {
//        // Here we close the connection to the database file, so it can be opened again later.
//        // We will set commit to false because we do not want to save the changes to the database
//        // between test cases.
//        //db.closeConnection(false);
//    }

    @Test
    public void insertPass() throws DataAccessException, SQLException {
        // Start by inserting an event into the database.
        PersonDAO.insert(bestPerson);
        // Let's use a find method to get the event that we just put in back out.
        Person compareTest = PersonDAO.find(bestPerson.getPersonID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException, SQLException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        PersonDAO.insert(bestPerson);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> PersonDAO.insert(bestPerson));
    }
    @Test
    public void findPass() throws DataAccessException, SQLException {
        PersonDAO.insert(bestPerson);
        Person compareTest = PersonDAO.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException, SQLException {
        // create a new event object and search for that
        PersonDAO.insert(bestPerson);
        Person wrongPerson = new Person("Calum_123", "Calum123", "Calum",
                "Harris", "M", "Bob_123", "Carol_123",
                "Melanie_123");

        assertNull(PersonDAO.find(wrongPerson.getPersonID()));
    }

    @Test
    public void findAllPass() throws DataAccessException, SQLException {
        //Create two more events and put them and best event into an array list
        ArrayList<Person> personArray = new ArrayList<>();

        Person person1 = new Person("Kevin123", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");

        Person person2 = new Person("Dave", "Dave123A", "Dave",
                "Smith", "M", "Martin", "Annabell",
                "Sarah");

        Person person3 = new Person("123A", "Dave123A", "Dave",
                "Smith", "M", "John_1234", "Ashley_1234",
                "Frida_123");

        personArray.add(bestPerson);
        // Not adding person1, because it is owned by a different user and should NOT be grabbed.
        personArray.add(person2);
        personArray.add(person3);
        //Add those events to the database
        PersonDAO.insert(bestPerson);
        PersonDAO.insert(person1);
        PersonDAO.insert(person2);
        PersonDAO.insert(person3);
        //Call find all events
        ArrayList<Person> allPersons = PersonDAO.findAllPersons("Dave123A");
        //asssert equals with the array list
        assertEquals(personArray, allPersons);
    }

    @Test
    public void findAllFail() throws SQLException, DataAccessException {
        ArrayList<Person> personArray = new ArrayList<>();

        Person person1 = new Person("Kevin123", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");

        Person person2 = new Person("Dave", "Dave123A", "Dave",
                "Smith", "M", "Martin", "Annabell",
                "Sarah");

        Person person3 = new Person("123A", "Dave123A", "Dave",
                "Smith", "M", "John_1234", "Ashley_1234",
                "Frida_123");

        personArray.add(bestPerson);
        // Not adding person1, because it is owned by a different user and should NOT be grabbed.
        personArray.add(person2);
        personArray.add(person3);
        //Add those events to the database
        PersonDAO.insert(bestPerson);
        PersonDAO.insert(person1);
        PersonDAO.insert(person2);
        PersonDAO.insert(person3);
        //Call find all events
        ArrayList<Person> allPersons = PersonDAO.findAllPersons("FakeUsername");
        //asssert equals with the array list
        assertNotEquals(personArray, allPersons);
    }

    @Test
    public void clearTest() throws DataAccessException, SQLException {
        PersonDAO.insert(bestPerson);
        assertNotNull(PersonDAO.find(bestPerson.getPersonID()));

        PersonDAO.clear();
        assertNull(PersonDAO.find(bestPerson.getPersonID()));
    }
    @Test
    public void clearFromPersonPass() throws SQLException, DataAccessException {
        PersonDAO.insert(bestPerson);
        assertNotNull(PersonDAO.find(bestPerson.getPersonID()));
        PersonDAO.clearPersonInfoFromUser(bestPerson.getAssociatedUsername());
        assertNull(PersonDAO.find(bestPerson.getPersonID()));
    }
    @Test
    public void clearFromPersonFail() throws SQLException, DataAccessException {
        Person person1 = new Person("Kevin123", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");
        PersonDAO.insert(bestPerson);
        PersonDAO.clearPersonInfoFromUser(person1.getAssociatedUsername());
        assertNotNull(PersonDAO.find(bestPerson.getPersonID()));
    }
    @Test
    public void updatePass() throws DataAccessException, SQLException {
        Person person1 = new Person("Kevin123", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");
        Person updatedPerson = new Person("Kevin321", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");

        PersonDAO.insert(person1);
        assertEquals(person1, PersonDAO.find(person1.getPersonID()));

        PersonDAO.update(person1.getPersonID(), updatedPerson);
        assertEquals(updatedPerson, PersonDAO.find(updatedPerson.getPersonID()));

        assertNull(PersonDAO.find(person1.getPersonID()));
    }
    @Test
    public void updateFail() throws SQLException, DataAccessException {
        //insert best event
        Person person1 = new Person("Kevin123", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");
        Person updatedPerson = new Person("Kevin321", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");
        PersonDAO.insert(bestPerson);
        PersonDAO.insert(person1);
        PersonDAO.update(person1.getPersonID(), updatedPerson);
        assertNotEquals(bestPerson, updatedPerson);
    }
}
