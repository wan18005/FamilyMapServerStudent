package MakeFakeData;
import java.util.*;
import java.io.*;

import Model.*;

public class FakeDataStorage {
  private ArrayList<Person> personArrayList = null;
  private ArrayList<Event> eventArrayList = null;

  public FakeDataStorage(ArrayList<Person> personArrayList, ArrayList<Event> eventArrayList) {
    this.personArrayList=personArrayList;
    this.eventArrayList=eventArrayList;
  }

  public ArrayList<Person> getPersonArrayList() {
    return personArrayList;
  }

  public void setPersonArrayList(ArrayList<Person> personArrayList) {
    this.personArrayList=personArrayList;
  }

  public ArrayList<Event> getEventArrayList() {
    return eventArrayList;
  }

  public void setEventArrayList(ArrayList<Event> eventArrayList) {
    this.eventArrayList=eventArrayList;
  }
}
