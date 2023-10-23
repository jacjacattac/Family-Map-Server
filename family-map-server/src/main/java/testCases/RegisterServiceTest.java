package main.java.testCases;

import result.*;
import dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    RegisterRequest request = new RegisterRequest();
    @BeforeEach
    public void setUp() throws DataAccessException {
        request.setUsername("UsernameTest");
        request.setPassword("Password123");
        request.setGender("Male");
        request.setEmail("IHateThisProject@gmail.com");
        request.setFirstName("Bob");
        request.setLastName("Dylan");
        UserDAO.clear();
        PersonDAO.clear();
    }
    @Test
    public void registerPass () throws DataAccessException {
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);
        assertEquals(true, result.getSuccess());
        assertEquals(request.getUsername(), result.getUsername());
    }
    @Test
    public void registerFail () throws DataAccessException {
        RegisterRequest fakeRequest = new RegisterRequest();
        fakeRequest.setUsername("weofuwhef");
        fakeRequest.setPassword("wvgwegvw");
        fakeRequest.setGender("Male");
        fakeRequest.setEmail("q123r23tr23");
        fakeRequest.setFirstName("Jacob");
        fakeRequest.setLastName("Richards");

        RegisterService service = new RegisterService();
        RegisterResult result = service.register(fakeRequest);
        assertNotEquals(request.getUsername(), result.getUsername());
    }

}
