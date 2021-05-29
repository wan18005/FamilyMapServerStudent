package Result;
import Model.*;
import java.util.*;
public class  personResult
{
  private ArrayList<Person> personModel;
  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouseID;

  private boolean status;
  public String output;

  public personResult(String output, boolean status) {
    this.status=status;
    this.output=output;
  }

  public personResult(Person p) {
    this.setPersonID(p.getPersonID());
    this.setAssociatedUsername(p.getAssociatedUserName());
    this.setFirstName(p.getFirstName());
    this.setLastName(p.getLastName());
    this.setGender(p.getGender());
    this.setFatherID(p.getFatherID());
    this.setMotherID(p.getMotherID());
    this.setSpouseID(p.getSpouseID());
    this.setStatus(true);
  }

  public personResult(ArrayList<Person> personModel , Person p) {
    this.setPersonID(p.getPersonID());
    this.setAssociatedUsername(p.getAssociatedUserName());
    this.setFirstName(p.getFirstName());
    this.setLastName(p.getLastName());
    this.setGender(p.getGender());
    this.setFatherID(p.getFatherID());
    this.setMotherID(p.getMotherID());
    this.setSpouseID(p.getSpouseID());
    this.setPersonModel(personModel);
    this.setOutput(null);
    this.setStatus(true);
  }


  public ArrayList<Person> getPersonModel() {
    return personModel;
  }

  public void setPersonModel(ArrayList<Person> personModel) {
    this.personModel=personModel;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
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

  public String getFatherID() {
    return fatherID;
  }

  public void setFatherID(String fatherID) {
    this.fatherID=fatherID;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String motherID) {
    this.motherID=motherID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String spouseID) {
    this.spouseID=spouseID;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status=status;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output=output;
  }
}
