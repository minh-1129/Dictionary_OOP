package Dictionary.Controllers;

import Dictionary.Models.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuizSceneController implements Initializable {
    private final Quiz quiz;

    @FXML
    private Label mQues, mScore;
    @FXML
    private RadioButton mAnsA, mAnsB, mAnsC, mAnsD;

    @FXML
    private Button mNext;

    private int indexchoice;

    ToggleGroup group = new ToggleGroup();


    public QuizSceneController() throws SQLException {
        quiz = new Quiz();
    }


    public void setQues() {
        mQues.setText(quiz.generateQuestion());
        mQues.setWrapText(true);
    }

    public int getIndexchoice() {
        return indexchoice;
    }

    public void setIndexchoice(int indexchoice) {
        this.indexchoice = indexchoice;
    }

    public void setChoice() {
        for (RadioButton button : List.of(mAnsA, mAnsB, mAnsC, mAnsD)) {
            int index = List.of(mAnsA, mAnsB, mAnsC, mAnsD).indexOf(button);
            button.setText(quiz.getChoices()[index]);
            button.setWrapText(true);
        }
    }

    public void setInputAns() {
        for (RadioButton button : List.of(mAnsA, mAnsB, mAnsC, mAnsD)) {
            if (button.isSelected()) {
                quiz.setInputAnswer(button.getText());
            }
        }
    }

    public void clearInputAnswer() {
        for (RadioButton button : List.of(mAnsA, mAnsB, mAnsC, mAnsD)) {
            button.setSelected(false);
        }
    }

    public void handleSubmit(ActionEvent event) {
        if (quiz.getQuestionNumber() == 10) {
            quiz.setScores(0);
            return;
        }
        startQuiz();

    }

    public void startQuiz() {
        setAvailable();
        quiz.initQuiz();
        setQues();
        setChoice();
        showScore();
        clearInputAnswer();
        quiz.increaseQuesNumber();
    }

    public void handleSelected(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) event.getSource();
        System.out.println(selectedRadioButton.getText() + " selected");

        group.getToggles().forEach(toggle -> {
            RadioButton radioButton = (RadioButton) toggle;
            if (!radioButton.equals(selectedRadioButton)) {
                radioButton.setDisable(true);
            }
        });

        quiz.setInputAnswer(selectedRadioButton.getText());
        if (quiz.checkAnswer()) {
            quiz.increaseScore();
        }
    }

    public void showScore() {
        mScore.setText(String.valueOf(quiz.getScores()));
    }

    public void setAvailable() {
        mAnsA.setDisable(false);
        mAnsB.setDisable(false);
        mAnsC.setDisable(false);
        mAnsD.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (RadioButton button : List.of(mAnsA, mAnsB, mAnsC, mAnsD)) {
            button.setToggleGroup(group);
        }

        startQuiz();
        for (RadioButton button : List.of(mAnsA, mAnsB, mAnsC, mAnsD)) {
            button.setOnAction(this::handleSelected);
        }

        mNext.setOnAction(this::handleSubmit);
    }
}
