package UnitTests.Service;

import DAO.*;
import Result.*;
import Services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


import Model.*;


import static org.junit.jupiter.api.Assertions.*;


public class PersonServiceTest {
  clearService clearService = new clearService();
  personService personService = new personService();
  DAO db = new DAO();

  personResult personResult;
  PersonDAO personsDAO;
  AuthTokenDAO authTokenDAO;
  UserDAO usersDAO;
  Connection conn;


  @BeforeEach
  public void setUp() throws DataAccessException, SQLException {
    clearService.clearDatabase();
    conn = db.getConnection();
    personsDAO = new PersonDAO(conn);
    authTokenDAO = new AuthTokenDAO(conn);
    usersDAO = new UserDAO(conn);

    authTokenDAO.Insert(new AuthToken("OptimusPrime", "1111"));
    authTokenDAO.Insert(new AuthToken("Megatron", "2222"));
    conn.commit();
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    clearService.clearDatabase();
    db.closeConnection(false);
  }

  @Test
  public void PersonPass() throws DataAccessException, SQLException {
    ArrayList<Person> persons = new ArrayList<Person>();

    User uOne = new User("Megatron", "autobots", "stars@yahoo", "Optimus", "Prime", "R", "OptimusPappi");

    Person pOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person pTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pThree = new Person("1-2-3", "OptimusPrime", "Boop", "Saggin", "M", null, "abc1234", null);
    Person pFour = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);
    Person pFive = new Person("1-2-3-4-5", "Megatron", "Pob", "Reggas", "M", "dcba321", null, null);
    Person pSix = new Person("OptimusPappi", "OptimusPrime", "Oppy", "Poppy", "R", "m", "1234", null);

    usersDAO.insert(uOne);
    personsDAO.insert(pOne);
    personsDAO.insert(pTwo);
    personsDAO.insert(pThree);
    personsDAO.insert(pFour);
    personsDAO.insert(pFive);
    personsDAO.insert(pSix);
    conn.commit();

    personResult = personService.Person("OptimusPappi", "1111");

    assertNotNull(personResult.getAssociatedUsername());
    assertNotNull(personResult.getFatherID());
    assertNotNull(personResult.getFirstName());
    assertNotNull(personResult.getLastName());
    assertNotNull(personResult.getGender());
    assertNull(personResult.getOutput());

    assertEquals("OptimusPrime", personResult.getAssociatedUsername());

    personResult = personService.Person("2222");

    assertNotNull(personResult.getPersonModel());
    assertEquals(2, personResult.getPersonModel().size());
    assertNull(personResult.getOutput());

    persons.add(pFour);
    persons.add(pFive);

    for (int i = 0; i < persons.size(); i++) {
      assertEquals(persons.get(i), personResult.getPersonModel().get(i));
    }
  }

  @Test
  public void PersonFail() throws DataAccessException, SQLException {
    Person pOne = new Person("OptimusPappi", "autobots", "Oppy", "Poppy", "R", "m", "1234", null);
    Person pTwo = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person pThree = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pFour = new Person("1-2-3", "Megatron", "Boop", "Saggin", "M", null, "abc1234", null);
    Person pFive = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);

    personsDAO.insert(pOne);
    personsDAO.insert(pTwo);
    personsDAO.insert(pThree);
    personsDAO.insert(pFour);
    personsDAO.insert(pFive);
    conn.commit();

    personResult = personService.Person("OptimusPappi", "1212");

    assertNull(personResult.getAssociatedUsername());
    assertNull(personResult.getFatherID());
    assertNull(personResult.getFirstName());
    assertNull(personResult.getLastName());
    assertNull(personResult.getGender());
    assertNotNull(personResult.getOutput());
    assertEquals("Error: Invalid authToken returned null.", personResult.getOutput());

    personResult = personService.Person("10");

    assertNull(personResult.getPersonModel());
    assertNotNull(personResult.getOutput());
    assertEquals("Error: AuthToken returned null.", personResult.getOutput());
  }
}
