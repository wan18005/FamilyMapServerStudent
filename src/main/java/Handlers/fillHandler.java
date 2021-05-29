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

public class fillHandler implements HttpHandler
{
  fillService fillService = new fillService();
  Gson gson = new Gson();

  @Override
  public  void handle(HttpExchange httpExchange) throws IOException
  {
    fillResult fillResult;
    String output = "Error: Invalid request.";
    try
    {
      if (httpExchange.getRequestMethod().equalsIgnoreCase("post"))
      {
        String uri = httpExchange.getRequestURI().toString();
        uri = uri.substring(6);

        if (uri.contains("/")) {
          int i = uri.indexOf("/");
          fillResult = fillService.fill(uri.substring(0, i), Integer.parseInt(uri.substring(i + 1)));
          output = gson.toJson(fillResult);
        } else {
          fillResult = fillService.fill(uri, 4);
          output = gson.toJson(fillResult);
        }
        if (fillResult.isStatus()) {
          httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          OutputStream responseBody = httpExchange.getResponseBody();
          ToString(output, responseBody);
          responseBody.close();
        } else {
          httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          OutputStream responseBody = httpExchange.getResponseBody();
          ToString(output, responseBody);
          responseBody.close();
        }
      }
    } catch (IOException | DataAccessException e) {
      e.printStackTrace();
      httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      httpExchange.getResponseBody().close();

    }
  }

  private void ToString(String in, OutputStream out) throws IOException {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(in);
    s.flush();
  }


}
