package tetris.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreTest {
    
    Score score;
    
    public ScoreTest() {
        this.score = new Score();
    }
    
    @Test
    public void clearedLinesGetAddedCorrectly() {
        score.setLinesCleared(21);
        score.addClearedLines(4);
        assertEquals(25, score.getLinesCleared());
    }
    
    @Test
    public void pointsGetAddedCorrectly() {
        score.setPoints(100);
        score.setGameLevel(1);
        score.addPoints(2);
        assertEquals(200, score.getPoints());
        score.addPoints(3);
        assertEquals(500, score.getPoints());
        score.addPoints(4);
        assertEquals(1700, score.getPoints());
    }
}
