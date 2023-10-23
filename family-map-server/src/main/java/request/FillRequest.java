package main.java.request;

import java.util.ArrayList;
import java.util.Random;

/**
 * Submits request to fill information into database
 */
public class FillRequest {
    private int DEFAULT_GENERATIONS = 4;

    private ArrayList<String> femaleNames;

    private ArrayList<String> maleNames;

    private ArrayList<String> lastNames;

    private ArrayList<String> locations;

    private int CURRENT_YEAR = 2020;

    private int personsAdded = 0;

    private int eventsAdded = 0;

    private int totalGenerations = 0;

    private Random rand = new Random();

    public int getPersonsAdded() {
        return personsAdded;
    }

    public void setPersonsAdded(int num) {
        personsAdded = num;
    }

    public int getEventsAdded() {
        return eventsAdded;
    }

    public void setEventsAdded(int num) {
        eventsAdded = num;
    }
}