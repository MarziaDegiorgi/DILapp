package com.polimi.dilapp.report;

import android.content.Context;
import android.view.MenuItem;

public interface IReportSpec {
    interface View {
    Context getContext();
    }
    interface Presenter{
        void onItemMenuSelected(MenuItem item);

    }
}
