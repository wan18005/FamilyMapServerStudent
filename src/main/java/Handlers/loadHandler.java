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

public class loadHandler implements HttpHandler
{
  loadService loadService = new loadService();
  Gson gson = new Gson();


  public String StringConversion(InputStream is)
  {
    Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }

  private void ToString(String in, OutputStream out) throws IOException
  {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(in);
    s.flush();
  }

  @Override
  public  void handle(HttpExchange httpExchange) throws IOException
  {

    try
    {
      if (httpExchange.getRequestMethod().equalsIgnoreCase("post"))
      {
        String requestBody = StringConversion(httpExchange.getRequestBody());
        loadRequest loadRequest = gson.fromJson(requestBody, Request.loadRequest.class);
        loadResult loadResult = loadService.Load(loadRequest);
        String response = gson.toJson(loadResult);

        if (loadResult.isStatus()) {
          httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          OutputStream responseBody = httpExchange.getResponseBody();
          ToString(response, responseBody);
          responseBody.close();
        } else {
          httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          OutputStream responseBody = httpExchange.getResponseBody();
          ToString(response, responseBody);
          responseBody.close();
        }
      }
    } catch (DataAccessException | IOException inputException) {
      httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      httpExchange.getResponseBody().close();
      inputException.printStackTrace();
    }
  }


}
