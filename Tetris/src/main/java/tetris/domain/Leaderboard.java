package tetris.domain;

import java.util.*;
import tetris.dao.LeaderboardDao;

public class Leaderboard {
    
    private ArrayList<HighScore> leaderboard;
    private LeaderboardDao lbDao;
    
    public Leaderboard() {
        this.lbDao = new LeaderboardDao("leaderboard.db");
        this.leaderboard = lbDao.getLeaderBoardFromDatabase();
        Collections.sort(leaderboard);
    }
    
    public int getHighScore() {
        if (!leaderboard.isEmpty()) {
            return leaderboard.get(0).getPoints();
        }
        return 0;
    }

    public ArrayList<String> getLeaderBoardPlayers() {
        ArrayList<String> players = new ArrayList<>();
        leaderboard = lbDao.getLeaderBoardFromDatabase();
        for (HighScore score : leaderboard) {
            players.add(score.getPlayer());
        }
        return players;
    }
    
    public ArrayList<Integer> getLeaderBoardPoints() {
        ArrayList<Integer> points = new ArrayList<>();
        leaderboard = lbDao.getLeaderBoardFromDatabase();
        for (HighScore score : leaderboard) {
            points.add(score.getPoints());
        }
        return points;
    }
    
    public boolean pointsEnoughForLeaderboard(int currentPoints) {
        if (currentPoints == 0) {
            return false;
        }
        if (leaderboard.size() < 5) {
            return true;
        }
        return currentPoints > leaderboard.get(leaderboard.size() - 1).getPoints();
    }
    
    public void addNewHighScoreToLeaderboard(String player, int currentPoints) {
        if (leaderboard.size() >= 5) {
            leaderboard = lbDao.removeScoreFromDatabase();
        }
        leaderboard = lbDao.addScoreToDatabase(player, currentPoints);
    }
}
