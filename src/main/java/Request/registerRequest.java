package Request;

public class registerRequest {

 private String userName;
  private String passWord;
  private String email_address;
  private String firstName;
  private String lastName;
  private String gender;
  private String personID;



  public registerRequest() {
    userName = null;
    passWord = null;
    email_address = null;
    firstName = null;
    lastName = null;
    gender = null;
    personID = null;
  }

  public registerRequest(String userName, String passWord, String email_address, String firstName, String lastName, String gender) {
    this.userName=userName;
    this.passWord=passWord;
    this.email_address=email_address;
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    this.personID = null;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName=userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord=passWord;
  }

  public String getEmail_address() {
    return email_address;
  }

  public void setEmail_address(String email_address) {
    this.email_address=email_address;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName=firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName=lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender=gender;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }
}
