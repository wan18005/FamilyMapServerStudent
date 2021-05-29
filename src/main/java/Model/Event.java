package Model;

import java.util.UUID;

/**
 * The event class represents important events from the persons life. These events contain information like the
 * unique event identification tag, the username that the event is associated with,  the country and city where it took place,
 * the latitude and longitude of where it took place, the type of event that took place, and the year that it took place.
 */
public class Event {

  private String eventID;
  private String associatedUsername;
  private String personID;
  private String country;
  private String city;
  private String eventType;
  private float latitude;
  private float longitude;
  private int year;

  public Event() {
    eventID = UUID.randomUUID().toString();
    associatedUsername = null;
    personID = null;
    country = null;
    city = null;
    eventType = null;
    latitude = 0.0f;
    longitude = 0.0f;
    year = 0;
  }

  /**
   * The event pojo that represents an event that takes place in a persons life.
   *
   * @param setEventID            the unique identifier for the event.
   * @param setAssociatedUsername the user's username that is tied to this event.
   * @param setPersonID           the user's ID that this event is tied to.
   * @param setCountry            the country where the event took place.
   * @param setCity               the city where the event took place.
   * @param setEventType          the type of event that happened (birth, baptism, christening, marriage, death, etc.).
   * @param setLatitude           the latitude of event's location.
   * @param setLongitude          the longitude of event's location.
   * @param setYear               the year the event took place.
   */
  public Event(String setEventID, String setAssociatedUsername, String setPersonID, String setCountry, String setCity, String setEventType,
               float setLatitude, float setLongitude, int setYear) {
    this.setEventID(setEventID);
    this.setAssociatedUserName(setAssociatedUsername);
    this.setPersonID(setPersonID);
    this.setCountry(setCountry);
    this.setCity(setCity);
    this.setEventType(setEventType);
    this.setLatitude(setLatitude);
    this.setLongitude(setLongitude);
    this.setYear(setYear);
  }

  public Event(String setCity, String setCountry, float setLatitude, float setLongitude) {
    this.eventID = UUID.randomUUID().toString();
    this.setCity(setCity);
    this.setCountry(setCountry);
    this.setLatitude(setLatitude);
    this.setLongitude(setLongitude);
  }

  public String getEventID() { return eventID; }
  public String getAssociatedUserName() { return associatedUsername; }
  public String getPersonID() { return personID; }
  public String getCountry() { return country; }
  public String getCity() { return city; }
  public String getEventType() { return eventType; }
  public float getLatitude() { return latitude; }
  public float getLongitude() { return longitude; }
  public int getYear() { return year; }

  public void setEventID(String setEventID) { this.eventID = setEventID; }
  public void setAssociatedUserName(String setAssociatedUsername) { this.associatedUsername = setAssociatedUsername; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public void setCountry(String setCountry) { this.country = setCountry; }
  public void setCity(String setCity) { this.city = setCity; }
  public void setEventType(String setEventType) { this.eventType = setEventType; }
  public void setLatitude(Float setLatitude) { this.latitude = setLatitude; }
  public void setLongitude(Float setLongitude) { this.longitude = setLongitude; }
  public void setYear(Integer setYear) { this.year = setYear; }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof Event) {
      Event oEvent = (Event) o;
      return oEvent.getEventID().equals(getEventID()) &&
              oEvent.getAssociatedUserName().equals(getAssociatedUserName()) &&
              oEvent.getPersonID().equals(getPersonID()) &&
              oEvent.getLatitude() == (getLatitude()) &&
              oEvent.getLongitude() == (getLongitude()) &&
              oEvent.getCountry().equals(getCountry()) &&
              oEvent.getCity().equals(getCity()) &&
              oEvent.getEventType().equals(getEventType()) &&
              oEvent.getYear() == (getYear());
    } else {
      return false;
    }
  }
}