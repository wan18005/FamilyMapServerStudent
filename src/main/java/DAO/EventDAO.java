package DAO;

import Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A class used for accessing the Event table in the database.
 */
public class EventDAO {

  private final Connection conn;

  public EventDAO(Connection conn) {
    this.conn = conn;
  }

  public EventDAO() { this.conn = null; }


  /**
   * Inserts the event into the Event table in the database.
   *
   * @param event the event to be inserted into the database.
   * @return true or false depending on if the event is correctly inserted into the table.
   */
  public void insert(Event event) throws DataAccessException {
    //We can structure our string to be similar to a sql command, but if we insert question
    //marks we can change them later with help from the statement
    String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Country, City, EventType," +
            "Latitude, Longitude, Year) VALUES(?,?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, event.getEventID());
      stmt.setString(2, event.getAssociatedUserName());
      stmt.setString(3, event.getPersonID());
      stmt.setString(4, event.getCountry());
      stmt.setString(5, event.getCity());
      stmt.setString(6, event.getEventType());
      stmt.setFloat(7, event.getLatitude());
      stmt.setFloat(8, event.getLongitude());
      stmt.setInt(9, event.getYear());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  public Boolean Delete(String associatedUsername) {
    String sql = "DELETE FROM Events WHERE AssociatedUsername = ?;";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, associatedUsername);
      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Clears the Event table in the database.
   *
   * @return true or false depending on if the table is cleared correctly.
   */
  public Boolean Clear() {
    boolean success = false;
    String sql = "DROP TABLE IF EXISTS Events;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Error Clearing Event's Table\n");
    }
    return success;
  }


  public Event find(String eventID) throws DataAccessException {
    Event event;
    ResultSet rs = null;
    String sql = "SELECT * FROM Events WHERE EventID = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, eventID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                rs.getString("PersonID"), rs.getString("Country"), rs.getString("City"),
                rs.getString("EventType"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                rs.getInt("Year"));
        return event;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding event");
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
    return null;
  }

  public ArrayList<Event> FindAll(String username) throws DataAccessException {
    ArrayList<Event> events = new ArrayList<Event>();
    Event event;
    ResultSet rs = null;
    String sql = "SELECT * FROM Events WHERE AssociatedUsername = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      while (rs.next()) {
        event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                rs.getString("PersonID"), rs.getString("Country"), rs.getString("City"),
                rs.getString("EventType"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                rs.getInt("Year"));

        events.add(event);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding event");
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    if (events.size() == 0) {
      return null;
    } else {
      return events;
    }
  }
}
