package com.polimi.dilapp.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.ChildEntity;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.levels.view.ActivityOneOne;
import com.polimi.dilapp.levels.view.ActivityTwoOne;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CreateAccountActivity extends AppCompatActivity {

    private TextView mTextView;
    private int teporaryChildId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        mTextView = findViewById(R.id.createAccount);
        List<ChildEntity> listOfChildren = DatabaseInitializer.getListOfChildren(AppDatabase.getAppDatabase(getApplicationContext()));


        // recovering the instance state
        if (DatabaseInitializer.getListOfChildren(AppDatabase.getAppDatabase(getApplicationContext())).size() == 0) {
            mTextView.setText(R.string.create_account);
        } else {
            mTextView.setText(R.string.select_account);

        }


        // Get the Intent that started this activity
        getIntent();

        //link to already existing account of children
        ImageButton account = (ImageButton) findViewById(R.id.account);
        LinearLayout layout = (LinearLayout) findViewById(R.id.listOfAccounts);

        ArrayList<ImageButton> accounts = new ArrayList<ImageButton>();
        // get reference to LayoutInflater
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < listOfChildren.size(); i++) {
            //Creating copy of imagebutton by inflating it
            final ImageButton btn = (ImageButton) inflater.inflate(R.layout.account_box, null);
            teporaryChildId = listOfChildren.get(i).getId();
            Drawable drawable = null;
            try {
                drawable = new BitmapDrawable(getResources(), DatabaseInitializer.getChildPhoto(getContentResolver(),listOfChildren.get(i)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            btn.setImageDrawable(drawable);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DatabaseInitializer.setCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()),teporaryChildId);
                    //TODO set currentPlayer
                    Intent startGame = new Intent(getApplicationContext(), StartGameActivity.class);
                    startActivity(startGame);
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(account.getLayoutParams());
            params.setMargins(20,0,20,0);
            btn.setScaleType(ImageButton.ScaleType.CENTER_CROP);

            btn.setLayoutParams(params);
            layout.addView(btn);
        }


    //link to the creation of a new account child
        ImageButton newAccountButton = (ImageButton) inflater.inflate(R.layout.account_box, null);
        newAccountButton.setImageDrawable(getDrawable(R.drawable.avatar_add));
        newAccountButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        Intent newAccountIntent = new Intent(getApplicationContext(), NewAccountActivity.class);
        startActivity(newAccountIntent);
    }
    });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(account.getLayoutParams());
        params.setMargins(20,0,20,0);
        newAccountButton.setScaleType(ImageButton.ScaleType.CENTER_CROP);
        newAccountButton.setLayoutParams(params);
        layout.addView(newAccountButton);


        //dummy link to ActivityAlfa for debugging purpose
        Button dummyButton = findViewById(R.id.DummyLink);
        dummyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dummyIntent = new Intent(getApplicationContext(), ActivityOneOne.class);
                startActivity(dummyIntent);
            }
        });
        //dummy link to Start Game screen
        Button dummyButton2 = findViewById(R.id.DummyLink2);
        dummyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dummyIntent = new Intent(getApplicationContext(), StartGameActivity.class);
                startActivity(dummyIntent);
            }
        });
        //dummy link to ActivityBeta
        Button activityBetaButton = findViewById(R.id.DummyLink3);
        activityBetaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dummyIntent = new Intent(getApplicationContext(), ActivityTwoOne.class);
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
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
    }



}
