package MakeFakeData;
import Model.*;
import com.google.gson.*;

import java.io.*;
import java.util.*;

public class FakeLocations {
  public Event makeFakeLocation(){
    try
    {
      Random random = new Random();
      JsonParser jsonParser = new JsonParser();
      FileReader fileReader = new FileReader(new File("json/locations.json"));
      JsonObject jsonObject = (JsonObject) jsonParser.parse(fileReader);
      JsonArray jsonArray = (JsonArray) jsonObject.get("data");
      int  i = random.nextInt(jsonArray.size());

      JsonObject location = (JsonObject) jsonArray.get(i);
      String city = location.get("city").toString();
      String country = location.get("country").toString();
      float latitude = location.get("latitude").getAsFloat();
      float longitude = location.get("longitude").getAsFloat();

      return new Event(city,country,latitude,longitude);


    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    return new Event("Null city" , "Null country", 00.00f,00.00f);
  }
}
