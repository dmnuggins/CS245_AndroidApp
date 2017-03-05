package cs245.concentration;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs245.concentration.Game.CardAdapter;

public class GameActivity extends AppCompatActivity {

    MediaPlayer player;
    GridView gridView;
//    Button mBtn = (Button) findViewById(R.id.musicButton);
    int input = 0;

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

        gridView = (GridView) findViewById(R.id.cardsGridView);
        gridView.setAdapter(new CardAdapter(this, cardList(input)));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), gridView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });


        player = MediaPlayer.create(this, R.raw.metal_gear_solid_alert_theme);
        player.setLooping(true);
        player.setVolume(100,100);
        player.start();

        Button musicOnOff = (Button) findViewById(R.id.musicButton);
        musicOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying()) {
                    player.pause();
                } else {
                    player.start();
                }
            }
        });

    }

    public void newGame(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        // reset everything etc
        // actually already resets everything so that's nice
        startActivity(intent);
        finish();
        player.stop();
    }

    public void tryAgain(View vew) {
        // will do later
        player.pause();
    }

    public void endGame(View view) {
        // will do later
        player.start();
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
    protected void onPause() {
        if (this.isFinishing()){ //basically BACK was pressed from this activity
            player.stop();
        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                player.stop();
            }
        }
        super.onPause();
    }
}
