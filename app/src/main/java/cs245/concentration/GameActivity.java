package cs245.concentration;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

        player = MediaPlayer.create(this, R.raw.mii_channel_loop);
        player.setLooping(true);
        player.setVolume(100,100);
        player.start();
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
        // find way to save gridview stuff
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

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            if(player == null) {
//                player = MediaPlayer.create(this, R.raw.mii_channel_loop);
//            }
//            player.seekTo(position);
//            player.setLooping(true);
//            player.start();
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            if(player == null) {
//                player = MediaPlayer.create(this, R.raw.mii_channel_loop);
//            }
//            player.seekTo(position);
//            player.setLooping(true);
//            player.start();
//        }
//
//        super.onConfigurationChanged(newConfig);
//
//
//    }
}
