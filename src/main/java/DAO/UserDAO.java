package DAO;
import Model.*;
import java.sql.*;

public class UserDAO
{

  private Connection connection;
  public UserDAO(Connection connection)
  {
    this.connection = connection;
  }


  //The 3 methods you will implement inside each DAO class will handle inserting new data into the table,
  // retrieving information from a table, and clearing all information from the table.

  public void insert(User user) throws DataAccessException {
    //We can structure our string to be similar to a sql command, but if we insert question
    //marks we can change them later with help from the statement
    String sql = "INSERT INTO Users (Username, Password, Email, First_Name, Last_Name, Gender, Person_ID)" +
            " VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, user.getUserName());
      stmt.setString(2, user.getPassWord());
      stmt.setString(3, user.getEmail_address());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getGender());
      stmt.setString(7, user.getPersonID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  public User findByUsername(String userID) throws DataAccessException {
    User user;
    ResultSet rs = null;
    String sql = "SELECT * FROM Users WHERE Username = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, userID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("Username"), rs.getString("Password"),
                rs.getString("Email"), rs.getString("First_Name"), rs.getString("Last_Name"),
                rs.getString("Gender"), rs.getString("Person_ID"));
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding user" + userID);
    } finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
    return null;
  }


  public boolean Clear() throws DataAccessException {
    boolean success = false;
    PreparedStatement stmt = null;
    String sqlUsersDrop = "DROP TABLE IF EXISTS Users;";
    String sqlUsersCreate = "CREATE TABLE IF NOT EXISTS `Users` (\n" +
            "\t`Username`\ttext NOT NULL,\n" +
            "\t`Password`\ttext NOT NULL,\n" +
            "\t`Email`\ttext NOT NULL,\n" +
            "\t`First_Name`\ttext NOT NULL,\n" +
            "\t`Last_Name`\ttext NOT NULL,\n" +
            "\t`Gender`\ttext NOT NULL,\n" +
            "\t`Person_ID`\ttext NOT NULL,\n" +
            "\tPRIMARY KEY(`Username`)\n" +
            ");\n";
    try {
      stmt = connection.prepareStatement(sqlUsersDrop);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlUsersCreate);
      stmt.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Error Clearing User's Table\n");
    }
    return success;
  }
}

