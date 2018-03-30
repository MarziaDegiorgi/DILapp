package com.polimi.dilapp.levels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

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
        List<String> getSessionArray(int vectorID);

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
         * Display in the View the video in which the game was played correctly
         */
        void setVideoCorrectAnswer();

        /**
         * Display in the View the video in which the game was played uncorrectly before asking again the same element
         */
        void setVideoWrongAnswerToRepeat();
        /**
         * Display in the View the video in which the game was played uncorrectly before going on
         */
        void setVideoWrongAnswerAndGoOn();
        /*
        * The View is changed into the appropriate end game screen.
        *
        * */
        void setRepeatOrExitScreen();
        void setGoOnOrExitScreen();
        String getString();

        /**
         * Display the new sub-item required, this is used when an element is multiple such as for numbers or words to learn
         * @param currentSubElement the element actually required by the presenter
         */
        void setSubItemAnimation(String currentSubElement);

        void initGridView(String currentSubItem);

        void disableViews();
    }

    interface  Presenter {

        void askCurrentElement();
        boolean checkNfcAvailability();
        void startGame(List<String> sequence);
        void onDestroy();
        void chooseElement();
        void handleIntent(Intent intent);
        void setupForegroundDispatch();
        void stopForegroundDispatch();
        int getResourceId(String name,  Class resType);

        String getCurrentSequenceElement();
        boolean getMultipleElement();
        String getCurrentSubElement();
        int getNumberOfElements();
        void setLevelCurrentPlayer();
        void setObjectCurrentPlayer();
        void setSubStringCurrentPlayer();
        void setColourLevel();
        void setEnableNFC();
        void setRecipeLevel();
        String getCurrentReadTag();
        String getCurrentElement();

        /**
         * This is called by the view once that the main animation of a multiple object is completed and
         * a subitem should be required
         */
        void notifyFirstSubElement();
        void storeCurrentPlayer(Bundle savedInstanceState);
    }
}
