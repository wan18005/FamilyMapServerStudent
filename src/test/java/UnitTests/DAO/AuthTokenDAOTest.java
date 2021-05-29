package UnitTests.DAO;

import DAO.*;

import Model.*;
import Services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the AuthTokenDAO class.
 * Tests:
 *  InsertPass
 *  InsertFail
 *  FindPass
 *  FindFail
 */
class AuthTokenDAOTest {
  DAO db = new DAO();
  clearService clearService = new clearService();

  Connection conn;
  AuthToken optimusToken;
  AuthToken megatronToken;
  AuthToken bumbleToken;
  AuthTokenDAO aDAO;


  @BeforeEach
  void setUp() throws DataAccessException {
    clearService.clearDatabase();
    conn = db.getConnection();

    aDAO = new AuthTokenDAO(conn);

    optimusToken = new AuthToken("OptimusPrime", "OppyPoppy1234");
    megatronToken = new AuthToken("Megatron", "MeggaBronze4321");
    bumbleToken = new AuthToken("BumbleBee", "BumbleRumble9000");

  }

  @AfterEach
  void tearDown() {
    try {
      db.closeConnection(false);
      clearService.clearDatabase();
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * Inserts 3 AuthTokens into the AuthToken table and verifies that they were inserted correctly.
   * Test passes when each User's username and authtoken match the User that was inserted.
   * @throws DataAccessException when something goes wrong with Find.
   * @throws SQLException when something goes wrong with Insert.
   */
  @Test
  void InsertPass() throws DataAccessException, SQLException {
    assertNull(aDAO.find("OppyPoppy1234"));
    assertNull(aDAO.find("MeggaBronze4321"));
    assertNull(aDAO.find("BumbleRumble9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    aDAO.Insert(bumbleToken);

    conn.commit();

    AuthToken tempOptimus = aDAO.find("OppyPoppy1234");
    AuthToken tempMegatron = aDAO.find("MeggaBronze4321");
    AuthToken tempBumble = aDAO.find("BumbleRumble9000");

    assertNotNull(tempOptimus);
    assertNotNull(tempMegatron);
    assertNotNull(tempBumble);

    assertEquals("OptimusPrime", tempOptimus.getUserName());
    assertEquals("OppyPoppy1234", tempOptimus.getAuthToken());
    assertEquals("Megatron", tempMegatron.getUserName());
    assertEquals("MeggaBronze4321", tempMegatron.getAuthToken());
    assertEquals("BumbleBee", tempBumble.getUserName());
    assertEquals("BumbleRumble9000", tempBumble.getAuthToken());
  }

  /**
   * Inserts 2 AuthTokens into the AuthToken table and verifies that they were inserted correctly.
   * Insert fails when the same tokens are attempted to be inserted again.
   * After Insert fails the test ensures that the first 2 tokens that were inserted are still
   * correclty stored in the database.
   * @throws DataAccessException when something goes wrong with Find.
   * @throws SQLException when something goes wrong with Insert.
   */
  @Test
  void InsertFail() throws DataAccessException, SQLException {
    assertNull(aDAO.find("OppyPoppy1234"));
    assertNull(aDAO.find("MeggaBronze4321"));
    assertNull(aDAO.find("BumbleRumble9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    assertThrows(DataAccessException.class, () -> aDAO.Insert(optimusToken));
    assertThrows(DataAccessException.class, () -> aDAO.Insert(megatronToken));

    conn.commit();

    AuthToken tempOptimus = aDAO.find("OppyPoppy1234");
    AuthToken tempMegatron = aDAO.find("MeggaBronze4321");

    assertNotNull(tempOptimus);
    assertNotNull(tempMegatron);

    assertEquals("OptimusPrime", tempOptimus.getUserName());
    assertEquals("OppyPoppy1234", tempOptimus.getAuthToken());
    assertEquals("Megatron", tempMegatron.getUserName());
    assertEquals("MeggaBronze4321", tempMegatron.getAuthToken());
  }

  /**
   * Inserts 3 AuthTokens into the AuthToken table and then finds those 3 tokens again.
   * Test passes when it verifies that each found token is not null and that it matches the
   * username and authtoken associated with that token.
   * @throws DataAccessException when something goes wrong with Find.
   * @throws SQLException when something goes wrong with Insert.
   */
  @Test
  void FindPass() throws DataAccessException, SQLException {
    assertNull(aDAO.find("OppyPoppy1234"));
    assertNull(aDAO.find("MeggaBronze4321"));
    assertNull(aDAO.find("BumbleRumble9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    aDAO.Insert(bumbleToken);

    conn.commit();

    AuthToken tempOptimus = aDAO.find("OppyPoppy1234");
    AuthToken tempMegatron = aDAO.find("MeggaBronze4321");
    AuthToken tempBumble = aDAO.find("BumbleRumble9000");

    assertNotNull(tempOptimus);
    assertNotNull(tempMegatron);
    assertNotNull(tempBumble);
    assertEquals("OptimusPrime", tempOptimus.getUserName());
    assertEquals("OppyPoppy1234", tempOptimus.getAuthToken());
    assertEquals("Megatron", tempMegatron.getUserName());
    assertEquals("MeggaBronze4321", tempMegatron.getAuthToken());
    assertEquals("BumbleBee", tempBumble.getUserName());
    assertEquals("BumbleRumble9000", tempBumble.getAuthToken());
  }

  /**
   * Inserts 3 AuthTokens into the AuthToken table and then attempts to find those 3 tokens again.
   * Test passes when it verifies that each token it is attempting to find does not exist, and the Find
   * method returns null.
   * @throws DataAccessException when something goes wrong with Find.
   * @throws SQLException when something goes wrong with Insert.
   */
  @Test
  void FindFail() throws DataAccessException, SQLException {
    assertNull(aDAO.find("OppyPoppy1234"));
    assertNull(aDAO.find("MeggaBronze4321"));
    assertNull(aDAO.find("BumbleRumble9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    aDAO.Insert(bumbleToken);

    conn.commit();

    AuthToken tempOptimus = aDAO.find("OpyPopy123");
    AuthToken tempMegatron = aDAO.find("MegaBronz432");
    AuthToken tempBumble = aDAO.find("BumblRumbl900");

    assertNull(tempOptimus);
    assertNull(tempMegatron);
    assertNull(tempBumble);
  }
}