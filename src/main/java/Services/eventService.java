package Services;
import java.sql.*;
import java.util.*;

import DAO.*;
import Model.*;
import Request.*;
import Result.*;
import passoffresult.EventResult;

public class eventService {
    private DAO dao;
    private Connection connection;
    private AuthTokenDAO authTokenDAO;
    private EventDAO eventDAO;

  public eventResult event(String authtoken) throws DataAccessException {
    dao = new DAO();
    connection = dao.getConnection();
    authTokenDAO = new AuthTokenDAO(connection);
    eventDAO = new EventDAO(connection);

    try
    {
      AuthToken token = authTokenDAO.find(authtoken);
      if (token == null)
      {
        dao.closeConnection(false);
        return new eventResult("Error: authtoken return null",false);
      }
      else
      {
        ArrayList<Event> event = eventDAO.FindAll(token.getUserName());
        if (event == null)
        {
          dao.closeConnection(false);
          return new eventResult("Error: Person is not associated with " + token.getUserName() + ".", false);
        }
        else
        {
          dao.closeConnection(true);
          return new eventResult(event);
        }
      }
    }
    catch (DataAccessException e)
    {
      e.printStackTrace();
      dao.closeConnection(false);
      return new eventResult("Error: Database fatal error.", false);
    }

  }


  public eventResult event(String eventID,String authtoken) throws DataAccessException
  {
    DAO dao = new DAO();
    Connection connection = dao.getConnection();
    AuthTokenDAO authTokenDAO = new AuthTokenDAO(connection);
    EventDAO eventDAO = new EventDAO(connection);

    try
    {
      AuthToken token = authTokenDAO.find(authtoken);
      if (token == null)
      {
        dao.closeConnection(false);
        return new eventResult("Error: authtoken return null",false);
      }
      else
      {
        Event event =eventDAO.find(eventID);
        if (event == null)
        {
          dao.closeConnection(false);
          return new eventResult("Error: Event does not exist.", false);
        }
        else if (! token.getUserName().equals(event.getAssociatedUserName()))
        {
          dao.closeConnection(false);
          return new eventResult("Error: Event is not associated with " + token.getUserName() + ".", false);
        }
        else
        {
          dao.closeConnection(true);
          return new eventResult(event,token.getUserName());
        }
      }
    }
    catch (DataAccessException e)
    {
      e.printStackTrace();
      dao.closeConnection(false);
      return new eventResult("Error: Database fatal error.", false);
    }
  }
}
