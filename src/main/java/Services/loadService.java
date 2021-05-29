package Services;

import DAO.*;

import Model.*;
import Result.*;
import Request.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class that will handle Load business logic and determine if a request was successful.
 */
public class loadService {

  private AuthTokenDAO authTokenDAO;
  private EventDAO eventsDAO;
  private PersonDAO personsDAO;
  private UserDAO usersDAO;

  private AuthToken authToken = new AuthToken();
  private Event event = new Event();
  private Person person = new Person();
  private User user = new User();

  private clearService clearService = new clearService();

  /**
   * Function that will load clear all data form the database and then load the user, person, and event into the database.
   *
   * @param r the request passed in
   * @return the result of the operation.
   */
  public loadResult Load(loadRequest r) throws DataAccessException {
    DAO db = new DAO();
    Connection conn = db.getConnection();

    authTokenDAO = new AuthTokenDAO(conn);
    eventsDAO = new EventDAO(conn);
    personsDAO = new PersonDAO(conn);
    usersDAO = new UserDAO(conn);

    try {
      if (! ValidInput(r)) {
        return new loadResult("Error: Invalid input.", false);
      }

      try {
        clearService.clearDatabase();
      } catch (DataAccessException failedToClear) {
        failedToClear.printStackTrace();
        db.closeConnection(false);
        return new loadResult("Error: Failed to clear database before loading.", false);
      }

      InsertUsers(r.getUserModel());
      conn.commit();
      InsertPersons(r.getPersonModel());
      conn.commit();
      InsertEvents(r.getEventModel());

      db.closeConnection(true);

      String successString = "Successfully added " +
              r.getUserModel().length + " users, " +
              r.getPersonModel().length + " persons, and " +
              r.getEventModel().length + " events.";

      return new loadResult(successString, true);

    } catch (DataAccessException | SQLException e) {
      e.printStackTrace();
      db.closeConnection(false);
      clearService.clearDatabase();
      return new loadResult(e.toString(), false);
    }
  }

  private boolean ValidInput(loadRequest r) {
    return (CheckUsers(r.getUserModel()) &&
            CheckPersons(r.getPersonModel()) &&
            CheckEvents(r.getEventModel()));
  }

  private boolean CheckUsers(User[] users) {
    for (User temp : users) {
      if (temp.getUserName() == null ||
              temp.getPassWord() == null ||
              temp.getEmail_address() == null ||
              temp.getFirstName() == null ||
              temp.getLastName() == null ||
              temp.getGender() == null ||
              temp.getPersonID() == null) {
        return false;
      }
    }
    return true;
  }

  private boolean CheckPersons(Person[] persons) {
    for (Person temp : persons) {
      if (temp.getPersonID() == null ||
              temp.getAssociatedUserName() == null ||
              temp.getFirstName() == null ||
              temp.getLastName() == null ||
              temp.getGender() == null) {
        return false;
      }
    }
    return true;
  }

  private boolean CheckEvents(Event[] events) {
    for (Event temp : events) {
      if (temp.getEventID() == null ||
              temp.getAssociatedUserName() == null ||
              temp.getPersonID() == null ||
              temp.getCountry() == null ||
              temp.getCity() == null ||
              temp.getEventType() == null) {
        return false;
      }
    }
    return true;
  }

  private void InsertUsers(User[] users) throws DataAccessException {
    if (users.length == 0) {
      throw new DataAccessException("Error: Users array is empty.");
    }

    for (User temp : users) {
      if (usersDAO.findByUsername(temp.getUserName()) == null) {
        usersDAO.insert(temp);
        authTokenDAO.Insert(new AuthToken(temp.getUserName()));
      } else {
        throw new DataAccessException("Error: User has already been created.");
      }
    }
  }

  private void InsertPersons(Person[] persons) throws DataAccessException {
    if (persons.length == 0) {
      throw new DataAccessException("Error: Persons array is empty.");
    }

    for (Person temp : persons) {

      if (usersDAO.findByUsername(temp.getAssociatedUserName()) == null) {
        throw new DataAccessException("Error: User does not exist.");
      } else if (personsDAO.find(temp.getPersonID()) == null) {
        personsDAO.insert(temp);
      } else {
        throw new DataAccessException("Error: Potential duplicate persons in database.");
      }
    }
  }

  private void InsertEvents(Event[] events) throws DataAccessException {
    if (events.length == 0) {
      throw new DataAccessException("Error: Events array is empty.");
    }

    for (Event temp : events) {
      if (usersDAO.findByUsername(temp.getAssociatedUserName()) == null) {
        throw new DataAccessException("Error: User does not exist.");
      } else if (eventsDAO.find(temp.getEventID()) == null) {
        eventsDAO.insert(temp);
      } else {
        throw new DataAccessException("Error: Potential duplicate events in database.");
      }
    }
  }
}
