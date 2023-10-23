package main.java.service;

import org.json.simple.parser.ParseException;
import request.LoginRequest;
import request.RegisterRequest;
import result.FillResult;
import result.LoginResult;
import result.RegisterResult;
import dao.*;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Takes information from handlers and uses it to register a new user
 */
public class RegisterService {

    /**
     * Takes the information provided to request to register, returns a result
     * of whether or not the registering was successful
     * @param request
     * @return
     */
    public RegisterResult register(RegisterRequest request) {
        RegisterResult result;
        int defaultGenerations = 4;
        try {
            //Takes the info from the request object to register a new user
            // and generate 4 generations
            User user = new User(request.getUsername(), request.getPassword(), request.getEmail(),
                    request.getFirstName(), request.getLastName(), request.getGender(), request.getFirstName() + UUID.randomUUID().toString());
            //UserDAO to create new User and add it to database

            //Uses generateTree class to generate random people
            FillService fillService = new FillService();
            FillResult fillResult = fillService.fill(user, defaultGenerations);

            if(!fillResult.getSuccess()) {
                result = new RegisterResult(fillResult.getMessage());
                return result;
            }

//            GenerateTrees generate = new GenerateTrees();
//            Person userPerson = generate.generatePerson(user.getUsername(), request.getGender(), 4, request.getFirstName(), request.getLastName(), 1999);

            UserDAO.insert(user);



            //Creates an authtoken for user
            LoginService loginService = new LoginService();
            LoginResult loginResult = loginService.login(new LoginRequest(request.getUsername(), request.getPassword()));
            result =  new RegisterResult(loginResult);
//            UUID token = UUID.randomUUID();
//            AuthToken authToken = new AuthToken((String.valueOf(token)), request.getUsername());
//            AuthTokenDAO.insert(authToken);
//            result.setAuthtoken(authToken.getAuthtoken());
//            result.setSuccess(true);
//            result.setPersonID(userPerson.getPersonID());
//            result.setUsername(userPerson.getAssociatedUsername());
        } catch (Exception e){
            e.printStackTrace();
            result = new RegisterResult(e.getMessage());
        }
        return result;
    }
}

