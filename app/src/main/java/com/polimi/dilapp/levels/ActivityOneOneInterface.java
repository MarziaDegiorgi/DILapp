package com.polimi.dilapp.levels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public interface ActivityOneOneInterface {
    interface  View {

        /**
         * Called by the presenter
         * @return the context of the view
         */
        Context getScreenContext();
        void setVideoView(int videoID);
        ArrayList<String> getSessionArray(int vectorID);
        void setPresentationAnimation(String currentElement);
        void setWaitingAnimation();
        Context getApplicationContext();
        Class getApplicationClass();
        Activity getActivity();
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
