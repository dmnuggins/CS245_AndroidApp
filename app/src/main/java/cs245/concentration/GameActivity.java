package cs245.concentration;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import cs245.concentration.Game.CardAdapter;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    CardAdapter cardAdapter;

    int input = 0;
    int position = 0;

    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";

    private MusicFragment mRetainedFragment;

    private final String[] answers = new String[]{
            "DOLPHIN", "WHALE", "SHARK", "OCTOPUS", "RAY", "TURTLE", "SEAL", "STARFISH", "JELLYFISH", "CRAB"
    };

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        input = intent.getIntExtra("input", 0);

        cardAdapter = new CardAdapter(this, cardList(input));

        gridView = (GridView) findViewById(R.id.cardsGridView);
        gridView.setAdapter(cardAdapter);

        // find the retained fragment on activity restarts
        FragmentManager manager = getFragmentManager();
        mRetainedFragment = (MusicFragment) manager.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // create the fragment and data the first time
        if (mRetainedFragment == null) {
            // add the fragment
            mRetainedFragment = new MusicFragment();
            manager.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
            mRetainedFragment.setData(mRetainedFragment.getData());
        }



        Button musicBtn = (Button) findViewById(R.id.musicButton);

        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRetainedFragment.toggleMusic();
            }
        });




    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
    }

    public void newGame(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void tryAgain(View view) {
        // will do later
    }

    public void endGame(View view) {
        // will do later
    }

    protected ArrayList<String> cardList(int input) {
        input /= 2;
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < input; i++) {
            for (int j = 0; j < 2; j++) {
                list.add(answers[i]);
            }
        }
        Collections.shuffle(list);
        return list;
    }

    @Override
    public void onPause() {

        if(isFinishing()) {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().remove(mRetainedFragment).commit();
        }
        mRetainedFragment.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mRetainedFragment.play();
        super.onResume();
    }

}
