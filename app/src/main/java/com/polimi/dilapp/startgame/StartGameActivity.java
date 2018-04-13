package com.polimi.dilapp.startgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.ChildEntity;
import com.polimi.dilapp.database.DatabaseInitializer;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class StartGameActivity extends AppCompatActivity implements IStartGame.View {

    IStartGame.Presenter presenter;
    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDatabase.getAppDatabase(this);
        presenter = new StartGamePresenter(this);

        setContentView(R.layout.activity_startgame);
        Bundle extras = getIntent().getExtras();
        Button playButton = findViewById(R.id.playButton);

        int currentPlayerId = -1;

        if (extras != null) {
            Log.i("[STARTGAME ACTIVITY] ", "Intent extra is "+ extras.getInt(EXTRA_MESSAGE));
            currentPlayerId = extras.getInt(EXTRA_MESSAGE);
            DatabaseInitializer.setCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()), currentPlayerId);
            Log.i("[StartGameActivity]", "Current Player Level" + String.valueOf(DatabaseInitializer.getLevelCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()))));
        }

        this.initCurrentPlayer();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPlayButtonPressed();
                finish();
            }
        });

        startAnimation();
    }

    private void initCurrentPlayer() {
        int currentPlayer = DatabaseInitializer.getCurrentPlayer(db);
        int levelCurrentPlayer = DatabaseInitializer.getLevelCurrentPlayer(db);
        String objectCurrentPlayer = DatabaseInitializer.getObjectCurrentPlayer(db);
        String subStringCurrentPlayer = DatabaseInitializer.getSubStringCurrentPlayer(db);
        if(currentPlayer != 0 &&  levelCurrentPlayer != 0 && objectCurrentPlayer != null){
            presenter.setCurrentPlayer(currentPlayer, levelCurrentPlayer, objectCurrentPlayer, subStringCurrentPlayer);
        }

        List<ChildEntity> list = DatabaseInitializer.getListOfChildren(AppDatabase.getAppDatabase(getApplicationContext()));
        if(!list.isEmpty()) {
            for (ChildEntity child : list) {
                Log.i("Player " + child.getName() + "(" + child.getId() + ") ", child.getCurrentPlayer().toString());
            }
        }
    }

    /**
     * Start the animations in the start game activity
     */
   private void startAnimation() {
       ImageView carrotImage = findViewById(R.id.carrot);
       ImageView appleImage = findViewById(R.id.apple);
       ImageView pearImage = findViewById(R.id.pear);
       Button playButton = findViewById(R.id.playButton);

       // Load animations
       Animation animationBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
       Animation animationRight = AnimationUtils.loadAnimation(StartGameActivity.this, R.anim.half_rotation_right);
       Animation animationLeft = AnimationUtils.loadAnimation(StartGameActivity.this, R.anim.half_rotation_left);
       Animation animationLeftAndRight = AnimationUtils.loadAnimation(StartGameActivity.this, R.anim.lion_rotation);
       // Add the personilized interpolator for "animationBounce"
       presenter.onInit(animationBounce);

       // Start animations
       playButton.startAnimation(animationBounce);
       carrotImage.startAnimation(animationRight);
       appleImage.startAnimation(animationLeftAndRight);
       pearImage.startAnimation(animationLeft);
   }

    /**
     * Disable animations
     */
    private void clearAnimations() {
        ImageView carrotImage = findViewById(R.id.carrot);
        ImageView appleImage = findViewById(R.id.apple);
        ImageView pearImage = findViewById(R.id.pear);
        Button playButton = findViewById(R.id.playButton);

        // Clear animations
        playButton.clearAnimation();
        carrotImage.clearAnimation();
        appleImage.clearAnimation();
        pearImage.clearAnimation();
    }

    /**
     *  Display a popup menu when the menu button is clicked
     * @param view view
     */
   public void showPopup(View view){
       PopupMenu popup = new PopupMenu (this, view);
       MenuInflater inflater = popup.getMenuInflater();
       inflater.inflate(R.menu.actions, popup.getMenu());
       popup.show();
   }

    public void onClickMenuItem (MenuItem item) {
     presenter.onItemMenuSelected(item);
   }


    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
        presenter.storeCurrentPlayer(savedInstanceState);
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
    public Context getScreenContext() {
        return  this;
    }

    @Override
    public void onDestroy() {
        this.clearAnimations();
        presenter.onDestroy();
        super.onDestroy();
        presenter = null;
        db = null;
        Log.i ("[START GAME]", "On Destroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("[STARTGAME]", " I'm in onNewIntent.");
        if (intent != null)
            setIntent(intent);
        Bundle extras = getIntent().getExtras();
        int currentPlayerId = -1;
        if (extras != null) {
            Log.i("[STARTGAME ACTIVITY] ", "Intent extra is "+ extras.getInt(EXTRA_MESSAGE));
            currentPlayerId = extras.getInt(EXTRA_MESSAGE);
            DatabaseInitializer.setCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()), currentPlayerId);
            Log.e("[StartGameActivity]", "Current Player Level " + String.valueOf(DatabaseInitializer.getLevelCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()))));
        }

    }


}
