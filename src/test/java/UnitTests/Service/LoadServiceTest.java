package UnitTests.Service;

import DAO.DataAccessException;

import Model.*;

import Request.*;
import Result.*;
import Services.*;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadServiceTest {

  @Test
  public void LoadPass() throws DataAccessException {
    clearService clearService = new clearService();

    Event eOne = new Event("123", "OptimusPrime", "Alpha1", "Merica", "NY", "Dead", 100f, 900f, 6969);
    Event eTwo = new Event("345", "OptimusPrime", "Alpha2", "Taiwan", "Brown", "Shoppping", 100f, 900f, 1900);
    Event eThree = new Event("456", "Megatron", "Alpha3", "England", "Yellow", "Kickin", 100f, 900f, 6969);
    Event eFour = new Event("678", "Megatron", "Alpha4", "Russia", "Green", "Alive", 100f, 900f, 6969);

    Person pOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person pTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pThree = new Person("1-2-3", "Megatron", "Boop", "Saggin", "M", null, "abc1234", null);
    Person pFour = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);

    User uOne = new User("OptimusPrime", "autobotsrox", "stars@gmail.com", "Op", "Prime", "R", "Op123");
    User uTwo = new User("Megatron", "killoptimus", "bumblebeestinks@yahoo.com", "Mega", "Tron", "R", "Me123");

    Event[] eventArray = new Event[]{eOne, eTwo, eThree, eFour};
    Person[] personArray = new Person[]{pOne, pTwo, pThree, pFour};
    User[] userArray = new User[]{uOne, uTwo};

    loadRequest loadRequest = new loadRequest(userArray, personArray, eventArray);
    loadService loadService = new loadService();
    loadResult loadResult = loadService.Load(loadRequest);

    assertEquals("Successfully added 2 users, 4 persons, and 4 events.", loadResult.getOutput());

    clearService.clearDatabase();
  }

  @Test
  public void LoadFail() throws DataAccessException {
    clearService clearService = new clearService();

    Event eOne = new Event("123", "OptimusPrime", "Alpha1", "Merica", "NY", "Dead", 100f, 900f, 6969);
    Event eTwo = new Event("345", "OptimusPrime", "Alpha2", "Taiwan", "Brown", "Shoppping", 100f, 900f, 1900);
    Event eThree = new Event("456", "Megatron", "Alpha3", "England", "Yellow", "Kickin", 100f, 900f, 6969);
    Event eFour = new Event("678", "Megatron", "Alpha4", "Russia", "Green", "Alive", 100f, 900f, 6969);
    Event eBad = new Event();

    Person pOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person pTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pThree = new Person("1-2-3", "Megatron", "Boop", "Saggin", "M", null, "abc1234", null);
    Person pFour = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);

    User uOne = new User("OptimusPrime", "autobotsrox", "stars@gmail.com", "Op", "Prime", "R", "Op123");
    User uTwo = new User("Megatron", "killoptimus", "bumblebeestinks@yahoo.com", "Mega", "Tron", "R", "Me123");

    Event[] events = new Event[]{eOne, eTwo, eThree, eFour, eBad};
    Person[] persons = new Person[]{pOne, pTwo, pThree, pFour};
    User[] users = new User[]{uOne, uTwo};

    loadRequest loadRequest = new loadRequest(users, persons, events);
    loadService loadService = new loadService();
    loadResult loadResult = loadService.Load(loadRequest);

    assertEquals("Error: Invalid input.", loadResult.getOutput());

    clearService.clearDatabase();
  }
}
