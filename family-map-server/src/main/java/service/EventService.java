package main.java.service;

import com.sun.jdi.request.EventRequest;
import dao.*;
import model.*;
import model.Event;
import result.AllEventsResult;
import result.AllPersonsResult;
import result.SingleEventResult;
import result.SinglePersonResult;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Takes information from handlers and uses it to obtain a event or events from the database
 */
public class EventService {

    /**
     * Requests and returns a single event from database
     * @param EventID
     * @return
     */
    //public SingleEventResult getSingleEvent(String authToken, String EventID) - original parameters, changed bc I didn't feel I need the personID
    public SingleEventResult getSingleEvent(String EventID, AuthToken token) throws DataAccessException {
        SingleEventResult result;
        Event event = EventDAO.find(EventID);
        if ( event == null || token == null || !token.getUsername().equals(event.getAssociatedUsername())){
            result = new SingleEventResult();
        } else {
            result = new SingleEventResult(event);
        }
        return result;
    }

    /**
     * Requests and returns all events from the database attached to a specific user
     * @param token
     * @return
     */
    public AllEventsResult getAllEvents(AuthToken token) throws DataAccessException {
        AllEventsResult result;
        if (token == null){
            result = new AllEventsResult();
        } else {
            ArrayList<Event> events = EventDAO.findAllEvents(token.getUsername());
            result = new AllEventsResult(events);
        }
        return result;
    }

}
