package Dictionary.Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class History {
    private final int MAX_LENGTH_HISTORY = 15;
    private final ArrayList<String> historyList = new ArrayList<>();

    private static History instance;

    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    public void load(String dirName, String fileName) {
        File dir = new File("user-data/") ;
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.out.println("Failed to create directory user-data");
            }
        }
        File file = new File(dir + "/search-history.txt");
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println("Failed to create search-history txt file");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(
                                            "user-data/search-history.txt"),
                                    StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                historyList.add(line.strip());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Failed to find history txt file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed to read history txt file");
            e.printStackTrace();
        }
        refactorHistory();
    }

    /** */
    public void addWord(String word) {
        historyList.removeIf(w->w.equals(word));
        historyList.add(word);
        refactorHistory();
    }

    public void exportHistory() {
        try {
            Writer out =
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(
                                            "user-data/search-history.txt"),
                                    StandardCharsets.UTF_8));
            StringBuilder content = new StringBuilder();
            for (String target : historyList) {
                content.append(target).append("\n");
            }
            out.write(content.toString());
            out.close();

        } catch (IOException e) {
            System.out.println("Failed to export history");
            e.printStackTrace();

        }
    }
    /** Keep history list contains at most MAX_LENGTH_HISTORY words. */
    public void refactorHistory() {
        if (historyList.size() > MAX_LENGTH_HISTORY) {
            historyList.remove(0);
        }
    }


}
