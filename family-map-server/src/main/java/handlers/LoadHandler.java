package main.java.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.RegisterRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import request.*;
import service.LoadService;
import result.*;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        System.out.println("Breadcrumb 1");

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                System.out.println("Breadcrumb 2");
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                try {
                    LoadRequest request = gson.fromJson(reqData, LoadRequest.class);

                    LoadService service = new LoadService();
                    LoadResult result = service.load(request);

                    System.out.println("Breadcrumb 3");

                    if(result.getSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    OutputStream resBody = exchange.getResponseBody();
                    String json = gson.toJson(result);
                    byte[] jsonBytes = json.getBytes();
                    resBody.write(jsonBytes);
                    resBody.close();
                    return;
                } catch (Exception e){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                    e.printStackTrace();
                }

            }
            if (!success){
                System.out.println("FAIL");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch(IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
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
