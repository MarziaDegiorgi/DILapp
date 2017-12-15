package com.polimi.dilapp.main;


import android.net.Uri;
import android.widget.VideoView;

import com.polimi.dilapp.R;

public class MainPresenter implements IMain.Presenter{

    private IMain.View mainView;
    MainPresenter(IMain.View view){
        this.mainView= view;
    }

    @Override
    public void startVideo(VideoView introVideoView, String packageName) {
        introVideoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.intro));
        introVideoView.start();
    }

}
