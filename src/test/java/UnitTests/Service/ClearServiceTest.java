package UnitTests.Service;

import DAO.DataAccessException;
import Result.*;
import Services.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearServiceTest {

  @Test
  public void ClearPass() throws DataAccessException {
    clearService clearService = new clearService();
    clearResult clearResult = clearService.clearDatabase();

    assertEquals(clearResult.getOutput(), "Clear succeeded.");
  }
}
