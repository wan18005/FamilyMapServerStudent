package Services;

import DAO.*;
import Result.clearResult;


public class clearService {
  private clearResult clearResult;

  public clearResult clearDatabase() throws DataAccessException
  {

    DAO databaseobject = new DAO();

    try
    {
      databaseobject.getConnection();
      databaseobject.clearTable();
      databaseobject.closeConnection(true);

      clearResult = new clearResult("Clear succeeded." , true);
      return  clearResult;
    }
    catch (DataAccessException e)
    {

        databaseobject.closeConnection(false);
      clearResult = new clearResult("Error: not cleared",false);
      return clearResult;
    }

  }
}
