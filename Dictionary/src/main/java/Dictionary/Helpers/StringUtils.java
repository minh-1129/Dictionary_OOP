package Dictionary.Helpers;

public class StringUtils {
    public static String normalizeEnEnString(String searchTerm) {
        searchTerm = searchTerm.toLowerCase();
        searchTerm = searchTerm.substring(0, 1).toUpperCase() + searchTerm.substring(1);
        searchTerm = searchTerm.strip();
        searchTerm = searchTerm.trim();
        return searchTerm;
    }

    public static String normalizeUserMsg(String content) {
        if (content.contains("\n")) {
            content = content.replaceAll("\n", "");
        }
        return content;
    }

    public static String normalizeChatResponse(String content) {
        if (content.contains("\n")) {
            content = content.replaceAll("\n", "");
        }
        String [] words = content.split(" ");
        String res = "";
        int word_count = 0;
        for (String word : words) {
            res = res + word + " ";
            word_count += word.length() + 1;
            if (word_count > 30) {
                res += "\n";
                word_count = 0;
            }
        }
        return res;

    }

}
