package main.java.testCases;

import dao.DataAccessException;
import dao.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import request.LoginRequest;
import result.*;
import service.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class LoadServiceTest {
    Person person1;
    Person person2;
    Person person3;
    Event event1;
    Event event2;
    Event event3;
    User user1;
    User user2;
    User user3;

    ArrayList<Event> eventArray = new ArrayList<>();
    ArrayList<Person> personArray = new ArrayList<>();
    ArrayList<User> userArray = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        person1 = new Person("Kevin123", "Kevin1234", "Billy",
                "John", "M", "David_1234", "Ashton_1234",
                "Betsy_123");

        person2 = new Person("Dave", "Dave123A", "Dave",
                "Smith", "M", "Martin", "Annabell",
                "Sarah");

        person3 = new Person("123A", "Dave123A", "Dave",
                "Smith", "M", "John_1234", "Ashley_1234",
                "Frida_123");


        event1 = new Event("abc", "Gabby", "Gabby123A",
                74.7f, 162.1f, "China", "Chengdu",
                "Walking_Around", 2004);

        event2 = new Event("asdf", "Gale", "Gale123A",
                74.7f, 162.1f, "China", "Chengdu",
                "Walking_Around", 2004);

        event3 = new Event("bbbb", "Gale", "Gale123A",
                54.6f, 163.7f, "Australia", "Melbourne",
                "Biking", 1997);

        user1 = new User("username123", "password145", "gmail@yahoo.com",
                "Jack", "Black", "M", "Jack1234");
        user2 = new User("Johnny123", "password223", "bing@yahoo.com",
                "John", "Brown", "M", "John43156");
        user3 = new User("Maddy213", "password333", "gmail@bing.com",
                "Madelyn", "Brown", "F", "Maddy1255");
    }
    @Test
    public void loadPass(){
        personArray.add(person1);
        personArray.add(person2);
        personArray.add(person3);

        eventArray.add(event1);
        eventArray.add(event2);
        eventArray.add(event3);

        userArray.add(user1);
        userArray.add(user2);
        userArray.add(user3);

        LoadRequest request = new LoadRequest(userArray, personArray, eventArray);
        LoadService service = new LoadService();
        LoadResult result = service.load(request);
        assertTrue(result.getSuccess());
    }
    @Test
    public void loadFail(){
        LoadRequest request = new LoadRequest(userArray, personArray, eventArray);
        LoadService service = new LoadService();
        LoadResult result = service.load(request);
        assertFalse(result.getSuccess());
    }
}
