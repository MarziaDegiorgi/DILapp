package com.polimi.dilapp.levelmap;


import android.content.Context;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.polimi.dilapp.database.AppDatabase;

public interface ILevelMap {
    interface View{

        Context getContext();

        /**
         *  Called by the presenter to link the adapter to the extendable list view
         * @param adapter created by the presenter
         */
        void showAdapter(CustomExpandableListAdapter adapter);
    }

    interface Presenter {
        /**
         * Initialize the expandable list retrieving from the model the data and setting up the adapter
         * then calls the view's method to link the adapter to the view
         */
       void initData();

        /**
         * When an item is selected the view notify the presenter with this method, that respond redirecting
         * to the selected activity
         *
         * @param parent view
         * @param mainCategoryPosition main category clicked
         * @param subcategoryPosition sub category clicked
         */
       void onItemSelected(ExpandableListView parent, int mainCategoryPosition, int subcategoryPosition);

        /**
         *  Called when click back
         */
        void onClickBack();


    }
}

