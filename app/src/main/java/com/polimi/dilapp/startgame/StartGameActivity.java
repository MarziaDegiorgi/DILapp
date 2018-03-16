package com.polimi.dilapp.startgame;

import android.content.Context;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_startgame);
        Bundle extras = getIntent().getExtras();
        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPlayButtonPressed();
                finish();
            }
        });

       /* Button fake = findViewById(R.id.fake);
        Intent intent;
        fake.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getApplicationContext(), ActivityOneTwo.class );
                                        startActivity(intent);
                                    }
                                }

        );*/
        int currentPlayerId = -1;
        if (extras != null) {
            currentPlayerId = extras.getInt(EXTRA_MESSAGE);
            DatabaseInitializer.setCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()), currentPlayerId);
            Log.e("[StartGameActivity]", "Current Player Level" + String.valueOf(DatabaseInitializer.getLevelCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()))));
        }

       List<ChildEntity> list = DatabaseInitializer.getListOfChildren(AppDatabase.getAppDatabase(getApplicationContext()));
        if(!list.isEmpty()) {
            for (ChildEntity child : list) {
                Log.i("Player " + child.getName() + "(" + child.getId() + ") ", child.getCurrentPlayer().toString());
            }
        }
        // Set up the presenter
        presenter = new StartGamePresenter(this);

        startAnimation();
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
        super.onRestoreInstanceState(savedInstanceState);
        presenter.resumeCurrentPlayer(savedInstanceState);
    }
    @Override
    public Context getScreenContext() {
        return  this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
