package Handlers;
import java.io.*;
import java.net.*;
import java.util.*;

import Model.Event;
import Services.*;
import Result.*;
import Handlers.*;
import com.google.gson.*;
import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpHandler;
import Request.*;
import Result.*;
import DAO.*;
import Services.*;
import com.sun.net.httpserver.HttpHandler;
import passoffrequest.LoadRequest;
import java.sql.*;

public class loginHandler implements HttpHandler {
  Gson gson = new Gson();
  loginService service = new loginService();

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    try {

      if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
        String requestBody = StringConversion(exchange.getRequestBody());
        loginRequest loginRequest = gson.fromJson(requestBody, loginRequest.class);
        loginResult loginResult = service.login(loginRequest);
        String response = gson.toJson(loginResult);
        if (loginResult.isStatus()) {
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
    } catch (IOException | DataAccessException | SQLException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
    }
  }

  private void ToString(String in, OutputStream out) throws IOException {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(in);
    s.flush();
  }

  public String StringConversion(InputStream is) {
    Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }
}
