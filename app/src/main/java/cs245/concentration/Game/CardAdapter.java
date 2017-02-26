package cs245.concentration.Game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        int columnWid = 200;
        int rowHigh = 300;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (view == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.card, null);
            gridView.setLayoutParams(new GridView.LayoutParams(columnWid, rowHigh));

            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

            Collections.shuffle(cardValues);
            String card = cardValues.get(i);
            imageView.setImageResource(R.drawable.playing_card);

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
