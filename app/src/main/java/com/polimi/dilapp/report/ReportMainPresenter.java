package com.polimi.dilapp.report;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levelmap.LevelMapActivity;

public class ReportMainPresenter implements IReport.Presenter{
    private IReport.View view;

    ReportMainPresenter(IReport.View view){
        this.view = view;
    }


    @Override
    public void onItemMenuSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings:
                    Intent intent = new Intent(view.getContext(), ReportSettingsActivity.class);
                    view.getContext().startActivity(intent);
                    break;
                default:
                    break;
            }
        }

        @Override
    public void onDestroy() {
            view = null;
        }


    public IReport.View getView() {
        return view;
    }

}
