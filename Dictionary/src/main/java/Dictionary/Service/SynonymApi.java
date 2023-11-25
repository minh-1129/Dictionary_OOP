package Dictionary.Service;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

public class SynonymApi {
  public static JSONObject getSynonymList (String word) {
    try {
      String apiUrl = "https://api.api-ninjas.com/v1/thesaurus?word=" + word;
      String apiKey = "BIf0OcLCKvXdfEJ+ZoBE9A==c0EsICN5G5nEQGra";
      URL url = new URL(apiUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("X-Api-Key", apiKey);

      int responseCode = connection.getResponseCode();
      if (responseCode == 200) {
        InputStream inputStream = connection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        StringBuilder response = new StringBuilder();
        while (scanner.hasNextLine()) {
          response.append(scanner.nextLine());
        }
        scanner.close();
        return new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
      } else {
        System.out.println("Failed to get response. Response code: " + responseCode);
        return new JSONObject("{\"synonyms\":[],\"antonyms\":[]}");
      }
    } catch (IOException e) {
      return new JSONObject("{\"synonyms\":[],\"antonyms\":[]}");
    }
  }
  public static void main(String[] args) {

  }
}

