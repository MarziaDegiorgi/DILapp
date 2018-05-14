package com.polimi.dilapp.report;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.LinearLayout;

public interface IReport {

    interface View{
        Context getContext();
        void openPdf();
        void sharePdf();

    }
    interface Presenter{
        void onItemMenuSelected(MenuItem item);
        void onDestroy();
        void takeScreenshot(LinearLayout v1, LinearLayout v2, ProgressDialog progress);
    }
}
