package UnitTests.Service;

import DAO.*;


import Request.*;
import Result.*;
import Services.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
  DAO db = new DAO();
  clearService clearService = new clearService();


  @BeforeEach
  public void setUp() throws DataAccessException {
    clearService.clearDatabase();
    Connection conn = db.getConnection();
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  public void RegisterPass() throws DataAccessException {
    registerService registerService = new registerService();
    registerRequest registerRequest = new registerRequest("username", "password", "email", "firstname",
            "lastname", "M");

    registerResult registerResult = registerService.Register(registerRequest);

    assertNotNull(registerResult.getPersonID());
    assertNotNull(registerResult.getAuthtoken());
    assertNotNull(registerResult.getUserName());
    assertNull(registerResult.getOutput());
    clearService.clearDatabase();
  }

  @Test
  public void RegisterFail() throws DataAccessException {
    registerService registerService = new registerService();
    registerRequest registerRequest = new registerRequest("A", "B", "C", "D",
            "E", "F");
    registerRequest.setGender(null);

    registerResult registerResult = registerService.Register(registerRequest);

    assertNotNull(registerResult.getOutput());
    assertEquals("Error: Invalid input.", registerResult.getOutput());
    assertNull(registerResult.getAuthtoken());
    assertNull(registerResult.getUserName());
    assertNull(registerResult.getPersonID());
  }

  @Test
  public void ExistingUserPass() throws DataAccessException {

    registerService registerService = new registerService();
    registerRequest registerRequest = new registerRequest("A", "B", "C", "D",
            "E", "F");
    registerResult registerResult = registerService.Register(registerRequest);

    registerResult = registerService.Register(registerRequest);

    assertNotNull(registerResult.getOutput());
    assertEquals("Error: Username is already taken by another user.", registerResult.getOutput());
    assertNull(registerResult.getAuthtoken());
    assertNull(registerResult.getUserName());
    assertNull(registerResult.getPersonID());

    clearService.clearDatabase();
  }

  @Test
  public void DoubleRegisterPass() throws DataAccessException {
    registerService registerService = new registerService();
    registerRequest registerRequest1 = new registerRequest("User1", "Pass1", "e1", "User1First",
            "User1Last", "M");
    registerRequest registerRequest2 = new registerRequest("User2", "Pass2", "e2", "User2First",
            "User2Last", "M");

    registerResult registerResult1 = registerService.Register(registerRequest1);
    registerResult registerResult2 = registerService.Register(registerRequest2);

    assertNotNull(registerResult1.getPersonID());
    assertNotNull(registerResult1.getAuthtoken());
    assertNotNull(registerResult1.getUserName());
    assertNull(registerResult1.getOutput());


    assertNotNull(registerResult2.getPersonID());
    assertNotNull(registerResult2.getAuthtoken());
    assertNotNull(registerResult2.getUserName());
    assertNull(registerResult2.getOutput());


    clearService.clearDatabase();
  }


}