package tetris.domain;

//tetris-palikoiden muodostamista varten

public class Shape {
    
    private Tile[] tiles;
    private final Tile centerTile = new Tile(5, 1);
    private String type;
    private int orientation;
    
    public Shape(String type) {
        this.tiles = new Tile[4];
        this.tiles[0] = centerTile;
        this.type = type;
        this.orientation = 1;
        createShape(type);
    }
    
    //loput metodit apuna palikoiden luomisessa
    private void createShape(String type) {
        switch (type) {
            case "L":
                createL();
                break;
            case "J":
                createJ();
                break;
            case "S":
                createS();
                break;
            case "Z":
                createZ();
                break;
            case "O":
                createO();
                break;
            case "I":
                createI();
                break;
            case "T":
                createT();
                break;
        }
    }
    
    private void createL() {
        this.tiles[1] = new Tile(centerTile.getX() + 1, centerTile.getY() - 1);
        this.tiles[2] = new Tile(centerTile.getX() - 1, centerTile.getY());
        this.tiles[3] = new Tile(centerTile.getX() + 1, centerTile.getY());
    }
    
    private void createJ() {
        this.tiles[1] = new Tile(centerTile.getX() - 1, centerTile.getY() - 1);
        this.tiles[2] = new Tile(centerTile.getX() - 1, centerTile.getY());
        this.tiles[3] = new Tile(centerTile.getX() + 1, centerTile.getY());
    }
    
    private void createS() {
        this.tiles[1] = new Tile(centerTile.getX() + 1, centerTile.getY() - 1);
        this.tiles[2] = new Tile(centerTile.getX(), centerTile.getY() - 1);
        this.tiles[3] = new Tile(centerTile.getX() - 1, centerTile.getY());
    }
    
    private void createZ() {
        this.tiles[1] = new Tile(centerTile.getX() - 1, centerTile.getY() - 1);
        this.tiles[2] = new Tile(centerTile.getX(), centerTile.getY() - 1);
        this.tiles[3] = new Tile(centerTile.getX() + 1, centerTile.getY());
    }
    
    private void createO() {
        this.tiles[1] = new Tile(centerTile.getX() - 1, centerTile.getY() - 1);
        this.tiles[2] = new Tile(centerTile.getX(), centerTile.getY() - 1);
        this.tiles[3] = new Tile(centerTile.getX() - 1, centerTile.getY());
    }
    
    private void createI() {
        this.tiles[1] = new Tile(centerTile.getX() - 2, centerTile.getY());
        this.tiles[2] = new Tile(centerTile.getX() - 1, centerTile.getY());
        this.tiles[3] = new Tile(centerTile.getX() + 1, centerTile.getY());
    }
    
    private void createT() {
        this.tiles[1] = new Tile(centerTile.getX(), centerTile.getY() - 1);
        this.tiles[2] = new Tile(centerTile.getX() - 1, centerTile.getY());
        this.tiles[3] = new Tile(centerTile.getX() + 1, centerTile.getY());
    }
    
    
    public Tile[] getTiles() {
        return tiles;
    }

    public String getType() {
        return type;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
