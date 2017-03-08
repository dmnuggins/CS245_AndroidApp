package cs245.concentration;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by MingKie on 2/22/2017.
 */

public class StartActivity extends AppCompatActivity{

    Button submit;
    Button hiscores;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        submit = (Button)findViewById(R.id.submitBtn);
        hiscores = (Button) findViewById(R.id.hiscore_button);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.difficulty_array, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    int input = Integer.parseInt(String.valueOf(spinner.getSelectedItem()));
                        Intent i = new Intent(StartActivity.this, GameActivity.class);
                        i.putExtra("input", input);
                        startActivityForResult(i, 107);
                }
        });

        hiscores.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent i = new Intent(StartActivity.this, ScoreActivity.class);
            startActivity(i);
        }
    });
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
