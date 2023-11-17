package Dictionary.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BaseSceneController implements Initializable {
    @FXML
    private Button mSetting, mSearch, mTranslate, mGame, mAdd;

    @FXML
    private AnchorPane mainLayout;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showComponent("/View/SearchScene.fxml");
        mSearch.setOnAction(event -> showComponent("/View/SearchScene.fxml"));

        mTranslate.setOnAction(event -> showComponent("/View/TranslateScene.fxml"));

        mGame.setOnAction(event -> showComponent("/View/GameScene.fxml"));
        mSetting.setOnAction(event -> showComponent("/View/SettingScene.fxml"));
        mAdd.setOnAction(event -> showComponentAdd());
    }

    public void setNode(Node node) {
        mainLayout.getChildren().clear();
        mainLayout.getChildren().add(node);
    }

    @FXML
    public void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showComponentAdd() {
        if(SettingSceneController.getTransMode() == SettingSceneController.ENEN) {
            showComponent("/View/AddWordSceneEn.fxml");
        } else {

        }
    }
}