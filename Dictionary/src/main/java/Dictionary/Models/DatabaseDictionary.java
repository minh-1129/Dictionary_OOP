package Dictionary.Models;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class DatabaseDictionary {
    protected Connection connection = null;
    public Trie trie = new Trie();

    /** connect to sqlite database. */
    public abstract void connectToDatabase();

    /** delete a word with wordTarget = target from database. */
    public abstract boolean deleteWord(String target);
    /** get all wordTargets. */
    public abstract ArrayList<String> getAllWordsTargets();

    /** connect to database and get all word targets. */
    public void init() {
        connectToDatabase();
        ArrayList<String> wordTargets = getAllWordsTargets();
        for (String wordTarget: wordTargets) {
            trie.insert(wordTarget);
        }
    }
    /** close the connection. */
    protected void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** close the PreparedStatement. */
    protected void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** close the ResultSet. */
    protected void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** close Dictionary. */
    public void close() {
        close(connection);
        System.out.println("Database disconnected");
    }
}

