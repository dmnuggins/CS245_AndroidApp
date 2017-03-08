/***************************************************************
 * file: MusicFragment.java
 * author: E. Lee, D. Nyugen, S. Lee, H. Bozawglanian, J. Canalita
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: Android App
 * date last modified: 3/07/2017
 *
 * purpose: This class acts as a Fragment to control music being
 *          played during the game.
 *
 ****************************************************************/

package cs245.concentration;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MusicFragment extends Fragment {

    MediaPlayer player;
    boolean toggled = false;

    // method: onCreate
    // purpose: this method creates a Media Player when started and starts
    //  playing music in the app.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment

        player = MediaPlayer.create(getActivity(), R.raw.mii_channel_loop);
        player.setLooping(true);
        player.start();

        setRetainInstance(true);
    }

    // method: setPlayer
    // purpose: Player setter
    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    // method: getPlayer
    // purpose: Player getter
    public MediaPlayer getPlayer() {
        return player;
    }

    // method: pause
    // purpose: Pauses Media Player at that given point
    public void pause() { player.pause(); }

    // method: play
    // purpose: starts or restarts MediaPlayer, depending on circumstances.
    public void play() { player.start(); }

    // method: toggleMusic
    // purpose: provides a toggle for the music playing in the app w/boolean check to keep music paused
    //          when app is resumed
    public void toggleMusic() {
        if(player.isPlaying()) {
            player.pause();
            toggled = true;
        } else {
            player.start();
            toggled = false;
        }
    }
    // method: getToggled
    // purpose: Toggled getter
    public boolean getToggled() {
        return toggled;
    }

}
