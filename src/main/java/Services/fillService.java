package Services;

import DAO.*;
import MakeFakeData.*;
import Model.*;

import Result.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that will handle Fill business logic and determine if a request was successful.
 */
public class fillService {
  private EventDAO eventsDAO;
  private PersonDAO personsDAO;
  private UserDAO usersDAO;
  private DAO db;
  private Connection conn;

  public fillResult fill(String username, int numGen) throws DataAccessException {
   FakeData fakeData = new FakeData();
    db = new DAO();
    conn = db.getConnection();
    eventsDAO = new EventDAO(conn);
    personsDAO = new PersonDAO(conn);
    usersDAO = new UserDAO(conn);

    if (numGen <= 0) {
      db.closeConnection(false);
      return new fillResult("Error: Number of generations is less than or equal to 0.", false);
    }

    try {
      if (usersDAO.findByUsername(username) == null) {
        db.closeConnection(false);
        return new fillResult("Error: User does not exist.", false);
      } else if (! ClearUsersInfo(username)) {
        db.closeConnection(false);
        return new fillResult("Error: Failed to delete " + username + " Events and Persons" +
                " information from the database.", false);
      } else {
        Person temp = UserToPerson(usersDAO.findByUsername(username));
        FakeDataStorage fakeDataStorage = fakeData.numberofpeople(temp, numGen);
        Insert(fakeDataStorage.getPersonArrayList(), fakeDataStorage.getEventArrayList());
        ;
        db.closeConnection(true);
        return new fillResult("Successfully added " + fakeDataStorage.getPersonArrayList().size() +
                " persons and " + fakeDataStorage.getEventArrayList().size() + " events.", true);
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
    }
    db.closeConnection(false);
    return new fillResult("Error: Fatal fill error.", false);
  }

  private boolean ClearUsersInfo(String username) {
    boolean success = false;

    if (eventsDAO.Delete(username) && personsDAO.Delete(username)) {
      try {
        conn.commit();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      success = true;
    }
    return success;
  }

  private Person UserToPerson(User user) {
    Person p = new Person();

    p.setPersonID(user.getPersonID());
    p.setAssociatedUserName(user.getUserName());
    p.setFirstName(user.getFirstName());
    p.setLastName(user.getLastName());
    p.setGender(user.getGender());

    return p;
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
