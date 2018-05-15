package com.polimi.dilapp.report;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

public interface IReportSettings {

    interface View {
    Context getContext();
    }
    interface Presenter {
        void enableAutoRepo(String email);

        void disableAutoRepo();

        void setAutoRepo(Button confirm, LinearLayout edit);
        Boolean isAutoRepoEnabled();

        void checkAutoReport();

    }
}
