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


public class eventHandler implements HttpHandler
{
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String response;
    Gson gson = new Gson();
    eventService eventService = new eventService();
    eventResult eventResult = new eventResult("Error: Fatal result error.", false);

    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
        if (exchange.getRequestHeaders().containsKey("Authorization")) {
          String authToken = exchange.getRequestHeaders().getFirst("Authorization");
          String uri = exchange.getRequestURI().toString();

          if (uri.equals("/event")) {
            eventResult = eventService.event(authToken);
            if (eventResult == null) {
              response = "{ \"message\": \"" + eventResult.getOutput() + "\"}";
            } else {
              response = gson.toJson(eventResult);
            }
          } else if (uri.substring(0, 7).equals("/event/")) {
            eventResult = eventService.event(uri.substring(7), authToken);
            if (! (eventResult.getOutput() == null)) {
              response = "{ \"message\": \"" + eventResult.getOutput() + "\"}";
            } else {
              response = gson.toJson(eventResult);
            }
          } else {
            response = "Error: Request is invalid.";
          }
          if (eventResult.isStatus()) {
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
      }
    } catch (IOException | DataAccessException e) {
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
}



