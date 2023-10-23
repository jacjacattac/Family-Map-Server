package main.java.result;

import model.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Contains information about all the events in the database
 * Returns all the events in the database.
 */
public class AllEventsResult {
    /**
     * Default constructor
     */
    public AllEventsResult() {
        message = "Error: there was an error.";
        success = false;
    }

    /**
     * Initializing the result object
     * @param events
     */
    public AllEventsResult(ArrayList<Event> events) {
        data = events;
        success = true;
    }
    private ArrayList<Event> data;
    private String message;
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String m) {
        message = m;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean s) {
        success = s;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> events) {
        data = events;
    }
}