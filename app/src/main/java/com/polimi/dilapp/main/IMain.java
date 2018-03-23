package com.polimi.dilapp.main;

import android.content.Context;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.VideoView;

import com.polimi.dilapp.levelmap.CustomExpandableListAdapter;

public interface IMain {

    interface View{

        Context getContext();
    }

    interface Presenter {

        void startVideo(VideoView introVideoView, String packageName);
        void resumeCurrentPlayer(Bundle savedInstanceState);
        void storeCurrentPlayer(Bundle savedInstanceState);
        void resetCurrentPlayer();
    }
}
