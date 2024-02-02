package pt.upskill.projeto1.game;

import java.io.Serializable;

/**
 *
 * Defines the Score object, to keep track of player score
 *
 */

public class Score implements Serializable, Comparable<Score> {

    private String playerName;
    private int playerScore;

    private int index;

    public Score(String playerName, int playerScore){
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(Score score) {
        if (this.getPlayerScore() > score.getPlayerScore())
            return -1;
        else if (this.getPlayerScore() < score.getPlayerScore()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return this.getIndex() + ". \n" + "Player name: " + this.getPlayerName() + "\nScore: " + this.getPlayerScore();
    }
}
