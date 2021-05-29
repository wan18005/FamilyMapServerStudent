package Result;

public class registerResult {

  private String authtoken;
  private String userName;
  private String personID;
  private String output;
  private boolean status;

  public registerResult(String output, boolean status) {
    this.output=output;
    this.status=status;
  }

  public registerResult(String authtoken, String userName, String personID) {
    this.authtoken=authtoken;
    this.userName=userName;
    this.personID=personID;
    this.status=true;
  }

  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken=authtoken;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName=userName;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output=output;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status=status;
  }
}
