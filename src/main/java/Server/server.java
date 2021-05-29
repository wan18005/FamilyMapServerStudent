package Server;
import Handlers.*;
import java.io.*;
import java.net.*;
import java.util.*;
import com.sun.net.httpserver.*;

public class server {
  private static final int Maximum_Waiting_Time = 12;
  private HttpServer httpServer;


  public static void main(String[] args)
  {
    String port = "8087";
    new server().run(port);
  }



  private void run(String port)
  {

     System.out.println("Starting the HTTP server");
     System.out.println("Using Port: " + port);

     try
     {
       httpServer = HttpServer.create( new InetSocketAddress(Integer.parseInt(port)),Maximum_Waiting_Time);
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }

    httpServer.setExecutor(null);
     System.out.println("Creating context");

    httpServer.createContext("/", new defaultHandler());
    httpServer.createContext("/user/register", new registerHandler());
    httpServer.createContext("/user/login", new loginHandler());
    httpServer.createContext("/clear", new clearHandler());
    httpServer.createContext("/fill", new fillHandler());
    httpServer.createContext("/load", new loadHandler());
    httpServer.createContext("/person", new personHandler());
    httpServer.createContext("/event", new eventHandler());

    System.out.println("Starting server");
    httpServer.start();
    System.out.println("Server started");
  }


}
