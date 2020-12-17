package tetris.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LeaderboardTest {
    
    private Leaderboard leaderboardFull;
    private Leaderboard leaderboardEmpty;
    private Properties properties;
    private String leaderboardFullTestDatabaseName;
    private String leaderboardEmptyTestDatabaseName;
    private InputStream stream;
    
    public LeaderboardTest() {
        this.properties = new Properties();
        this.stream = LeaderboardTest.class.getResourceAsStream("/tetris/config.properties");
        try {
            this.properties.load(this.stream);
        } catch (IOException e) {
            System.out.println("Fetching file failed with message: " + e.getMessage());
        }
        this.leaderboardFullTestDatabaseName = properties.getProperty("leaderboardFullTestDatabase");
        this.leaderboardEmptyTestDatabaseName = properties.getProperty("leaderboardEmptyTestDatabase");
        this.leaderboardFull = new Leaderboard(leaderboardFullTestDatabaseName);
        this.leaderboardEmpty = new Leaderboard(leaderboardEmptyTestDatabaseName);
    }
    
    @Before
    public void setUp() {
        leaderboardFull.addNewHighScoreToLeaderboard("Player1", 1000);
        leaderboardFull.addNewHighScoreToLeaderboard("Player2", 900);
        leaderboardFull.addNewHighScoreToLeaderboard("Player3", 200);
        leaderboardFull.addNewHighScoreToLeaderboard("Player4", 1500);
        leaderboardFull.addNewHighScoreToLeaderboard("Player5", 800);
    }
    
    @Test
    public void foundHighScoreIsCorrect() {
        assertEquals(1500, leaderboardFull.getHighScore());
        assertEquals(0, leaderboardEmpty.getHighScore());
        resetDatabase();
    }
    
    @Test
    public void foundLeaderboardPlayersAreCorrect() {
        ArrayList<String> players = leaderboardFull.getLeaderboardPlayers();
        assertEquals("Player4", players.get(0));
        assertEquals("Player1", players.get(1));
        assertEquals("Player2", players.get(2));
        assertEquals("Player5", players.get(3));
        assertEquals("Player3", players.get(4));
        resetDatabase();
    }
    
    @Test
    public void foundLeaderboardPointsAreCorrect() {
        ArrayList<Integer> points = leaderboardFull.getLeaderboardPoints();
        assertEquals(1500, (int)points.get(0));
        assertEquals(1000, (int)points.get(1));
        assertEquals(900, (int)points.get(2));
        assertEquals(800, (int)points.get(3));
        assertEquals(200, (int)points.get(4));
        resetDatabase();
    }
    
    @Test
    public void calculatesPointsEnoughForLeaderboardCorrectly() {
        assertEquals(false, leaderboardEmpty.pointsEnoughForLeaderboard(0));
        assertEquals(true, leaderboardEmpty.pointsEnoughForLeaderboard(100));
        assertEquals(false, leaderboardFull.pointsEnoughForLeaderboard(100));
        assertEquals(true, leaderboardFull.pointsEnoughForLeaderboard(3000));
        assertEquals(false, leaderboardFull.pointsEnoughForLeaderboard(200));
        resetDatabase();
    }
    
    @After
    public void tearDown() {
        resetDatabase();
    }
    
    public void resetDatabase() {
        int n = leaderboardFull.getLeaderboard().size();
        for (int i = 0; i <= n; i++) {
            leaderboardFull.getLbDao().setRownumber(i+1);
            leaderboardFull.getLbDao().removeScoreFromDatabase();
        }
        int m = leaderboardEmpty.getLeaderboard().size();
        for (int i = 0; i <= n; i++) {
            leaderboardEmpty.getLbDao().setRownumber(i+1);
            leaderboardEmpty.getLbDao().removeScoreFromDatabase();
        }
    }
}
