package Dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Dictionary.Models.*;

import java.io.IOException;

public class App extends Application {
    public static EnEnDictionary enenDictionary = new EnEnDictionary();
    public static EnViDictionary enviDictionary = new EnViDictionary();
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/View/BaseScene.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 960, 600);
            scene.getStylesheets().add(getClass().getResource("/Style/Style.css").toExternalForm());
            stage.setTitle("CMP_Project");
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
    }

    public static void main(String[] args) {
        launch();
    }
}