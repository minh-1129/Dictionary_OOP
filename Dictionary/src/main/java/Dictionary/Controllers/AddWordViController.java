package Dictionary.Controllers;

import Dictionary.Helpers.StringUtils;
import Dictionary.Models.EnViWord;
import Dictionary.Models.EnViDictionary;

import static Dictionary.App.enenDictionary;
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
        htmlEditor.setHtmlText("");
    }

    public boolean findIfInDatabase() {
        String word = mWordTarget.getText();
        if (word.isBlank() || word.isEmpty()) {
            reset();
            return false;
        }
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

        byte[] ptext = htmlEditor.getHtmlText().getBytes(StandardCharsets.UTF_8);
        String html = new String(ptext, StandardCharsets.UTF_8);
        System.out.println(html);

        if (word.isBlank() || word.isEmpty()) {
            alert.setTitle("Warning");
            alert.setContentText("Cannot add empty word !");
            alert.showAndWait();
        } else if (description.isBlank() || description.isEmpty()) {
            alert.setTitle("Warning");
            alert.setContentText("Please type in description for word !");
            alert.showAndWait();
        } else {
            if (findIfInDatabase()) {
                enviDictionary.update(word, description, html, pronunciation);
                alert.setTitle("Success");
                alert.setContentText("Your word: " + word + " has been updated");
                alert.showAndWait();
                reset();
                mWordTarget.setText("");
            }
            else if (enviDictionary.insertWord(word, description, html, pronunciation)) {
                alert.setTitle("Success");
                alert.setContentText("Your word: " + word + " has been added");
                alert.showAndWait();
                reset();
                mWordTarget.setText("");
            }
        }
    }






}

