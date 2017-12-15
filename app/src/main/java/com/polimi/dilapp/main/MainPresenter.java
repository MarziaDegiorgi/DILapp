package com.polimi.dilapp.main;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

public class MainPresenter implements IMain.Presenter{

    private IMain.View mainView;
    private AppDatabase db;
    MainPresenter(IMain.View view){

        this.mainView= view;
        db = AppDatabase.getAppDatabase(mainView.getContext());
    }

    @Override
    public void startVideo(VideoView introVideoView, String packageName) {
        introVideoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.intro));
        introVideoView.start();
    }
    @Override
    public void resumeCurrentPlayer(Bundle savedInstanceState) {
        DatabaseInitializer.setCurrentPlayer(db, savedInstanceState.getInt("current_player"));
        DatabaseInitializer.setLevelCurrentPlayer(db, savedInstanceState.getInt("level"));
        Log.i("Current player: ", String.valueOf(DatabaseInitializer.getCurrentPlayer(db)));

    }

    @Override
    public void storeCurrentPlayer(Bundle savedInstanceState) {
        savedInstanceState.putInt("current_player", DatabaseInitializer.getCurrentPlayer(db));
        savedInstanceState.putInt("level", DatabaseInitializer.getLevelCurrentPlayer(db));
    }

}
