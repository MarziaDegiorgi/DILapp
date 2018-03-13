package com.polimi.dilapp.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.ChildEntity;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.levels.view.ActivityOneFour;
import com.polimi.dilapp.levels.view.ActivityTwoOne;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.io.IOException;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class CreateAccountActivity extends AppCompatActivity implements ICreateAccount.View{

    ICreateAccount.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        TextView mTextView = findViewById(R.id.createAccount);


        //Set up the presenter
        presenter = new CreateAccountPresenter(this);

        List<ChildEntity> listOfChildren = presenter.getListOfChildren();

        // recovering the instance state
        if (listOfChildren.size() == 0) {
            mTextView.setText(R.string.create_account);
        } else {
            mTextView.setText(R.string.select_account);

        }

        //link to already existing account of children
        ImageButton account = (ImageButton) findViewById(R.id.account);
        LinearLayout layout = (LinearLayout) findViewById(R.id.listOfAccounts);

        // get reference to LayoutInflater
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < listOfChildren.size(); i++) {

            //Creating copy of imagebutton by inflating it
            final ImageButton btn = (ImageButton) inflater.inflate(R.layout.account_box, null);
            int temporaryChildId = listOfChildren.get(i).getId();
            btn.setId(temporaryChildId);
            Drawable drawable = null;
            try {
                drawable = new BitmapDrawable(getResources(), DatabaseInitializer.getChildPhoto(getContentResolver(),listOfChildren.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            btn.setImageDrawable(drawable);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent startGame = new Intent(getApplicationContext(), StartGameActivity.class);
                    startGame.putExtra(EXTRA_MESSAGE, btn.getId());
                    startActivity(startGame);
                    finish();
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(account.getLayoutParams());
            params.setMargins(20,0,20,0);
            btn.setScaleType(ImageButton.ScaleType.CENTER_CROP);

            btn.setLayoutParams(params);
            layout.addView(btn);
        }


        //link to the add button that enables the user to create a new account
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

    }


    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // call superclass to save any view hierarchy
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);

    }

    // This callback is called only when there is a saved instance previously saved using
    // onSaveInstanceState(). We restore some state in onCreate() while we can optionally restore
    // other state here, possibly usable after onStart() has completed.
    // The savedInstanceState Bundle is same as the _1 used in onCreate().
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        presenter.resumeCurrentPlayer(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public Context getContext() {
        return this;
    }
}
