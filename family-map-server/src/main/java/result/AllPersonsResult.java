package main.java.result;

import model.Person;

/**
 * Contains information about all the persons in the database
 * Returns all the persons in the database.
 */
import java.util.ArrayList;

public class AllPersonsResult {
    /**
     * Default constructor
     */
    public AllPersonsResult() {
        message = "Error: there was an error.";
        success = false;
    }
    /**
     * Initializing the result object
     * @param people
     */
    public AllPersonsResult(ArrayList<Person> people) {
        data = people;
        success = true;
    }


    private ArrayList<Person> data;

    private String message;

    private Boolean success;

    public String getMessage() {
        return message;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setMessage(String m) {
        message = m;
    }

    public void setSuccess(Boolean s) {
        success = s;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setData(ArrayList<Person> people) {
        data = people;
    }
}
