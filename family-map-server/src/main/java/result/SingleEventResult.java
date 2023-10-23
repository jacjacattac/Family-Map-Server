package main.java.result;

import model.Event;

import java.util.ArrayList;

/**
 * Contains information about a single event.
 * Returns a single event from the database
 */
public class SingleEventResult {
    public SingleEventResult() {
        message = "Error: there was an error.";
        success = false;
    }

    public SingleEventResult(Event e) {
        eventID = e.getEventID();
        associatedUsername = e.getAssociatedUsername();
        personID = e.getPersonID();
        latitude = Double.valueOf(e.getLatitude());
        longitude = Double.valueOf(e.getLongitude());
        country = e.getCountry();
        city = e.getCity();
        eventType = e.getEventType();
        year = e.getYear();
        success = true;
    }

    private String eventID;
    private String associatedUsername;
    private String personID;

    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;

    private Event data;

    private String message;

    private Boolean success;

    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public Integer getYear() {
        return year;
    }

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

    public Event getData() {
        return data;
    }

    public void setData(Event events) {
        data = events;
    }
}
