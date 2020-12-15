package tetris.domain;

public class HighScore implements Comparable<HighScore> {
    
    private String player;
    private int points;
    
    public HighScore(String player, int points) {
        this.player = player;
        this.points = points;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public int compareTo(HighScore other) {
        return other.points - this.points;
    }
}
