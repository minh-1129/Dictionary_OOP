package Dictionary.Controllers;

import Dictionary.Controllers.Wordle.ScoreWindow;
import Dictionary.Models.Quiz;
import Dictionary.Service.TextToSpeech;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javazoom.jl.decoder.JavaLayerException;
import org.kordamp.bootstrapfx.BootstrapFX;

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
    private Button mNext, playAudio;

    private int indexchoice;

    ToggleGroup group = new ToggleGroup();


    public QuizSceneController() throws SQLException {
        quiz = new Quiz();
        //quiz.runTimePlay();
    }

    @FXML
    public void playAudioQues() throws IOException, JavaLayerException {
        if (quiz.getTypeQuestion() == 2) {
            TextToSpeech.playSoundGoogleTranslateEnglish(quiz.getQuestion());
        }
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
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setMaxWidth(500);
            stage.setMaxHeight(460);

            VBox root = new VBox(15);
            root.setAlignment(Pos.CENTER);

            Label mainLabel = new Label();
            mainLabel.setText("Your Score \n       " + String.valueOf(quiz.getScores()));
            mainLabel.getStyleClass().setAll("lead", "big-font");

            VBox buttonsVBox = new VBox(5);
            buttonsVBox.setAlignment(Pos.CENTER);

            Button playAgainButton = new Button("PLAY AGAIN");
            playAgainButton.getStyleClass().setAll("btn", "btn-primary");
            playAgainButton.setOnMouseClicked(me -> {
                quiz.setScores(0);
                quiz.setQuestionNumber(0);
                startQuiz();
                stage.close();
            });

            Button quitButton = new Button("  QUIT");
            quitButton.getStyleClass().setAll("btn", "btn-warning");
            quitButton.setOnMouseClicked(me -> {
                stage.close();
            });

            buttonsVBox.getChildren().addAll(playAgainButton, quitButton);

            root.getChildren().addAll(mainLabel, buttonsVBox);
            Scene scene = new Scene(root, 300, 260);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

            scene.getStylesheets()
                    .add(Objects.requireNonNull(ScoreWindow.class.getResource("/Style/Wordle.css"))
                            .toExternalForm());

            stage.setScene(scene);
            stage.showAndWait();
        } else {
            startQuiz();
        }


    }

    public void startQuiz() {
        setAvailable();
        quiz.initQuiz();
        setChoice();
        showScore();
        //showTime();
        clearInputAnswer();
        quiz.increaseQuesNumber();
        setQues();
    }

    public void setTime() {

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
            selectedRadioButton.setStyle("-fx-background-color: green");
        } else {
            selectedRadioButton.setStyle("-fx-background-color: red");
            RadioButton trueButton = List.of(mAnsA, mAnsB, mAnsC, mAnsD).get(quiz.getTrueAnsIndex());
            trueButton.setStyle("-fx-background-color: green");
        }

    }

    public void showScore() {
        mScore.setText(String.valueOf(quiz.getScores()) + " / 10");
    }

//    public void showTime() {
//        mTime.setText(String.valueOf(quiz.getTimeplay()));
//    }

    public void setAvailable() {
        mAnsA.setDisable(false);
        mAnsB.setDisable(false);
        mAnsC.setDisable(false);
        mAnsD.setDisable(false);
        mAnsA.setStyle("fx-background-color: white");
        mAnsB.setStyle("fx-background-color: white");
        mAnsC.setStyle("fx-background-color: white");
        mAnsD.setStyle("fx-background-color: white");
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
        //mTime.setText(String.valueOf(System.currentTimeMillis() - quiz.getStartTime()));
        //setQues();

    }
}
