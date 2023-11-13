package Dictionary.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslatorApi {
  private static String translate(String langFrom, String langTo, String text) throws IOException {
    String urlStr = "https://script.google.com/macros/s/AKfycbx_IL9PUcJS4FBHRe5Chceb7HiaxP8NFTHIEnlnVHoMoVNkUK_bsvEjotvUggfritkfMA/exec" +
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
      response.append(inputLine);
    }
    in.close();
    return response.toString();
  }

  public static String translateEnglishToVietnamese(String text) {
    try {
      return translate("en", "vi", text);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "Cannot translate to Vietnamese";
  }

  public static String translateVietnameseToEnglish(String text) {
    try {
      return translate("vi", "en", text);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "Cannot translate to English";
  }

}
