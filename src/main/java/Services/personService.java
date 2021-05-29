package Services;

import DAO.*;
import Model.*;
;
import Result.*;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that will handle Person business logic and determine if a request was successful.
 */
public class personService {
  private AuthTokenDAO authTokenDAO;
  private PersonDAO personsDAO;
  private DAO db;
  private Connection conn;


  public personResult Person(String personID, String authToken) throws DataAccessException {
    db = new DAO();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    personsDAO = new PersonDAO(conn);

    try {
      AuthToken findToken = authTokenDAO.find(authToken);
      Person findPerson = personsDAO.find(personID);

      if (findToken == null) {
        db.closeConnection(false);
        return new personResult("Error: Invalid authToken returned null.", false);
      } else {
        if (findPerson == null) {
          db.closeConnection(false);
          return new personResult("Error: Person was not found in the database.", false);
        } else if (! findToken.getUserName().equals(findPerson.getAssociatedUserName())) {
          db.closeConnection(false);
          return new personResult("Error: Person is not associated with " + findToken.getUserName() + ".", false);
        } else {
          db.closeConnection(true);
          return new personResult(findPerson);
        }
      }
    } catch (DataAccessException e) {
      db.closeConnection(false);
      return new personResult(e.toString(), false);
    }
  }

  public personResult Person(String authToken) throws DataAccessException {
    db = new DAO();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    personsDAO = new PersonDAO(conn);

    try {
      AuthToken findToken = authTokenDAO.find(authToken);

      if (findToken == null) {
        db.closeConnection(false);
        return new personResult("Error: AuthToken returned null.", false);
      } else {
        ArrayList<Person> persons = personsDAO.FindAll(findToken.getUserName());
        if (persons == null) {
          db.closeConnection(false);
          return new personResult("Error: " + findToken.getUserName() + " has no associated Persons.", false);
        } else {
          Person personsPerson = FindPerson(findToken);
          db.closeConnection(true);
          return new personResult(persons, personsPerson);
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new personResult(e.toString(), false);
    }
  }

  private Person FindPerson(AuthToken t) throws DataAccessException {
    UserDAO uDAO = new UserDAO(conn);
    User temp = uDAO.findByUsername(t.getUserName());
    return personsDAO.find(temp.getPersonID());
  }
}
