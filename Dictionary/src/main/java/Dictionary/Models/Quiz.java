package Dictionary.Models;

import Dictionary.Service.TextToSpeech;
import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
// thời gian, kết thúc hiện điểm cuối, thêm kiểu câu hỏi fill;

public class Quiz {

    long seed = System.currentTimeMillis();
    Random random = new Random(seed);

    public static Map<String, String> WordMapMean = new HashMap<>();
    public static Map<String, String> MeanMapWord = new HashMap<>();

    enum QuestionType {
        Meaning,
        Word,
        Audio,
      //  Fill,
    }

    ArrayList<EnViWord> wordlist = EnViDictionary.getInstance().getAllWords();
    private String question;
    private String[] choices = new String[4];
    private int scores;
    private int typeQuestion;
    private String inputAnswer;
    private String trueAnswer;
    private int questionNumber;

    public Quiz() throws SQLException {
        for(EnViWord word :wordlist) {
            WordMapMean.put(word.getWordTarget(), word.getDescription());
            MeanMapWord.put(word.getDescription(), word.getWordTarget());
        }
    }


    public String endQuiz() {
        return "Your score: " + scores;
    }

    public String correctAnswer() {

        return "Correct answer: " + trueAnswer;
    }

    public String generateQuestion() {
        switch (QuestionType.values()[typeQuestion]) {
            case Meaning:
                return "Chose the meaning of: " + question;
            case Word:
                return "Chose the word for: " + question;
            case Audio:
                try {
                    TextToSpeech.playSoundGoogleTranslateEnglish(question);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
                return "Listen and choose the correct answer";
            default:
                return "";
        }
    }

    public void initQuiz() {
        int randomNumber = random.nextInt(4);
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

    public void initMeaningQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomMeaning();
        }
        int randomNumber = random.nextInt(4);
        setQuestion(getWordFromMeaning(choices[randomNumber]));
        setTrueAnswer(choices[randomNumber]);
    }

    public void initWordQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomWord();
        }
        int randomNumber = random.nextInt(4);
        setQuestion(getMeaningFromWord(choices[randomNumber]));
        setTrueAnswer(choices[randomNumber]);
    }

    public void initAudioQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomMeaning();
        }
        int randomNumber = random.nextInt(4);
        setQuestion(getWordFromMeaning(choices[randomNumber]));
        setTrueAnswer(choices[randomNumber]);
    }

    public void increaseScore() {
        scores++;
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

    public void setScores(int scores) {
        this.scores = scores;
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

    public void increaseQuesNumber() {
        questionNumber++;
    }
}
