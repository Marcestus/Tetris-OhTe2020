package tetris.dao;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tetris.domain.HighScore;

public class LeaderboardDaoTest {
    
    LeaderboardDao lbDaoTest;
    ArrayList<HighScore> leaderboard;
    
    public LeaderboardDaoTest() {
        this.lbDaoTest = new LeaderboardDao("leaderboardDaoTest.db");
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
    public void gettingLeaderBoardWorksCorrectly() {
        lbDaoTest.addScoreToDatabase("player1", 100);
        leaderboard = lbDaoTest.getLeaderBoardFromDatabase();
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
