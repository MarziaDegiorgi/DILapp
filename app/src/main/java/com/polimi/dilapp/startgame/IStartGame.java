package com.polimi.dilapp.startgame;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;

import com.polimi.dilapp.database.AppDatabase;

public interface IStartGame {
    interface  View {

        /**
         * Called by the presenter
         * @return the context of the view
         */
        Context getScreenContext();
    }
    interface  Presenter {

        /**
         * Called when the view is destroyed
         */
        void onDestroy();

        /**
         * Called when the view has to notify that the button for start the game is
         * clicked
         */
        void onPlayButtonPressed();

        void resumeCurrentPlayer(Bundle savedInstanceState);
        void storeCurrentPlayer(Bundle savedInstanceState);
        int getLevelCurrentPlayer();
        void linkToActivity(int level, String object);
        void resumeCurrentPlayer(AppDatabase db, Bundle savedInstanceState);
        void setCurrentPlayer(int currentPlayer, int level, String object, String subString);
        void resetCurrentPlayer();

    }
}
