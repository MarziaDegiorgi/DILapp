package com.polimi.dilapp.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.ActivityAlfa;
import com.polimi.dilapp.startgame.StartGameActivity;


public class CreateAccountActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        // Get the Intent that started this activity
        getIntent();

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
}