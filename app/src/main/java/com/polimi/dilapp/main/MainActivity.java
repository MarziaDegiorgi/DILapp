package com.polimi.dilapp.main;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levelmap.ILevelMap;
import com.polimi.dilapp.levelmap.LevelMapPresenter;

public class MainActivity extends AppCompatActivity implements IMain.View{

    IMain.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoView introVideoView = (VideoView) findViewById(R.id.intro);

        //Set up the presenter
        presenter = new MainPresenter(this);
        presenter.startVideo(introVideoView, getPackageName());

        introVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

}
