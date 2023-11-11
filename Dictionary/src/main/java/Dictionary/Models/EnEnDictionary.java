package Dictionary.Models;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class EnEnDictionary extends DatabaseDictionary{
    protected String databaseURL = "jdbc:sqlite:Dictionary\\src\\engData.db";
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
    /**
     * insert a word into database with folowing properties
     * @param wordTarget
     * @param type
     * @param meaning
     * @param pronunciation
     * @param example
     * @param synonyms
     * @param antonyms
     * @return
     */
    public boolean insertWord(String wordTarget, String type, String meaning, String pronunciation, String example, String synonyms, String antonyms) {
        String SQL_QUERY = "INSERT INTO English (Word, Type, Meaning, Pronunciation, Example, Synonym, Antonyms VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, wordTarget);
            ps.setString(2, type);
            ps.setString(3, meaning);
            ps.setString(4, pronunciation);
            ps.setString(5, example);
            ps.setString(6, synonyms);
            ps.setString(7, antonyms);
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
     */
    public EnEnWord lookupWord(String target) {
        String SQL_QUERY = "SELECT * FROM English WHERE Word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        EnEnWord res = new EnEnWord();
                        res.setWordTarget(target);
                        res.setType(rs.getString("Type"));
                        res.setMeaning(rs.getString("Meaning"));
                        res.setExample(rs.getString("Example"));
                        res.setPronunciation(rs.getString("Pronunciation"));
                        res.setAntonyms(rs.getString("Antonyms"));
                        res.setSynonyms(rs.getString("Synonym"));
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
        String SQL_QUERY = "DELETE FROM English WHERE Word = ?";
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
        String SQL_QUERY = "UPDATE English SET Pronunciation = ? WHERE Word = ?";
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

    public boolean updateType(String target, String type) {
        String SQL_QUERY = "UPDATE English SET Type = ? WHERE Word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, type);
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

    public boolean updateMeaning(String target, String meaning) {
        String SQL_QUERY = "UPDATE English SET Meaning = ? WHERE Word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, meaning);
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

    public boolean updateExample(String target, String example) {
        String SQL_QUERY = "UPDATE English SET Example = ? WHERE Word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, example);
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

    public boolean updateSynonyms(String target, String synonyms) {
        String SQL_QUERY = "UPDATE English SET Synonym = ? WHERE Word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, synonyms);
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

    public boolean updateAntonyms(String target, String antonyms) {
        String SQL_QUERY = "UPDATE English SET Antonyms = ? WHERE Word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, antonyms);
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

    protected ArrayList<EnEnWord> getWordsFromRs(PreparedStatement ps) throws SQLException {
        try {
            ResultSet rs = ps.executeQuery();
            try {
                ArrayList<EnEnWord> enEnWords = new ArrayList<>();
                while (rs.next()) {
                    EnEnWord cur = new EnEnWord();
                    cur.setWordTarget(rs.getString("Word"));
                    cur.setType(rs.getString("Type"));
                    cur.setMeaning(rs.getString("Meaning"));
                    cur.setExample(rs.getString("Example"));
                    cur.setPronunciation(rs.getString("Pronunciation"));
                    cur.setAntonyms(rs.getString("Antonyms"));
                    cur.setSynonyms(rs.getString("Synonym"));
                    enEnWords.add(cur);
                }
                return enEnWords;
            } finally {
                close(rs);
            }
        } finally {
            close(ps);
        }
    }


    public ArrayList<EnEnWord> getAllWords() {
        String SQL_QUERY = "SELECT * FROM English";
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
        String SQL_QUERY = "SELECT * FROM English";
        try {
            ps = connection.prepareStatement(SQL_QUERY);
            try {
                rs = ps.executeQuery();
                try {
                    ArrayList<String> targets = new ArrayList<>();
                    while (rs.next()) {
                        targets.add(rs.getString(2));
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


