package cs245.concentration;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by MingKie on 2/22/2017.
 */

public class StartActivity extends AppCompatActivity{

    Button submit;
    EditText userInput;
    MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        userInput = (EditText)findViewById(R.id.input);
        submit = (Button)findViewById(R.id.submitBtn);


        submit.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    String tempInput = userInput.getText().toString();
                    int input = Integer.parseInt(tempInput);
                    if (input > 20) {
                        userInput.requestFocus();
                        userInput.setError("CANNOT BE GREATER THAN 20");
                    } else if (input < 4) {
                        userInput.requestFocus();
                        userInput.setError("CANNOT BE LESS THAN 4");
                    } else if (input % 2 != 0) {
                        userInput.requestFocus();
                        userInput.setError("CANNOT BE AN ODD NUMBER");
                    } else {
                        Intent i = new Intent(StartActivity.this, GameActivity.class);
                        i.putExtra("input", input);
                        startActivity(i);
                        finish();

                    }
                }
        });

        player = MediaPlayer.create(this, R.raw.mii_channel_loop);
        player.setLooping(true);
        player.setVolume(100,100);
        player.start();
    }

    @Override
    protected void onPause() {
        if (this.isFinishing()){ //basically BACK was pressed from this activity
            player.stop();
            Toast.makeText(StartActivity.this, "YOU PRESSED BACK FROM YOUR 'HOME/MAIN' ACTIVITY", Toast.LENGTH_SHORT).show();
        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                player.stop();
                Toast.makeText(StartActivity.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(StartActivity.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();

            }
        }
        super.onPause();
    }

}
