/***************************************************************
 * file: ScoreActivity.java
 * author: E. Lee, D. Nguyen, S. Lee, H. Bozawglanian, J. Canalita
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: Android App
 * date last modified: 3/07/2017
 *
 * purpose: This activity runs the high scores via a Viewpager and
 *          a database.
 *
 ****************************************************************/

package cs245.concentration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cs245.concentration.Game.CustomPagerAdapter;
import cs245.concentration.Game.MySQLiteHelper;
import cs245.concentration.Game.Score;

public class ScoreActivity extends AppCompatActivity {

    MySQLiteHelper db;

    // method: onCreate
    // purpose: This method takes input of the recent score and adds it
    // to the database. Furthermore, initializes the ViewPager and Button.
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

        // up navigation
        Toolbar myChildToolbar =  (Toolbar) findViewById(R.id.scoreActionBar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // method: onOptionsItemsSelected
    // purpose: this method runs when the user selects from the action bar, navigating back to the
    //          parent activity (StartActivity)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.scoreActionBar:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
