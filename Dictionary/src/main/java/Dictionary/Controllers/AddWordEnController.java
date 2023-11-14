package Dictionary.Controllers;

import Dictionary.Helpers.StringUtils;
import Dictionary.Models.EnEnWord;
import Dictionary.Models.EnEnDictionary;
import static Dictionary.App.enenDictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.sql.SQLException;

public class AddWordEnController {
    @FXML
    private Button addButton;
    @FXML
    private TextField mWordTarget;
    @FXML
    private TextField mType;
    @FXML
    private TextArea mMeaning;
    @FXML
    private TextField mPronunciation;
    @FXML
    private TextArea mExample;
    @FXML
    private TextField mSynonyms;
    @FXML
    private TextField mAntonyms;

    public void reset() {
        mType.setText("");
        mMeaning.setText("");
        mPronunciation.setText("");
        mExample.setText("");
        mSynonyms.setText("");
        mAntonyms.setText("");
    }

    public boolean findIfInDatabase() {
        String word = mWordTarget.getText();
        if (word.isBlank() || word.isEmpty()) {
            reset();
            return false;
        }
        word = StringUtils.normalizeEnEnString(word);
        EnEnWord res = enenDictionary.lookupWord(word);
        if (res == null) {
            reset();
            return false;
        } else {
            mType.setText(res.getType());
            mMeaning.setText(res.getMeaning());
            mPronunciation.setText(res.getPronunciation());
            mExample.setText(res.getExample());
            mSynonyms.setText(res.getSynonyms());
            mAntonyms.setText(res.getAntonyms());
            return true;
        }
    }

    @FXML
    public void Add() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String word = mWordTarget.getText();
        String type = mType.getText();
        String pronunciation = mPronunciation.getText();
        String example = mExample.getText();
        String synonyms = mSynonyms.getText();
        String antonyms = mAntonyms.getText();
        String meaning = mMeaning.getText();
        if (word.isBlank() || word.isEmpty()) {
            alert.setTitle("Warning");
            alert.setContentText("Cannot add empty word !");
            alert.showAndWait();
        } else if (meaning.isBlank() || meaning.isEmpty()) {
            alert.setTitle("Warning");
            alert.setContentText("Please type in meaning for word !");
            alert.showAndWait();
        } else {
            word = StringUtils.normalizeEnEnString(word);
            if (findIfInDatabase()) {
                enenDictionary.update(word, type, meaning, example, pronunciation, synonyms, antonyms);
                alert.setTitle("Success");
                alert.setContentText("Your word: " + word + " has been updated");
                alert.showAndWait();
                reset();
                mWordTarget.setText("");
            }
            else if (enenDictionary.insertWord(word, type, meaning, pronunciation, example, synonyms, antonyms)) {
                alert.setTitle("Success");
                alert.setContentText("Your word: " + word + " has been added");
                alert.showAndWait();
                reset();
                mWordTarget.setText("");
            }
        }
    }






}
