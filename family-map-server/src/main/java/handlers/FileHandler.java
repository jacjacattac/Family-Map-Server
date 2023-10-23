package main.java.handlers;

import java.io.*;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import com.sun.net.httpserver.*;

public class FileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String filepath = exchange.getRequestURI().toString();
        System.out.println(filepath);
        if(filepath.equals("/")) { // http://server.com:8080/uri-i-just-made-up
            filepath = "web/index.html";
        }
        else {
            filepath = "web"+filepath; // web/uri-i-just-made-up
        }
        byte[] fileData;
        try {
            fileData = FileUtils.readFileToByteArray(new File(filepath));

            exchange.sendResponseHeaders(200, 0);
            OutputStream respBody = exchange.getResponseBody();
            respBody.write(fileData);
            respBody.close();
        } catch(Exception e) {
            //Assume they messed up and deserve a 404 page.
            fileData = FileUtils.readFileToByteArray(new File("web/HTML/404.html"));

            exchange.sendResponseHeaders(404, 0);
            OutputStream respBody = exchange.getResponseBody();
            respBody.write(fileData);
            respBody.close();
        }
//        exchange.sendResponseHeaders(200, 0);
//        OutputStream respBody = exchange.getResponseBody();
//        respBody.write(fileData);
//        respBody.close();
    }

}
