package main.java.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import result.ClearResult;
import service.ClearService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        Boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                ClearService clear = new ClearService();
                ClearResult result;

                result = clear.clear();
                String json = gson.toJson(result);
                if(result.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    success = true;
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                byte[] jsonBytes = json.getBytes();
                respBody.write(jsonBytes);
                respBody.close();
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
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
