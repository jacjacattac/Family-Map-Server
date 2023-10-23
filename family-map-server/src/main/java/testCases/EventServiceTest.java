package main.java.testCases;

import model.*;
import dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.AllEventsResult;
import result.AllPersonsResult;
import result.SingleEventResult;
import result.SinglePersonResult;
import service.EventService;
import service.PersonService;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
    Event bestEvent;
    AuthToken bestToken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        //clear the database and initialize any objects I might need

        bestEvent = new Event("Birth123", "BobbyJ", "Danny_ABC",
                123.5f, 234.6f, "China", "Beijing", "Birth", 1987);
        bestToken = new AuthToken("authtoken", "BobbyJ");

        EventDAO.clear();
    }
    @Test
    public void singeEventPass() throws SQLException, DataAccessException {
        EventDAO.insert(bestEvent);
        EventService service = new EventService();
        SingleEventResult result = service.getSingleEvent(bestEvent.getEventID(), bestToken);
        assertEquals(bestEvent.getEventID(), result.getEventID());
    }
    @Test
    public void singleEventFail() throws SQLException, DataAccessException {
        //create alternate event
        EventDAO.insert(bestEvent);
        Event wrongEvent = new Event("Death", "BobbyJ", "Robert_123",
                142.5f, 827.6f, "Egypt", "Cairo", "Death", 2021);
        EventService service = new EventService();
        Event event = service.getSingleEvent(wrongEvent.getEventID(), bestToken).getData();
        assertNotEquals(bestEvent, event);
    }
    @Test
    public void allEventsPass() throws SQLException, DataAccessException {
        Event event1 = new Event("Eating123", "BobbyJ", "Robert_123",
                37.5f, 92.6f, "France", "Paris", "Eating", 2020);
        Event event2 = new Event("Smiling", "BobbyJ", "Robert_123",
                142.5f, 827.6f, "Egypt", "Cairo", "Death", 1284);
        Event event3 = new Event("Death", "Carol234", "CarolAB",
                34.5f, 85.6f, "Taiwan", "Taipei", "Birth", 1987);
        ArrayList<Event> allEvents = new ArrayList<>();
        allEvents.add(bestEvent);
        allEvents.add(event1);
        allEvents.add(event2);

        EventDAO.insert(bestEvent);
        EventDAO.insert(event1);
        EventDAO.insert(event2);
        EventDAO.insert(event3);

        EventService service = new EventService();
        AllEventsResult result = service.getAllEvents(bestToken);
        ArrayList<Event> eventArray =  result.getData();
        assertEquals(allEvents, eventArray);
    }
    @Test
    public void allEventsFail() throws SQLException, DataAccessException {
        Event event1 = new Event("Eating123", "BobbyJ", "Robert_123",
                37.5f, 92.6f, "France", "Paris", "Eating", 2020);
        Event event2 = new Event("Smiling", "BobbyJ", "Robert_123",
                142.5f, 827.6f, "Egypt", "Cairo", "Death", 1284);
        Event event3 = new Event("Death", "Carol234", "CarolAB",
                34.5f, 85.6f, "Taiwan", "Taipei", "Birth", 1987);
        ArrayList<Event> allEvents = new ArrayList<>();
        allEvents.add(bestEvent);
        allEvents.add(event1);
        allEvents.add(event2);

        EventDAO.insert(bestEvent);
        EventDAO.insert(event1);
        EventDAO.insert(event2);
        EventDAO.insert(event3);

        EventService service = new EventService();
        AllEventsResult result = service.getAllEvents(new AuthToken(":p", "faker"));
        ArrayList<Event> eventArray =  result.getData();
        assertNotEquals(allEvents, eventArray);
    }
}
