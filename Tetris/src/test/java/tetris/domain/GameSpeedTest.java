package tetris.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameSpeedTest {
    
    GameSpeed gameSpeed;
    
    public GameSpeedTest() {
        this.gameSpeed = new GameSpeed();
    }
    
    @Test
    public void gameSpeedGetsSetCorrectly() {
        gameSpeed.setGameSpeed(0);
        assertEquals(800, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(1);
        assertEquals(700, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(2);
        assertEquals(600, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(3);
        assertEquals(500, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(4);
        assertEquals(450, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(5);
        assertEquals(400, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(6);
        assertEquals(350, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(7);
        assertEquals(300, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(8);
        assertEquals(250, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(9);
        assertEquals(200, gameSpeed.getSpeed());
        gameSpeed.setGameSpeed(10);
        assertEquals(150, gameSpeed.getSpeed());
    }
}
