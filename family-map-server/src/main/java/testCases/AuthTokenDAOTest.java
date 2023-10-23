package main.java.testCases;

import database.Database;
import model.*;
import dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthTokenDAOTest {
    AuthToken bestAuthToken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        bestAuthToken = new AuthToken("RandomToken", "Addison345");

        AuthTokenDAO.clear();
    }

    @Test
    public void insertPass() throws DataAccessException {
        AuthTokenDAO.insert(bestAuthToken);
        AuthToken compareTest = AuthTokenDAO.find(bestAuthToken.getAuthtoken());
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }
    @Test
    public void insertFail() throws DataAccessException {
        AuthTokenDAO.insert(bestAuthToken);
        assertThrows(DataAccessException.class, () -> AuthTokenDAO.insert(bestAuthToken));
    }
    @Test
    public void findPass() throws DataAccessException {
        AuthTokenDAO.insert(bestAuthToken);
        AuthToken compareTest = AuthTokenDAO.find(bestAuthToken.getAuthtoken().toString());
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }
    @Test
    public void findFail() throws DataAccessException {
        // create a new event object and search for that
        AuthTokenDAO.insert(bestAuthToken);
        AuthToken wrongToken = new AuthToken("badtoken", "badusername");

        assertNull(AuthTokenDAO.find(wrongToken.getUsername()));
    }
    @Test
    public void clearTest() throws DataAccessException {
        AuthTokenDAO.insert(bestAuthToken);
        assertNotNull(AuthTokenDAO.find(bestAuthToken.getAuthtoken()));

        AuthTokenDAO.clear();
        assertNull(AuthTokenDAO.find(bestAuthToken.getUsername()));
    }
}
