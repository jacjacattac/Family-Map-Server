package main.java.request;

import model.Event;
import model.Person;
import model.User;

import java.util.ArrayList;

/**
 * Submits request to load information into database
 */
public class LoadRequest {
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Default constructor
     */
    public LoadRequest() {}

    /**
     * Initializing information of request object
     * @param u
     * @param p
     * @param e
     */
    public LoadRequest(ArrayList<User> u, ArrayList<Person> p, ArrayList<Event> e) {
        users = u;
        persons = p;
        events = e;
    }
}