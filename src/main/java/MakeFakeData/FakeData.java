package MakeFakeData;
import java.io.*;
import java.net.*;
import java.util.*;

import Model.Event;
import Model.Person;
import Services.*;
import Result.*;
import Handlers.*;
import com.google.gson.*;
import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpHandler;
import Request.*;
import Result.*;
import DAO.*;
import Services.*;
import MakeFakeData.*;

public class FakeData {
  private ArrayList<Person> personArrayList;
  private final FakeNames fakeNames = new FakeNames();
  private FakeEvent fakeEvent;
  private String username;
  private Random random = new Random();


  public FakeDataStorage numberofpeople (Person person, int generations)
  {

    username = person.getAssociatedUserName();
    fakeEvent = new FakeEvent(username);

    FamilyTree(person,generations);
    return new FakeDataStorage(personArrayList,fakeEvent.GetEvents());
  }

  private void FamilyTree(Person root , int generations)
  {

    int year = 2000 - (random.nextInt(10));
    personArrayList = new ArrayList<Person>();
    personArrayList.add(root);
    fakeEvent.DefaultBirth(root , year);

    FakeParents(root , generations -1 , year);
  }


  private Person FakeDad(Person child)
  {
    Person father = new Person();

    father.setAssociatedUserName(username);
    father.setFirstName(fakeNames.FakeMale());
    father.setLastName(child.getLastName());
    father.setGender("M");
    return father;
  }

  private Person FakeMom(Person child)
  {
    Person mother = new Person();

    mother.setAssociatedUserName(username);
    mother.setFirstName(fakeNames.FakeFemale());
    mother.setLastName(child.getLastName());
    mother.setGender("F");
    return mother;
  }

  private void FakeFamily(Person child, Person father, Person mother)
  {
    father.setSpouseID(mother.getSpouseID());
    mother.setSpouseID(father.getSpouseID());
    child.setFatherID(father.getPersonID());
    child.setMotherID(mother.getMotherID());
  }


  private void FakeLifeEvents(Person father, Person mother, int year)
  {
    fakeEvent.Birth(father,year);
    fakeEvent.Birth(mother,year);
    fakeEvent.FakeMarriageYear(father,mother,year);
    fakeEvent.Death(father,year);
    fakeEvent.Death(mother,year);


    int chance = random.nextInt(2);
    if (chance == 0 )
    {
      fakeEvent.Random(mother,year);
    }
    else
    {
      fakeEvent.Random(father,year);
    }
  }

  private void FakeParents(Person rootPerson, int generation_now , int year)
  {
    int Gap = 40;
    year = year - Gap;
    Person father = FakeDad(rootPerson);
    Person mother = FakeMom(rootPerson);
    FakeFamily(rootPerson,father,mother);
    FakeLifeEvents(father,mother,year);

    personArrayList.add(father);
    personArrayList.add(mother);

    if (generation_now !=0)
    {
      FakeParents(father,generation_now -1 ,year);
      FakeParents(mother,generation_now -1 ,year);
    }
  }


}
