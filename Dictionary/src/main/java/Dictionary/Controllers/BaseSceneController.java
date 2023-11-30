package Dictionary.Controllers;

import Dictionary.App;
import Dictionary.WordleGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BaseSceneController implements Initializable {
    @FXML
    private Button mSetting, mSearch, mTranslate, mGame, mAdd, mQuiz, mChatBot;

    @FXML
    private Button mSynonym;

    @FXML
    private AnchorPane mainLayout;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showComponent("/View/SearchScene.fxml");
        mSearch.setOnAction(event -> showComponent("/View/SearchScene.fxml"));

        mTranslate.setOnAction(event -> showComponent("/View/TranslateScene.fxml"));

        mGame.setOnAction(event -> showWordle());
        mSetting.setOnAction(event -> showComponent("/View/SettingScene.fxml"));
        //mSetting.setOnAction(event -> showComponentSetting("/View/SettingScene.fxml"));
        mAdd.setOnAction(event -> showComponentAdd());
        mQuiz.setOnAction(event -> showComponentQuiz() );
        mSynonym.setOnAction(event -> showComponent("/View/SynonymScene.fxml") );
        mChatBot.setOnAction(event -> showComponent("/View/ChatBotScene.fxml"));
    }

    public void setNode(Node node) {
        mainLayout.getChildren().clear();
        mainLayout.getChildren().add(node);
    }

    @FXML
    public void showWordle() {
        if (!WordleGame.getInstance().isRunning()) {
            WordleGame.getInstance().init();
        }
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
            showComponent("/View/AddWordSceneVi.fxml");
        }
    }

    @FXML
    public void showComponentQuiz() {
        if(SettingSceneController.getQuizMode() == SettingSceneController.Quiz) {
            showComponent("/View/QuizScene.fxml");
        } else {
            showComponent("/View/PracticeEnglishScene.fxml");
        }
    }
}