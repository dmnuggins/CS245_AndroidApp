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
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import cs245.concentration.Game.CardAdapter;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    CardAdapter cardAdapter;
    MediaPlayer player;
    int input = 0;
    int position;

    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";
    private MusicFragment musicFragment;


    private final String[] answers = new String[]{
            "DOLPHIN", "WHALE", "SHARK", "OCTOPUS", "RAY", "TURTLE", "SEAL", "STARFISH", "JELLYFISH", "CRAB"
    };

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        FragmentManager fm = getFragmentManager();
        musicFragment = (MusicFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        if (musicFragment == null) {
            musicFragment = new MusicFragment();
            fm.beginTransaction().add(musicFragment, TAG_RETAINED_FRAGMENT).commit();
            musicFragment.setPlayer(musicFragment.getPlayer());
        }

        Intent intent = getIntent();
        input = intent.getIntExtra("input", 0);

       cardAdapter = new CardAdapter(this, cardList(input));

        gridView = (GridView) findViewById(R.id.cardsGridView);
        gridView.setAdapter(cardAdapter);

        if(player == null) {
            player = MediaPlayer.create(this, R.raw.mii_channel_loop);
        }
        player.seekTo(position);
        player.setLooping(true);
        player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(player!=null) {
            position = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // find way to save gridview stuff
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
    }

    public void newGame(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void tryAgain(View vew) {
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
        if(player != null) {
            position = player.getCurrentPosition();
            player.release();
            player = null;
        }
        super.onPause();
    }

    @Override
    public void onResume() {

        if(player == null) {
            player = MediaPlayer.create(this, R.raw.mii_channel_loop);
        }
        player.seekTo(position);
        player.setLooping(true);
        player.start();
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();

            if(player == null) {
                player = MediaPlayer.create(this, R.raw.mii_channel_loop);
            }
            player.seekTo(position);
            player.setLooping(true);
            player.start();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();

            if(player == null) {
                player = MediaPlayer.create(this, R.raw.mii_channel_loop);
            }
            player.seekTo(position);
            player.setLooping(true);
            player.start();
        }

        super.onConfigurationChanged(newConfig);

    }

}
