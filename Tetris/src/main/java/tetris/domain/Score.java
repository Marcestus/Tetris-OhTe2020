package tetris.domain;

/**
 * Pelin pisteytystä kuvaava luokka.
 * Pisteytykseen vaikuttavat poistettujen rivien määrä
 * ja pelin nykyinen vaikeustaso (level).
 * Lisäksi luokassa pidetään yllä tietoa maksimilevelistä ja siitä,
 * onko se jo saavutettu.
 */

public class Score {
    private int points;
    private int level;
    private int linesCleared;
    private int maxLevel;
    private boolean maxLevelReached;
    
    public Score() {
        this.points = 0;
        this.level = 0;
        this.linesCleared = 0;
        this.maxLevel = 10;
        this.maxLevelReached = false;
    }

    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getLinesCleared() {
        return linesCleared;
    }
    
    public void setLinesCleared(int linesCleared) {
        this.linesCleared = linesCleared;
    }
    
    public int getMaxLevel() {
        return maxLevel;
    }
    
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
    
    public boolean getMaxLevelReached() {
        return maxLevelReached;
    }
    
    public void setMaxLevelReached(boolean maxLevelReached) {
        this.maxLevelReached = maxLevelReached;
    }
}
