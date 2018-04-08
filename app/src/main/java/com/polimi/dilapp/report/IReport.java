package com.polimi.dilapp.report;

import android.content.Context;
import android.view.MenuItem;

public interface IReport {

    interface View{

    }
    interface Presenter{
        void onItemMenuSelected(MenuItem item);
        void onDestroy();
    }
}
