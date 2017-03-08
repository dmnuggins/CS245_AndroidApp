/***************************************************************
 * file: Card.java
 * author: E. Lee, D. Nyugen, S. Lee, H. Bozawglanian, J. Canalita
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: Android App
 * date last modified: 3/07/2017
 *
 * purpose: This class acts as the Card object to be used in the
 *          game.
 *
 ****************************************************************/

package cs245.concentration;

import android.widget.Button;

class Card{

    int x;
    int y;
    Button button;

    // method: Card
    // purpose: this method acts as the class constructor.
    Card(Button button, int x,int y) {
        this.x = x;
        this.y=y;
        this.button=button;
    }


}