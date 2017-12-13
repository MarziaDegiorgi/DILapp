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
import com.polimi.dilapp.levelmap.LevelMapActivity;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class StartGameActivity extends AppCompatActivity implements IStartGame.View {

    IStartGame.Presenter presenter;
    private int currentPlayerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startgame);
        Bundle extras = getIntent().getExtras();

       currentPlayerId = -1;
        if (extras != null) {
            currentPlayerId = extras.getInt(EXTRA_MESSAGE);
            setCurrentPlayer(currentPlayerId);
        }

       List<ChildEntity> list = DatabaseInitializer.getListOfChildren(AppDatabase.getAppDatabase(getApplicationContext()));
        for (ChildEntity child : list) {
            Log.i("Player "+ child.getName(), child.getCurrentPlayer().toString());
        }

        // Set up the presenter
        presenter = new StartGamePresenter(this);

        startAnimation();
    }

    /**
     * Start the animations in the start game activity
     */
   private void startAnimation() {
       ImageView carrotImage = (ImageView) findViewById(R.id.carrot);
       ImageView appleImage = (ImageView) findViewById(R.id.apple);
       ImageView pearImage = (ImageView) findViewById(R.id.pear);
       Button playButton = (Button) findViewById(R.id.playButton);

       // Load animations
       final Animation animationBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
       final Animation animationRight = AnimationUtils.loadAnimation(StartGameActivity.this, R.anim.half_rotation_right);
       final Animation animationLeft = AnimationUtils.loadAnimation(StartGameActivity.this, R.anim.half_rotation_left);

       // Add the personilized interpolator for "animationBounce"
       presenter.onInit(animationBounce);

       // Start animations
       playButton.startAnimation(animationBounce);
       carrotImage.startAnimation(animationRight);
       appleImage.startAnimation(animationRight);
       pearImage.startAnimation(animationLeft);
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

   public void onClickPlay(View view){
       presenter.onPlayButtonPressed();
   }

    @Override
    public Context getScreenContext() {
        return  this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        resetCurrentPlayer(currentPlayerId);

    }

    private void setCurrentPlayer(int id) {
       DatabaseInitializer.setCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()), id);
    }
    private void resetCurrentPlayer(int id) {
        DatabaseInitializer.resetCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()), id);
    }
}
