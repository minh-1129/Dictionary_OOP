package Dictionary.Service;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class TextToSpeech {
  private static TextToSpeech audio;

  private TextToSpeech() {
  }

  public static TextToSpeech getInstance() {

    if (audio == null) {
      audio = new TextToSpeech();
    }
    return audio;
  }

  public static InputStream getAudio(String text, String languageOutput)
      throws IOException {

    URL url = new URL(
        "https://translate.google.com/translate_tts?ie=UTF-8&tl="
            + "en"
            + "&client=tw-ob&q="
            + URLEncoder.encode(text, StandardCharsets.UTF_8));
    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
    urlConn.addRequestProperty("User-Agent",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
    InputStream audioSrc = urlConn.getInputStream();
    return new BufferedInputStream(audioSrc);
  }

  public static void play(InputStream sound) throws JavaLayerException {
    new Player(sound).play();
  }

  public static void playSoundGoogleTranslateVietnamese(String text) throws IOException, JavaLayerException {
    TextToSpeech audio = TextToSpeech.getInstance();
    InputStream soundStream = audio.getAudio(text, "vi");
    audio.play(soundStream);
  }

  public static void playSoundGoogleTranslateEnglish(String text) throws IOException, JavaLayerException {
    TextToSpeech audio = TextToSpeech.getInstance();
    InputStream soundStream = audio.getAudio(text, "en");
    audio.play(soundStream);
  }

  public static void playSound(String text, String language) throws IOException, JavaLayerException {
    TextToSpeech audio = TextToSpeech.getInstance();
    InputStream soundStream = audio.getAudio(text, language);
    audio.play(soundStream);
  }

}
