package Handlers;
import com.sun.net.httpserver.HttpHandler;
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


public class registerHandler implements HttpHandler {
  registerService service = new registerService();
  Gson gson = new Gson();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      System.out.println("Attempting to register in Handler.\n");
      if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
        String requestBody = StringConversion(exchange.getRequestBody());
        registerRequest registerRequest = gson.fromJson(requestBody, registerRequest.class);
        registerResult registerResult = service.Register(registerRequest);
        String response = gson.toJson(registerResult);
        if (registerResult.isStatus()) {
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
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
    }
  }

  private void ToString(String sentResponse, OutputStream out) throws IOException {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(sentResponse);
    s.flush();
  }

  public String StringConversion(InputStream is) {
    Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }
}
