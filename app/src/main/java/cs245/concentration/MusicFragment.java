package cs245.concentration;

/**
 * Created by Dylan Nguyen on 3/5/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.media.MediaPlayer;


public class MusicFragment extends Fragment {

    private MediaPlayer player;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public MediaPlayer getPlayer() {
        return player;
    }
}

