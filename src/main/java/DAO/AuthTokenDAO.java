package DAO;

import Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class used for accessing the AuthToken table in the database.
 */
public class AuthTokenDAO {

  private Connection conn;

  public AuthTokenDAO(Connection conn) {this.conn = conn; }


  /**
   * Inserts the authToken into the AuthToken table in the database.
   *
   * @param authToken the token to be inserted into the database.
   * @return true or false depending on if the token is correctly inserted into the table.
   */
  public void Insert(AuthToken authToken) throws DataAccessException {
    String sql = "INSERT INTO AuthToken (Username, Auth_Token) VALUES (?,?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, authToken.getUserName());
      stmt.setString(2, authToken.getAuthToken());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  public AuthToken find(String searchToken) throws DataAccessException {
    AuthToken token;
    ResultSet rs = null;
    String sql = "SELECT * FROM AuthToken WHERE Auth_Token = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, searchToken);
      rs = stmt.executeQuery();
      if (rs.next()) {
        token = new AuthToken(rs.getString("Username"), rs.getString("Auth_Token"));
        return token;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding token");
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

  public String getUserName(User user) { return user.getUserName(); }

  public String getPassword(User user) { return user.getPassWord(); }
}
