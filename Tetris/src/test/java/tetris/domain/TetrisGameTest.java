package tetris.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TetrisGameTest {
    
    public TetrisGameTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void ActiveShapeMovesCorrectlyToLeft() {
        TetrisGame game = new TetrisGame(0);
        game.getActiveShape();
        Tile[] tiles = game.getActiveShapeTiles();
        game.getPassiveShapes();
        game.moveLeft();
        assertEquals(4, tiles[0].getX());
        assertEquals(5, tiles[1].getX());
        assertEquals(3, tiles[2].getX());
        assertEquals(5, tiles[3].getX());
    }
    
    @Test
    public void ActiveShapeMovesCorrectlyToRight() {
        TetrisGame game = new TetrisGame(0);
        game.getActiveShape();
        Tile[] tiles = game.getActiveShapeTiles();
        game.getPassiveShapes();
        game.moveRight();
        assertEquals(6, tiles[0].getX());
        assertEquals(7, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(7, tiles[3].getX());
    }
    
    @Test
    public void ActiveShapeMovesCorrectlyToDown() {
        TetrisGame game = new TetrisGame(0);
        game.getActiveShape();
        Tile[] tiles = game.getActiveShapeTiles();
        game.getPassiveShapes();
        game.moveDown();
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
    }
}