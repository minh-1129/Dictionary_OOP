package Dictionary.Controllers;

import Dictionary.Helpers.StringUtils;
import Dictionary.Models.*;
import Dictionary.Service.TextToSpeech;

import static Dictionary.App.enenDictionary;
import static Dictionary.App.enviDictionary;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

import java.io.IOException;
import javafx.util.Callback;
import javazoom.jl.decoder.JavaLayerException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import java.net.URL;




public class SearchSceneController implements Initializable {

    private int lastIndex = 0;
    private String lastLookupWord;
    @FXML
    private Button deleteButton;
    @FXML
    private Button pronounceButton;
    @FXML
    private TextField searchBox;
    @FXML
    private ListView<String> searchResultsListView;
    @FXML
    private WebView showWeb;



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
                alert.setTitle("Cảnh báo");
                alert.setContentText("Từ này không nằm trong cơ sở dữ liệu Anh - Anh");
                alert.show();
            } else {
                lastLookupWord = target;
                String info = String.format("<html><body><h1>%s <br> <h4><i>%s<br>%s</i></h4></h1><h4>Definition:</h4><p>%s<p><h4>Example:</h4><p>%s<p><h4>Synonyms:</h4><p>%s<p><h4>Antonyms</h4><p>%s</p></body></html>"
                        , cur.getWordTarget(), cur.getType(), cur.getPronunciation(), cur.getMeaning(), cur.getExample(), cur.getSynonyms(), cur.getAntonyms());
                showWeb.getEngine().loadContent(info, "text/html");
            }
        } else if (SettingSceneController.getTransMode() == SettingSceneController.ENVI) {
            //Dich Anh-Viet
            EnViWord cur = enviDictionary.lookupWord(target);

            if (cur == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cảnh báo");
                alert.setContentText("Từ này không nằm trong cơ sở dữ liệu Anh - Việt");
                alert.show();
            } else {
                lastLookupWord = target;
                String info = String.format("<html><body>%s</body></html>", cur.getHtml());
                showWeb.getEngine().loadContent(info, "text/html");
            }

        } else {
            // Loi
            System.out.println("TransMode not found in lookup !!");
        }
    }

    @FXML
    public void clickSelectWord(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            String target = searchResultsListView.getSelectionModel().getSelectedItem();
            searchBox.setText(target);
            lookUpWord();
        }
    }

    @FXML
    public void playSound() {
        if (!lastLookupWord.isEmpty()) {
            try {
                TextToSpeech.getInstance().playSoundGoogleTranslateEnglish(lastLookupWord);
            } catch (JavaLayerException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setContentText("Từ điển hiện chưa thể phát âm từ này");
                alert.show();
            }

        }
    }

    @FXML
    public void deleteWord() {
        if (lastLookupWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Bạn chưa chọn từ để xóa");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xóa từ");
            alert.setHeaderText(
                    "Bạn có chắc là muốn xóa từ `"
                            + lastLookupWord
                            + "` khỏi cơ sở dữ liệu không ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent()) {
                if (option.get() == ButtonType.OK) {
                    if (SettingSceneController.getTransMode() == SettingSceneController.ENEN) {
                        if (enenDictionary.deleteWord(lastLookupWord)) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Thông báo");
                            alert1.setContentText("Xóa từ `" + lastLookupWord + "` thành công!");
                            alert1.show();
                        } else {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Lỗi");
                            alert1.setContentText(
                                    "Không tồn tại từ `" + lastLookupWord + "` trong từ điển Anh-Anh để xóa!");
                            alert1.show();
                        }
                    } else if (SettingSceneController.getTransMode() == SettingSceneController.ENVI) {
                        if (enviDictionary.deleteWord(lastLookupWord)) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Thông báo");
                            alert1.setContentText("Xóa từ `" + lastLookupWord + "` thành công!");
                            alert1.show();
                        } else {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Lỗi");
                            alert1.setContentText("Không tồn tại từ `" + lastLookupWord + "` trong từ điển Anh-Việt để xóa!");
                            alert1.show();
                        }
                    }
                }
            }
            lastLookupWord = "";
        }
    }

    @FXML
    public void editWord() {
        if (lastLookupWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setContentText("Chưa chọn từ để chỉnh sửa!");
            alert.show();
            return;
        } else {

        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        searchBox.setOnAction(event->lookUpWord());
    }



}
