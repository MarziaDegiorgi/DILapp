package com.polimi.dilapp.report;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levelmap.LevelMapActivity;

public class ReportMainPresenter extends AppCompatActivity implements IReport.Presenter{
    private IReport.View activityInterface;

    public ReportMainPresenter(IReport.View view){
        activityInterface = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemMenuSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings:
                    Intent intent = new Intent(this, ReportSettingsActivity.class);
                    this.startActivity(intent);
                    break;
                default:
                    break;
            }
        }


}
