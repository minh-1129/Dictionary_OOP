package Dictionary.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.util.ResourceBundle;
import java.net.URL;

public class SettingSceneController implements Initializable{
    public static final String ENEN = "ENEN", ENVI = "ENVI";
    private static String transMode = ENVI;
    private String[] choicesTrans = {ENEN, ENVI};

    public static final String Quiz = "Quiz", LisPrac = "LisPrac";
    private static String QuizMode = Quiz;
    private String[] choicesQuiz = {Quiz, LisPrac};

    @FXML private ChoiceBox<String> mTransModeChoice, mQuizModeChoice;



    public static String getTransMode() {
        return transMode;
    }

    public static String getQuizMode() {
        return QuizMode;
    }

    public void getTransChoice(ActionEvent event) {
        String mChoice = mTransModeChoice.getValue();
        transMode = mChoice;
    }

    public void getQuizChoice(ActionEvent event) {
        String mChoice = mQuizModeChoice.getValue();
        QuizMode = mChoice;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        mTransModeChoice.getItems().addAll(choicesTrans);
        mTransModeChoice.setOnAction(this::getTransChoice);
        mQuizModeChoice.getItems().addAll(choicesQuiz);
        mQuizModeChoice.setOnAction(this::getQuizChoice);
    }


}


