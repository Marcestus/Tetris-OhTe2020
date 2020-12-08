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
    
    public boolean getMaxLevelReached() {
        return maxLevelReached;
    }
    
    public void setMaxLevelReached(boolean maxLevelReached) {
        this.maxLevelReached = maxLevelReached;
    }
    
    /**
     * Pisteiden kasvattaminen poistettujen rivien mukaisella määrällä pisteitä.
     * Mitä enemmän pisteitä poistetaan kerralla, sitä enemmän niitä saa pisteitä.
     */
    public void addPoints(int fullRowsAtSameTime) {
        switch (fullRowsAtSameTime) {
            case 1:
                this.setPoints(this.getPoints() + (40 * (this.getLevel() + 1)));
                break;
            case 2:
                this.setPoints(this.getPoints() + (100 * (this.getLevel() + 1)));
                break;
            case 3:
                this.setPoints(this.getPoints() + (300 * (this.getLevel() + 1)));
                break;
            case 4:
                this.setPoints(this.getPoints() + (1200 * (this.getLevel() + 1)));
                break;
        }
    }
    
    /**
     * Poistettujen rivien kokonaismäärän kasvattaminen poistettujen rivien määrällä.
     */
    public void addClearedLines(int fullRowsAtSameTime) {
        this.setLinesCleared(this.getLinesCleared() + fullRowsAtSameTime);
    }
    
    /**
     * Vaikeustason määrittäminen poistettujen rivien kokonaismäärän mukaiseksi.
     * Pelissä päästään uudelle vaikeustasolle joka kymmenennen rivin poistamisen jälkeen.
     */
    public void levelUp() {
        if (this.getLinesCleared() >= ((this.getLevel() + 1) * 10) && this.getLevel() < this.getMaxLevel()) {
            this.setLevel(this.getLevel() + 1);
        }
        if (this.getLevel() == 10) {
            this.setMaxLevelReached(true);
        }
    }
}
