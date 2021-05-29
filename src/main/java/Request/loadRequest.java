package Request;

import Model.*;

import java.util.*;

public class loadRequest {
  public User[] userModel;
  public Person[] personModel;
  public Event[] eventModel;


  public loadRequest() {
    userModel = null;
    personModel = null;
    eventModel = null;
  }

  public loadRequest(User[] userModel, Person[] personModel, Event[] eventModel) {
    this.userModel=userModel;
    this.personModel=personModel;
    this.eventModel=eventModel;
  }

  public User[] getUserModel() {
    return userModel;
  }

  public void setUserModel(User[] userModel) {
    this.userModel=userModel;
  }

  public Person[] getPersonModel() {
    return personModel;
  }

  public void setPersonModel(Person[] personModel) {
    this.personModel=personModel;
  }

  public Event[] getEventModel() {
    return eventModel;
  }

  public void setEventModel(Event[] eventModel) {
    this.eventModel=eventModel;
  }
}
