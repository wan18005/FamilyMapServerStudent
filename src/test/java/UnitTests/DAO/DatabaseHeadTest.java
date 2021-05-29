package UnitTests.DAO;

import DAO.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHeadTest {

  DAO db = new DAO();
  Connection conn;

  @BeforeEach
  void setUp() throws DataAccessException {
    conn = db.getConnection();
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    if (db.connection != null) {
      db.closeConnection(false);
    }
  }

  @Test
  void OpenConnectionPass() throws DataAccessException {
    assertNotNull(conn);
  }

  @Test
  void OpenConnectionFail() throws DataAccessException {
    db.closeConnection(false);
    assertNull(db.connection);
  }

  @Test
  void GetConnectionPass() throws DataAccessException {
    assertNotNull(db.getConnection());
  }

  @Test
  void ClearTablesPass() {
    boolean passed;

    try {
      db.getConnection();
      db.clearTable();
      passed = true;
    } catch (DataAccessException e) {
      passed = false;
    }
    assertTrue(passed);
  }
}