package Dictionary.Models;

import java.sql.SQLException;



public class Quiz {

    enum QuestionType {
        ChooseMeaning,
        ChooseWord,
        ListenAndChoose,
        FillBlank,
    }

    private String question;
    private String[] choices = new String[4];
    private int scores;
    private int typeOfQuestion;
    private String inputAnswer;
    private String trueAnswer;

    public Quiz() throws SQLException {
    }


    public String endQuiz() {
        return "Your score: " + scores;
    }

    public String correctAnswer() {

        return "Correct answer: " + trueAnswer;
    }

    public String generateQuestion() {
        return null;
    }

    public void initQuiz() {

    }

    public boolean checkAnswer() {
        return false;
    }

    public boolean validChoice(String choice) {
        if (choice == null || choice.trim().isEmpty()) {
            return false;
        }

        String lowerCaseChoice = choice.trim().toLowerCase();

        if (lowerCaseChoice.startsWith("of") || lowerCaseChoice.startsWith("alt") || lowerCaseChoice.startsWith(" ")) {
            return false;
        }

        return choice.length() < 150;
    }

    public void initChooseMeaningQuiz() {

    }

    public void initChooseWordQuiz() {

    }

    public void initFillBlankQuiz() {

    }

    public void initListenQuiz() {

    }

    public void increaseScore() {
        scores++;
    }

    public String getRandomWord() {
       return null;
    }

    public String getRandomMeaning() {
        return null;
    }


    public static String getWordFromMeaning(String meaning) {
        return null;
    }

    public static String getMeaningFromWord(String word) {
        return null;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public int getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(int type) {
        this.typeOfQuestion = type;
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
}
