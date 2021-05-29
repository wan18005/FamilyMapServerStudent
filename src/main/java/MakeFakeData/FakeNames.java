package MakeFakeData;
import com.google.gson.*;
import java.io.*;
import java.util.*;


public class FakeNames {
  public String FakeMale()
  {
    try
    {
      Random random = new Random();
      JsonParser jsonParser = new JsonParser();
      FileReader fileReader = new FileReader(new File("json/mnames.json"));
      JsonObject jsonObject = (JsonObject) jsonParser.parse(fileReader);
      JsonArray jsonArray = (JsonArray) jsonObject.get("data");
      int  i = random.nextInt(jsonArray.size());
      String name = jsonArray.get(i).toString();
      name = name.substring(1,name.length()-1);
      return name;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }

    return "Error: in generating male name.\n";
  }


  public String FakeFemale()
  {
    try
    {
      Random random = new Random();
      JsonParser jsonParser = new JsonParser();
      FileReader fileReader = new FileReader(new File("json/fnames.json"));
      JsonObject jsonObject = (JsonObject) jsonParser.parse(fileReader);
      JsonArray jsonArray = (JsonArray) jsonObject.get("data");
      int  i = random.nextInt(jsonArray.size());
      String name = jsonArray.get(i).toString();
      name = name.substring(1,name.length()-1);
      return name;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }

    return "Error: in generating female name.\n";
  }

  public String FakeFirstName()
  {
    try
    {
      Random random = new Random();
      JsonParser jsonParser = new JsonParser();
      FileReader fileReader = new FileReader(new File("json/snames.json"));
      JsonObject jsonObject = (JsonObject) jsonParser.parse(fileReader);
      JsonArray jsonArray = (JsonArray) jsonObject.get("data");
      int  i = random.nextInt(jsonArray.size());
      String name = jsonArray.get(i).toString();
      name = name.substring(1,name.length()-1);
      return name;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }

    return "Error: in generating surname.\n";
  }

}
