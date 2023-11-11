package Dictionary.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import java.util.ArrayList;
import javafx.util.Callback;
import java.util.ResourceBundle;
import java.net.URL;

public class SettingSceneController implements Initializable{
    public static final String ENEN = "ENEN", ENVI = "ENVI";
    private static String transMode = ENVI;
    private String[] choices = {ENEN, ENVI};

    @FXML private ChoiceBox<String> mTransModeChoice;



    public static String getTransMode() {
        return transMode;
    }

    public void getChoice(ActionEvent event) {
        String mChoice = mTransModeChoice.getValue();
        transMode = mChoice;
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        mTransModeChoice.getItems().addAll(choices);
        mTransModeChoice.setOnAction(this::getChoice);
    }


}


