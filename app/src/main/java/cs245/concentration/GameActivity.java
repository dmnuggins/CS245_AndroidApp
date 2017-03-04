package cs245.concentration;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

import cs245.concentration.Game.CardAdapter;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
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
    }

    public void newGame(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        // reset everything etc
        // actually already resets everything so that's nice
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


}
