package tetris.domain;

/**
 * Leaderboardiin pääsevää pelitulosta kuvaava luokka.
 * Muodostuu pelaajan syöttämästä nimimerkistä ja pelin aikana kerätyistä kokonaispisteistä.
 */
public class HighScore {
    
    private String player;
    private int points;
    
    public HighScore(String player, int points) {
        this.player = player;
        this.points = points;
    }

    public String getPlayer() {
        return player;
    }
    
    public int getPoints() {
        return points;
    }
    
}
