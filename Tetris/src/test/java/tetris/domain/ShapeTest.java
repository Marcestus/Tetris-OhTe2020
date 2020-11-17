package tetris.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShapeTest {
    
    @Before
    public void setUp() {
        
    }
    
    @Test
    public void LShapeIsGenerated() {
        Shape shape = new Shape("L");
        Tile[] tiles = shape.getTiles();
        assertEquals(6,tiles[1].getX());
        assertEquals(4,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
    
    public void JShapeIsGenerated() {
        Shape shape = new Shape("J");
        Tile[] tiles = shape.getTiles();
        assertEquals(4,tiles[1].getX());
        assertEquals(4,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
    
    public void SShapeIsGenerated() {
        Shape shape = new Shape("S");
        Tile[] tiles = shape.getTiles();
        assertEquals(5,tiles[1].getX());
        assertEquals(4,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
    
    public void ZShapeIsGenerated() {
        Shape shape = new Shape("Z");
        Tile[] tiles = shape.getTiles();
        assertEquals(4,tiles[1].getX());
        assertEquals(5,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
    
    public void OShapeIsGenerated() {
        Shape shape = new Shape("O");
        Tile[] tiles = shape.getTiles();
        assertEquals(4,tiles[1].getX());
        assertEquals(5,tiles[2].getX());
        assertEquals(4,tiles[3].getX());
    }
    
    public void IShapeIsGenerated() {
        Shape shape = new Shape("I");
        Tile[] tiles = shape.getTiles();
        assertEquals(3,tiles[1].getX());
        assertEquals(4,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
    
    public void TShapeIsGenerated() {
        Shape shape = new Shape("T");
        Tile[] tiles = shape.getTiles();
        assertEquals(5,tiles[1].getX());
        assertEquals(4,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }

}
