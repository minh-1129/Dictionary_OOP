package Dictionary.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

import Dictionary.Service.TextToSpeech;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javazoom.jl.decoder.JavaLayerException;
import net.ricecode.similarity.LevenshteinDistanceStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

public class PracticeEnglishController implements Initializable {
    @FXML
    private TextArea mTextArea;

    @FXML
    private Button mSubmit;

    @FXML
    private Label mScore;
    @FXML
    private Button mNext, playAudio;
    List<String> allQuestions = new ArrayList<>(List.of("Hi, nice to meet you", "Where are you from", "My favorite footballer is Ronaldo", "Do you know Viet Nam- a beautiful country"));
    private String questions;
    private int numberQuestions = 0;

    public void setNumberQuestions(int numberQuestions) {
        this.numberQuestions = numberQuestions;
    }

    long seed = System.currentTimeMillis();
    Random random = new Random(seed);
    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public int getNumberQuestions() {
        return numberQuestions;
    }

    public String getQuestions() {
        return questions;
    }

    public void generateQuestion() {
        int randomNumber = random.nextInt(allQuestions.size());
        setQuestions(allQuestions.get(randomNumber));
    }

    public void playSoundQuestion() {
        try {
            TextToSpeech.playSoundGoogleTranslateEnglish(questions);
        }  catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (JavaLayerException e) {
            e.printStackTrace();
            return;
        }
    }

    public void start() {
        numberQuestions++;
        generateQuestion();
        if (mTextArea != null) {
            mTextArea.setText("");
        }
        if (mScore != null) {
            mScore.setText("");
        }
    }

    public void handleSubmit(ActionEvent event) {
        if (getNumberQuestions() == 5) {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setMaxWidth(500);
            stage.setMaxHeight(460);

            VBox root = new VBox(15);
            root.setAlignment(Pos.CENTER);
            VBox buttonsVBox = new VBox(5);
            buttonsVBox.setAlignment(Pos.CENTER);

            Button playAgainButton = new Button("PLAY AGAIN");
            playAgainButton.setOnMouseClicked(me -> {
                setNumberQuestions(0);
                start();
                stage.close();
            });

            Button quitButton = new Button("  QUIT");
            quitButton.setOnMouseClicked(me -> {
                stage.close();
            });

            buttonsVBox.getChildren().addAll(playAgainButton, quitButton);
            Scene scene = new Scene(root, 300, 260);
            stage.setScene(scene);
            stage.showAndWait();
        } else {
            start();
        }


    }

    public String getTextAnswer() {
        if (mTextArea == null || mTextArea.getText().isBlank()) {
            System.out.println("Please answer");
            mScore.setText("0");
            return "Please answer";
        } else {
            System.out.println(questions);
            String answer = mTextArea.getText();
            System.out.println(answer);
            SimilarityStrategy strategy = new LevenshteinDistanceStrategy();
            StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
            String questionConvertToLowerCase = questions.toLowerCase();
            double score = service.score(questionConvertToLowerCase, answer.toLowerCase());
            int rounded_score = (int)(score * 100);
            System.out.println(score);
            System.out.println(rounded_score);
            mScore.setText(String.valueOf(rounded_score));
            return mTextArea.getText();
        }
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        start();
        mNext.setOnAction(this::handleSubmit);
        //mTime.setText(String.valueOf(System.currentTimeMillis() - quiz.getStartTime()));
        //setQues();

    }




}