package main.java.result;

import model.Person;

import java.util.ArrayList;

/**
 * Contains information about a single person.
 * Returns a single person from the database
 */
public class SinglePersonResult {
    public SinglePersonResult() {
        message = "Error: there was an error.";
        success = false;
    }

    public SinglePersonResult(Person p) {
        personID = p.getPersonID();
        associatedUsername = p.getAssociatedUsername();
        firstName = p.getFirstName();
        lastName = p.getLastName();
        gender = p.getGender();
        fatherID = p.getFatherID();
        motherID = p.getMotherID();
        spouseID = p.getSpouseID();
        success = true;
    }
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    private Person data;

    private String message;

    private Boolean success;

    public String getPersonID() {
        return personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public String getMessage() {
        return message;
    }

    public Person getData() {
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

    public void setData(Person person) {
        data = person;
    }
}
