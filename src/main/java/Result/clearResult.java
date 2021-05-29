package Result;

public class clearResult
{
  private String output;
  private boolean status;

  public clearResult(String output,boolean status)
  {
    this.output = output;
    this.status = status;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output=output;
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status=status;
  }
}
