package cs245.concentration;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import cs245.concentration.Game.CustomPagerAdapter;
import cs245.concentration.Game.MySQLiteHelper;
import cs245.concentration.Game.Score;

public class ScoreActivity extends AppCompatActivity{

    public MySQLiteHelper getDb() {
        return db;
    }

    MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        db = new MySQLiteHelper(this);

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

    }

}
