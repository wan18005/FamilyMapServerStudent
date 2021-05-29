package Model;

import java.io.*;
import java.util.*;

public class AuthToken
{
  private String authToken;
  private String userName;
//default
  public AuthToken()
  {
    authToken = UUID.randomUUID().toString();
    userName = null;
  }

  public AuthToken(String userName)
  {
    this.userName = userName;
    this.authToken = UUID.randomUUID().toString();
  }

  public AuthToken(String Username, String AuthToken)
  {
    this.userName = Username;
    this.authToken = AuthToken;
  }
/*
  @Override
  public boolean equals(Object object)
  {
    if (this == object)
      return true;
    if (object == null || getClass() != object.getClass())
      return false;

    AuthToken authTokenO = (AuthToken) object;
    return authToken.equals(authTokenO.authToken);
  }
*/

  public String getAuthToken() { return authToken; }
  public String getUserName() { return userName; }

  public void setAuthToken(String authToken) { this.authToken=authToken; }
  public void setUserName(String userName) { this.userName=userName; }
}
