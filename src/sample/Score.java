package sample;

import javafx.scene.control.Label;

public class Score extends Label{
    private long score;
    /**
     * Creates new form Score
     */
    public Score() {
        this.score = 0;
        this.setMaxSize(150, 50);
        update();
    }

    /**
     * @return score
     */
    public long getScore(){
        return this.score;
    }

    /**
     * set the score value.
     * @param score: value to be assigned to score.
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * adds bonus to the score.
     * @param bonus: value to be added to bonus.
     */
    public void addScore(int bonus){
        this.score += bonus;
    }

    public void update(){
        this.setText(String.format("%08d", score));
    }
}