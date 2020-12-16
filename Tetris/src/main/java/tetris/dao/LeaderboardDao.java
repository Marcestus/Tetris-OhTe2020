package tetris.dao;

import java.sql.*;
import java.util.*;
import tetris.domain.HighScore;
import tetris.domain.Leaderboard;

/**
 * Tietokantakomennot sisältävä luokka.
 */

public class LeaderboardDao {
    
    private String dbName;
    private ArrayList<HighScore> leaderboard;
    private int rownumber;
    
    public LeaderboardDao(String dbName) {
        this.dbName = dbName;
        createNewLeaderBoardTable();
        leaderboard = new ArrayList<>();
        rownumber = -1;
    }

    public void setRownumber(int rownumber) {
        this.rownumber = rownumber;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    /**
     * Yhteyden luominen tietokantaan.
     * @return tietokantayhteys
     */
    public Connection connect() {
        Connection dbConn = null;
        try {
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch (SQLException e) {
            System.out.println("Connection failed with message: " + e.getMessage());
        }
        return dbConn;
    }
    
    /**
     * Uuden Leaderboard-taulun luominen tarvittaessa.
     */
    public void createNewLeaderBoardTable() {
        try (Connection dbConn = connect()) {
            Statement stmt = dbConn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Leaderboard (id INTEGER PRIMARY KEY, nickname TEXT, points INTEGER)";
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("Creating table failed with message: " + e.getMessage());
        }
    }
    
    /**
     * Pistetuloksen lisääminen tietokantaan.
     * @param nickname pelaajan antama nimimerkki
     * @param points päättyneen pelikierroksen pisteet
     * @return päivitetty leaderboard listana
     */
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
    
    /**
     * Pistetuloksen poistaminen tietokannasta.
     * @return päivitetty leaderboard listana
     */
    public ArrayList<HighScore> removeScoreFromDatabase() {
        try (Connection dbConn = connect()) {
            getLeaderBoardFromDatabase();
            String query = "DELETE FROM Leaderboard WHERE id = ?";
            PreparedStatement stmt = dbConn.prepareStatement(query);
            stmt.setInt(1, rownumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Removing old score from leaderboard failed with message: " + e.getMessage());
        }
        leaderboard = getLeaderBoardFromDatabase();
        return leaderboard;
    }
    
    /**
     * Leaderboardin hakeminen tietokannasta.
     * @return päivitetty leaderboard listana
     */
    public ArrayList<HighScore> getLeaderBoardFromDatabase() {
        try (Connection dbConn = connect()) {
            String query = "SELECT ROW_NUMBER () OVER (ORDER BY points DESC) rownbr, id, nickname, points FROM Leaderboard";
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            leaderboard.clear();
            while (rs.next()) {
                leaderboard.add(new HighScore(rs.getString("nickname"), rs.getInt("points")));
                if (rs.getInt("rownbr") == 5) {
                    rownumber = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Fetching leaderboard failed with message: " + e.getMessage());
        }
        return leaderboard;
    }
}
