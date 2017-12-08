package com.polimi.dilapp.levels;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public interface IGame {
    interface  View {

        /**
         * Called by the presenter
         * @return the context of the view
         */
        Context getScreenContext();

        /**
         * Display the video according to the id passed as parameter
         * @param videoID chosen by the presenter
         */
        void setVideoView(int videoID);

        /**
         *  Called by the presenter to get the sequence of elements in the view
         * @param vectorID resource
         * @return the array of elements of the activity
         */
        ArrayList<String> getSessionArray(int vectorID);

        /**
         * Display in the View the animation of the element selected by the presenter
         * @param currentElement chosen by the presenter
         */
        void setPresentationAnimation(String currentElement);

        /**
         * @return the class of the View activity
         */
        Class getApplicationClass();

        /**
         * Display in the View the animation in which the game was played correctly
         */
        void setCorrectAnswerAnimation();

        /**
         * Display in the View the animation in which the game was played uncorrectly
         */
        void setNotCorrectAnswerAnimation();
    }

    interface  Presenter {

        boolean checkNfcAvailability();
        void startGame(ArrayList<String> sequence);
        void onDestroy();
        void chooseElement();
        void handleIntent(Intent intent);
        void setupForegroundDispatch();
        void stopForegroundDispatch();
        int getResourceId(String name,  Class resType);
    }
}
