/***************************************************************
 * file: GameActivity.java
 * author: E. Lee, D. Nyugen, S. Lee, H. Bozawglanian, J. Canalita
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: Android App
 * date last modified: 3/07/2017
 *
 * purpose: This program runs the activity in which the
 *          Concentration game is played. All the logic of
 *          the game is contained in here as well.
 *
 ****************************************************************/

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
import android.support.v4.app.NavUtils;
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

// This class is the game acitivity, where it handles all the game.
public class GameActivity extends AppCompatActivity {

    private static int ROW_COUNT = -1;
    private static int COL_COUNT = -1;
    private final Context prompt = this;
    private Context context;
    private Drawable backImage;
    private int [] [] cards;
    private List<Drawable> images;
    private Card firstCard; // firstCard
    private Card secondCard; // secondCard
    private ButtonListener buttonListener;
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
    private Card answerCards[]; // all cards that are generated
    private int index = 0; //index for answerCards
    private int input = 0;
    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";
    private MusicFragment mRetainedFragment;
    private final String[] answers = new String[]{
            "DOLPHIN", "WHALE", "SHARK", "OCTOPUS", "RAY", "TURTLE", "SEAL", "STARFISH", "JELLYFISH", "CRAB"
    };

    // method: onCreate
    // purpose: This method creates the views within the game activity. Generate a game board and
    // let the user to play the game. When it's finished, it passes name, score, and difficulty
    // the score activity.
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        scoreTxt = (TextView) findViewById(R.id.score);
        newGame = (Button) findViewById(R.id.newGame);
        check = (Button) findViewById(R.id.check);
        endGame = (Button) findViewById(R.id.endGame);
        loadImages();
        backImage =  getResources().getDrawable(R.drawable.icon);
        buttonListener = new ButtonListener();
        mainTable = (TableLayout)findViewById(R.id.TableLayout03);
        context  = mainTable.getContext();

        Intent intent = getIntent();
        input = intent.getIntExtra("input", 0);
        difficulty = input;
        maxMatch = input/2;

        if (input == 10) {
            answerCards = new Card[12];
        } else if (input == 14) {
            answerCards = new Card[16];
        } else if (input == 18) {
            answerCards = new Card[20];
        } else {
            answerCards = new Card[input];
        }

        int x= 0;
        int y = 0;
        switch(input) {
            case 4:
                x = 2;
                y = 2;
                break;
            case 6:
                x = 3;
                y = 2;
                break;
            case 8:
                x = 4;
                y = 2;
                break;
            case 10:
                x = 4;
                y = 3;
                break;
            case 12:
                x = 4;
                y = 3;
                break;
            case 14:
                x = 4;
                y = 4;
                break;
            case 16:
                x = 4;
                y = 4;
                break;
            case 18:
                x = 4;
                y = 5;
                break;
            case 20:
                x = 4;
                y = 5;
                break;
        }

        generateGame(x,y);
        // check if the difficulty is one of three options, if yes, delete 2 cards.
        if (input == 10 || input == 14 || input == 18) {
            Card tempCard1 = answerCards[answerCards.length - 1];
            Card tempCard2;
            for (int i = 0; i < answerCards.length - 1; ++i) {
                tempCard2 = answerCards[i];
                if (cards[tempCard1.x][tempCard1.y] == cards[tempCard2.x][tempCard2.y]) {
                    tempCard1.button.setVisibility(View.INVISIBLE);
                    tempCard2.button.setVisibility(View.INVISIBLE);
                }

            }
        }

        scoreTxt.setEnabled(false);
        scoreTxt.setText("Score: " + score);

        // clickListerner for Check button.
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if both cards are flipped.
                if (firstCard != null && secondCard != null) {
                    Log.d("myTag", "Checking cards");
                    if (cards[secondCard.x][secondCard.y] == cards[firstCard.x][firstCard.y]) {
                        firstCard.button.setEnabled(false);
                        secondCard.button.setEnabled(false);
                        firstCard.flipped = true;
                        secondCard.flipped = true;
                        Card tempCard;
                        for (int i = 0; i < answerCards.length; ++i) {
                            tempCard = answerCards[i];
                            if (!tempCard.flipped)
                                tempCard.button.setEnabled(true);
                        }
                        score = score + 2;
                        match = match + 1;
                        scoreTxt.setText("Score: " + score);
                    } else {
                        secondCard.button.setBackgroundDrawable(backImage);
                        firstCard.button.setBackgroundDrawable(backImage);
                        firstCard.flipped = false;
                        secondCard.flipped = false;

                        Card tempCard;
                        for (int i = 0; i < answerCards.length; ++i) {
                            tempCard = answerCards[i];
                            if (!tempCard.flipped)
                                tempCard.button.setEnabled(true);
                        }

                        score = score - 1;
                        if (score < 0) {
                            score = 0;
                        }
                        scoreTxt.setText("Score: " + score);
                    }

                    firstCard = null;
                    secondCard = null;
                    //Check if player found all matches.
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
                                            public void onClick(DialogInterface dialog, int id) {
                                                // get user input and set it to result
                                                // edit text
                                                name = userInput.getText().toString();
                                                Log.d("myTag", "Passed name");
                                                Intent i = new Intent(GameActivity.this, ScoreActivity.class);
                                                i.putExtra("difficulty", difficulty);
                                                i.putExtra("name", name);
                                                i.putExtra("score", score);
                                                startActivity(i);
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                    }
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
        // clickListener for New Game button.
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finishActivity(107);
            }
        });
        // clickListener for End Game button.
        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(false);
                score = 0;

                Card tempCard;
                for(int i = 0; i < answerCards.length; ++i) {
                    tempCard = answerCards[i];
                    tempCard.button.setBackgroundDrawable(images.get(cards[tempCard.x][tempCard.y]));
                    tempCard.button.setEnabled(false);
                }

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
    /*
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
    */
    /*
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
    */
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
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // method: generateGame()
    // purpose: This method generate a game board.
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

    // method: loadImages()
    // purpose: This method loads all the images.
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

    // method: loardCards()
    // purpose: This method loads cards based on the input.
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

    // method: createRow()
    // purpose: This method create number of rows based on the input.
    private TableRow createRow(int y){
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);
        for (int x = 0; x < COL_COUNT; x++) {
            row.addView(createImageButton(x,y));
        }
        return row;
    }

    // method: createImageButton()
    // purpose: This method create a button with its image.
    private View createImageButton(int x, int y){
        Button button = new Button(context);
        button.setBackgroundDrawable(backImage);
        button.setId(100*x+y);
        button.setOnClickListener(buttonListener);

        Card tempCard= new Card(button, x, y);
        answerCards[index] = tempCard;
        index++;
        return button;
    }

    // This class is for each image button.
    class ButtonListener implements View.OnClickListener {
        // method: onClick()
        // purpose: This method is the clickListener for each image button.
        @Override
        public void onClick(View v) {
            int id = v.getId();
            int x = id / 100;
            int y = id % 100;
            turnCard((Button) v, x, y);
        }
        // method: turnCard()
        // purpose: turn a card over when it's clicked.
        private void turnCard(Button button, int x, int y) {
            button.setBackgroundDrawable(images.get(cards[x][y]));

            if (firstCard == null) {
                firstCard = new Card(button, x, y);
                firstCard.flipped = true;
            } else {
                if (firstCard.x == x && firstCard.y == y) {
                    return; //the user pressed the same card
                }
                secondCard = new Card(button, x, y);
                secondCard.flipped = true;
                Card tempCard;
                for (int i = 0; i < answerCards.length; ++i) {
                    tempCard = answerCards[i];
                    if (!tempCard.flipped)
                        tempCard.button.setEnabled(false);
                }
            }
        }
    }

}
