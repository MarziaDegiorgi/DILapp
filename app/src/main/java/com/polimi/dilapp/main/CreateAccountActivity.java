package com.polimi.dilapp.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.ActivityAlfa;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.text.Normalizer;


public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);


        // recovering the instance state
        if (savedInstanceState != null) {
        }

        // Get the Intent that started this activity
        getIntent();

        //link to the creation of a new account child
        ImageButton newAccountButton = (ImageButton) findViewById(R.id.newAccount);
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAccountIntent = new Intent(getApplicationContext(), NewAccountActivity.class);
                startActivity(newAccountIntent);
            }
        });

        //dummy link to ActivityAlfa for debugging purpose
        Button dummyButton = findViewById(R.id.DummyLink);
        dummyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dummyIntent = new Intent(getApplicationContext(), ActivityAlfa.class);
                startActivity(dummyIntent);
            }
        });
        //dummy link to ActivityAlfa for debugging purpose
        Button dummyButton2 = findViewById(R.id.DummyLink2);
        dummyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dummyIntent = new Intent(getApplicationContext(), StartGameActivity.class);
                startActivity(dummyIntent);
            }
        });

    }

    // This callback is called only when there is a saved instance previously saved using
    // onSaveInstanceState(). We restore some state in onCreate() while we can optionally restore
    // other state here, possibly usable after onStart() has completed.
    // The savedInstanceState Bundle is same as the one used in onCreate().
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }


    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

}
