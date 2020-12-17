package tetris.domain;

/**
 * Pelin pisteytystä kuvaava luokka.
 * Pisteitä kertyy, kun täysiä vaakasuoria rivejä saadaan täytettyä.
 */

public class Score {
    private int points;
    private int linesCleared;
    private int gameLevel;
    private String gameLevelText;
    
    public Score() {
        this.points = 0;
        this.linesCleared = 0;
        this.gameLevel = 0;
        this.gameLevelText = "";
    }

    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public int getLinesCleared() {
        return linesCleared;
    }
    
    public void setLinesCleared(int linesCleared) {
        this.linesCleared = linesCleared;
    }

    public int getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }
    
    public String getGameLevelText() {
        return gameLevelText;
    }

    public void setGameLevelText(String gameLevelText) {
        this.gameLevelText = gameLevelText;
        
    }
    
    /**
     * Pisteiden kasvattaminen.
     * Mitä enemmän rivejä saa poistettua kerralla, ja mitä kovemmalla vaikeustasolla pelaa, sitä enemmän pisteitä kertyy.
     * @param fullRowsAtSameTime montako riviä on poistettu kerralla
     */
    public void addPoints(int fullRowsAtSameTime) {
        switch (fullRowsAtSameTime) {
            case 1:
                this.setPoints(this.getPoints() + (40 * this.getGameLevel()));
                break;
            case 2:
                this.setPoints(this.getPoints() + (100 * this.getGameLevel()));
                break;
            case 3:
                this.setPoints(this.getPoints() + (300 * this.getGameLevel()));
                break;
            case 4:
                this.setPoints(this.getPoints() + (1200 * this.getGameLevel()));
        }
    }
    
    /**
     * Poistettujen rivien kokonaismäärän kasvattaminen poistettujen rivien määrällä.
     * @param fullRowsAtSameTime montako riviä on poistettu kerralla
     */
    public void addClearedLines(int fullRowsAtSameTime) {
        this.setLinesCleared(this.getLinesCleared() + fullRowsAtSameTime);
    }
}
