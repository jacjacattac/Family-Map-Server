package main.java.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.UserDAO;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import request.*;
import result.*;
import service.*;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                try {
                    FillService service = new FillService();
                    FillResult result;
                    String username;
                    int generations;
                    //Create a service object and result object
                    // and call it with request inside

                    String[] uriStrings = exchange.getRequestURI().toString().split("/");
                    if (uriStrings.length == 3 || uriStrings.length == 4){
                        if(uriStrings[3].equals("")) {
                            generations = 4; //default generations
                        } else {
                            generations = Integer.parseInt(uriStrings[3]);
                        }
                        username = uriStrings[2];
                        User user = UserDAO.find(username);
                        result = service.fill(user, generations);
                        if(result.getSuccess()) {
                            UserDAO.update(user);
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            success = true;
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        OutputStream resBody = exchange.getResponseBody();
                        String json = gson.toJson(result);
                        System.out.println("FILL RESPONSE: " + json);
                        byte[] jsonBytes = json.getBytes();
                        resBody.write(jsonBytes);
                        resBody.close();
                        return;
                    }
                } catch (Exception e){
                    System.out.println("FIRST CATCH");
                    e.printStackTrace();
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
            if (!success){
                System.out.println("SUCCESS IS FALSE");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException e){
            System.out.println("SECOND CATCH");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
