package Dictionary.Controllers;

import Dictionary.Service.SynonymApi;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.json.JSONArray;
import org.json.JSONObject;

public class SynonymSceneController {
  @FXML
  private TextField searchTextField;
  @FXML
  private VBox contentVBox;

  @FXML
  public void onSubmitSearchBtn() {
    Thread thread = new Thread(() -> {
      JSONObject list = SynonymApi.getSynonymList(searchTextField.getText());
      Platform.runLater(() -> {
        contentVBox.getChildren().clear();
        fetchData(list, "synonyms");
        fetchData(list, "antonyms");
      });
    });
    thread.setDaemon(true);
    thread.start();
    contentVBox.getChildren().clear();
    contentVBox.getChildren().add(new Label("Searching..."));
  }

  private void fetchData(JSONObject list, String type) {
    JSONArray wordlist = list.getJSONArray(type);
    if (wordlist.length() < 1) return;
    TextFlow wordlistBox = new TextFlow();
    contentVBox.getChildren().add(new Label(type));
    contentVBox.getChildren().add(wordlistBox);
    for (Object v : wordlist) {
      Button wordItem = new Button((String) v);
      EventHandler<ActionEvent> event = e -> {
        searchTextField.setText((String) v);
        contentVBox.getChildren().clear();
        onSubmitSearchBtn();
      };
      wordItem.setOnAction(event);
      wordlistBox.getChildren().add(wordItem);
    }
  }
}

