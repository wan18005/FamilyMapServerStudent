package Result;
import Model.*;
import Services.*;


public class loadResult
{
  private String output;
  boolean status;

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

  public loadResult(String output, boolean status) {
    this.output=output;
    this.status=status;
  }
}
