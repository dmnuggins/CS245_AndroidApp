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

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLE = "CREATE TABLE scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "difficulty TEXT NOT NULL, "+
                "username TEXT NOT NULL, " +
                "score TEXT NOT NULL)";

        db.execSQL(CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS scores");

        // create fresh table
        this.onCreate(db);
    }

    // Scores table name
    private static final String TABLE_SCORES = "scores";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_SCORE = "score";

    private static final String[] COLUMNS = {KEY_ID,KEY_DIFFICULTY, KEY_USERNAME, KEY_SCORE};

    public void addScore(Score score){
        //for logging
        Log.d("addScore", score.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_DIFFICULTY, score.getDifficulty()); // get title
        values.put(KEY_USERNAME, score.getUsername()); // get author
        values.put(KEY_SCORE, score.getScore()); // get score

        // 3. insert
        db.insert(TABLE_SCORES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Score getScore(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_SCORES, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build object
        Score score = new Score();
        score.setId(Integer.parseInt(cursor.getString(0)));
        score.setDifficulty(cursor.getString(1));
        score.setUsername(cursor.getString(2));
        score.setScore(cursor.getString(3));

        //log
        Log.d("getScore("+id+")", score.toString());

        return score;
    }

    public List<Score> getDifficultyScores (String difficulty){
        List<Score> scores = new LinkedList<>();
        String query = "SELECT * FROM scores WHERE difficulty =" + difficulty + " ORDER BY score DESC LIMIT 3";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Score score = null;
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

    public List<Score> getAllScores() {
        List<Score> scores = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_SCORES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build and add it to list
        Score score = null;
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

    public int updateScore(Score score) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("difficulty", score.getDifficulty()); // get title
        values.put("username", score.getUsername()); // get author
        values.put("score", score.getScore()); // get author

        // 3. updating row
        int i = db.update(TABLE_SCORES, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(score.getId()) }); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteScore(Score score) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_SCORES, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(score.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteScore", score.toString());

    }

}
