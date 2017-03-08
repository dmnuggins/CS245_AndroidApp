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

    // method: Score
    // purpose: this method acts as the class constructor.
    Score(){}

    // method: Score
    // purpose: this method also acts as a constructor, but with parameters.
    public Score(String difficulty, String username, String score){
        super();
        this.difficulty = difficulty;
        this.username = username;
        this.score = score;
    }

    // method: toString
    // purpose: this method returns the given Score as a readable String.
    @Override
    public String toString() {
        return "Score [id=" + id + ", difficulty=" + difficulty + ", username=" + username + ", score=" + score
                + "]";
    }

    // method: getId
    // purpose: id getter
    public int getId() {
        return id;
    }

    // method: setId
    // purpose: id setter
    public void setId(int id) {
        this.id = id;
    }

    // method: getDifficulty
    // purpose: difficulty getter
    String getDifficulty() {
        return difficulty;
    }

    // method: setDifficulty
    // purpose: difficulty setter
    void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // method: getUsername
    // purpose: username getter
    String getUsername() {
        return username;
    }

    // method: setUsername
    // purpose: username setter
    void setUsername(String username) {
        this.username = username;
    }

    // method: getScore
    // purpose: score getter
    String getScore() {
        return score;
    }

    // method: setScore
    // purpose: score setter
    void setScore(String score) {
        this.score = score;
    }
}
