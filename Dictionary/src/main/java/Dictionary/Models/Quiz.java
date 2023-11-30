package Dictionary.Models;

import Dictionary.Service.TextToSpeech;
import javafx.scene.control.RadioButton;
import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static Dictionary.App.*;
// thời gian, kết thúc hiện điểm cuối, thêm kiểu câu hỏi fill;

public class Quiz {

    long seed = System.currentTimeMillis();
    Random random = new Random(seed);

    enum QuestionType {
        Meaning,
        Word,
        Audio,
      //  Fill,
    }
    private int trueAnsIndex;
    private String question;
    private String[] choices = new String[4];
    private int scores;
    private int typeQuestion;
    private String inputAnswer;
    private String trueAnswer;
    private int questionNumber;
    private int timeplay;

    private long startTime;

    public Quiz() throws SQLException {
    }

    public long getStartTime() {
        return startTime;
    }

    public String generateQuestion() {
        switch (QuestionType.values()[typeQuestion]) {
            case Meaning:
                return "Choose the meaning of: " + question;
            case Word:
                return "Choose the word for: " + question;
            case Audio:
                return "Listen and choose the correct answer";
            default:
                return "";
        }
    }

    public void initQuiz() {
        int randomNumber = random.nextInt(3);
        setTypeQuestion(randomNumber);
        switch (QuestionType.values()[getTypeQuestion()]) {
            case Meaning:
                initMeaningQuiz();
                break;
            case Word:
                initWordQuiz();
                break;
            case Audio:
                initAudioQuiz();
                break;
            default:
                break;
        }
    }

    public boolean checkAnswer() {
        return inputAnswer.equals(trueAnswer);
    }

    public boolean validChoice(String choice) {
        if (choice == null || choice.trim().isEmpty()) {
            return false;
        }

        String lowerCaseChoice = choice.trim().toLowerCase();

        if (lowerCaseChoice.startsWith("of") || lowerCaseChoice.startsWith("alt") || lowerCaseChoice.startsWith(" ")) {
            return false;
        }
        return choice.length() < 80;
    }

    public void initMeaningQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomMeaning();
            while(!validChoice(choices[i]) && !validChoice(getWordFromMeaning(choices[i]))) {
                choices[i] = getRandomMeaning();
            }
        }
        int randomNumber = random.nextInt(4);
        setQuestion(getWordFromMeaning(choices[randomNumber]));
        setTrueAnswer(choices[randomNumber]);
        setTrueAnsIndex(randomNumber);
    }

    public void initWordQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomWord();
            while(!validChoice(choices[i]) && !validChoice(getMeaningFromWord(choices[i]))) {
                choices[i] = getRandomWord();
            }
        }
        int randomNumber = random.nextInt(4);
        setQuestion(getMeaningFromWord(choices[randomNumber]));
        setTrueAnswer(choices[randomNumber]);
        setTrueAnsIndex(randomNumber);
    }

    public void initAudioQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomMeaning();
            while(!validChoice(choices[i]) && !validChoice(getWordFromMeaning(choices[i]))) {
                choices[i] = getRandomMeaning();
            }
        }
        int randomNumber = random.nextInt(4);
        setQuestion(getWordFromMeaning(choices[randomNumber]));
        setTrueAnswer(choices[randomNumber]);
        setTrueAnsIndex(randomNumber);
    }

    public void increaseScore() {
        scores++;
    }

    public void runTimePlay() {
        for (int i = 300; i >= 0; i--) {
            timeplay = i;
            try {
                Thread.sleep(1000); // Đợi một giây (1000 milliseconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRandomWord() {
       try {
           int randomNumber = random.nextInt(wordlist.size());
           return wordlist.get(randomNumber).getWordTarget();
       } catch (Exception e) {
           e.printStackTrace();
           return "";
       }
    }

    public String getRandomMeaning() {
        try {
            int randomNumber = random.nextInt(wordlist.size());
            return wordlist.get(randomNumber).getDescription();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getWordFromMeaning(String meaning) {
        return MeanMapWord.get(meaning);
    }

    public static String getMeaningFromWord(String word) {
        return WordMapMean.get(word);
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public void setTrueAnsIndex(int trueAnsIndex) {
        this.trueAnsIndex = trueAnsIndex;
    }

    public int getTrueAnsIndex() {
        return trueAnsIndex;
    }

    public int getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(int type) {
        this.typeQuestion = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String ques) {
        this.question = ques;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getScores() {
        return scores;
    }

    public int getTimeplay() {
        return timeplay;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public void setTimeplay(int timeplay) {
        this.timeplay = timeplay;
    }

    public String getInputAnswer() {
        return inputAnswer;
    }

    public void setInputAnswer(String answer) {
        this.inputAnswer = answer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void increaseQuesNumber() {
        questionNumber++;
    }
}
