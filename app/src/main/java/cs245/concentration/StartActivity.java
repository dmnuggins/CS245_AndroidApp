/***************************************************************
 * file: StartActivity.java
 * author: E. Lee, D. Nyugen, S. Lee, H. Bozawglanian, J. Canalita
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: Android App
 * date last modified: 3/07/2017
 *
 * purpose: This activity is where the user lands on app start,
 *          and can lead to playing the game or viewing high
 *          scores.
 *
 ****************************************************************/

package cs245.concentration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class StartActivity extends AppCompatActivity{

    private Button submit;
    private Button hiscores;
    private Spinner spinner;

    // method: onCreate
    // purpose: this method creates the views within the start activity and sends the data
    //  to the next activity on button click.
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

    // method: onPause
    // purpose: this method runs when app is paused.
    @Override
    public void onPause() {
        super.onPause();
    }
}
