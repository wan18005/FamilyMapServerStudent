package UnitTests.DAO;

import DAO.*;


import Model.Person;

import Model.User;
import Services.clearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonsDAOTest {
  private DAO db = new DAO();
  clearService clearService = new clearService();

  Connection conn;
  private Person bestPerson;
  private PersonDAO personDAO;
  private UserDAO userDAO;


  @BeforeEach
  public void SetUp() throws DataAccessException {
    clearService.clearDatabase();
    bestPerson = new Person("Optimus123", "OptimusPrime123", "Optimus", "Prime",
            "M", "OptimusPapa001", "OptimusMama001", "000000");

    conn = db.getConnection();
    personDAO = new PersonDAO(conn);
    userDAO = new UserDAO(conn);
  }

  @AfterEach
  public void TearDown() throws DataAccessException {
    db.closeConnection(false);
    clearService.clearDatabase();
  }

  @Test
  void InsertPass() throws DataAccessException {
    personDAO.insert(bestPerson);

    Person compareTest = personDAO.find(bestPerson.getPersonID());

    assertNotNull(compareTest);

    assertEquals(bestPerson, compareTest);
  }

  @Test
  void InsertFail() throws DataAccessException {
    personDAO.insert(bestPerson);
    assertThrows(DataAccessException.class, () -> personDAO.insert(bestPerson));
  }

  @Test
  void DeletePass() throws DataAccessException, SQLException {
    assertNull(personDAO.find("Optimus123"));

    personDAO.insert(bestPerson);
    conn.commit();

    assertEquals("OptimusPrime123", personDAO.find("Optimus123").getAssociatedUserName());
    personDAO.Delete("OptimusPrime123");
    conn.commit();
    assertNull(personDAO.find("Optimus123"));
  }

  @Test
  void DeleteFail() throws DataAccessException, SQLException {
    assertNull(personDAO.find("Optimus123"));

    personDAO.insert(bestPerson);
    conn.commit();

    assertEquals("OptimusPrime123", personDAO.find("Optimus123").getAssociatedUserName());

    personDAO.Delete("Optimus123");
    conn.commit();

    assertNotNull(personDAO.find("Optimus123"));
  }

  @Test
  void ClearPass() {
    try {
      assertNull(personDAO.find("Optimus123"));
      personDAO.insert(bestPerson);
      assertNotNull(personDAO.find("Optimus123"));
      assertTrue(personDAO.Clear());
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding Person\n");
    }
  }

  @Test
  void FindPass() {
    try {
      assertNull(personDAO.find("Optimus123"));
      personDAO.insert(bestPerson);
      assertNotNull(personDAO.find("Optimus123"));

      Person testPerson = personDAO.find("Optimus123");

      assertEquals(bestPerson, testPerson);
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding Person\n");
    }
  }

  @Test
  void FindFail() {
    try {
      assertNull(personDAO.find("Optimus123"));
      personDAO.insert(bestPerson);
      assertNotNull(personDAO.find("Optimus123"));
      assertNull(personDAO.find("Doesn'tExist"));
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding Person\n");
    }
  }

  @Test
  void FindAllPass() throws SQLException, DataAccessException {
    ArrayList<Person> persons = new ArrayList<Person>();
    ArrayList<Person> findPersons = new ArrayList<>();

    User uOne = new User("Megatron", "autobots", "stars@yahoo", "Optimus", "Prime", "R", "OptimusPappi");

    Person pOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person pTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pThree = new Person("1-2-3", "OptimusPrime", "Boop", "Saggin", "M", null, "abc1234", null);
    Person pFour = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);
    Person pFive = new Person("1-2-3-4-5", "Megatron", "Pob", "Reggas", "M", "dcba321", null, null);
    Person pSix = new Person("OptimusPappi", "OptimusPrime", "Oppy", "Poppy", "R", "m", "1234", null);

    userDAO.insert(uOne);
    personDAO.insert(pOne);
    personDAO.insert(pTwo);
    personDAO.insert(pThree);
    personDAO.insert(pFour);
    personDAO.insert(pFive);
    personDAO.insert(pSix);
    conn.commit();

    persons.add(pOne);
    persons.add(pTwo);
    persons.add(pThree);
    persons.add(pSix);

    findPersons = personDAO.FindAll("OptimusPrime");
    assertNotNull(findPersons);
    assertEquals(4, findPersons.size());

    for (int i = 0; i < persons.size(); i++) {
      assertEquals(persons.get(i), findPersons.get(i));
    }
  }

  @Test
  void FindAllFail() throws DataAccessException, SQLException {
    ArrayList<Person> persons = new ArrayList<Person>();
    ArrayList<Person> findPersons = new ArrayList<>();

    User uOne = new User("Megatron", "autobots", "stars@yahoo", "Optimus", "Prime", "R", "OptimusPappi");

    Person pOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person pTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pThree = new Person("1-2-3", "OptimusPrime", "Boop", "Saggin", "M", null, "abc1234", null);
    Person pFour = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);
    Person pFive = new Person("1-2-3-4-5", "Megatron", "Pob", "Reggas", "M", "dcba321", null, null);
    Person pSix = new Person("OptimusPappi", "OptimusPrime", "Oppy", "Poppy", "R", "m", "1234", null);
    Person pSeven = new Person("1-2-3-4-5-6", "Megatron", "Chilly", "Are", "F", "1029Boo", null, null);
    Person pEight = new Person("1-2-3-4-5-6-7", "Megatron", "Dogs", "Yummy", "F", "Lol744", null, null);

    userDAO.insert(uOne);
    personDAO.insert(pOne);
    personDAO.insert(pTwo);
    personDAO.insert(pThree);
    personDAO.insert(pFour);
    personDAO.insert(pFive);
    personDAO.insert(pSix);
    personDAO.insert(pSeven);
    personDAO.insert(pEight);
    conn.commit();

    persons.add(pOne);
    persons.add(pTwo);
    persons.add(pThree);
    persons.add(pSix);

    findPersons = personDAO.FindAll("Megatron");
    assertNotNull(findPersons);
    assertEquals(4, findPersons.size());

    for (int i = 0; i < persons.size(); i++) {
      assertNotEquals(persons.get(i), findPersons.get(i));
    }
  }
}


