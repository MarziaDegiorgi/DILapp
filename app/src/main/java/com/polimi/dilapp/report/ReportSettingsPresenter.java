package com.polimi.dilapp.report;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ReportSettingsPresenter extends AppCompatActivity implements IReportSettings.Presenter{
    private IReportSettings.View activityInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ReportSettingsPresenter(IReportSettings.View view){
        activityInterface = view;

    }
}
