package tetris.domain;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TetrisGameTest {
    
    TetrisGame randomGame;
    TetrisGame shapeLGame;
    TetrisGame shapeZGame;
    TetrisGame shapeIGame;
    
    public TetrisGameTest() {
        this.randomGame = new TetrisGame();
        this.shapeLGame = new TetrisGame(0);
        this.shapeZGame = new TetrisGame(3);
        this.shapeIGame = new TetrisGame(5);
    }
    
    @Test
    public void ActiveShapeMovesCorrectlyToLeft() {
        Tile[] tiles = shapeLGame.getActiveShapeTiles();
        shapeLGame.moveLeft();
        assertEquals(4, tiles[0].getX());
        assertEquals(5, tiles[1].getX());
        assertEquals(3, tiles[2].getX());
        assertEquals(5, tiles[3].getX());
    }
    
    @Test
    public void ActiveShapeMovesCorrectlyToRight() {
        Tile[] tiles = shapeLGame.getActiveShapeTiles();
        shapeLGame.moveRight();
        assertEquals(6, tiles[0].getX());
        assertEquals(7, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(7, tiles[3].getX());
    }
    
    @Test
    public void ActiveShapeMovesCorrectlyToDown() {
        Tile[] tiles = shapeLGame.getActiveShapeTiles();
        shapeLGame.moveDown();
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
    }
    
    @Test
    public void LShapeRotatesCorrectly() {
        Tile[] tiles = shapeLGame.getActiveShapeTiles();
        shapeLGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(6, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(5, tiles[3].getX());
        
        assertEquals(1, tiles[0].getY());
        assertEquals(2, tiles[1].getY());
        assertEquals(0, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
    }
    
    @Test
    public void IShapeRotatesCorrectly() {
        Tile[] tiles = shapeIGame.getActiveShapeTiles();
        shapeIGame.moveDown();
        shapeIGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(5, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(5, tiles[3].getX());
        
        assertEquals(2, tiles[0].getY());
        assertEquals(0, tiles[1].getY());
        assertEquals(1, tiles[2].getY());
        assertEquals(3, tiles[3].getY());
    }
    
    @Test
    public void ZShapeRotatesCorrectly() {
        Tile[] tiles = shapeZGame.getActiveShapeTiles();
        shapeZGame.moveDown();
        shapeZGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(6, tiles[1].getX());
        assertEquals(6, tiles[2].getX());
        assertEquals(5, tiles[3].getX());
        
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(3, tiles[3].getY());
    }
    
    @Test
    public void hardDropWorks() {
        Tile[] tiles = randomGame.getActiveShapeTiles();
        randomGame.hardDrop();
        assertEquals(5 ,tiles[0].getX());
        assertEquals(19 , tiles[0].getY());
    }
    
    @Test
    public void gameOverOccursCorrectly() {
        ArrayList<Tile> passiveTiles = randomGame.getPassiveTiles();
        Tile[] tiles = randomGame.getActiveShapeTiles();
        passiveTiles.add(new Tile(5,2));
        randomGame.moveDown();
        assertEquals(true, randomGame.gameOver());
    }
    
    @Test
    public void fullRowIsDetectedCorrectly() {
        ArrayList<Tile> passiveTiles = randomGame.getPassiveTiles();
        Tile[] tiles = randomGame.getActiveShapeTiles();
        for (int i = 0; i < 10; i++) {
            passiveTiles.add(new Tile(i,18));
        }
        assertEquals(true, randomGame.checkForFullRows());
    }
    
    @Test
    public void rowsGetDeletedCorrectly() {
        TetrisGame game = new TetrisGame();
        ArrayList<Tile> passiveTiles = randomGame.getPassiveTiles();
        Tile[] tiles = randomGame.getActiveShapeTiles();
        for (int i = 0; i < 10; i++) {
            passiveTiles.add(new Tile(i,18));
        }
        passiveTiles.add(new Tile(5,17));
        randomGame.checkForFullRows();
        randomGame.deleteRowsAndDropRowsAboveFullRows();
        assertEquals(5, passiveTiles.get(0).getX());
        assertEquals(18, passiveTiles.get(0).getY());
        assertEquals(1, passiveTiles.size());
    }
}