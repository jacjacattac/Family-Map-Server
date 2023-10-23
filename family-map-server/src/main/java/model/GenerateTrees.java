package main.java.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;
import java.util.Iterator;

import dao.DataAccessException;
import dao.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.math.BigDecimal;
import java.sql.*;


import javax.swing.text.html.parser.Parser;

public class GenerateTrees {
    JSONArray locations = null;
    ArrayList<String> fNames = new ArrayList<>();
    ArrayList<String> mNames = new ArrayList<>();
    ArrayList<String> sNames = new ArrayList<>();
    Random random = new Random();
    float latitude;
    float longitude;
    String country;
    String city;
    String eventID;

    int currentYear = 2022;
    int marriageAge = 20;
    int generationGap = 25;
    int lifeSpan = 80;

    //DOCUMENTATION NOTE: firstname and lastname can be null. if null, they will be randomly generated.
    public Person generatePerson(String username, String personID, String gender, int generations, String firstname, String lastname, int birthYear) throws IOException, ParseException, SQLException, DataAccessException {
        Person mother = null;
        Person father = null;
        int deathYear;

        if(locations == null) {
            //call the function to read json.
            fNames = readJSONFiles("fnames.json");
            locations = readLocationFiles("locations.json");
            mNames = readJSONFiles("mnames.json");
            sNames = readJSONFiles("snames.json");
        }

        if (firstname == null) {

            if (gender.toLowerCase().equals("m") || gender.toLowerCase().equals("male")){
                int randomInt = random.nextInt(mNames.size());
                firstname = mNames.get(randomInt);
            } else {
                int randomInt = random.nextInt(fNames.size());
                firstname = fNames.get(randomInt); //generate random firstname
            }
        }
        if (lastname == null) {
            int randomInt = random.nextInt(sNames.size());
            lastname = sNames.get(randomInt); //generate random lastname
        }

        if (generations > 0){
            mother = generatePerson(username, null,"FEMALE", generations - 1, null, null, birthYear - generationGap);
            father = generatePerson(username, null,"MALE", generations - 1, null, lastname, birthYear - generationGap);

            //Set mother's and father's spouse IDs (personID of the other spouse)
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            PersonDAO.update(mother.getPersonID(), mother);
            PersonDAO.update(father.getPersonID(), father);

            //Add marriage events to mother and father
            int marriageYear = birthYear - generationGap + marriageAge;
            setLocation();
            eventID = "Marriage" + UUID.randomUUID();
            Event fatherMarriage = new Event (eventID, father.getAssociatedUsername(), father.getPersonID(),
                    latitude, longitude, country, city, "Marriage", marriageYear);
            EventDAO.insert(fatherMarriage);

            eventID = "Marriage" + UUID.randomUUID();
            Event motherMarriage = new Event (eventID, father.getAssociatedUsername(), mother.getPersonID(),
                    latitude, longitude, country, city, "Marriage", marriageYear);
            EventDAO.insert(motherMarriage);
        }
        if (personID == null) {
            personID = firstname + UUID.randomUUID();
        }
        String motherID = null;
        String fatherID = null;
        if (mother != null) {
            motherID = mother.getPersonID();
            fatherID = father.getPersonID();
        }
        Person person = new Person(personID, username, firstname, lastname,
                gender, fatherID, motherID, null);
        PersonDAO.insert(person);

        //Birth Event
        setLocation();
        eventID = "Birth" + (UUID.randomUUID());
        Event birth = new Event(eventID, username, personID, latitude, longitude, country, city, "Birth", birthYear);
        EventDAO.insert(birth);
        //Death Event
        deathYear = birthYear + lifeSpan;
        setLocation();
        eventID = "Death" + (UUID.randomUUID());
        Event death = new Event(eventID, username, personID, latitude, longitude, country, city, "Death", deathYear);
        EventDAO.insert(death);

        return person;
    }

    public ArrayList<String> readJSONFiles (String fileName) throws IOException, ParseException {
        ArrayList<String> JSONData = null;
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("json/" + fileName));
        JSONObject jsonObj = (JSONObject) obj;
        JSONData = (ArrayList<String>) jsonObj.get("data");
        return JSONData;
    }
    private JSONArray readLocationFiles (String fileName) throws IOException, ParseException {
        JSONArray JSONData;
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("json/" + fileName));
        JSONObject jsonObj = (JSONObject) obj;
        JSONData = (JSONArray) jsonObj.get("data");
        //}
        return JSONData;
    }
    private void setLocation () {
        int randomInt = random.nextInt(locations.size());
        JSONObject obj = (JSONObject) locations.get(randomInt);
        //System.out.println(obj);
        city = obj.get("city").toString();
        //System.out.println(city);
        country = obj.get("country").toString();
        //System.out.println(country);
        //System.out.println(obj.get("latitude"));
        latitude = Float.parseFloat(obj.get("latitude").toString());
        longitude = Float.parseFloat(obj.get("longitude").toString());
        //System.out.println(latitude);
        //longitude = obj.get("Longitude");
        //System.out.println(longitude);
        //Float.valueOf(jsonObject.getString("KEY_STRING"));
    }
}
