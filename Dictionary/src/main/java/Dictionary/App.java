package Dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Dictionary.Models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class App extends Application {
    public static EnEnDictionary enenDictionary = EnEnDictionary.getInstance();
    public static EnViDictionary enviDictionary = EnViDictionary.getInstance();

    public static ArrayList<EnViWord> wordlist;

    public static Map<String, String> WordMapMean = new HashMap<>();
    public static Map<String, String> MeanMapWord = new HashMap<>();

    @Override 
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/View/BaseScene.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 960, 600);
            stage.setTitle("CMP_Project");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setOnCloseRequest(
                    arg0 -> {
                        enenDictionary.close();
                        enviDictionary.close();
                    });
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

        enenDictionary.init();
        enviDictionary.init();
        wordlist = EnViDictionary.getInstance().getAllWords();
        for(EnViWord word :wordlist) {
            WordMapMean.put(word.getWordTarget(), word.getDescription());
            MeanMapWord.put(word.getDescription(), word.getWordTarget());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}