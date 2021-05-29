package DAO;

public class DataAccessException extends Exception{
  public DataAccessException(String message)
  {
    // reference to super class parents
    super(message);
  }
  DataAccessException()
  {
    super();
  }
}
