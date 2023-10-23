package main.java.service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.UserDAO;
import request.LoginRequest;
import result.LoginResult;
import model.*;
import main.java.testCases.AuthTokenDAOTest;

import java.util.UUID;

/**
 * Performs login request
 */
public class LoginService {
    /**
     * Requests to login and returns whether or not it was successful
     * @param request
     * @return
     */
    public LoginResult login(LoginRequest request) {
        LoginResult result;
        try {
            User user = UserDAO.find(request.getUsername());
            if (user == null) {
                result = new LoginResult();
                result.setMessage("Error: User does not exist.");
            } else if (!request.getPassword().equals(user.getPassword())) {
                result = new LoginResult();
                result.setMessage("Error: Incorrect password.");
            } else {
                AuthToken token = new AuthToken(UUID.randomUUID().toString(), user.getUsername());
                AuthTokenDAO.insert(token);
                result = new LoginResult(token.getAuthtoken(), user.getUsername(), user.getPersonID());
            }
        } catch (Exception e) {
            result = new LoginResult();
        }
        return result;
    }
}
