package Dictionary.Controllers;

import Dictionary.Service.TextToSpeech;
import Dictionary.Service.TranslatorApi;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javazoom.jl.decoder.JavaLayerException;

public class TranslateSceneController implements Initializable {

  @FXML
  public ComboBox<String> sourceLanguage, targetLanguage;

  ObservableList<String> listSourceLanguage = FXCollections.observableArrayList("Phát hiện ngôn ngữ",
      "Tiếng Anh", "Tiếng Hàn", "Tiếng Trung", "Tiếng Nhật", "Tiếng Việt");
  ObservableList<String> listTargetLanguage = FXCollections.observableArrayList("Tiếng Việt",
      "Tiếng Anh", "Tiếng Trung","Tiếng Hàn", "Tiếng Nhật");

  @FXML
  TextArea inputText, outputText;

  @FXML
  ImageView btnSwap, speakerTextInInput, speakerTextInOutput;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    sourceLanguage.setItems(listSourceLanguage);
    targetLanguage.setItems(listTargetLanguage);
    sourceLanguage.setValue("Phát hiện ngôn ngữ");
    targetLanguage.setValue("Tiếng Việt");
  }

  private String getLanguageCode(String language) {
    switch (language) {
      case "Tiếng Việt":
        return "vi";
      case "Tiếng Anh":
        return "en";
      case "Tiếng Trung":
        return "zh-CN";
      case "Tiếng Nhật":
        return "ja";
      case "Tiếng Hàn":
        return "ko";
      default:
        return "";
    }
  }

  @FXML
  public void translate() {
    String inputLanguage = getLanguageCode(sourceLanguage.getValue());
    String outputLanguage = getLanguageCode(targetLanguage.getValue());
    String textToTranslate = inputText.getText();
    if (textToTranslate.isBlank()) {
      outputText.setText("");
      return;
    }

    if (inputLanguage.equals(outputLanguage)) {
      outputText.setText(textToTranslate);
      return;
    }
    var executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      try {
        String translatedText = TranslatorApi.translate(inputLanguage, outputLanguage, textToTranslate);
        Platform.runLater(() -> {
          outputText.setText(translatedText);
        });
      } catch (Exception e) {
        Platform.runLater(() -> {
          outputText.setText("Translation error: " + e.getMessage());
        });
      } finally {
        executorService.shutdown();
      }
    });
  }

  @FXML
  public void swap() {
    if (sourceLanguage.getValue().equals("Phát hiện ngôn ngữ")) {
      return;
    }
    String currentOutputLanguage = targetLanguage.getValue();
    String currentTextInOutput = outputText.getText();
    targetLanguage.setValue(sourceLanguage.getValue());
    outputText.setText(inputText.getText());
    sourceLanguage.setValue(currentOutputLanguage);
    inputText.setText(currentTextInOutput);
  }

  @FXML
  public void translateTextInInput() {
    String language = getLanguageCode(sourceLanguage.getValue());
    if (language.isBlank()) {
      return;
    }
    String textToTranslate = inputText.getText();
    if (textToTranslate.isBlank()) {
      return;
    }
    var executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      try {
        String translatedText = TranslatorApi.translate("", language, textToTranslate);
        Platform.runLater(() -> {
          inputText.setText(translatedText);
        });
      } catch (Exception e) {
        Platform.runLater(() -> {
          System.err.println("Translation error: " + e.getMessage());
        });
      } finally {
        executorService.shutdown();
      }
    });
  }

  @FXML
  public void translateTextInOutput() {
    String language = getLanguageCode(targetLanguage.getValue());
    String textToTranslate = outputText.getText();
    if (textToTranslate.isBlank()) {
      return;
    }
    var executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      try {
        String translatedText = TranslatorApi.translate("", language, textToTranslate);
        Platform.runLater(() -> {
          outputText.setText(translatedText);
        });
      } catch (Exception e) {
        Platform.runLater(() -> {
          System.err.println("Translation error: " + e.getMessage());
        });
      } finally {
        executorService.shutdown();
      }
    });
  }

  @FXML
  public void speakTextInInput() {
    String textInInput = inputText.getText();
    String language = getLanguageCode(sourceLanguage.getValue());
    if (textInInput.isBlank() || textInInput == null || language.isBlank()) {
      return;
    }
    var executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      try {
        TextToSpeech.playSound(textInInput, language);
      } catch (Exception e) {
        Platform.runLater(() -> {
          System.err.println("Text to speech error: " + e.getMessage());
        });
      } finally {
        executorService.shutdown();
      }
    });
  }

  @FXML
  public void speakTextInOutput() {
    if (outputText != null) {
      String textInOutput = outputText.getText();
      String language = getLanguageCode(targetLanguage.getValue());
      if (textInOutput != null && !textInOutput.isBlank()) {
        var executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
          try {
            TextToSpeech.playSound(textInOutput, language);
          } catch (Exception e) {
            Platform.runLater(() -> {
              System.err.println("Text to speech error: " + e.getMessage());
            });
          } finally {
            executorService.shutdown();
          }
        });
      }
    }
  }
}
