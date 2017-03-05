package cs245.concentration;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.Collections;

import cs245.concentration.Game.CardAdapter;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    CardAdapter cardAdapter;
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

       cardAdapter = new CardAdapter(this, cardList(input));

        gridView = (GridView) findViewById(R.id.cardsGridView);
        gridView.setAdapter(cardAdapter);
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


}
