package Dictionary.Controllers.Wordle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.ArrayList;
import java.util.Objects;

public class InstructionWindow {
    public static void display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("HOW TO PLAY");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20, 20, 20, 20));
        Label helpParagraph = new Label("Guess the WORDLE in six tries. \n Each guess must be a valid five-letter word.\n Hit the enter button to submit.\n\n After each guess, the color of the tiles will change to \n show how close your guess was to the word.");
        helpParagraph.setTextAlignment(TextAlignment.CENTER);
        helpParagraph.getStyleClass().setAll("lead");

        Line line1 = new Line();
        line1.setStroke(Paint.valueOf("b8b8b8"));
        line1.setEndX(2000);

        Label labelExample = new Label("Examples");
        labelExample.getStyleClass().setAll("h3");
        labelExample.setTextAlignment(TextAlignment.LEFT);

        ArrayList<Label> firstWord = new ArrayList<>();
        Label label1 = new Label("S");
        label1.getStyleClass().setAll("correct-letter-example");
        firstWord.add(label1);
        for (String letter : new String[]{"U", "P", "E", "R"}) {
            Label label = new Label(letter);
            label.getStyleClass().setAll("default-letter-example");
            firstWord.add(label);
        }
        HBox firstWordVBox = new HBox(3);
        for (Label label : firstWord)
            firstWordVBox.getChildren().add(label);
        Label firstWordLabel = new Label("The letter S is in the word and in the correct spot.");
        firstWordLabel.getStyleClass().setAll("lead");


        ArrayList<Label> secondWord = new ArrayList<>();
        Label labelP = new Label("W");
        labelP.getStyleClass().setAll("default-letter-example");
        Label labelI = new Label("R");
        labelI.getStyleClass().setAll("present-letter-example");
        secondWord.add(labelP);
        secondWord.add(labelI);
        for (String letter : new String[]{"I", "T", "E"}) {
            Label label = new Label(letter);
            label.getStyleClass().setAll("default-letter-example");
            secondWord.add(label);
        }
        HBox secondWordVBox = new HBox(3);
        for (Label label : secondWord)
            secondWordVBox.getChildren().add(label);
        Label secondWordLabel = new Label("The letter R is in the word but in the wrong spot.");
        secondWordLabel.getStyleClass().setAll("lead");

        ArrayList<Label> thirdWord = new ArrayList<>();
        for (String letter : new String[]{"A", "C", "H"}) {
            Label label = new Label(letter);
            label.getStyleClass().setAll("default-letter-example");
            thirdWord.add(label);
        }
        Label labelU = new Label("E");
        labelU.getStyleClass().setAll("wrong-letter-example");
        Label labelE = new Label("S");
        labelE.getStyleClass().setAll("default-letter-example");
        thirdWord.add(labelU);
        thirdWord.add(labelE);
        HBox thirdWordVBox = new HBox(3);
        for (Label label : thirdWord)
            thirdWordVBox.getChildren().add(label);
        Label thirdWordLabel = new Label("The letter E is not in the word in any spot.");
        thirdWordLabel.getStyleClass().setAll("lead");

        Line line2 = new Line();
        line2.setStroke(Paint.valueOf("b8b8b8"));
        line2.setEndX(2000);

        Button goBackButton = new Button("GO BACK");
        goBackButton.getStyleClass().setAll("btn", "btn-primary");

        goBackButton.setOnMouseClicked(me -> stage.close());

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(helpParagraph, line1, labelExample, firstWordVBox,
                firstWordLabel, secondWordVBox, secondWordLabel, thirdWordVBox, thirdWordLabel,
                line2, goBackButton);
        Scene scene = new Scene(root, 500, 515);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets()
                .add(Objects.requireNonNull(ScoreWindow.class.getResource("/Style/Wordle.css"))
                        .toExternalForm());

        stage.getIcons().add(new Image(Objects.requireNonNull(InstructionWindow.class.getResourceAsStream("/Image/Wordle/instruction.png"))));
        stage.setScene(scene);
        stage.showAndWait();
    }
}
