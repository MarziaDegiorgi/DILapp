package com.polimi.dilapp.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.polimi.dilapp.R;

public class ReportSettingsActivity extends AppCompatActivity implements IReportSettings.View{
    private IReportSettings.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_report);
        presenter = new ReportSettingsPresenter(this);
    }
}
