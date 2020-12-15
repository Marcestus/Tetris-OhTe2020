package tetris.dao;

import java.sql.*;
import java.util.*;
import tetris.domain.HighScore;
import tetris.domain.Leaderboard;

public class LeaderboardDao {
    
    private String dbName;
    private ArrayList<HighScore> leaderboard;
    private int lastID;
    
    public LeaderboardDao(String dbName) {
        this.dbName = dbName;
        createNewLeaderBoardTable();
        leaderboard = new ArrayList<>();
        lastID = -1;
    }
    
    public Connection connect() {
        Connection dbConn = null;
        try {
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch (SQLException e) {
            System.out.println("Connection failed with message: " + e.getMessage());
        }
        return dbConn;
    }
    
    public void createNewLeaderBoardTable() {
        try (Connection dbConn = connect()) {
            Statement stmt = dbConn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Leaderboard (id INTEGER PRIMARY KEY, rank INTEGER, nickname TEXT, points INTEGER)";
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("Creating table failed with message: " + e.getMessage());
        }
    }
    
    public ArrayList<HighScore> addScoreToDatabase(String nickname, int points) {
        try (Connection dbConn = connect()) {
            String query = "INSERT INTO Leaderboard(nickname, points) VALUES(?,?)";
            PreparedStatement stmt = dbConn.prepareStatement(query);
            stmt.setString(1, nickname);
            stmt.setInt(2, points);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Adding new score to leaderboard failed with message: " + e.getMessage());
        }
        leaderboard = getLeaderBoardFromDatabase();
        return leaderboard;
    }
    
    public ArrayList<HighScore> removeScoreFromDatabase() {
        try (Connection dbConn = connect()) {
            getLeaderBoardFromDatabase();
            String query = "DELETE FROM Leaderboard WHERE id = ?";
            PreparedStatement stmt = dbConn.prepareStatement(query);
            stmt.setInt(1, lastID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Removing old score from leaderboard failed with message: " + e.getMessage());
        }
        leaderboard = getLeaderBoardFromDatabase();
        return leaderboard;
    }
    
    public ArrayList<HighScore> getLeaderBoardFromDatabase() {
        try (Connection dbConn = connect()) {
            String query = "SELECT ROW_NUMBER () OVER (ORDER BY points DESC) rownbr, id, nickname, points FROM Leaderboard";
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            leaderboard.clear();
            while (rs.next()) {
                leaderboard.add(new HighScore(rs.getString("nickname"), rs.getInt("points")));
                if (rs.getInt("rownbr") == 5) {
                    lastID = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Fetching leaderboard failed with message: " + e.getMessage());
        }
        return leaderboard;
    }
}
