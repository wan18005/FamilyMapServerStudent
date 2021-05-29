package UnitTests.DAO;

import DAO.*;
import Model.*;

import Services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class EventsDAOTest {

  private clearService clearService = new clearService();
  private DAO db = new DAO();

  private Connection conn;
  private Event bestEvent;
  private EventDAO eventDAO;

  @BeforeEach
  public void SetUp() throws DataAccessException {
    clearService.clearDatabase();

    bestEvent = new Event("Biking_123A", "Gale", "Gale123A", "Japan", "Ushiku",
            "Biking_Around", 35.9f, 140.1f, 2016);

    conn = db.getConnection();
    eventDAO = new EventDAO(conn);
  }

  @AfterEach
  public void TearDown() throws DataAccessException {
    db.closeConnection(false);
    clearService.clearDatabase();

  }

  @Test
  public void InsertPass() throws DataAccessException {
    eventDAO.insert(bestEvent);

    Event findEvent = eventDAO.find(bestEvent.getEventID());

    assertNotNull(findEvent);

    assertEquals(bestEvent, findEvent);
  }

  @Test
  public void InsertFail() throws DataAccessException {
    eventDAO.insert(bestEvent);
    assertThrows(DataAccessException.class, () -> eventDAO.insert(bestEvent));
  }

  @Test
  public void FindEventPass() throws DataAccessException {
    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);

    eventDAO.insert(eOne);
    Event findEvent = eventDAO.find("1");
    assertEquals(findEvent, eOne);
  }

  @Test
  public void FindEventFail() throws DataAccessException, SQLException {

    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);
    eventDAO.insert(eOne);
    conn.commit();
    assertNull(eventDAO.find("1-2"));
  }

  @Test
  public void FindAllEventsPass() throws DataAccessException, SQLException {
    ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<Event> findEvents;

    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);
    Event eTwo = new Event("1-2", "OptimusPrime", "You", "Ussiar", "Stink", "HandelBar", 12, 23, 222);
    Event eThree = new Event("1-2-3", "OptimusPrime", "Feel", "Ssiaru", "Stankin", "Curler", 123, 234, 333);
    Event eFive = new Event("1-2-3-4-5", "OptimusPrime", "Love", "Iaruss", "VStank", "Laughed", 12345, 23456, 555);
    eventDAO.insert(eOne);
    eventDAO.insert(eTwo);
    eventDAO.insert(eThree);
    eventDAO.insert(eFive);
    conn.commit();


    events.add(eOne);
    events.add(eTwo);
    events.add(eThree);
    events.add(eFive);

    try {
      clearService.clearDatabase();
      eventDAO.insert(eOne);
      eventDAO.insert(eTwo);
      eventDAO.insert(eThree);
      eventDAO.insert(eFive);

      findEvents = eventDAO.FindAll("OptimusPrime");

      for (int i = 0; i < events.size(); i++) {
        assertEquals(events.get(i), findEvents.get(i));
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void FindAllEventsFail() throws DataAccessException, SQLException {
    ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<Event> findEvents;

    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);
    Event eTwo = new Event("1-2", "OptimusPrime", "You", "Ussiar", "Stink", "HandelBar", 12, 23, 222);
    Event eThree = new Event("1-2-3", "OptimusPrime", "Feel", "Ssiaru", "Stankin", "Curler", 123, 234, 333);
    Event eFour = new Event("1-2-3-4", "Megatron", "The", "Siarus", "PooStink", "HairDryer", 1234, 2345, 444);
    Event eFive = new Event("1-2-3-4-5", "OptimusPrime", "Love", "Iaruss", "VStank", "Laughed", 12345, 23456, 555);
    eventDAO.insert(eOne);
    eventDAO.insert(eTwo);
    eventDAO.insert(eThree);
    eventDAO.insert(eFour);
    eventDAO.insert(eFive);
    conn.commit();


    events.add(eOne);
    events.add(eTwo);
    events.add(eThree);
    events.add(eFour);
    events.add(eFive);

    try {
      clearService.clearDatabase();
      eventDAO.insert(eOne);
      eventDAO.insert(eTwo);
      eventDAO.insert(eThree);
      eventDAO.insert(eFour);
      eventDAO.insert(eFive);

      findEvents = eventDAO.FindAll("OptimusPrime");

      for (int i = 0; i < 4; i++) {
        if (events.get(i) == eFour) {
          assertNotEquals(events.get(i), findEvents.get(i));
        } else {
          assertEquals(events.get(i), findEvents.get(i));
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }
}
