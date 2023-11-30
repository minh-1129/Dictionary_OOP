package Dictionary.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslatorApi {
  public static String process_translate(String langFrom, String langTo, String text) throws IOException {
    String urlStr = "https://script.google.com/macros/s/AKfycbygaM-R8ptY1i7tRJBmfnLAxbCLe2sr-vlkgOaIHROW0dBYZe9zfm8VCXgqFBu5kOvXNw/exec" +
        "?q=" + URLEncoder.encode(text, "UTF-8") +
        "&target=" + langTo +
        "&source=" + langFrom;
    URL url = new URL(urlStr);
    StringBuilder response = new StringBuilder();
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestProperty("User-Agent", "Mozilla/5.0");
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine + "\n");
    }
    in.close();
    return response.toString();
  }

  public static String translateEnglishToVietnamese(String text) {
      return translate("en", "vi", text);
  }

  public static String translateVietnameseToEnglish(String text) {
      return translate("vi", "en", text);
  }

  public static String translate(String langFrom, String langTo, String text) {
    try {
      return process_translate(langFrom, langTo, text);
    } catch (IOException e) {
      return "Cannot translate";
    }
  }

}
