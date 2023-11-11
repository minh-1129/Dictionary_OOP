package Dictionary.Models;

public class EnViWord {
    private String wordTarget = "";
    private String description = "";
    private String html = "";
    private String pronunciation = "";

    public String getWordTarget() {
        return wordTarget;
    }

    public String getDescription() {
        return description;
    }

    public String getHtml() {
        return html;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public EnViWord() {}

    public EnViWord(String wordTarget, String description, String html, String pronunciation) {
        this.wordTarget = wordTarget;
        this.description = description;
        this.html = html;
        this.pronunciation = pronunciation;
    }




}
