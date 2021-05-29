package Model;
import java.util.*;
public class User
{
  private String userName;
  private String passWord;
  private String email_address;
  private String firstName;
  private String lastName;
  private String gender;
  private String personID;

  public User() {
    userName = null;
    passWord = null;
    email_address = null;
    firstName = null;
    lastName = null;
    gender = null;
    personID = null;
  }

  public User(String userName, String passWord, String email_address, String firstName, String lastName, String gender, String personID) {
    this.userName=userName;
    this.passWord=passWord;
    this.email_address=email_address;
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    this.personID=personID;
  }

  public String getUserName() { return userName; }

  public String getPassWord() {
    return passWord;
  }

  public String getEmail_address() {
    return email_address;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getGender() {
    return gender;
  }

  public String getPersonID() {
    return personID;
  }

  public void setUserName(String userName) {
    this.userName=userName;
  }

  public void setPassWord(String passWord) {
    this.passWord=passWord;
  }

  public void setEmail_address(String email_address) {
    this.email_address=email_address;
  }

  public void setFirstName(String firstName) {
    this.firstName=firstName;
  }

  public void setLastName(String lastName) {
    this.lastName=lastName;
  }

  public void setGender(String gender) {
    this.gender=gender;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof User) {
      User oUser = (User) o;
      return oUser.getUserName().equals(getUserName()) &&
              oUser.getPassWord().equals(getPassWord()) &&
              oUser.getEmail_address().equals(getEmail_address()) &&
              oUser.getFirstName().equals(getFirstName()) &&
              oUser.getLastName().equals(getLastName()) &&
              oUser.getGender().equals(getGender()) &&
              oUser.getPersonID().equals(getPersonID());
    } else {
      return false;
    }
  }
}


