package Dictionary;


import Dictionary.Controllers.Wordle.GameController;
import Dictionary.Controllers.Wordle.GameWarning;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class WordleGame  {

    //for choosing the winning words that are not too hard for players to guess
    public static ArrayList<String> wordList = new ArrayList<>();
    //for more diverse choices
    public static ArrayList<String> secondaryWordList = new ArrayList<>();

    private static WordleGame instance;
    private static Stage stage;

    private boolean isRunning = false;
    public static WordleGame getInstance() {
        if (instance == null) {
            instance = new WordleGame();
        }
        return instance;
    }

    public boolean isRunning() {
        return isRunning;
    }
    private WordleGame() {
        initializeWordLists();
    }

    public void init() {
        try {
            isRunning = true;
            stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(WordleGame.class.getResource("/View/Wordle/WordleView.fxml"));

            Parent root = fxmlLoader.load();

            GameController gameController = fxmlLoader.getController();
            gameController.prepareUI();
            gameController.getRandomWord();

            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();
            Scene scene = new Scene(root, 500, 715);
            stage.setMinWidth(500);
            stage.setMinHeight(730);
            stage.setMaxWidth(screenWidth);
            stage.setMaxHeight(screenHeight);
            stage.setTitle("Wordle");
            stage.setOnCloseRequest(event -> {
                event.consume();
                quit();
            });
            stage.setScene(scene);
            stage.show();
            gameController.gridFocus();
        } catch (IOException e) {
            System.out.println("Lỗi khởi tạo Wordle");
            e.printStackTrace();
        }
    }

    public void showWarning() {
        GameWarning.makeText(stage);
    }

    public void quit() {
        isRunning = false;
        stage.close();

    }

    public void initializeWordLists() {
        InputStream wordStream = getClass().getResourceAsStream("/Text/wordleList.txt");
        InputStream availableStream = getClass().getResourceAsStream("/Text/secondaryList.txt");
        if (wordStream != null) {
            Stream<String> main = new BufferedReader(new InputStreamReader(wordStream)).lines();
            main.forEach(wordList::add);
            Stream<String> secondary = new BufferedReader(new InputStreamReader(availableStream)).lines();
            secondary.forEach(secondaryWordList::add);
        } else {
            quit();
        }
    }



}
