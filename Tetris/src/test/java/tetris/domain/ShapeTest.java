package tetris.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShapeTest {
    
    public ShapeTest() {
        
    }
    
    @Test
    public void LShapeIsGeneratedProperly() {
        Shape shapeL = new Shape("L");
        Tile[] tiles = shapeL.getTiles();
        assertEquals(5,tiles[0].getX());
        assertEquals(6,tiles[1].getX());
        assertEquals(4,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
    
    @Test
    public void JShapeIsGeneratedProperly() {
        Shape shapeJ = new Shape("J");
        Tile[] tiles = shapeJ.getTiles();
        assertEquals(5,tiles[0].getX());
        assertEquals(4,tiles[1].getX());
        assertEquals(4,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
    
    @Test
    public void SShapeIsGeneratedProperly() {
        Shape shapeS = new Shape("S");
        Tile[] tiles = shapeS.getTiles();
        assertEquals(5,tiles[0].getX());
        assertEquals(6,tiles[1].getX());
        assertEquals(5,tiles[2].getX());
        assertEquals(4,tiles[3].getX());
    }
    
    @Test
    public void ZShapeIsGeneratedProperly() {
        Shape shapeZ = new Shape("Z");
        Tile[] tiles = shapeZ.getTiles();
        assertEquals(5,tiles[0].getX());
        assertEquals(4,tiles[1].getX());
        assertEquals(5,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
    
    @Test
    public void OShapeIsGeneratedProperly() {
        Shape shapeO = new Shape("O");
        Tile[] tiles = shapeO.getTiles();
        assertEquals(5,tiles[0].getX());
        assertEquals(4,tiles[1].getX());
        assertEquals(5,tiles[2].getX());
        assertEquals(4,tiles[3].getX());
    }
    
    @Test
    public void IShapeIsGeneratedProperly() {
        Shape shapeI = new Shape("I");
        Tile[] tiles = shapeI.getTiles();
        assertEquals(5,tiles[0].getX());
        assertEquals(3,tiles[1].getX());
        assertEquals(4,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
    
    @Test
    public void TShapeIsGeneratedProperly() {
        Shape shapeT = new Shape("T");
        Tile[] tiles = shapeT.getTiles();
        assertEquals(5,tiles[0].getX());
        assertEquals(5,tiles[1].getX());
        assertEquals(4,tiles[2].getX());
        assertEquals(6,tiles[3].getX());
    }
}

