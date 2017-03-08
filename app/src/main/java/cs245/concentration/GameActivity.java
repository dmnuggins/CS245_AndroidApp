package cs245.concentration;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import cs245.concentration.Game.CardAdapter;
import cs245.concentration.Game.StaticGridView;

public class GameActivity extends AppCompatActivity {

    StaticGridView gridView;
    CardAdapter cardAdapter;
    MediaPlayer player;
    int input = 0;

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

       cardAdapter = new CardAdapter(this, this, cardList(input));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width /= 6;

        gridView = (StaticGridView) findViewById(R.id.cardsGridView);
        gridView.setColumnWidth(width);
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            gridView.setNumColumns(10);
//            gridView.setPadding(60,60,60,60);
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            gridView.setNumColumns(4);
//            gridView.setPadding(40,40,40,40);
//        }
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
        ArrayList<Integer> enabledList = new ArrayList<>();
        ArrayList<Integer> resourceList = new ArrayList<>();
        ArrayList<Integer> flippedList = new ArrayList<>();

        for (int i = 0; i < gridView.getChildCount(); i++){
            ImageView child = (ImageView) gridView.getChildAt(i).findViewById(R.id.image);
            if (child.isEnabled()) {
                enabledList.add(1);
            } else {
                enabledList.add(0);
            }
            resourceList.add(cardAdapter.getImageResource(cardAdapter.getCardValues().get(i)));
            if (child.getDrawable() == getResources().getDrawable(R.drawable.playing_card)){
                flippedList.add(1);
            } else {
                flippedList.add(0);
            }
        }
        outState.putIntegerArrayList("enabled", enabledList);
        outState.putIntegerArrayList("resource", resourceList);
        outState.putIntegerArrayList("flipped", flippedList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        ArrayList<Integer> enabledList = saveInstanceState.getIntegerArrayList("enabled");
        ArrayList<Integer> resourceList = saveInstanceState.getIntegerArrayList("resource");
        ArrayList<Integer> flippedList = saveInstanceState.getIntegerArrayList("flipped");

        for (int i = 0; i < gridView.getChildCount(); i++){
            ImageView child = (ImageView) gridView.getChildAt(i).findViewById(R.id.image);
            if (enabledList.get(i) == 1){
                child.setEnabled(true);
            } else {
                child.setEnabled(false);
            }
            child.setImageResource(resourceList.get(i));
            if (flippedList.get(i) == 1){
                child.setImageResource(R.drawable.playing_card);
            }
        }
    }

    public void newGame(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void tryAgain(View view) {
        for (int i = 0; i < gridView.getChildCount(); i++){
            ImageView imageView = (ImageView) gridView.getChildAt(i).findViewById(R.id.image);
            imageView.setImageResource(R.drawable.playing_card);
            imageView.setEnabled(true);
        }
        cardAdapter = new CardAdapter(this, this, cardList(input));

    }

    public void endGame(View view) {
        //Intent intent = new Intent (this, EndActivity.class);
        //intent.putExtra("score", cardAdapter.getScore());
        //startActivity(intent);
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
