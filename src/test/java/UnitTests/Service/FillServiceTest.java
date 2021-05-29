package UnitTests.Service;

import Model.*;

import Request.*;


import Result.*;
import Services.*;


import DAO.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
  DAO db = new DAO();
  clearService clearService = new clearService();

  registerRequest registerRequest;
  Connection conn;


  @BeforeEach
  public void setUp() throws SQLException, DataAccessException {
    clearService clearService = new clearService();
    clearService.clearDatabase();
    conn = db.getConnection();

    AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);

    registerRequest = new registerRequest("OptimusPrime", "autobots", "stars@yahoo.com",
            "Optimus", "Prime", "R");

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
  public void FillPass() throws DataAccessException {
    personService personService = new personService();
    personResult personResult = personService.Person("1111");

    registerService registerService = new registerService();
    registerResult registerResult = registerService.Register(registerRequest);

    assertNull(personResult.getPersonModel());
    assertEquals("Error: OptimusPrime has no associated Persons.", personResult.getOutput());

    fillService fillService = new fillService();
    fillResult fillResult = fillService.fill("OptimusPrime", 4);

    personResult = personService.Person("1111");
    assertNotNull(personResult.getPersonModel());
    assertEquals(personResult.getPersonModel().size(), 31);
    assertNotEquals("User was not found.", fillResult.getOutput());
    assertNotEquals("Number of generations is not valid.", fillResult.getOutput());
    assertNotEquals("FatalError", fillResult.getOutput());
  }

  @Test
  public void FillFail() throws DataAccessException {
    personService personService = new personService();
    personResult personResult = personService.Person("2222");
    assertNull(personResult.getPersonModel());
    assertEquals("Error: Megatron has no associated Persons.", personResult.getOutput());

    fillService fillService = new fillService();
    fillResult fillResult = fillService.fill("Megatron", - 1);

    assertEquals("Error: Number of generations is less than or equal to 0.", fillResult.getOutput());
  }
}
