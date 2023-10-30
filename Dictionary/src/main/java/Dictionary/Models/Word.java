package Dictionary.Models;

public class Word {
    private String wordTarget = "";
    private String type = "";
    private String meaning = "";
    private String pronunciation = "";
    private String example = "";
    private String synonyms = "";
    private String antonyms = "";

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(String antonyms) {
        this.antonyms = antonyms;
    }

    public Word(String wordTarget, String type, String meaning, String pronunciation,
                String example, String synonyms, String antonyms) {
        this.wordTarget = wordTarget;
        this.type = type;
        this.meaning = meaning;
        this.pronunciation = pronunciation;
        this.example = example;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    public Word(String wordTarget, String type, String meaning, String pronunciation,
                String example) {
        this.wordTarget = wordTarget;
        this.type = type;
        this.meaning = meaning;
        this.pronunciation = pronunciation;
        this.example = example;
    }

    public Word(String wordTarget, String meaning, String pronunciation) {
        this.wordTarget = wordTarget;
        this.meaning = meaning;
        this.pronunciation = pronunciation;
    }

    public Word(String wordTarget, String meaning) {
        this.wordTarget = wordTarget;
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "This word is " + wordTarget + " " + type + " " + meaning + " " + pronunciation + " "
                + example + " " + synonyms + " " + antonyms;
    }

}

