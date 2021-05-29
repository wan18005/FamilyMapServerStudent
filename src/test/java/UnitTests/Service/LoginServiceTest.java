package UnitTests.Service;

import DAO.*;


import Model.*;

import Request.*;
import Result.*;
import Services.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
  clearService clearService = new clearService();
  loginService loginService = new loginService();
  DAO db = new DAO();

  Connection conn;
  UserDAO UsersDAO;
  loginResult loginResult;


  @BeforeEach
  public void setUp() throws SQLException, DataAccessException {
    conn = db.getConnection();
    clearService.clearDatabase();

    UsersDAO = new UserDAO(conn);

    User uOne = new User("OptimusPrime", "autobotsrox", "OptimusRocks@gmail.com", "Optimus", "Prime", "R", "Optimus123");
    User uTwo = new User("Megatron", "OptimusIsSilly", "MegaRocks@gmail.com", "Mega", "Tron", "R", "MegaMega123321Babylessgo!");

    UsersDAO.insert(uOne);
    UsersDAO.insert(uTwo);
    conn.commit();
  }


  @AfterEach
  public void tearDown() throws DataAccessException {
    clearService.clearDatabase();
    db.closeConnection(false);
  }

  @Test
  public void LoginPass() throws DataAccessException, SQLException {
    loginResult = loginService.login(new loginRequest("OptimusPrime", "autobotsrox"));

    assertEquals("OptimusPrime", loginResult.getUserName());
    assertNull(loginResult.getOutput());
    assertNotNull(loginResult.getPersonID());
    assertNotNull(loginResult.getAuthtoken());

  }

  @Test
  public void LoginFailBadUser() throws SQLException, DataAccessException {
    UsersDAO.Clear();
    conn.commit();

    loginResult = loginService.login(new loginRequest("Megatron", "OptimusIsSilly"));
    assertNull(loginResult.getAuthtoken());
    assertNull(loginResult.getUserName());
    assertNull(loginResult.getPersonID());
    assertNotNull(loginResult.getOutput());
    assertEquals("Error: Unable to retrieve requested user, user does not exist.", loginResult.getOutput());
  }

  @Test
  public void LoginFailBadPassword() throws DataAccessException, SQLException {
    loginResult = loginService.login(new loginRequest("OptimusPrime", "autobotsroxx"));

    assertNull(loginResult.getAuthtoken());
    assertNull(loginResult.getUserName());
    assertNull(loginResult.getPersonID());
    assertNotNull(loginResult.getOutput());
    assertEquals("Error: Password is incorrect.", loginResult.getOutput());
  }
}