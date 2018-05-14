package com.polimi.dilapp.levels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

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

        /**
         *
         * @return the Sequence of the level
         */
        String getString();

        /**
         * Display the new sub-item required, this is used when an element is multiple such as for numbers or words to learn
         * @param currentSubElement the element actually required by the presenter
         */
        void setSubItemAnimation(String currentSubElement);

        /**
         * Init the Gridview in the activity for multiple objects
         * @param currentSubItem of the sequence
         */
        void initTableView(String currentSubItem);

        /**
         * disable all the image views in the activity
         */
        void disableViews();

        void sendEmail(Intent i);
    }

    interface  Presenter {

        /**
         * Called by the view to notify the presenter to choose the element to visualize
         */
        void askCurrentElement();

        /**
         * Checks the availability of the NFC in the current device
         * @return boolean value
         */
        boolean checkNfcAvailability();

        /**
         * Start the game from the point on which the account has finished the last time
         * @param sequence of elements of the level
         */
        void startGame(List<String> sequence);

        void onDestroy();

        /**
         * The presenter choose the next element to ask in the view
         */
        void chooseElement();

        /**
         * Called by the view anytime that an NFC intent is expected
         * @param intent NFC
         */
        void handleIntent(Intent intent);

        /**
         * Needed to enable and disable NFC
         */
        void setupForegroundDispatch();
        void stopForegroundDispatch();

        /**
         * The view ask the presenter to retrieve the resources needed to show
         * @param name of the resource
         * @param resType type of the resources
         * @return the resource ID
         */
        int getResourceId(String name,  Class resType);

        /**
         * @return the current sequence of element in the level
         */
        String getCurrentSequenceElement();

        /**
         * Define if the current element is composed by multiple items or not
         * @return boolean value
         */
        boolean getMultipleElement();

        /**
         * @return the current sub element in an element composed by multiple items
         */
        String getCurrentSubElement();

        /**
         * The number of sub elements that compose the current element, this value is keeping update
         * and decrease anytime that a subelement was recognized
         * @return the number of sub elements
         */
        int getNumberOfElements();

        /**
         * Save the level of the current player
         */
        void setLevelCurrentPlayer();

        /**
         * Save the element at which the current player has arrived
         */
        void setObjectCurrentPlayer();

        /**
         * Save the array at which the element is contained (session)
         */
        void setSubStringCurrentPlayer();

        /**
         * Save the colour at which the current player has arrived
         */
        void setColourLevel();

        /**
         * Enable the read of the NFC tag
         */
        void setEnableNFC();

        /**
         * Save the sub level of the recipe (3.2) at which the player as arrived
         */
        void setRecipeLevel();

        /**
         * @return the last read tag
         */
        String getCurrentReadTag();

        /**
         * @return the current element
         */
        String getCurrentElement();
        int getEndTime();
        void storeProgress();

        /**
         * This is called by the view once that the main animation of a multiple object is completed and
         * a subitem should be required
         */
        void notifyFirstSubElement();

        /**
         * @param savedInstanceState store the current player situation in the game
         */
        void storeCurrentPlayer(Bundle savedInstanceState);

    }
}
