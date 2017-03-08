/***************************************************************
 * file: MusicFragment.java
 * author: E. Lee, D. Nyugen, S. Lee, H. Bozawglanian, J. Canalita
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: Android App
 * date last modified: 3/07/2017
 *
 * purpose: This class acts as a Fragment to control music being
 *          played throughout the app.
 *
 ****************************************************************/

package cs245.concentration;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MusicFragment extends Fragment {

    MediaPlayer player;
    boolean toggled = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment

        player = MediaPlayer.create(getActivity(), R.raw.mii_channel_loop);
        player.setLooping(true);
        player.start();

        setRetainInstance(true);
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    // Pauses MediaPlayer
    public void pause() { player.pause(); }

    // Starts/restarts MediaPLayer
    public void play() { player.start(); }

    // Toggles music to pause and changes boolean toggled
    public void toggleMusic() {
        if(player.isPlaying()) {
            player.pause();
            toggled = true;
        } else {
            player.start();
            toggled = false;
        }
    }
    // returns toggled
    public boolean getToggled() {
        return toggled;
    }

}
