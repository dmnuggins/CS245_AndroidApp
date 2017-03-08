package cs245.concentration;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private static int ROW_COUNT = -1;
    private static int COL_COUNT = -1;
    final Context prompt = this;
    private Context context;
    private Drawable backImage;
    private int [] [] cards;
    private List<Drawable> images;
    private Card firstCard;
    private Card seconedCard;
    private ButtonListener buttonListener;
    private static Object lock = new Object();
    private TableLayout mainTable;
    private int score = 0;
    private int match = 0;
    private int maxMatch = 0;
    private String name;
    private int difficulty;
    private TextView scoreTxt;
    private Button newGame;
    private Button check;
    private Button endGame;
    private Button move;

    //MediaPlayer player;
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
        scoreTxt = (TextView) findViewById(R.id.score);
        newGame = (Button) findViewById(R.id.newGame);
        check = (Button) findViewById(R.id.check);
        endGame = (Button) findViewById(R.id.endGame);
        move = (Button) findViewById(R.id.move);
        move.setVisibility(View.INVISIBLE);
        loadImages();
        backImage =  getResources().getDrawable(R.drawable.playing_card);

        buttonListener = new ButtonListener();
        mainTable = (TableLayout)findViewById(R.id.TableLayout03);
        context  = mainTable.getContext();

        Intent intent = getIntent();
        input = intent.getIntExtra("input", 0);
        difficulty = input;
        maxMatch = input/2;
        int x= 0;
        int y = 0;
        switch(input) {
            case 4:
                x = 2;
                y = 2;
                break;
            case 6:
                x = 2;
                y = 3;
                break;
            case 8:
                x = 2;
                y = 4;
                break;
            case 10:
                x = 2;
                y = 5;
                break;
            case 12:
                x = 3;
                y = 4;
                break;
            case 14:
                x = 2;
                y = 7;
                break;
            case 16:
                x = 4;
                y = 4;
                break;
            case 18:
                x = 3;
                y = 6;
                break;
            case 20:
                x = 4;
                y = 5;
                break;
        }
        generateGame(x,y);
        scoreTxt.setEnabled(false);
        scoreTxt.setText("Score: " + score);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myTag", "Checking cards");
                if(cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]){
                    firstCard.button.setEnabled(false);
                    seconedCard.button.setEnabled(false);
                    score = score + 2;
                    match = match + 1;
                    scoreTxt.setText("Score: " + score);
                }
                else {
                    seconedCard.button.setBackgroundDrawable(backImage);
                    firstCard.button.setBackgroundDrawable(backImage);
                    score = score - 1;
                    if (score < 0) {
                        score = 0;
                    }
                    scoreTxt.setText("Score: " + score);
                }
                firstCard=null;
                seconedCard=null;
                if (match == maxMatch) {
                    LayoutInflater li = LayoutInflater.from(prompt);
                    View promptView = li.inflate(R.layout.prompt, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            prompt);
                    alertDialogBuilder.setView(promptView);

                    final EditText userInput = (EditText) promptView.findViewById(R.id.editTextDialogUserInput);
                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Enter",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input and set it to result
                                            // edit text
                                            //result.setText(userInput.getText());
                                            name = userInput.getText().toString();
                                            Log.d("myTag", "Passed name");
                                            Intent i = new Intent(GameActivity.this, ScoreActivity.class);
                                            i.putExtra("difficulty", difficulty);
                                            i.putExtra("name", name);
                                            i.putExtra("score", score);
                                            startActivity(i);
                                            //move.setVisibility(View.VISIBLE);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width /= 6;

        // up navigation
        Toolbar myChildToolbar =  (Toolbar) findViewById(R.id.gameActionBar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        // find the retained fragment on activity restarts
        FragmentManager manager = getFragmentManager();
        mRetainedFragment = (MusicFragment) manager.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // create the fragment and data the first time
        if (mRetainedFragment == null) {
            // add the fragment
            mRetainedFragment = new MusicFragment();
            manager.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
            mRetainedFragment.setPlayer(mRetainedFragment.getPlayer());
        }

        Button musicBtn = (Button) findViewById(R.id.musicButton);

        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRetainedFragment.toggleMusic();
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameActivity.this, StartActivity.class);
                startActivity(i);
            }
        });
        /*
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameActivity.this, ScoreActivity.class);

                i.putExtra("difficulty", difficulty);
                i.putExtra("name", name);
                i.putExtra("score", score);
                startActivity(i);
            }
        });
        */
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
        boolean toggled = mRetainedFragment.getToggled();
        if(!toggled) {
            mRetainedFragment.play();
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gameActionBar:
                finish();
                finishActivity(107);
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateGame(int c, int r) {
        ROW_COUNT = r;
        COL_COUNT = c;
        cards = new int [COL_COUNT] [ROW_COUNT];
        TableRow tr = ((TableRow)findViewById(R.id.TableRow01));
        tr.removeAllViews();
        mainTable = new TableLayout(context);
        tr.addView(mainTable);

        for (int y = 0; y < ROW_COUNT; y++) {
            mainTable.addView(createRow(y));
        }
        firstCard=null;
        loadCards();
    }

    private void loadImages() {
        images = new ArrayList<Drawable>();
        images.add(getResources().getDrawable(R.drawable.crab_card));
        images.add(getResources().getDrawable(R.drawable.dolphin_card));
        images.add(getResources().getDrawable(R.drawable.jellyfish_card));
        images.add(getResources().getDrawable(R.drawable.octopus_card));
        images.add(getResources().getDrawable(R.drawable.ray_card));
        images.add(getResources().getDrawable(R.drawable.whale_card));
        images.add(getResources().getDrawable(R.drawable.turtle_card));
        images.add(getResources().getDrawable(R.drawable.starfish_card));
        images.add(getResources().getDrawable(R.drawable.shark_card));
        images.add(getResources().getDrawable(R.drawable.seal_card));
    }

    private void loadCards(){
        try{
            int size = ROW_COUNT*COL_COUNT;
            Log.i("loadCards()","size=" + size);
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int i=0;i<size;i++){
                list.add(new Integer(i));
            }
            Random r = new Random();
            for(int i=size-1;i>=0;i--){
                int t=0;
                if(i>0){
                    t = r.nextInt(i);
                }
                t=list.remove(t).intValue();
                cards[i%COL_COUNT][i/COL_COUNT]=t%(size/2);

                Log.i("loadCards()", "card["+(i%COL_COUNT)+
                        "]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
            }
        }
        catch (Exception e) {
            Log.e("loadCards()", e+"");
        }
    }

    private TableRow createRow(int y){
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);
        for (int x = 0; x < COL_COUNT; x++) {
            row.addView(createImageButton(x,y));
        }
        return row;
    }

    private View createImageButton(int x, int y){
        Button button = new Button(context);
        button.setBackgroundDrawable(backImage);
        button.setId(100*x+y);
        button.setOnClickListener(buttonListener);
        return button;
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            int x = id / 100;
            int y = id % 100;
            turnCard((Button) v, x, y);
        }
        private void turnCard(Button button, int x, int y) {
            button.setBackgroundDrawable(images.get(cards[x][y]));
            if (firstCard == null) {
                firstCard = new Card(button, x, y);
            } else {
                if (firstCard.x == x && firstCard.y == y) {
                    return; //the user pressed the same card
                }
                seconedCard = new Card(button, x, y);
            }
        }
    }

}
