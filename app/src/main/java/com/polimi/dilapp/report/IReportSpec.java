package com.polimi.dilapp.report;

import android.view.MenuItem;

public interface IReportSpec {
    interface View {

    }
    interface Presenter{
        void onItemMenuSelected(MenuItem item);

    }
}
