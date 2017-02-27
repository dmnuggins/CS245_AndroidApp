package cs245.concentration.Game;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

import cs245.concentration.R;

public class CardAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> cardValues = null;

    public CardAdapter(Context context, ArrayList<String> cardValues) {
        this.context = context;
        this.cardValues = cardValues;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (view == null) {
            //gridView = new View(context);
            gridView = inflater.inflate(R.layout.card, viewGroup, false);
            final ImageView imageView = (ImageView) gridView.findViewById(R.id.image);
            imageView.setAdjustViewBounds(true);
            //Collections.shuffle(cardValues);
            final String card = cardValues.get(i);
            imageView.setImageResource(R.drawable.playing_card);

            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    switch (card){
                        case "DOLPHIN":
                            imageView.setImageResource(R.drawable.dolphin_card);
                            break;
                        case "WHALE":
                            imageView.setImageResource(R.drawable.whale_card);
                            break;
                        case "SHARK":
                            imageView.setImageResource(R.drawable.shark_card);
                            break;
                        case "OCTOPUS":
                            imageView.setImageResource(R.drawable.octopus_card);
                            break;
                        case "RAY":
                            imageView.setImageResource(R.drawable.ray_card);
                            break;
                        case "TURTLE":
                            imageView.setImageResource(R.drawable.turtle_card);
                            break;
                        case "SEAL":
                            imageView.setImageResource(R.drawable.seal_card);
                            break;
                        case "STARFISH":
                            imageView.setImageResource(R.drawable.starfish_card);
                            break;
                        case "JELLYFISH":
                            imageView.setImageResource(R.drawable.jellyfish_card);
                            break;
                        case "CRAB":
                            imageView.setImageResource(R.drawable.crab_card);
                            break;
                        default:
                             imageView.setImageResource(R.drawable.playing_card_empty);
                            break;
                    }

                }
            });

        } else {
            gridView = (View) view;
        }
        return gridView;
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
