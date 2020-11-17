package tetris.domain;

//tetris-palikoiden muodostamista varten

public class Shape {
    
    private Tile[] tiles;
    private String type;
    
    public Shape(String type) {
        this.tiles = new Tile[4];
        this.tiles[0] = new Tile(5,1);
        this.type = type;
        createShape(type);
    }
    
    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Shape{" + "tiles=" + tiles + ", type=" + type + '}';
    }
    
    //loput metodit apuna palikoiden luomisessa
    private void createShape(String type) {
        switch(type) {
            case "L": createL(); break;
            case "J": createJ(); break;
            case "S": createS(); break;
            case "Z": createZ(); break;
            case "O": createO(); break;
            case "I": createI(); break;
            case "T": createT(); break;
        }
    }
    
    private void createL() {
        this.tiles[1] = new Tile(6,0);
        this.tiles[2] = new Tile(4,1);
        this.tiles[3] = new Tile(6,1);
    }
    
    private void createJ() {
        this.tiles[1] = new Tile(4,0);
        this.tiles[2] = new Tile(4,1);
        this.tiles[3] = new Tile(6,1);
    }
    
    private void createS() {
        this.tiles[1] = new Tile(5,0);
        this.tiles[2] = new Tile(6,0);
        this.tiles[3] = new Tile(4,1);
    }
    
    private void createZ() {
        this.tiles[1] = new Tile(4,0);
        this.tiles[2] = new Tile(5,0);
        this.tiles[3] = new Tile(6,1);
    }
    
    private void createO() {
        this.tiles[1] = new Tile(4,0);
        this.tiles[2] = new Tile(5,0);
        this.tiles[3] = new Tile(4,1);
    }
    
    private void createI() {
        this.tiles[1] = new Tile(3,1);
        this.tiles[2] = new Tile(4,1);
        this.tiles[3] = new Tile(6,1);
    }
    
    private void createT() {
        this.tiles[1] = new Tile(5,0);
        this.tiles[2] = new Tile(4,1);
        this.tiles[3] = new Tile(6,1);
    }
    
}
