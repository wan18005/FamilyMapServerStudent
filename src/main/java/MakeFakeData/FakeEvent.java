package MakeFakeData;
import Model.*;
import com.google.gson.*;

import java.io.*;
import java.util.*;


public class FakeEvent {

  public FakeEvent(String setUsername) { username = setUsername; }

  public ArrayList<Event> GetEvents() { return eventArrayList; }


  Random random = new Random();
  FakeLocations fakeLocations = new FakeLocations();
  String username = null;
  ArrayList<Event> eventArrayList = new ArrayList<Event>();

  public String getEventFromJson()
  {
    try
    {

      JsonParser jsonParser = new JsonParser();
      FileReader fileReader = new FileReader(new File("json/enames.json"));
      JsonObject jsonObject = (JsonObject) jsonParser.parse(fileReader);
      JsonArray jsonArray = (JsonArray) jsonObject.get("data");
      int i = random.nextInt(jsonArray.size());
      String event = jsonArray.get(i).toString();
      event = event.substring(1,event.length() -1);
      return  event;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  public void DefaultBirth(Person rootPerson, int year)
  {
    Event birth = fakeLocations.makeFakeLocation();
    birth.setPersonID(rootPerson.getPersonID());
    birth.setEventType("Birth");
    birth.setYear(year);
    birth.setAssociatedUserName(username);
    eventArrayList.add(birth);
  }

  public void Birth(Person person, int year)
  {
    Random random = new Random();
    Event birth = fakeLocations.makeFakeLocation();
    int born = year -random.nextInt(10);

    birth.setPersonID(person.getPersonID());
    birth.setEventType("Birth");
    birth.setYear(born);
    birth.setAssociatedUserName(username);
    eventArrayList.add(birth);
  }

  public void Death(Person person, int year)
  {
    Random random = new Random();
    Event death = fakeLocations.makeFakeLocation();
    int lifeSpan = 35;
    int deathYear = year + lifeSpan + random.nextInt(50);
    if ((deathYear - year) > 120)
    {
      deathYear = year + 120;
    }

    death.setPersonID(person.getPersonID());
    death.setEventType("Death");
    death.setYear(deathYear);
    death.setAssociatedUserName(username);
    eventArrayList.add(death);
  }

  public void Random(Person person, int year) {
    Random random = new Random();
    int yearsBeforeBirth = 10;
    int eventYear = year + yearsBeforeBirth + random.nextInt(20);

    Event randomEvent = fakeLocations.makeFakeLocation();
    randomEvent.setPersonID(person.getPersonID());
    randomEvent.setEventType(getEventFromJson());
    randomEvent.setYear(eventYear);
    randomEvent.setAssociatedUserName(username);

    eventArrayList.add(randomEvent);
  }








  public void FakeMarriageYear(Person husband, Person wife, int year)
  {
    Random random = new Random();
    int married_year = year + random.nextInt(6) +20;

    Event married_year_hus = fakeLocations.makeFakeLocation();
    married_year_hus.setPersonID(husband.getPersonID());
    married_year_hus.setAssociatedUserName(username);
    married_year_hus.setEventType("Marriage");
    married_year_hus.setYear(married_year);

    Event married_year_wife = AddMarriageToHusband(married_year_hus, wife, married_year);

    eventArrayList.add(married_year_hus);
    eventArrayList.add(married_year_wife);

  }

  private Event AddMarriageToHusband(Event married_year_hus, Person wife, int married_year )
  {
    Event married_year_wife = new Event();
    married_year_wife.setPersonID(wife.getPersonID());
    married_year_wife.setAssociatedUserName(username);
    married_year_wife.setEventType("Marriage");
    married_year_wife.setYear(married_year);
    married_year_wife.setCity(married_year_hus.getCity());
    married_year_wife.setCountry(married_year_hus.getCountry());
    married_year_wife.setLatitude(married_year_hus.getLatitude());
    married_year_wife.setLongitude(married_year_hus.getLongitude());

    return married_year_wife;

  }
}
