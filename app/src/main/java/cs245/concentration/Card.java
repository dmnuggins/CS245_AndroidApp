package cs245.concentration;

import android.widget.Button;
import android.widget.ImageButton;

//To represent a card object.
public class Card{

    public int x; // column number
    public int y; // row number
    public ImageButton button; // its button
    public boolean flipped = false; // to indicate if it's flipped

    //method: Card()
    //purpose:Tto create a new card object.
    public Card(ImageButton button, int x, int y) {
        this.x = x;
        this.y=y;
        this.button=button;
    }
}