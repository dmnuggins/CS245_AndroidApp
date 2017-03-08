package cs245.concentration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import cs245.concentration.Game.CustomPagerAdapter;
import cs245.concentration.Game.MySQLiteHelper;
import cs245.concentration.Game.Score;

public class ScoreActivity extends AppCompatActivity {

    //private int difficulty;
    //private int score;
    //private String name;
    private TextView nameTxt;
    private TextView scoreTxt;
    private TextView diffcultyTxt;

    public MySQLiteHelper getDb() {
        return db;
    }

    MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Intent intent = getIntent();
        int difficulty = intent.getIntExtra("difficulty", 0);
        String name = intent.getStringExtra("name");
        int score = intent.getIntExtra("score", 0);
//        nameTxt = (TextView) findViewById(R.id.name);
//        scoreTxt = (TextView) findViewById(R.id.score);
//        diffcultyTxt = (TextView) findViewById(R.id.difficulty);

        db = new MySQLiteHelper(this);

        db.addScore(new Score(Integer.toString(difficulty), name, Integer.toString(score)));

//        db.addScore(new Score("4", "lee", "5"));
//        db.addScore(new Score("8", "lee", "9"));
//        db.addScore(new Score("4", "lee", "4"));
//        db.addScore(new Score("4", "lee", "2"));
//        db.addScore(new Score("6", "lee", "4"));
//        db.addScore(new Score("12", "lee", "5"));
//        db.addScore(new Score("20", "lee", "1"));
//        db.addScore(new Score("4", "lee", "3"));
//
//        List<Score> list = db.getDifficultyScores("4");
//
//        db.deleteScore(list.get(0));
//
//        db.getAllScores();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this, db));
        //Intent intent = getIntent();
        //difficulty = intent.getIntExtra("difficulty", 0);
        //name = intent.getStringExtra("name");
        //score = intent.getIntExtra("score", 0);

//        scoreTxt.setText(Integer.toString(score));
//        nameTxt.setText(name);
//        diffcultyTxt.setText(Integer.toString(difficulty));


    }
}
