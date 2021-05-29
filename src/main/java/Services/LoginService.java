package Services;
import DAO.*;
import Model.*;
import Request.*;
import Result.*;

import java.util.*;
import java.io.*;
import java.sql.*;


public class loginService
{
  private AuthTokenDAO authTokenDAO;
  private UserDAO usersDAO;
  private PersonDAO personDAO;
  private EventDAO eventDAO;


  private User user = new User();

  public loginResult login(loginRequest r) throws DataAccessException, SQLException {
    DAO dao = new DAO();
    Connection connection = dao.getConnection();

    AuthToken authtoken = new AuthToken();
    AuthTokenDAO authTokenDAO = new AuthTokenDAO(connection);
    UserDAO userDAO = new UserDAO(connection);


    if (! isLoginValid(r)) {
      dao.closeConnection(false);
      return new loginResult("Error: Input is invalid.", false);
    }

    user = userDAO.findByUsername(r.getUserName());

    if (user == null) {
      dao.closeConnection(false);
      return new loginResult("Error: Unable to retrieve requested user, user does not exist.", false);
    } else if (! user.getPassWord().equals(r.getPassWord())) {
      dao.closeConnection(false);
      return new loginResult("Error: Password is incorrect.", false);
    } else {
      authtoken.setUserName(r.getUserName());
      authTokenDAO.Insert(authtoken);
      dao.closeConnection(true);
      return new loginResult(authtoken.getAuthToken(), user.getUserName(), user.getPersonID());
    }
  }

  private boolean isLoginValid(loginRequest r) {
    return ! (r.getPassWord() == null || r.getUserName() == null);
  }
}
