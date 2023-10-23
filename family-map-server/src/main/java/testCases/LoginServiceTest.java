package main.java.testCases;

import dao.DataAccessException;
import dao.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import result.*;
import service.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    User bestUser;
    @BeforeEach
    public void setUp() throws DataAccessException {
        bestUser = new User("SillyGoose", "password123", "yahoo@gmail.com",
                "Rebecca", "White", "F", "Rebecca_1234");
        UserDAO.clear();
    }
    @Test
    public void loginPass() throws DataAccessException {
        UserDAO.insert(bestUser);
        LoginRequest request = new LoginRequest("SillyGoose", "password123");
        LoginService service = new LoginService();
        LoginResult result = service.login(request);
        assertTrue(result.getSuccess());
    }
    @Test
    public void loginFail() throws DataAccessException {
        UserDAO.insert(bestUser);
        LoginRequest request = new LoginRequest("Username", "Pass");
        LoginService service = new LoginService();
        LoginResult result = service.login(request);
        assertFalse(result.getSuccess());
    }
}
