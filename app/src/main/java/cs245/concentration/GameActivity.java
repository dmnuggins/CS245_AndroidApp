package cs245.concentration;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    TextView mText;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
//        mText = (TextView)findViewById(R.id.textView);
//        Intent i = getIntent();
//        int input = i.getIntExtra("input", 0);
//        mText.setText("User input: " + input);
    }
}
