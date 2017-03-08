package cs245.concentration;

import android.widget.Button;


public class Card{

    public int x;
    public int y;
    public Button button;
    public boolean flipped = false;

    public Card(Button button, int x,int y) {
        this.x = x;
        this.y=y;
        this.button=button;
    }




}