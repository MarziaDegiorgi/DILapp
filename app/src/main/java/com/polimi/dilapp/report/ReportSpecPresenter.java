package com.polimi.dilapp.report;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.polimi.dilapp.R;

public class ReportSpecPresenter extends AppCompatActivity implements IReportSpec.Presenter{

    private IReportSpec.View reportSpecActivity;

    public ReportSpecPresenter(IReportSpec.View view){
        reportSpecActivity = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemMenuSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(reportSpecActivity.getContext(), ReportSettingsActivity.class);
                this.startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
