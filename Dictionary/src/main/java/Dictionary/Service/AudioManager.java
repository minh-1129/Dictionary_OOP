package Dictionary.Service;

import java.io.*;
import javax.sound.sampled.*;
public class AudioManager {
  private static TargetDataLine microphone;
  private static SourceDataLine speaker;
  public static boolean RECORDING = false;

  public static boolean isRecording() {
    return RECORDING;
  }

  public static void startRecording() {

    try {
      RECORDING = true;
      AudioFormat format = new AudioFormat(8000, 8, 1, true, true);
      DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
      microphone = AudioSystem.getTargetDataLine(format);
      microphone = (TargetDataLine) AudioSystem.getLine(info);
      microphone.open(format);
      microphone.start();
      AudioInputStream ais = new AudioInputStream(microphone);
      AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File("Dictionary/src/main/resources/FileRecording/temp.wav"));
      microphone.close();
    } catch(LineUnavailableException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void stopRecording() {
    microphone.stop();
    microphone.close();
    RECORDING = false;
  }

  public static void main(String[] args) {
    startRecording();
  }
}
