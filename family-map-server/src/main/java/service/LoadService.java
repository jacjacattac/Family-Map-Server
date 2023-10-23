package main.java.service;

import dao.*;
import model.*;
import request.LoadRequest;
import result.LoadResult;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Loading the current user into the database
 */
public class LoadService {
    /**
     * Clears all data from the database and then loads the
     *user, person, and event data into the database.
     */

    public LoadResult load(LoadRequest request){
        LoadResult result = new LoadResult();

        try {
            if (request.getEvents().size() == 0 || request.getPersons().size() == 0 || request.getUsers().size() == 0){
                result.setMessage("Error: " + "No data to load");
                result.setSuccess(false);
            } else {
                EventDAO.clear();
                PersonDAO.clear();
                AuthTokenDAO.clear();
                UserDAO.clear();
                //make three for loops inputting the arrays into the database
                ArrayList<Event> events = request.getEvents();
                for (int i = 0; i < events.size(); i++) {
                    EventDAO.insert(events.get(i));
                }
                ArrayList<Person> persons = request.getPersons();
                for (int i = 0; i < persons.size(); i++) {
                    PersonDAO.insert(persons.get(i));
                }
                ArrayList<User> users = request.getUsers();
                for (int i = 0; i < users.size(); i++) {
                    UserDAO.insert(users.get(i));
                }
                //set the result success and message
                result.setMessage("Successfully added " + users.size() + " users, " + persons.size() + " persons, and "
                        + events.size() + " events to the database");
                result.setSuccess(true);
            }
        } catch(Exception e){
            result.setMessage("Error: " + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}