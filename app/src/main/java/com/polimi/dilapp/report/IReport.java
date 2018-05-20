package com.polimi.dilapp.report;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.polimi.dilapp.emailSender.Mail;

public interface IReport {

    interface View{
        Context getContext();
        void openPdf();
        void sharePdf();

    }
    interface Presenter{
        void onDestroy();
        void takeScreenshot(LinearLayout v1, LinearLayout v2, ProgressDialog progress);
    }
}
