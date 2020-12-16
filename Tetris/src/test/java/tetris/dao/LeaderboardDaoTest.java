package tetris.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import tetris.domain.HighScore;

public class LeaderboardDaoTest {
    
    LeaderboardDao lbDaoTest;
    ArrayList<HighScore> leaderboard;
    private Properties properties;
    private String leaderboardDaoTestDatabaseName;
    private InputStream stream;
    
    public LeaderboardDaoTest() throws Exception {
        this.properties = new Properties();
        this.stream = LeaderboardDaoTest.class.getResourceAsStream("/tetris/config.properties");
        this.properties.load(this.stream);
        this.leaderboardDaoTestDatabaseName = properties.getProperty("leaderboardDaoTestDatabase");
        this.lbDaoTest = new LeaderboardDao(leaderboardDaoTestDatabaseName);
        this.leaderboard = new ArrayList<>();
    }
    
    @Test
    public void addingScoreWorksCorrectly() {
        leaderboard = lbDaoTest.addScoreToDatabase("player1", 100);
        assertEquals(100, leaderboard.get(0).getPoints());
        resetDatabase();
    }
    
    @Test
    public void removingScoreWorksCorrectly() {
        leaderboard = lbDaoTest.addScoreToDatabase("player1", 100);
        lbDaoTest.setRownumber(1);
        leaderboard = lbDaoTest.removeScoreFromDatabase();
        assertEquals(0, leaderboard.size());
        resetDatabase();
    }
    
    @Test
    public void gettingLeaderboardWorksCorrectly() {
        lbDaoTest.addScoreToDatabase("player1", 100);
        leaderboard = lbDaoTest.getLeaderboardFromDatabase();
        assertEquals(1, leaderboard.size());
        resetDatabase();
    }
    
    @After
    public void tearDown() {
        resetDatabase();
    }
    
    public void resetDatabase() {
        int n = leaderboard.size();
        for (int i = 0; i <= n; i++) {
            lbDaoTest.setRownumber(i+1);
            lbDaoTest.removeScoreFromDatabase();
        }
    }
}
