package DAO;

import Model.*;

import java.sql.*;
import java.util.*;

/**
 * A class used for accessing the Person table in the database.
 */
public class PersonDAO {
  private final Connection conn;

  public PersonDAO(Connection conn) {this.conn = conn; }

  public PersonDAO() { this.conn = null; }

  /**
   * Inserts the person into the Person table in the database.
   *
   * @param person the person to be inserted into the database.
   */
  public void insert(Person person) throws DataAccessException {
    String sql = "INSERT INTO Persons (Person_ID, Username, First_Name, Last_Name, Gender, Father_ID, Mother_ID, Spouse_ID)" +
            " VALUES(?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, person.getPersonID());
      stmt.setString(2, person.getAssociatedUserName());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getFatherID());
      stmt.setString(7, person.getMotherID());
      stmt.setString(8, person.getSpouseID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }


  public Boolean Delete(String username) {
    String sql = "DELETE FROM Persons WHERE Username = ?;";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Clears the Person table in the database.
   *
   * @return true or false depending on if the table is cleared correctly.
   */
  public boolean Clear() throws DataAccessException {
    boolean success = false;
    String sql = "DROP TABLE IF EXISTS Persons;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
      success = false;
      System.out.println("Error Clearing User's Table\n");
    }
    return success;
  }

  public Person find(String person_ID) throws DataAccessException {
    Person person;
    ResultSet rs = null;
    String sql = "SELECT * FROM Persons WHERE Person_ID = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, person_ID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        person = new Person(rs.getString("Person_ID"), rs.getString("Username"),
                rs.getString("First_Name"), rs.getString("Last_Name"), rs.getString("Gender"),
                rs.getString("Father_ID"), rs.getString("Mother_ID"), rs.getString("Spouse_ID"));
        return person;
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

  public ArrayList<Person> FindAll(String username) throws DataAccessException {
    ArrayList<Person> persons = new ArrayList<Person>();
    ResultSet rs = null;
    String sql = "SELECT * FROM Persons WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      while (rs.next()) {
        Person person = new Person(rs.getString("Person_ID"), rs.getString("Username"),
                rs.getString("First_Name"), rs.getString("Last_Name"), rs.getString("Gender"),
                rs.getString("Father_ID"), rs.getString("Mother_ID"), rs.getString("Spouse_ID"));

        persons.add(person);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding all persons associated with " + username + ".");
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    if (persons.size() == 0) {
      return null;
    } else {
      return persons;
    }
  }

  public void setPersonID(String setPersonID, Person person) { person.setPersonID(setPersonID); }
  public void setFirstName(String setFirstName, Person person) { person.setFirstName(setFirstName); }
  public void setLastName(String setLastName, Person person) { person.setLastName(setLastName); }
  public void setGender(String setGender, Person person) { person.setGender(setGender); }
  public void setAssociatedUsername(String setAssociatedUsername, Person person) { person.setAssociatedUserName(setAssociatedUsername); }
}
