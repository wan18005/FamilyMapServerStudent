package Services;

import DAO.*;
import MakeFakeData.*;

import Model.*;
import Result.*;
import Request.*;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that will handle Register business logic and determine if a request was successful.
 */
public class registerService {

  private AuthTokenDAO authTokenDAO;
  private EventDAO eventsDAO;
  private PersonDAO personsDAO;
  private UserDAO usersDAO;

  private AuthToken authToken = new AuthToken();
  private Event event = new Event();
  private Person person;
  private User user;

  private FakeData fakeData  = new FakeData();


  private int defaultGens = 4;


  /**
   * @param r the GSON information converted to an object.
   * @return the result object or error message.
   */
  public registerResult Register(registerRequest r) throws DataAccessException {
    DAO db = new DAO();
    Connection conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    eventsDAO = new EventDAO(conn);
    personsDAO = new PersonDAO(conn);
    usersDAO = new UserDAO(conn);

    if (! ValidInput(r)) {
      return new registerResult("Error: Invalid input.", false);
    }

    CreatePerson(r);
    CreateUser(r);

    try {
      if (usersDAO.findByUsername(user.getUserName()) == null) {
        usersDAO.insert(user);
        authToken = new AuthToken(user.getUserName());
        authTokenDAO.Insert(authToken);
          FakeDataStorage fakeDataStorage = fakeData.numberofpeople(person,defaultGens);


        Insert(fakeDataStorage.getPersonArrayList(), fakeDataStorage.getEventArrayList());

        db.closeConnection(true);
        return new registerResult(authToken.getAuthToken(), user.getUserName(), person.getPersonID());
      } else {
        db.closeConnection(false);
        return new registerResult("Error: Username is already taken by another user.", false);
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new registerResult(e.toString(), false);
    }

  }

  private boolean ValidInput(registerRequest r) {
    return ! ((r.getUserName() == null) ||
            (r.getPassWord() == null) ||
            (r.getEmail_address() == null) ||
            (r.getFirstName() == null) ||
            (r.getLastName() == null) ||
            (r.getGender() == null));
  }

  private void CreateUser(registerRequest r) {
    user = new User();
    user.setUserName(r.getUserName());
    user.setPassWord(r.getPassWord());
    user.setEmail_address(r.getEmail_address());
    user.setFirstName(r.getFirstName());
    user.setLastName(r.getLastName());
    user.setGender(r.getGender());
    user.setPersonID(person.getPersonID());
  }

  private void CreatePerson(registerRequest r) {
    person = new Person();
    person.setAssociatedUserName(r.getUserName());
    person.setFirstName(r.getFirstName());
    person.setLastName(r.getLastName());
    person.setGender(r.getGender());
  }

  private void Insert(ArrayList<Person> persons, ArrayList<Event> events) throws DataAccessException {
    if (persons.size() == 0) {
      throw new DataAccessException("Error: Persons array is empty.");
    }
    if (events.size() == 0) {
      throw new DataAccessException("Error: Events array is empty.");
    }
    for (Person temp : persons) {
      personsDAO.insert(temp);
    }
    for (Event temp : events) {
      eventsDAO.insert(temp);
    }
  }
}
