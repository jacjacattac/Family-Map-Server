package main.java.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.*;
import model.*;
import result.AllPersonsResult;
import result.SinglePersonResult;
import service.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final Gson gson = new Gson();
        boolean success = false;
//        -Check if its a GET request
//        -Create Header object and get request header
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                //Check if header has authorization key
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    //get authtoken from the user
                    String at = reqHeaders.getFirst("Authorization");
                    //Use split to change URI into strings
                    String[] uriStrings = exchange.getRequestURI().toString().split("/");
                    //Check if URI has 2 or 3 strings
                    if (uriStrings.length == 2 || (uriStrings.length == 3 && uriStrings[2].equals(""))) {
                        //if 2 strings or the third string is “”:
                        //get all events using service class
                        PersonService persons = new PersonService();
                        //Change events into JSON
                        AuthToken token = AuthTokenDAO.find(at);
                        AllPersonsResult result = persons.getAllPersons(token);
                        String json = gson.toJson(result);
                        //send response header
                        if(result.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            success = true;
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        //create outputstream response body
                        OutputStream respBody = exchange.getResponseBody();
                        //change JSON into byte array
                        byte[] jsonBytes = json.getBytes();
                        //Write bytes to response body
                        respBody.write(jsonBytes);
                        //close response body
                        respBody.close();
                        success = true;

                    } else if (uriStrings.length == 3) {
                        //get single event using service class
                        PersonService person = new PersonService();
                        //Change event into JSON
                        String personID = uriStrings[2];
                        System.out.println("Auth Token: " + at);
                        AuthToken token = AuthTokenDAO.find(at);
                        SinglePersonResult result = person.getSinglePerson(personID, token);
                        String json = gson.toJson(result);
                        //send response header
                        if(result.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            success = true;
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        //create outputstream response body
                        OutputStream respBody = exchange.getResponseBody();
                        //change JSON to byte array
                        byte[] jsonBytes = json.getBytes();
                        //Write bytes to response body
                        respBody.write(jsonBytes);
                        //close response body
                        respBody.close();
                    }
                }
            }
            if (!success){
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                // Since the client request was invalid, they will not receive the
                // list of games, so we close the response body output stream,
                // indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        }
        catch(IOException e){
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        } catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            throw new RuntimeException(e);
        }
    }
}
