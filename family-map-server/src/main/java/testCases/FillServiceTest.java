package main.java.testCases;

import com.google.gson.Gson;
import dao.DataAccessException;
import dao.PersonDAO;
import model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.AllPersonsResult;
import result.FillResult;
import service.FillService;
import service.PersonService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    Person bestPerson;
    User bestUser;
    AuthToken bestToken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        bestUser = new User("SillyGoose", "password123", "yahoo@gmail.com",
                "Rebecca", "White", "F", "Rebecca_1234");
        bestPerson = new Person("Dave_123A", "Dave123A", "Dave",
                "Smith", "M", "John_1234", "Ashley_1234",
                "Frida_123");
        bestToken = new AuthToken("authtoken", "Dave123A");
        PersonDAO.clear();
    }

    @Test
    public void fillPass() {
        Gson gson = new Gson();
        FillService service = new FillService();
        FillResult fResult = service.fill(bestUser, 2);
        //String fillResult = gson.toJson(fResult);
        //System.out.print(fillResult);
        PersonService personService = new PersonService();
        AllPersonsResult persons = personService.getAllPersons(new AuthToken("let me in", bestUser.getUsername()));
        //String people = gson.toJson(persons);
        //System.out.println(people);
        assertEquals(bestUser.getUsername(), persons.getData().get(0).getAssociatedUsername());
    }
    @Test
    public void fillFail(){
        User user1 = null;
        Gson gson = new Gson();
        FillService service = new FillService();
        FillResult fResult = service.fill(user1, 2);
        String fillResult = gson.toJson(fResult);
        System.out.print(fillResult);
        assertFalse(fResult.getSuccess());
    }
}
