package main.java.service;

import dao.*;
import model.Person;
import result.ClearResult;

/**
 * Deletes all data from the database
 */
public class ClearService {
    /**
     * Result of clearing the database
     * @return
     */
    public ClearResult clear () throws DataAccessException {
        ClearResult result = new ClearResult();
        try {
            EventDAO.clear();
            PersonDAO.clear();
            AuthTokenDAO.clear();
            UserDAO.clear();
            result.setMessage("Clear Succeeded");
            result.setSuccess(true);
        }
        catch(DataAccessException e){
            result.setMessage("Error:" + e);
            result.setSuccess(false);
        }
        return result;
    }
}

