package cs245.concentration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by MingKie on 3/7/2017.
 */

public class ScoreActivity extends AppCompatActivity {

    //private int difficulty;
    //private int score;
    //private String name;
    private TextView nameTxt;
    private TextView scoreTxt;
    private TextView diffcultyTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Intent intent = getIntent();
        int difficulty = intent.getIntExtra("difficulty", 0);
        String name = intent.getStringExtra("name");
        int score = intent.getIntExtra("score", 0);
        nameTxt = (TextView) findViewById(R.id.name);
        scoreTxt = (TextView) findViewById(R.id.score);
        diffcultyTxt = (TextView) findViewById(R.id.difficulty);

        //Intent intent = getIntent();
        //difficulty = intent.getIntExtra("difficulty", 0);
        //name = intent.getStringExtra("name");
        //score = intent.getIntExtra("score", 0);

        scoreTxt.setText(Integer.toString(score));
        nameTxt.setText(name);
        diffcultyTxt.setText(Integer.toString(difficulty));



    }
}
