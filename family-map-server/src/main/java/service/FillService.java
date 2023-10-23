package main.java.service;

import org.json.simple.parser.ParseException;
import request.FillRequest;
import result.FillResult;
import dao.*;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Fills the database with the generated data for a specific user
 */
public class FillService {

    /**
     * Performs the request to fill the database and returns if it was successful or not
     * @param user
     * @param generations
     * @return
     */
    public FillResult fill(User user, int generations) {
        FillResult result = new FillResult();
        try {
            //Check that the user actually exists
            if (user == null) {
                result.setSuccess(false);
                result.setMessage("Error: User doesn't exist");
            } else {
                PersonDAO.clearPersonInfoFromUser(user.getUsername());
                EventDAO.clearEventInfoFromUser(user.getUsername());
                //call the generate trees
                GenerateTrees generator = new GenerateTrees();
                Person userPerson = generator.generatePerson(user.getUsername(), user.getPersonID(), user.getGender(), generations, user.getFirstName(), user.getLastName(), 1999);

//                //update userPerson to have the right personID
//                String oldID = userPerson.getPersonID();
//                userPerson.setPersonID(user.getPersonID());
//                PersonDAO.update(oldID, userPerson);

                //set success and message in fillResult
                int x = 1;
                int y = 1;
                for (int i = 0; i < generations; i++) {
                    x = x * 2;
                    y += x;
                }
                int z = (y * 3) - 1;
                String successMessage = "Successfully added " + y + " persons and " + z + " events to the database.";
                result = new FillResult(successMessage);
            }
        } catch (Exception e) {
            result = new FillResult();
            result.setMessage("Error: " + e.getMessage());
        }
        return result;

    }
}
