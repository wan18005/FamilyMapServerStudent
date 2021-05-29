package Model;

import java.util.Objects;
import java.util.UUID;

/**
 * The person class that represents a person in the data base. This class contains information like the
 * username of the person this person is associated with, the first name, last name, gender, and family ID tags
 * that the person has.
 */
public class Person {

  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouseID;

  /**
   * The default constructor that initializes all of the local variables to null.
   */
  public Person() {
    personID = UUID.randomUUID().toString();
    associatedUsername = null;
    firstName = null;
    lastName = null;
    gender = null;
    fatherID = null;
    motherID = null;
    spouseID = null;
  }

  /**
   * The parameterized Person constructor that creates a pojo of a person data
   *
   * @param setPersonID           the person's unique ID
   * @param setAssociatedUsername the username that the user is using
   * @param setFirstName          the person's first name
   * @param setLastName           the person's last name
   * @param setGender             the person's gender
   * @param setFatherID           the person's father's unique ID
   * @param setMotherID           the person's mother's unique ID
   * @param setSpouseID           the person's spouse's unique ID if they are married
   */
  public Person(String setPersonID, String setAssociatedUsername, String setFirstName, String setLastName, String setGender,
                String setFatherID, String setMotherID, String setSpouseID) {
    this.personID = setPersonID;
    this.associatedUsername = setAssociatedUsername;
    this.firstName = setFirstName;
    this.lastName = setLastName;
    this.gender = setGender;
    this.fatherID = setFatherID;
    this.motherID = setMotherID;
    this.spouseID = setSpouseID;
  }

  public String getPersonID() { return personID; }
  public String getAssociatedUserName() { return associatedUsername; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getGender() { return gender; }
  public String getFatherID() { return fatherID; }
  public String getMotherID() { return motherID; }
  public String getSpouseID() { return spouseID;}

  public void setPersonID(String personID) { this.personID = personID; }
  public void setAssociatedUserName(String associatedUsername) { this.associatedUsername = associatedUsername; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public void setGender(String gender) { this.gender = gender; }
  public void setFatherID(String fatherID) { this.fatherID = fatherID; }
  public void setMotherID(String motherID) { this.motherID = motherID; }
  public void setSpouseID(String spouseID) { this.spouseID = spouseID; }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof Person) {
      Person oPerson = (Person) o;
      return oPerson.getPersonID().equals(getPersonID()) &&
              oPerson.getAssociatedUserName().equals(getAssociatedUserName()) &&
              oPerson.getFirstName().equals(getFirstName()) &&
              oPerson.getLastName().equals(getLastName()) &&
              oPerson.getGender().equals(getGender()) &&
              Objects.equals(oPerson.getFatherID(), getFatherID()) &&
              Objects.equals(oPerson.getMotherID(), getMotherID()) &&
              Objects.equals(oPerson.getSpouseID(), getSpouseID());
    } else {
      return false;
    }
  }
}
