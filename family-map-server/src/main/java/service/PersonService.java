package main.java.service;

import dao.DataAccessException;
import dao.*;
import model.AuthToken;
import model.Person;
import result.*;

import java.util.ArrayList;

/**
 * Takes information from handlers and uses it to obtain a person or persons from the database
 */
public class PersonService {

//    /**
//     * Determines whether or not a single person is requested or all persons
//     * @param authToken
//     * @param parsedArray
//     */
//    public void PersonResult(String authToken, String parsedArray []){
//
//    }

    /**
     * Requests and returns a single person from the database
     * @param personID
     * @return
     */
    public SinglePersonResult getSinglePerson(String personID, AuthToken token) throws DataAccessException {
        SinglePersonResult result;
        Person person = PersonDAO.find(personID);
        if (person == null || token == null || !token.getUsername().equals(person.getAssociatedUsername())) {
            result = new SinglePersonResult();
        } else {
            result = new SinglePersonResult(person);
        }
        return result;

    }

    /**
     * Requests and returns all events from the database
     * @param token
     *
     * @return
     */
    public AllPersonsResult getAllPersons(AuthToken token) {
        AllPersonsResult result;
//        AuthToken at = AuthTokenDAO.find(authToken);
//        User user = UserDAO.find(at.getUsername());
        try {
            if (token == null) {
                result = new AllPersonsResult();
            } else {
                ArrayList<Person> people = PersonDAO.findAllPersons(token.getUsername());
                result = new AllPersonsResult(people);
            }
        } catch (Exception e) {
            result = new AllPersonsResult();
        }
        return result;
    }
}
