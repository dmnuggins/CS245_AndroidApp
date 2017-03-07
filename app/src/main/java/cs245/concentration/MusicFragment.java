package cs245.concentration;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;

/**
 * Created by dmnguyen on 3/6/17.
 */

public class MusicFragment extends Fragment {

    MediaPlayer player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment

        player = MediaPlayer.create(getActivity(), R.raw.mii_channel_loop);
        player.setLooping(true);
        player.start();

        setRetainInstance(true);
    }

    public void setData(MediaPlayer player) {
        this.player = player;
    }

    public MediaPlayer getData() {
        return player;
    }



    public void pause() {
        player.pause();
    }
    public void play() {
        player.start();
    }

    public void toggleMusic() {
        if(player.isPlaying()) {
            player.pause();
        } else {
            player.start();
        }
    }

}
