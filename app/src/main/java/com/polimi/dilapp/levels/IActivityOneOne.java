package com.polimi.dilapp.levels;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public interface IActivityOneOne {
    interface  View {

        /**
         * Called by the presenter
         * @return the context of the view
         */
        Context getScreenContext();
        void setVideoView(int videoID);
        ArrayList<String> getSessionArray(int vectorID);
        void setPresentationAnimation(String currentElement);
        Class getApplicationClass();
        void setCorrectAnswerAnimation();
        void setNotCorrectAnswerAnimation();
    }

    interface  Presenter {

        boolean checkNfcAvailability();
        void startGame(List<String> sequence);
        void onDestroy();
        void chooseElement();
        void handleIntent(Intent intent);
        void setupForegroundDispatch();
        void stopForegroundDispatch();
        int getResourceId(String name,  Class resType);
    }
}
