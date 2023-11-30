package Dictionary.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class OpenAIApi implements API {
  public static void chatGPT(String text) throws Exception {
    String linkUrl = "https://api.openai.com/v1/completions";
    URL url = API.createURL(linkUrl);
    HttpURLConnection con = API.openConnection(url);
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Authorization", "Bearer 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");
    JSONObject data = new JSONObject();
    data.put("model", "text-davinci-003");
    data.put("prompt", text);
    data.put("max_tokens", 4000);
    data.put("temperature", 1.0);

    con.setDoOutput(true);
    con.getOutputStream().write(data.toString().getBytes());

    String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
        .reduce((a, b) -> a + b).get();

    System.out.println(new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
  }

  public static void main(String[] args) throws Exception {
    //chatGPT("Bạn biết ai là người vĩ đại nhất Việt Nam khôngq");
    chatGPT("\"role\": \"system\", \"content\": \"You are a helpful assistant.\"},\n" +
            "    {\"role\": \"user\", \"content\": \"What's the weather like today?\"},\n" +
            "    {\"role\": \"assistant\", \"content\": \"I'm sorry, I cannot provide real-time weather information.\"},");
  }
}
