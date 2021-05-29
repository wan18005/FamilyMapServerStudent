package Result;

import Model.Event;
import java.util.*;

public class eventResult {
 private ArrayList<Event> eventModel;
  private String eventID;
  private String associatedUsername;
  private String personID;
  private String country;
  private String city;
  private String eventType;
  private Float latitude;
  private Float longitude;
  private Integer year;
 private boolean status;
 private String output;


  public eventResult(String output,boolean status) {
    this.status=status;
    this.output=output;
  }

  public eventResult(ArrayList<Event> eventModel) {
    this.eventModel=eventModel;
    this.setStatus(true);
  }


  public eventResult(Event event,String userName) {
    this.setEventID(event.getEventID());
    this.setAssociatedUsername(userName);
    this.setPersonID(event.getPersonID());
    this.setCountry(event.getCountry());
    this.setCity(event.getCity());
    this.setEventType(event.getEventType());
    this.setLatitude(event.getLatitude());
    this.setLongitude(event.getLongitude());
    this.setYear(event.getYear());
    this.setStatus(true);
  }

  public ArrayList<Event> getEventModel() {
    return eventModel;
  }

  public String getEventID() {
    return eventID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public String getPersonID() {
    return personID;
  }

  public String getCountry() {
    return country;
  }

  public String getCity() {
    return city;
  }

  public String getEventType() {
    return eventType;
  }

  public Float getLatitude() {
    return latitude;
  }

  public Float getLongitude() {
    return longitude;
  }

  public Integer getYear() {
    return year;
  }

  public boolean isStatus() {
    return status;
  }

  public String getOutput() {
    return output;
  }


  public void setEventModel(ArrayList<Event> eventModel) {
    this.eventModel=eventModel;
  }

  public void setEventID(String eventID) {
    this.eventID=eventID;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public void setCountry(String country) {
    this.country=country;
  }

  public void setCity(String city) {
    this.city=city;
  }

  public void setEventType(String eventType) {
    this.eventType=eventType;
  }

  public void setLatitude(Float latitude) {
    this.latitude=latitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude=longitude;
  }

  public void setYear(Integer year) {
    this.year=year;
  }

  public void setStatus(boolean status) {
    this.status=status;
  }

  public void setOutput(String output) {
    this.output=output;
  }
}
