package Dictionary.Controllers;

import Dictionary.Helpers.StringUtils;
import Dictionary.Models.EnViWord;
import Dictionary.Models.EnViDictionary;
import static Dictionary.App.enviDictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class AddWordViController {
    @FXML
    private Button addButton;
    @FXML
    private TextField mWordTarget;
    @FXML
    private TextField mDescription;
    @FXML
    private TextField mPronunciation;
    @FXML
    private HTMLEditor htmlEditor;



    public void reset() {
        mDescription.setText("");
        mPronunciation.setText("");

    }

    public boolean findIfInDatabase() {
        String word = mWordTarget.getText();
        if (word.isBlank() || word.isEmpty()) {
            reset();
            return false;
        }
        word = StringUtils.normalizeEnEnString(word);
        EnViWord res = enviDictionary.lookupWord(word);
        if (res == null) {
            reset();
            return false;
        } else {
            mDescription.setText(res.getDescription());
            mPronunciation.setText(res.getPronunciation());
            htmlEditor.setHtmlText("<body>" + res.getHtml() + "</body>");
            return true;
        }
    }

    @FXML
    public void Add() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String word = mWordTarget.getText();
        String pronunciation = mPronunciation.getText();
        String description = mDescription.getText();

        byte[] ptext = htmlEditor.getHtmlText().getBytes(StandardCharsets.ISO_8859_1);
        String html = new String(ptext, StandardCharsets.UTF_8);
        html = html.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
        html = html.replace("</body></html>", "");
        html = html.replace("\"", "'");

        if (enviDictionary.update(word, pronunciation, html, description)) {

        }
    }






}

