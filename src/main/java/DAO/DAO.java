package DAO;

import java.sql.*;
public class DAO
{
  public Connection connection;


  public Connection getConnection() throws DataAccessException
  {
    if (connection == null) {
      return openConnection();
    } else {
      return connection;
    }
  }


  //open the conncection
  public Connection openConnection() throws DataAccessException
  {
    try
    {
      final String URL = "jdbc:sqlite:SQL/database.db";

      connection = DriverManager.getConnection(URL);

      // Start
      connection.setAutoCommit(false);
    }catch (SQLException exception)
    {
      exception.printStackTrace();
        throw new DataAccessException("Unable to connect to database given");
    }
    return connection;
  }
public void closeConnection ( boolean status) throws DataAccessException
{
  try
  {
   if (status)
   {
     connection.commit();
   }
   else
   {
     connection.rollback();
   }
   connection.close();
   connection = null;
  }
  catch (SQLException exception)
  {
    exception.printStackTrace();
    throw new DataAccessException("Unable to connect to database given");
  }
}


public void clearTable() throws DataAccessException
{

  try
  {
    clearHelper();
  }
  catch (DataAccessException e)
  {
    System.out.println(e.toString());
  }
}

private void clearHelper() throws DataAccessException
{
  PreparedStatement stmt = null;
  try
  {
    String sqlUsers = "DROP TABLE IF EXISTS Users; \n";
    String sqlPersons = "DROP TABLE IF EXISTS Persons; \n";
    String sqlEvents = "DROP TABLE IF EXISTS Events; \n";
    String sqlAuthToken = "DROP TABLE IF EXISTS AuthToken; \n";

    stmt = connection.prepareStatement(sqlUsers);
    stmt.executeUpdate();
    stmt = connection.prepareStatement(sqlPersons);
    stmt.executeUpdate();
    stmt = connection.prepareStatement(sqlEvents);
    stmt.executeUpdate();
    stmt = connection.prepareStatement(sqlAuthToken);
    stmt.executeUpdate();
    try {
      CreateTables();
    } catch (DataAccessException e) {
      System.out.println(e.toString());
    }

  }catch (SQLException e)
  {
    System.out.println(e.toString());
    throw new DataAccessException(e.toString());
  }
}

  public void CreateTables() throws DataAccessException, SQLException {
    PreparedStatement stmt = null;
    try {
      String sqlUsers = "CREATE TABLE IF NOT EXISTS `Users` (\n" +
              "\t`Username`\ttext NOT NULL,\n" +
              "\t`Password`\ttext NOT NULL,\n" +
              "\t`Email`\ttext NOT NULL,\n" +
              "\t`First_Name`\ttext NOT NULL,\n" +
              "\t`Last_Name`\ttext NOT NULL,\n" +
              "\t`Gender`\ttext NOT NULL,\n" +
              "\t`Person_ID`\ttext NOT NULL,\n" +
              "\tPRIMARY KEY(`Username`)\n" +
              ");\n";

      String sqlPersons = "CREATE TABLE IF NOT EXISTS `Persons` (\n" +
              "\t`Person_ID`\ttext NOT NULL,\n" +
              "\t`Username`\ttext NOT NULL,\n" +
              "\t`First_Name`\ttext NOT NULL,\n" +
              "\t`Last_Name`\ttext NOT NULL,\n" +
              "\t`Gender`\ttext NOT NULL,\n" +
              "\t`Father_ID`\ttext ,\n" +
              "\t`Mother_ID`\ttext ,\n" +
              "\t`Spouse_ID`\ttext,\n" +
              "\tPRIMARY KEY(`Person_ID`)\n" +
              ");\n";

      String sqlEvents = "CREATE TABLE IF NOT EXISTS `Events` (\n" +
              "\t`EventID`\ttext NOT NULL UNIQUE,\n" +
              "\t`AssociatedUsername`\ttext NOT NULL,\n" +
              "\t`PersonID`\ttext NOT NULL,\n" +
              "\t`Country`\ttext NOT NULL,\n" +
              "\t`City`\ttext NOT NULL,\n" +
              "\t`EventType`\ttext NOT NULL,\n" +
              "\t`Latitude`\treal NOT NULL,\n" +
              "\t`Longitude`\treal NOT NULL,\n" +
              "\t`Year`\tint NOT NULL,\n" +
              "\tPRIMARY KEY(`EventID`),\n" +
              "\tFOREIGN KEY('AssociatedUsername') references 'Users'('Username'),\n" +
              "\tFOREIGN KEY('PersonID') references 'Persons'('Person_ID')\n" +
              ");\n";

      String sqlAuthToken = "CREATE TABLE IF NOT EXISTS `AuthToken` (\n" +
              "\t`Username`\ttext NOT NULL,\n" +
              "\t`Auth_Token`\ttext NOT NULL UNIQUE,\n" +
              "\tPRIMARY KEY(`Auth_Token`)\n" +
              ");\n";

      stmt = connection.prepareStatement(sqlUsers);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlPersons);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlEvents);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlAuthToken);
      stmt.executeUpdate();
      stmt.close();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }
}
