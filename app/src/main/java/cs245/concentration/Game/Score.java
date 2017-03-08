/***************************************************************
 * file: Score.java
 * author: E. Lee, D. Nyugen, S. Lee, H. Bozawglanian, J. Canalita
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: Android App
 * date last modified: 3/07/2017
 *
 * purpose: This class acts as the Score object to be taken after
 *          a completed game or as database input.
 *
 ****************************************************************/

package cs245.concentration.Game;

public class Score {

    private int id;
    private String difficulty;
    private String username;
    private String score;

    public Score(){}

    public Score(String difficulty, String username, String score){
        super();
        this.difficulty = difficulty;
        this.username = username;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score [id=" + id + ", difficulty=" + difficulty + ", username=" + username + ", score=" + score
                + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
