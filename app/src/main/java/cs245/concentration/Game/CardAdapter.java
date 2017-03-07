package cs245.concentration.Game;

import android.app.Activity;
import android.content.Context;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cs245.concentration.R;

import static cs245.concentration.R.layout.card;

public class CardAdapter extends BaseAdapter {
    private Context context;
    private Activity mActivity;
    private ArrayList<String> cardValues = null;

    private Map<String, Integer> frequency = new HashMap<>();
    private boolean firstCardFlipped = false;
    private String firstCard = "";
    private String firstCardTag = "";
    private String secondCard = "";
    private String secondCardTag = "";

    private int counter = 0;

    int score = 0;

    public CardAdapter(Context context, Activity activity, ArrayList<String> cardValues) {
        this.context = context;
        this.mActivity = activity;
        this.cardValues = cardValues;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View gridView;

        final GridView gv = (GridView) viewGroup;

        if (view == null) {
            gridView = inflater.inflate(card, viewGroup, false);

            final ImageView imageView = (ImageView) gridView.findViewById(R.id.image);
            imageView.setAdjustViewBounds(true);

            final String card = cardValues.get(i);
            imageView.setImageResource(R.drawable.playing_card);
            imageView.setTag("card" + counter);
            gridView.setTag("card" + counter);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!firstCardFlipped) {
                        imageView.setImageDrawable(getImageDrawable(card));
                        firstCardFlipped = true;
                        firstCard = card;
                        addToMap(firstCard);
                        imageView.setEnabled(false);
                        firstCardTag = imageView.getTag().toString();
                    } else {
                        imageView.setImageResource(getImageResource(card));
                        firstCardFlipped = false;
                        secondCard = card;
                        addToMap(secondCard);
                        imageView.setEnabled(false);
                        secondCardTag = imageView.getTag().toString();
                        if (!isMatching(firstCard)) {
                            frequency.remove(firstCard);
                            frequency.remove(secondCard);
                            int i = 0;
                            while (i < gv.getChildCount()) {
                                if (gv.getChildAt(i).getTag().toString().equals(firstCardTag) || gv.getChildAt(i).getTag().toString().equals(secondCardTag)) {
                                    gv.getChildAt(i).findViewById(R.id.image).setEnabled(true);
                                }
                                i++;
                            }
                            notifyDataSetChanged();
                            //SystemClock.sleep(500);
                            for (int j = 0; j < gv.getChildCount(); j++) {
                                if (gv.getChildAt(j).getTag().toString().equals(firstCardTag) || gv.getChildAt(j).getTag().toString().equals(secondCardTag)) {
                                    hideCard((ImageView) gv.getChildAt(j).findViewById(R.id.image));
                                }
                            }
                            score -= 1;
                            if (score < 0) {
                                score = 0;
                            }
                        } else {
                            score += 2;
                        }

                        if (isSolved()) {
                            //send to end screen
                            //intent, send extras (cardValues.size(), score)
                            Toast toast2 = Toast.makeText(context, "Solved!", Toast.LENGTH_SHORT);
                            toast2.show();
                        }
                    }
                }

            });
        } else {
            gridView = (View) view;
        }
        counter++;
        return gridView;
    }

    public int getImageResource(String card) {
        int drawable;
        switch (card) {
            case "DOLPHIN":
                drawable = (R.drawable.dolphin_card);
                break;
            case "WHALE":
                drawable = (R.drawable.whale_card);
                break;
            case "SHARK":
                drawable = (R.drawable.shark_card);
                break;
            case "OCTOPUS":
                drawable = (R.drawable.octopus_card);
                break;
            case "RAY":
                drawable = (R.drawable.ray_card);
                break;
            case "TURTLE":
                drawable = (R.drawable.turtle_card);
                break;
            case "SEAL":
                drawable = (R.drawable.seal_card);
                break;
            case "STARFISH":
                drawable = (R.drawable.starfish_card);
                break;
            case "JELLYFISH":
                drawable = (R.drawable.jellyfish_card);
                break;
            case "CRAB":
                drawable = (R.drawable.crab_card);
                break;
            default:
                drawable = (R.drawable.playing_card_empty);
                break;
        }
        return drawable;
    }

    public Drawable getImageDrawable(String card) {
        Drawable drawable;
        Resources resources = context.getResources();
        Resources.Theme theme = context.getTheme();
        switch (card) {
            case "DOLPHIN":
                drawable = resources.getDrawable(R.drawable.dolphin_card, theme);
                break;
            case "WHALE":
                drawable = resources.getDrawable(R.drawable.whale_card, theme);
                break;
            case "SHARK":
                drawable = resources.getDrawable(R.drawable.shark_card, theme);
                break;
            case "OCTOPUS":
                drawable = resources.getDrawable(R.drawable.octopus_card, theme);
                break;
            case "RAY":
                drawable = resources.getDrawable(R.drawable.ray_card, theme);
                break;
            case "TURTLE":
                drawable = resources.getDrawable(R.drawable.turtle_card, theme);
                break;
            case "SEAL":
                drawable = resources.getDrawable(R.drawable.seal_card, theme);
                break;
            case "STARFISH":
                drawable = resources.getDrawable(R.drawable.starfish_card, theme);
                break;
            case "JELLYFISH":
                drawable = resources.getDrawable(R.drawable.jellyfish_card, theme);
                break;
            case "CRAB":
                drawable = resources.getDrawable(R.drawable.crab_card, theme);
                break;
            default:
                drawable = resources.getDrawable(R.drawable.playing_card_empty, theme);
                break;
        }
        return drawable;
    }



    public void addToMap(String string) {
        if (frequency.isEmpty()) {
            frequency.put(string, 1);
        } else {
            Integer count = frequency.get(string);
            if (count == null) {
                frequency.put(string, 1);
            } else {
                frequency.put(string, count + 1);
            }
        }
    }

    public boolean isMatching(String string) {
        boolean matches = false;
        int count = frequency.get(string);
        if (count == 2) {
            matches = true;
        }
        return matches;
    }

    public void hideCard(ImageView imageView) {
        //do this later
        imageView.setImageResource(R.drawable.playing_card);
    }

    public void disableAllCards(GridView gridView) {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            gridView.getChildAt(i).setEnabled(false);
        }
    }

    public void enableValidCards(GridView gridView) {
        // do later
    }

    public boolean isSolved() {
        boolean answer = true;
        int entries = cardValues.size() / 2;
        if (frequency.size() == entries) {
            for (int i : frequency.values()) {
                if (i != 2) {
                    answer = false;
                    break;
                }
            }
        } else {
            answer = false;
        }
        return answer;
    }

    @Override
    public int getCount() {
        return cardValues.size();
    }

    @Override
    public Object getItem(int i) {
        return cardValues.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
