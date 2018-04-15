package com.polimi.dilapp.main;

import android.content.Context;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.polimi.dilapp.levelmap.CustomExpandableListAdapter;

public interface IMain {

    interface View{

        Context getContext();
        void setColorCurrentDot(TextView[] textView, int position, boolean current);

    }

    interface Presenter {
        void resumeCurrentPlayer(Bundle savedInstanceState);
        void storeCurrentPlayer(Bundle savedInstanceState);
        void resetCurrentPlayer();
        void addDotsIndicator(Context context, LinearLayout linearLayou, int position);
        int getDotsNumber();
    }
}
