package Dictionary.Models;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class EnViDictionary extends DatabaseDictionary {
    protected String databaseURL = "jdbc:sqlite:Dictionary\\src\\vietData.db";
    protected Connection connection = null;

    @Override
    public void connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
            System.out.println("Database connected");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    public boolean insertWord(String wordTarget, String description, String html, String pronunciation) {
        String SQL_QUERY = "INSERT INTO av (word, description, html, pronounce) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, wordTarget);
            ps.setString(2, description);
            ps.setString(3, html);
            ps.setString(4, pronunciation);
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                e.printStackTrace();
                return false;
            } finally {
                close(ps);
            }
            trie.insert(wordTarget);
            // insert successful
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * look up target in database
     * @param target
     * @return EnViWord
     */
    public EnViWord lookupWord(String target) {
        String SQL_QUERY = "SELECT * FROM av WHERE word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        EnViWord res = new EnViWord();
                        res.setWordTarget(target);
                        res.setPronunciation(rs.getString("pronounce"));
                        res.setHtml(rs.getString("html"));
                        res.setDescription(rs.getString("description"));
                        return res;
                    } else {
                        return null;
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * delete target from database
     * @param target
     * @return
     */
    public boolean deleteWord(String target) {
        String SQL_QUERY = "DELETE FROM av WHERE word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            try {
                int id = ps.executeUpdate();
                if (id == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
            trie.delete(target);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePronunciation(String target, String pronunciation) {
        String SQL_QUERY = "UPDATE av SET pronounce = ? WHERE word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, pronunciation);
            ps.setString(2, target);
            try {
                int id = ps.executeUpdate();
                if (id == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateDescription(String target, String description) {
        String SQL_QUERY = "UPDATE av SET description = ? WHERE word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, description);
            ps.setString(2, target);
            try {
                int id = ps.executeUpdate();
                if (id == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateHtml(String target, String html) {
        String SQL_QUERY = "UPDATE av SET html = ? WHERE word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, html);
            ps.setString(2, target);
            try {
                int id = ps.executeUpdate();
                if (id == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private ArrayList<EnViWord> getWordsFromRs(PreparedStatement ps) throws SQLException {
        try {
            ResultSet rs = ps.executeQuery();
            try {
                ArrayList<EnViWord> enViWords = new ArrayList<>();
                while (rs.next()) {
                    EnViWord cur = new EnViWord();
                    cur.setWordTarget(rs.getString("word"));
                    cur.setDescription(rs.getString("description"));
                    cur.setHtml(rs.getString("html"));
                    cur.setPronunciation(rs.getString("pronounce"));
                    enViWords.add(cur);
                }
                return enViWords;
            } finally {
                close(rs);
            }
        } finally {
            close(ps);
        }
    }


    public ArrayList<EnViWord> getAllWords() {
        String SQL_QUERY = "SELECT * FROM av";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            return getWordsFromRs(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> getAllWordsTargets() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String SQL_QUERY = "SELECT * FROM av";
        try {
            ps = connection.prepareStatement(SQL_QUERY);
            try {
                rs = ps.executeQuery();
                try {
                    ArrayList<String> targets = new ArrayList<>();
                    while (rs.next()) {
                        targets.add(rs.getString("word"));
                    }
                    return targets;
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}


