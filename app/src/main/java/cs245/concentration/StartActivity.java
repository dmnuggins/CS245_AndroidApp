package cs245.concentration;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by MingKie on 2/22/2017.
 */

public class StartActivity extends AppCompatActivity{

    Button submit;
    EditText userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        userInput = (EditText)findViewById(R.id.input);
        submit = (Button)findViewById(R.id.submitBtn);


        submit.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    String tempInput = userInput.getText().toString();
                    int input = Integer.parseInt(tempInput);
                    if (input > 20) {
                        userInput.requestFocus();
                        userInput.setError("CANNOT BE GREATER THAN 20");
                    } else if (input < 4) {
                        userInput.requestFocus();
                        userInput.setError("CANNOT BE LESS THAN 4");
                    } else if (input % 2 != 0) {
                        userInput.requestFocus();
                        userInput.setError("CANNOT BE AN ODD NUMBER");
                    } else {
                        Intent i = new Intent(StartActivity.this, GameActivity.class);
                        i.putExtra("input", input);
                        startActivity(i);

                    }
                }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
