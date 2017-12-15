package com.polimi.dilapp.main;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.widget.VideoView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.levelmap.ILevelMap;
import com.polimi.dilapp.levelmap.LevelMapPresenter;

public class MainActivity extends AppCompatActivity implements IMain.View{

    IMain.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        VideoView introVideoView = (VideoView) findViewById(R.id.intro);

        //Set up the presenter
        presenter = new MainPresenter(this);
        presenter.startVideo(introVideoView, getPackageName());
        Toast.makeText(this, String.valueOf(DatabaseInitializer.getCurrentPlayer(AppDatabase.getAppDatabase(getApplicationContext()))),
                Toast.LENGTH_LONG).show();

        introVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
    // The savedInstanceState Bundle is same as the one used in onCreate().
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.resumeCurrentPlayer(savedInstanceState);
    }

    @Override
    public Context getContext() {
        return this;
    }

}
