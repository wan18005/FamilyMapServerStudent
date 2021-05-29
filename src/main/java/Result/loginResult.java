package Result;

public class loginResult
{
  private String userName;
  private String personID;
  public boolean status;
  private String authtoken;
  private String output;


  public void nullCheck()
  {
    personID = null;
    authtoken = null;
    output = "Error: Login failed";
  }

  public loginResult(String output ,boolean status) {
    this.status=status;
    this.output=output;
  }

  public loginResult(String authtoken,String userName, String personID) {
    this.userName=userName;
    this.personID=personID;
    this.status=true;
    this.authtoken=authtoken;
    this.output=output;
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

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status=status;
  }

  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken=authtoken;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output=output;
  }
}
