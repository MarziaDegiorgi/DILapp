package com.polimi.dilapp.main;

import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.VideoView;

import com.polimi.dilapp.levelmap.CustomExpandableListAdapter;

public interface IMain {

    interface View{

        Context getContext();
    }

    interface Presenter {

        void startVideo(VideoView introVideoView, String packageName);
    }
}
