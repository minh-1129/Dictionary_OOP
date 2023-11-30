package Dictionary.Models;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Trie {
    private TrieNode root = new TrieNode();

    private ArrayList<String> searchedWords = new ArrayList<>();

    public ArrayList<String> getSearchedWords() {
        return searchedWords;
    }
    /**
     * insert a word into trie
     * @param target
     */
    public void insert(String target) {
        TrieNode pCrawl = root;

        int length = target.length();
        for (int level = 0; level < length; level++) {
            char curChar = target.charAt(level);

            if (pCrawl.children.get(curChar) == null) {
                pCrawl.children.put(curChar, new TrieNode());
            }

            pCrawl = pCrawl.children.get(curChar);
        }

        pCrawl.isEndOfWord = true;
    }

    /**
     * delete word from Trie
     * @param target
     */
    public void delete(String target) {
        TrieNode pCrawl = root;

        int length = target.length();
        for (int level = 0; level < length; level++) {
            char curChar = target.charAt(level);

            if (pCrawl.children.get(curChar) == null) {
                System.out.println(String.format("%s is not in Trie", target));
                return;
            }

            pCrawl = pCrawl.children.get(curChar);
        }

        if (!pCrawl.isEndOfWord) {
            System.out.println(String.format("%s is not in Trie", target));
            return;
        }

        pCrawl.isEndOfWord = false;
    }

    /**
     * Helper for finding all strings with the same prefix
     * @param pCrawl
     * @param prefix
     */
    private void parseAllSubtrees(TrieNode pCrawl, String prefix) {
        if (pCrawl.isEndOfWord) {
            searchedWords.add(prefix);
        }

        //DFS
        for (char curChar : pCrawl.children.keySet()) {
            if (pCrawl.children.get(curChar) != null) {
                parseAllSubtrees(pCrawl.children.get(curChar), prefix + curChar);
            }
        }
    }

    /**
     * Find all strings with the same prefix
     * @param prefix
     * @return list of strings
     */
    public ArrayList<String> search(String prefix) {
        if (prefix.isEmpty()) {
            return new ArrayList<>();
        }

        searchedWords.clear();
        TrieNode pCrawl = root;

        int length = prefix.length();
        for (int level = 0; level < length; level++) {
            char curChar = prefix.charAt(level);

            if (pCrawl.children.get(curChar) == null) {
                pCrawl.children.put(curChar, new TrieNode());
            }

            pCrawl = pCrawl.children.get(curChar);
        }

        parseAllSubtrees(pCrawl, prefix);

        return getSearchedWords();
    }

    public static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();

        boolean isEndOfWord;

        TrieNode() {
            isEndOfWord = false;
        }
    }
}
