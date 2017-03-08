/***************************************************************
 * file: MySQLiteHelper.java
 * author: E. Lee, D. Nyugen, S. Lee, H. Bozawglanian, J. Canalita
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: Android App
 * date last modified: 3/07/2017
 *
 * purpose: This program is the SQLite helper class to make
 *          database queries easier as the database is updated
 *
 ****************************************************************/

package cs245.concentration.Game;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ScoreDB";
    // method: MySQLiteHelper
    // purpose: this method acts as the class constructor.
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // method: onCreate
    // purpose: this method initializes and creates a SQLite table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLE = "CREATE TABLE scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "difficulty TEXT NOT NULL, "+
                "username TEXT NOT NULL, " +
                "score TEXT NOT NULL)";
        db.execSQL(CREATE_SCORE_TABLE);
    }

    // method: onUpgrade
    // purpose: this method deletes a table if an old version is found.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS scores");
        this.onCreate(db);
    }

    private static final String TABLE_SCORES = "scores";
    private static final String KEY_ID = "id";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_SCORE = "score";

    // method: addScore
    // purpose: this method adds a certain score into the SQLite database.
    public void addScore(Score score){
        Log.d("addScore", score.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DIFFICULTY, score.getDifficulty());
        values.put(KEY_USERNAME, score.getUsername());
        values.put(KEY_SCORE, score.getScore());
        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    // method: getDifficultyScores
    // purpose: this method returns a list of 3 scores all done at a certain
    //  difficulty.
    List<Score> getDifficultyScores (String difficulty){
        List<Score> scores = new LinkedList<>();
        String query = "SELECT * FROM scores WHERE difficulty =" + difficulty + " ORDER BY score DESC LIMIT 3";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Score score;
        if (cursor.moveToFirst()) {
            do {
                score = new Score();
                score.setId(Integer.parseInt(cursor.getString(0)));
                score.setDifficulty(cursor.getString(1));
                score.setUsername(cursor.getString(2));
                score.setScore(cursor.getString(3));
                scores.add(score);
            } while (cursor.moveToNext());
        }
        Log.d("getDifficultyScores()", scores.toString());
        return scores;
    }

    // method: getAllScores
    // purpose: this method returns a list of every score in the database.
    public List<Score> getAllScores() {
        List<Score> scores = new LinkedList<>();
        String query = "SELECT  * FROM " + TABLE_SCORES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Score score ;
        if (cursor.moveToFirst()) {
            do {
                score = new Score();
                score.setId(Integer.parseInt(cursor.getString(0)));
                score.setDifficulty(cursor.getString(1));
                score.setUsername(cursor.getString(2));
                score.setScore(cursor.getString(3));
                scores.add(score);
            } while (cursor.moveToNext());
        }
        Log.d("getAllScores()", scores.toString());
        return scores;
    }

    // method: deleteScore
    // purpose: this method deletes a score from the database.
    public void deleteScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORES, KEY_ID+" = ?", new String[] { String.valueOf(score.getId())});
        db.close();
        Log.d("deleteScore", score.toString());

    }

}
