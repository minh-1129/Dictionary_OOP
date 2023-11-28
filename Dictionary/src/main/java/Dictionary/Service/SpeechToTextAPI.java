package Dictionary.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

public class SpeechToTextAPI {
  public static String uploadFileSpeech (String pathToSpeechRecorder) {
    try {
      URL url = new URL("https://api.assemblyai.com/v2/upload");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      String authHeaderKey = "7487d8c8c0004392ab7aec5cde30ef36";
      connection.setRequestProperty("authorization", authHeaderKey);
      connection.setRequestProperty("Transfer-Encoding", "chunked");
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      File fileSpeechRecorder = new File(pathToSpeechRecorder);
      byte[] bytes = new byte[(int) fileSpeechRecorder.length()];
      try (OutputStream outputStream = connection.getOutputStream()) {
        FileInputStream fis = new FileInputStream(fileSpeechRecorder);
        fis.read(bytes);
        outputStream.write(bytes, 0, bytes.length);
        //fis.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream(),
          StandardCharsets.UTF_8));
      StringBuilder response = new StringBuilder();
      String inputLine;
      while ((inputLine = inputStream.readLine()) != null) {
        response.append(inputLine.trim());
      }
      inputStream.close();
      JSONObject url_path = new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
      return url_path.getString("upload_url");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "error_upload_file";
  }

  public static String postSpeechToTextApi(String wordForm) {
    try {
      URL url = new URL("https://api.assemblyai.com/v2/transcript");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      String authHeaderKey = "7487d8c8c0004392ab7aec5cde30ef36";
      connection.setRequestProperty("authorization", authHeaderKey);
      connection.setRequestProperty("content-type", "application/json; utf-8");
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      String jsonInputString = "{\"audio_url\": " + "\"" + wordForm + "\"}";
      try (OutputStream outputStream = connection.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        outputStream.write(input, 0, input.length);
      } catch (Exception e) {
        e.printStackTrace();
      }
      BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
      StringBuilder response = new StringBuilder();
      String inputLine;
      while ((inputLine = inputStream.readLine()) != null) {
        response.append(inputLine.trim());
      }
      inputStream.close();

      JSONObject id = new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
      return id.getString("id");
    } catch (IOException e) {
      return "error: ";
    }
  }

  public static String getSpeechToTextApi(String wordForm) {
    try {
      String stringURL = "https://api.assemblyai.com/v2/transcript/" + wordForm;
      URL url = new URL(stringURL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      String authHeaderKey = "7487d8c8c0004392ab7aec5cde30ef36";
      connection.setRequestProperty("authorization", authHeaderKey);
      connection.setRequestMethod("GET");
      BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder response = new StringBuilder();
      String inputLine;
      while ((inputLine = inputStream.readLine()) != null) {
        response.append(inputLine);
      }
      inputStream.close();
      JSONObject textTranslated = new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
      if (textTranslated.get("status").equals("error")) {
        return "error";
      }
      return textTranslated.get("text").toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "error";
  }

  public static String getSpeechToText() {
    String url_path = uploadFileSpeech("Dictionary/src/main/resources/FileRecording/temp.wav");
    String idApiServer = postSpeechToTextApi(url_path);
    String wordTranslate;
    while (true) {
      wordTranslate = getSpeechToTextApi(idApiServer);
      if (Objects.equals(wordTranslate, "error") || Objects.equals(wordTranslate, "null")) {
        continue;
      }
      break;
    }
    if (wordTranslate != null && !wordTranslate.isEmpty()) {
      return wordTranslate.substring(0, wordTranslate.length() - 1).toLowerCase();
    } else {
      return "Cannot detect text";
    }

  }

  public static void main(String[] args) {
    String result = getSpeechToText();
    System.out.println(result);
  }
}



