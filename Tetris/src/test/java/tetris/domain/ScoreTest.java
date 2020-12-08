package tetris.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreTest {
    
    Score score;
    
    public ScoreTest() {
        this.score = new Score();
    }
    
    @Test
    public void pointsGetAddedCorrectlyWhenLevelIsTwo() {
        score.setLevel(2);
        score.addPoints(1);
        assertEquals(120, score.getPoints());
        score.setLevel(3);
        score.addPoints(2);
        assertEquals(520, score.getPoints());
        score.setLevel(4);
        score.addPoints(3);
        assertEquals(2020, score.getPoints());
        score.setLevel(5);
        score.addPoints(4);
        assertEquals(9220, score.getPoints());
    }
    
    @Test
    public void clearedLinesGetAddedCorrectly() {
        score.setLinesCleared(21);
        score.addClearedLines(4);
        assertEquals(25, score.getLinesCleared());
    }
    
    @Test
    public void levelGetsUpCorrectly() {
        score.setLevel(2);
        score.setLinesCleared(31);
        score.levelUp();
        assertEquals(3, score.getLevel());
    }
    
    @Test
    public void maxLevelReachedIsSetCorrectly() {
        score.setLevel(9);
        score.setLinesCleared(101);
        score.levelUp();
        assertEquals(10, score.getLevel());
        assertEquals(true, score.getMaxLevelReached());
        score.setLinesCleared(121);
        score.levelUp();
        assertEquals(10, score.getLevel());
    }
}
