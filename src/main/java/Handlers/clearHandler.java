package Handlers;

import java.io.*;
import java.net.*;
import java.util.*;
import Services.clearService;
import Result.clearResult;
import com.sun.net.httpserver.*;
import DAO.*;

public class clearHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
        clearService clearService = new clearService();
        clearResult clearResult = clearService.clearDatabase();

        String response = "{ \"message\": \"" + clearResult.getOutput() + "\"}";
        System.out.println("\"" + response + "\" \n");

        if (clearResult.getStatus()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          OutputStream responseBody = exchange.getResponseBody();
          ToString(response, responseBody);
          responseBody.close();
        } else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          OutputStream responseBody = exchange.getResponseBody();
          ToString(response, responseBody);
          responseBody.close();
        }
      }
    } catch (IOException | DataAccessException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }

  private void ToString(String in, OutputStream out) throws IOException {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(in);
    s.flush();
  }

}
