package cs245.concentration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        db = new MySQLiteHelper(this);

        db.addScore(new Score(Integer.toString(difficulty), name, Integer.toString(score)));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this, db));

        Button button = (Button) findViewById(R.id.reset_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Score score : db.getAllScores()) {
                    db.deleteScore(score);
                }
            }
        });

        Toast toast = Toast.makeText(this, "Swipe left for more scores!", Toast.LENGTH_LONG);
        toast.show();
    }
}
