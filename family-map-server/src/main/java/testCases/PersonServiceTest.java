package main.java.testCases;

import dao.DataAccessException;
import dao.PersonDAO;
import model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.AllPersonsResult;
import result.SinglePersonResult;
import service.PersonService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    Person bestPerson;
    AuthToken bestToken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        bestPerson = new Person("Dave_123A", "Dave123A", "Dave",
                "Smith", "M", "John_1234", "Ashley_1234",
                "Frida_123");
        bestToken = new AuthToken("authtoken", "Dave123A");
        PersonDAO.clear();
    }
    @Test
    public void singePersonPass() throws SQLException, DataAccessException {
        PersonDAO.insert(bestPerson);
        PersonService service = new PersonService();
        SinglePersonResult result = service.getSinglePerson(bestPerson.getPersonID(), bestToken);
        assertEquals(bestPerson.getPersonID(), result.getPersonID());
    }
    @Test
    public void singlePersonFail() throws SQLException, DataAccessException {
        PersonDAO.insert(bestPerson);
        Person wrongPerson = new Person("Calum_123", "Calum123", "Calum",
                "Harris", "M", "Bob_123", "Carol_123",
                "Melanie_123");
        PersonService service = new PersonService();
        Person person = service.getSinglePerson(wrongPerson.getPersonID(), bestToken).getData();
        assertNotEquals(bestPerson, person);
    }
    @Test
    public void allPersonsPass() throws SQLException, DataAccessException {
        Person person1 = new Person("Kevin123", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");

        Person person2 = new Person("Dave", "Dave123A", "Dave",
                "Smith", "M", "Martin", "Annabell",
                "Sarah");

        Person person3 = new Person("123A", "Dave123A", "Dave",
                "Smith", "M", "John_1234", "Ashley_1234",
                "Frida_123");
        ArrayList<Person> personArray = new ArrayList<>();
        personArray.add(bestPerson);
        personArray.add(person2);
        personArray.add(person3);

        PersonDAO.insert(bestPerson);
        PersonDAO.insert(person1);
        PersonDAO.insert(person2);
        PersonDAO.insert(person3);

        PersonService service = new PersonService();
        AllPersonsResult result = service.getAllPersons(bestToken);
        ArrayList<Person> allPersons = result.getData();
        assertEquals(personArray, allPersons);
    }
    @Test
    public void allPersonsFail() throws SQLException, DataAccessException {
        Person person1 = new Person("Kevin123", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");

        Person person2 = new Person("Dave", "Dave123A", "Dave",
                "Smith", "M", "Martin", "Annabell",
                "Sarah");

        Person person3 = new Person("123A", "Dave123A", "Dave",
                "Smith", "M", "John_1234", "Ashley_1234",
                "Frida_123");
        ArrayList<Person> personArray = new ArrayList<>();
        personArray.add(bestPerson);
        personArray.add(person2);
        personArray.add(person3);

        PersonDAO.insert(bestPerson);
        PersonDAO.insert(person1);
        PersonDAO.insert(person2);
        PersonDAO.insert(person3);

        PersonService service = new PersonService();
        AllPersonsResult result = service.getAllPersons(new AuthToken(":p", "faker"));
        ArrayList<Person> allPersons = result.getData();
        assertNotEquals(personArray, allPersons);
    }
}
