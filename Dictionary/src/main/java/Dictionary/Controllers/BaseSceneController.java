package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BaseSceneController implements Initializable {
    @FXML
    private Button mSetting, mSearch, mTranslate, mGame, mExit;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public void showComponent(String path) {

    }

}