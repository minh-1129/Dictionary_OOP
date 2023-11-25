package Dictionary.Controllers.Wordle;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GameController {

    private GameHelper gameHelper = GameHelper.getInstance();
    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane keyboardRow1;
    @FXML
    public GridPane keyboardRow2;
    @FXML
    public GridPane keyboardRow3;
    @FXML
    public ImageView instructionIcon;
    @FXML
    public ImageView restartIcon;
    @FXML
    public HBox titleBox;

    public void prepareUI() {
        gameHelper.prepareTitleBox(titleBox);
        gameHelper.prepareGrid(gridPane);
        gameHelper.prepareKeyboard(keyboardRow1, keyboardRow2, keyboardRow3);
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        gameHelper.onKeyPressed(gridPane, keyboardRow1, keyboardRow2, keyboardRow3, event);
    }

    public void getRandomWord() {
        gameHelper.getRandomWord();
    }


    public void showInstruction() {
        InstructionWindow.display();
    }


    public void restart() {
        gameHelper.restart(restartIcon, gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
    }

    public void gridFocus() {
        gridPane.requestFocus();
    }
}
