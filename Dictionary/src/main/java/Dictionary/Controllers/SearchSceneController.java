package Dictionary.Controllers;

import Dictionary.Helpers.StringUtils;
import Dictionary.Models.*;

import static Dictionary.App.enenDictionary;
import static Dictionary.App.enviDictionary;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import javafx.util.Callback;
import java.util.ResourceBundle;

import java.net.URL;




public class SearchSceneController implements Initializable {

    private int lastIndex = 0;
    @FXML private TextField searchBox;
    @FXML private ListView<String> searchResultsListView;
    @FXML private WebView showWeb;


    @FXML
    public void prepareSearchList() {
        searchResultsListView.getItems().clear();
        String target = searchBox.getText();
        ArrayList<String> searchedWords = new ArrayList<>();
        if (SettingSceneController.getTransMode() == SettingSceneController.ENEN) {
            target = StringUtils.normalizeEnEnString(target);
            searchedWords = enenDictionary.trie.search(target);
        } else {
            searchedWords = enviDictionary.trie.search(target);
        }
        for (String word : searchedWords) {
            searchResultsListView.getItems().add(word);
        }
        searchResultsListView.setCellFactory(
                new Callback<>() {
                    @Override
                    public ListCell<String> call(ListView<String> list) {
                        return new ListCell<>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    setGraphic(null);
                                    setText(item);
                                    setFont(Font.font(15));
                                    //setStyle("-fx-background-color: #33CEFF");
                                }
                            }
                        };
                    }
                }
        );
    }

    @FXML
    public void lookUpWord() {
        String target = searchBox.getText();

        //Dich Anh-Anh
        if (SettingSceneController.getTransMode() == SettingSceneController.ENEN) {
            target = StringUtils.normalizeEnEnString(target);
            EnEnWord cur = enenDictionary.lookupWord(target);

            if (cur == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setContentText("This words does not exist in our en-en database !!");
                alert.show();
            } else {
                String info = String.format("<html><body><h1>%s <br> <h4><i>%s</i></h4></h1><h4>Definition:</h4><p>%s<p><h4>Example:</h4><p>%s<p></body></html>"
                        , cur.getWordTarget(), cur.getPronunciation(), cur.getMeaning(), cur.getExample());
                showWeb.getEngine().loadContent(info, "text/html");
            }
        } else if (SettingSceneController.getTransMode() == SettingSceneController.ENVI) {
            //Dich Anh-Viet
            EnViWord cur = enviDictionary.lookupWord(target);

            if (cur == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setContentText("This words does not exist in our en-vi database !!");
                alert.show();
            } else {
                String info1 = String.format("<html><body>%s</body></html>", cur.getHtml());
                showWeb.getEngine().loadContent(info1, "text/html");
            }

        } else {
            // Loi
            System.out.println("TransMode not found in lookup !!");
        }
    }
    @FXML
    public void selectWord(KeyEvent e) {
        if (searchResultsListView.getSelectionModel().getSelectedIndices().isEmpty()) {
            return;
        }
        if (e.getCode() == KeyCode.ENTER) {
            String target = searchResultsListView.getSelectionModel().getSelectedItem();
            searchBox.setText(target);
            lookUpWord();
        } else if (e.getCode() == KeyCode.UP) {
            if (searchResultsListView.getSelectionModel().getSelectedIndex() == 0 && lastIndex == 0) {
                searchBox.requestFocus();
            }
        }
        lastIndex = searchResultsListView.getSelectionModel().getSelectedIndex();
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        searchBox.setOnAction(event->lookUpWord());
    }



}
