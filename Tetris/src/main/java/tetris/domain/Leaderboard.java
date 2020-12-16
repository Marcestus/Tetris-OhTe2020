package tetris.domain;

import java.util.*;
import tetris.dao.LeaderboardDao;

/**
 * Leaderboardia ja sen toimintalogiikkaa kuvaava luokka.
 * Pelin viittä parasta tulosta pidetään tallessa tietokannassa. Tämä luokka hallinnoi pelitulosten hakemista ja tallentamista tietokantaan.
 */

public class Leaderboard {
    
    private ArrayList<HighScore> leaderboard;
    private LeaderboardDao lbDao;
    
    public Leaderboard(String dataBaseName) {
        this.lbDao = new LeaderboardDao(dataBaseName);
        this.leaderboard = lbDao.getLeaderBoardFromDatabase();
    }

    public LeaderboardDao getLbDao() {
        return lbDao;
    }

    public ArrayList<HighScore> getLeaderboard() {
        return leaderboard;
    }

    /**
     * Leaderboardin parhaan tuloksen tulostaminen.
     * @return nolla, jos leaderboard on tyhjä, muuten leaderboardin paras pelitulos
     */
    public int getHighScore() {
        if (leaderboard.isEmpty()) {
            return 0;
        }
        return leaderboard.get(0).getPoints();
    }
    
    /**
     * Leaderboardin pelitulosten nimimerkkien tulostaminen.
     * @see tetris.dao.LeaderboardDao#getLeaderBoardFromDatabase() 
     * @return leaderboardin pelitulosten nimimerkit listana
     */
    public ArrayList<String> getLeaderBoardPlayers() {
        ArrayList<String> players = new ArrayList<>();
        leaderboard = lbDao.getLeaderBoardFromDatabase();
        for (HighScore highscore : leaderboard) {
            players.add(highscore.getPlayer());
        }
        return players;
    }
    
    /**
     * Leaderboardin pelitulosten pisteiden tulostaminen.
     * @see tetris.dao.LeaderboardDao#getLeaderBoardFromDatabase() 
     * @return leaderboardin pelitulosten pisteet listana
     */
    public ArrayList<Integer> getLeaderBoardPoints() {
        ArrayList<Integer> points = new ArrayList<>();
        leaderboard = lbDao.getLeaderBoardFromDatabase();
        for (HighScore highscore : leaderboard) {
            points.add(highscore.getPoints());
        }
        return points;
    }
    
    /**
     * Päättyneen pelikierroksen pisteiden vertaaminen leaderboardin viimeiseen pelitulokseen.
     * @param currentPoints päättyneen pelikierroksen pisteet
     * @return true, jos leaderboardissa on alle viisi tulosta tai jos päättyneen pelikierroksen pistemäärä on suurempi kuin leaderboardin viimeinen pelitulos ja
     * false, jos päättyneen pelikierroksen pistemäärä on nolla tai pienempi kuin leaderboardin viimeinen pelitulos
     */
    public boolean pointsEnoughForLeaderboard(int currentPoints) {
        if (currentPoints == 0) {
            return false;
        }
        if (leaderboard.size() < 5) {
            return true;
        }
        return currentPoints > leaderboard.get(leaderboard.size() - 1).getPoints();
    }
    
    /**
     * Päättyneen pelikierroksen pisteiden lisääminen leaderboardiin.
     * Jos leaderboardissa on jo viisi tulosta, poistetaan viidennellä sijalla oleva.
     * @param player pelaajan antama nimimerkki
     * @param currentPoints päättyneen pelikierroksen pisteet
     * @see tetris.dao.LeaderboardDao#removeScoreFromDatabase() 
     * @see tetris.dao.LeaderboardDao#addScoreToDatabase(java.lang.String, int) 
     */
    public void addNewHighScoreToLeaderboard(String player, int currentPoints) {
        if (leaderboard.size() >= 5) {
            leaderboard = lbDao.removeScoreFromDatabase();
        }
        leaderboard = lbDao.addScoreToDatabase(player, currentPoints);
    }
}
