package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchSceneController implements Initializable {
    @FXML
    private Button mSelect, mDelete, mSound;

    @FXML
    private TextField mWord;

    @FXML
    private TextArea mDefinition;

    @FXML
    private ListView <String> mListView;

    @FXML
    public void initialize(URL var1, ResourceBundle var2){
        ;
    }

    @FXML
    public void selectWord() {

    }

    @FXML
    public void deleteWord() {

    }

    @FXML
    public void soundWord() {

    }
}
