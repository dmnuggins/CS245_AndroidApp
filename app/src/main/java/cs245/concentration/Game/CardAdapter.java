package cs245.concentration.Game;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cs245.concentration.R;

import static cs245.concentration.R.layout.card;

public class CardAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> cardValues = null;

    int[] cardFlipped = null;

    Map<String, Integer> frequency = new HashMap<>();
    boolean firstCardFlipped = false;
    String firstCard = "";
    String secondCard = "";


    public CardAdapter(Context context, ArrayList<String> cardValues) {
        this.context = context;
        this.cardValues = cardValues;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View gridView;

        if (view == null) {
            //gridView = new View(context);
            gridView = inflater.inflate(card, viewGroup, false);
            final ImageView imageView = (ImageView) gridView.findViewById(R.id.image);
            imageView.setAdjustViewBounds(true);

            cardFlipped = new int[cardValues.size()];
            Arrays.fill(cardFlipped, 0); //all un-flipped

            final String card = cardValues.get(i);
            imageView.setImageResource(R.drawable.playing_card);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!firstCardFlipped) {
                        imageView.setImageResource(getImageResource(card));
                        firstCardFlipped = true;
                        firstCard = card;
                        addToMap(firstCard);
                        imageView.setEnabled(false);
                    } else {
                        imageView.setImageResource(getImageResource(card));
                        firstCardFlipped = false;
                        secondCard = card;
                        addToMap(secondCard);
                        imageView.setEnabled(false);
                        if (!isMatching(firstCard)){
                            frequency.remove(firstCard);
                            frequency.remove(secondCard);

                        }
                    }
                }
            });

        } else {
            gridView = (View) view;
        }
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

    public void addToMap (String string){
        if (frequency.isEmpty()){
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

    public boolean isMatching (String string){
        boolean matches = false;
        int count = frequency.get(string);
        if (count == 2) {
            matches = true;
        }
        return matches;
    }

    public void hideCard (ImageView imageView) {
        //do this later
        imageView.setImageResource(R.drawable.playing_card);
    }

    public ArrayList<Integer> findPositionsOfCard (GridView gridView, String string) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < gridView.getChildCount(); i++){
            if (gridView.getChildAt(i).toString().equals(string)){
                list.add(i);
            }
        }
        return list;
    }

    public void disableAllCards (GridView gridView){
        for (int i = 0; i < gridView.getChildCount(); i++){
            gridView.getChildAt(i).setEnabled(false);
            }
        }

    public void enableValidCards (GridView gridView){
        // do later
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
