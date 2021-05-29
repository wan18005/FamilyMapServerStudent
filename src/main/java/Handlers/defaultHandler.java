package Handlers;

import com.sun.net.httpserver.*;
import java.io.*;
import java.sql.*;
import java.net.*;
import java.nio.file.*;
import java.io.*;
import java.net.*;
import java.util.*;
import Services.*;
import Result.*;
import Handlers.*;
import com.google.gson.*;
import com.sun.net.httpserver.*;
import DAO.*;

public class defaultHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    OutputStream response;
    Path filePath;
    String path;
    String uri;
    try {
      uri = exchange.getRequestURI().toString();

      if (uri.equals("/")) {
        path = "web/index.html";
      } else {
        path = "web/" + uri;
      }

      File file = new File(path);
      if (file.exists()) {
        System.out.println(file);
      } else {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
        response = exchange.getResponseBody();
        File badPath = new File("web/HTML/404.html");
        Files.copy(badPath.toPath(), response);
        response.close();
      }

      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
      response = exchange.getResponseBody();
      filePath = FileSystems.getDefault().getPath(path);
      Files.copy(filePath, response);
      response.close();

    } catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      response = exchange.getResponseBody();
      response.close();
      e.printStackTrace();
    }
  }
}
