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
    TetrisGame shapeJGame;
    TetrisGame shapeSGame;
    TetrisGame shapeZGame;
    TetrisGame shapeOGame;
    TetrisGame shapeIGame;
    TetrisGame shapeTGame;
    
    public TetrisGameTest() {
        this.randomGame = new TetrisGame();
        this.shapeLGame = new TetrisGame(0);
        this.shapeJGame = new TetrisGame(1);
        this.shapeSGame = new TetrisGame(2);
        this.shapeZGame = new TetrisGame(3);
        this.shapeOGame = new TetrisGame(4);
        this.shapeIGame = new TetrisGame(5);
        this.shapeTGame = new TetrisGame(6);
        
    }
    
    @Test
    public void ActiveShapeMovesCorrectlyToLeft() {
        Tile[] tiles = shapeLGame.getActiveShapeTiles();
        shapeLGame.moveLeft();
        assertEquals(4, tiles[0].getX());
        assertEquals(5, tiles[1].getX());
        assertEquals(3, tiles[2].getX());
        assertEquals(5, tiles[3].getX());
        shapeLGame.moveLeft();
        shapeLGame.moveLeft();
        shapeLGame.moveLeft();
        assertEquals(1, tiles[0].getX());
        assertEquals(2, tiles[1].getX());
        assertEquals(0, tiles[2].getX());
        assertEquals(2, tiles[3].getX());
        shapeLGame.moveLeft();
        assertEquals(1, tiles[0].getX());
        assertEquals(2, tiles[1].getX());
        assertEquals(0, tiles[2].getX());
        assertEquals(2, tiles[3].getX());
    }
    
    @Test
    public void ActiveShapeMovesCorrectlyToRight() {
        Tile[] tiles = shapeLGame.getActiveShapeTiles();
        shapeLGame.moveRight();
        assertEquals(6, tiles[0].getX());
        assertEquals(7, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(7, tiles[3].getX());
        shapeLGame.moveRight();
        shapeLGame.moveRight();
        assertEquals(8, tiles[0].getX());
        assertEquals(9, tiles[1].getX());
        assertEquals(7, tiles[2].getX());
        assertEquals(9, tiles[3].getX());
        shapeLGame.moveRight();
        assertEquals(8, tiles[0].getX());
        assertEquals(9, tiles[1].getX());
        assertEquals(7, tiles[2].getX());
        assertEquals(9, tiles[3].getX());
    }
    
    @Test
    public void passiveTilesBlockSideMovementCorrectly() {
        ArrayList<Tile> passiveTiles = shapeOGame.getPassiveTiles();
        Tile[] tiles = shapeOGame.getActiveShapeTiles();
        passiveTiles.add(new Tile(3,1));
        shapeOGame.moveLeft();
        assertEquals(5, tiles[0].getX());
        assertEquals(4, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(4, tiles[3].getX());
        passiveTiles.add(new Tile(6,1));
        shapeOGame.moveRight();
        assertEquals(5, tiles[0].getX());
        assertEquals(4, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(4, tiles[3].getX());
    }
    
    @Test
    public void ActiveShapeMovesCorrectlyToDown() {
        ArrayList<Tile> passiveTiles = shapeJGame.getPassiveTiles();
        Tile[] tiles = shapeJGame.getActiveShapeTiles();
        shapeJGame.moveDown();
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
        passiveTiles.add(new Tile(5,3));
        shapeJGame.moveDown();
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
    }
    
    @Test
    public void hardDropWorksWhenNoPassivesOnTheWay() {
        Tile[] tiles = randomGame.getActiveShapeTiles();
        randomGame.hardDrop();
        assertEquals(5, tiles[0].getX());
        assertEquals(19, tiles[0].getY());
    }
    
    @Test
    public void hardDropWorksWithPassivesOnTheWay() {
        ArrayList<Tile> passiveTiles = randomGame.getPassiveTiles();
        Tile[] tiles = randomGame.getActiveShapeTiles();
        passiveTiles.add(new Tile(5,19));
        randomGame.hardDrop();
        assertEquals(5, tiles[0].getX());
        assertEquals(18, tiles[0].getY());
    }
    
    @Test
    public void LShapeRotatesCorrectly() {
        Tile[] tiles = shapeLGame.getActiveShapeTiles();
        shapeLGame.moveDown();
        shapeLGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(6, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(5, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(3, tiles[1].getY());
        assertEquals(1, tiles[2].getY());
        assertEquals(3, tiles[3].getY());
        shapeLGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(4, tiles[1].getX());
        assertEquals(6, tiles[2].getX());
        assertEquals(4, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(3, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
        shapeLGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(4, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(5, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(3, tiles[2].getY());
        assertEquals(1, tiles[3].getY());
        shapeLGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(6, tiles[1].getX());
        assertEquals(4, tiles[2].getX());
        assertEquals(6, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
    }
    
    @Test
    public void SShapeRotatesCorrectly() {
        Tile[] tiles = shapeSGame.getActiveShapeTiles();
        shapeSGame.moveDown();
        shapeSGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(6, tiles[1].getX());
        assertEquals(6, tiles[2].getX());
        assertEquals(5, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(3, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(1, tiles[3].getY());
        shapeSGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(6, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(4, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(1, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
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
        shapeZGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(4, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(6, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(1, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
    }
    
    @Test
    public void OShapeDoesNotRotate() {
        Tile[] tiles = shapeOGame.getActiveShapeTiles();
        shapeOGame.moveDown();
        assertEquals(5, tiles[0].getX());
        assertEquals(4, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(4, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(1, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
        shapeOGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(4, tiles[1].getX());
        assertEquals(5, tiles[2].getX());
        assertEquals(4, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(1, tiles[2].getY());
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
        shapeIGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(3, tiles[1].getX());
        assertEquals(4, tiles[2].getX());
        assertEquals(6, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(2, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
    }
    
    @Test
    public void rotationIsCorrectlyBlockedByPassiveShape() {
        ArrayList<Tile> passiveTiles = shapeTGame.getPassiveTiles();
        Tile[] tiles = shapeTGame.getActiveShapeTiles();
        shapeTGame.moveDown();
        assertEquals(5, tiles[0].getX());
        assertEquals(5, tiles[1].getX());
        assertEquals(4, tiles[2].getX());
        assertEquals(6, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
        passiveTiles.add(new Tile(5,3));
        shapeTGame.rotate();
        assertEquals(5, tiles[0].getX());
        assertEquals(5, tiles[1].getX());
        assertEquals(4, tiles[2].getX());
        assertEquals(6, tiles[3].getX());
        assertEquals(2, tiles[0].getY());
        assertEquals(1, tiles[1].getY());
        assertEquals(2, tiles[2].getY());
        assertEquals(2, tiles[3].getY());
    }
    
    @Test
    public void rotationIsCorrectlyBlockedByEdge() {
        ArrayList<Tile> passiveTiles = shapeTGame.getPassiveTiles();
        Tile[] tiles = shapeTGame.getActiveShapeTiles();
        shapeTGame.moveLeft();
        shapeTGame.moveLeft();
        shapeTGame.moveLeft();
        shapeTGame.moveLeft();
        assertEquals(1, tiles[0].getX());
        assertEquals(1, tiles[1].getX());
        assertEquals(0, tiles[2].getX());
        assertEquals(2, tiles[3].getX());
        shapeTGame.rotate();
        shapeTGame.moveLeft();
        assertEquals(0, tiles[0].getX());
        assertEquals(1, tiles[1].getX());
        assertEquals(0, tiles[2].getX());
        assertEquals(0, tiles[3].getX());
        shapeTGame.rotate();
        assertEquals(0, tiles[0].getX());
        assertEquals(1, tiles[1].getX());
        assertEquals(0, tiles[2].getX());
        assertEquals(0, tiles[3].getX());
        for (int i = 0; i < 17; i++) {
            shapeTGame.moveDown();
        }
        assertEquals(18, tiles[0].getY());
        assertEquals(18, tiles[1].getY());
        assertEquals(17, tiles[2].getY());
        assertEquals(19, tiles[3].getY());
        shapeTGame.moveDown();
        assertEquals(18, tiles[0].getY());
        assertEquals(18, tiles[1].getY());
        assertEquals(17, tiles[2].getY());
        assertEquals(19, tiles[3].getY());
    }
    
    @Test
    public void gameLevelGetsSetCorrectly() {
        randomGame.setGameSpeedAndGameLevel("Easy");
        assertEquals(1, randomGame.getScore().getGameLevel());
        randomGame.setGameSpeedAndGameLevel("Moderate");
        assertEquals(2, randomGame.getScore().getGameLevel());
        randomGame.setGameSpeedAndGameLevel("Hard");
        assertEquals(3, randomGame.getScore().getGameLevel());
    }
    
    @Test
    public void coordinatesThatAreTransferedToGameViewAreCorrect() {
        ArrayList<Tile> passiveTiles = randomGame.getPassiveTiles();
        Tile[] tiles = randomGame.getActiveShapeTiles();
        passiveTiles.add(new Tile(2,18));
        boolean[][] activeShapeCoord = randomGame.getActiveShapeCoord();
        assertEquals(true, activeShapeCoord[5][1]);
        randomGame.resetActiveShapeCoord();
        assertEquals(false, activeShapeCoord[5][1]);
        boolean[][] passiveShapeCoord = randomGame.getPassiveTileCoord();
        assertEquals(true, passiveShapeCoord[2][18]);
        randomGame.resetPassiveTileCoord();
        assertEquals(false, passiveShapeCoord[2][18]);
    }
    
    @Test
    public void fullRowsHandlerWorksCorrectly() {
        ArrayList<Tile> passiveTiles = randomGame.getPassiveTiles();
        Tile[] tiles = randomGame.getActiveShapeTiles();
        randomGame.fullRowsHandler();
        assertEquals(0, passiveTiles.size());
        for (int i = 0; i < 10; i++) {
            passiveTiles.add(new Tile(i,18));
        }
        passiveTiles.add(new Tile(5,17));
        assertEquals(11, passiveTiles.size());
        randomGame.fullRowsHandler();
        assertEquals(5, passiveTiles.get(0).getX());
        assertEquals(18, passiveTiles.get(0).getY());
        assertEquals(1, passiveTiles.size());
    }
    
    @Test
    public void gameOverOccursCorrectly() {
        ArrayList<Tile> passiveTiles = randomGame.getPassiveTiles();
        Tile[] tiles = randomGame.getActiveShapeTiles();
        assertEquals(false, randomGame.gameOver());
        passiveTiles.add(new Tile(5,2));
        randomGame.moveDown();
        assertEquals(true, randomGame.gameOver());
    }
}