package main.java.testCases;

import model.*;
import dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.AllEventsResult;
import service.ClearService;
import service.EventService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {

    Event bestEvent;
    User bestUser;
    AuthToken bestAuthToken;
    Person bestPerson;

    @BeforeEach
    public void setUp() throws DataAccessException {
        bestPerson = new Person("Dave_123A", "Dave123A", "Dave",
                "Smith", "M", "John_1234", "Ashley_1234",
                "Frida_123");

        bestEvent = new Event("Birth123", "BobbyJ", "Danny_ABC",
                123.5f, 234.6f, "China", "Beijing", "Birth", 1987);

        bestAuthToken = new AuthToken("RandomToken", "Addison345");

        bestUser = new User("SillyGoose", "password123", "yahoo@gmail.com",
                "Rebecca", "White", "F", "Rebecca_1234");
        EventDAO.clear();
        PersonDAO.clear();
        UserDAO.clear();
        AuthTokenDAO.clear();
    }
    @Test
    public void clearPass() throws SQLException, DataAccessException {
        EventDAO.insert(bestEvent);
        PersonDAO.insert(bestPerson);
        AuthTokenDAO.insert(bestAuthToken);
        UserDAO.insert(bestUser);

        ClearService service = new ClearService();
        service.clear();

        assertNull(EventDAO.find(bestEvent.getEventID()));
        assertNull(PersonDAO.find(bestPerson.getPersonID()));
        assertNull(AuthTokenDAO.find(bestAuthToken.getAuthtoken()));
        assertNull(UserDAO.find(bestUser.getUsername()));
    }
}
