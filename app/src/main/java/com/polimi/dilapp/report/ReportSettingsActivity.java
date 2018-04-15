package com.polimi.dilapp.report;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.polimi.dilapp.R;

public class ReportSettingsActivity extends AppCompatActivity implements IReportSettings.View{
    private IReportSettings.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_report);
        presenter = new ReportSettingsPresenter(this);
        Switch mySwitch = findViewById(R.id.switch1);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if(isChecked) {
                    LinearLayout edit = findViewById(R.id.email_edit);
                    final Button confirm = findViewById(R.id.confirm_btn);
                    presenter.setAutoRepo(confirm, edit);
                } else {
                    LinearLayout edit = findViewById(R.id.email_edit);
                    edit.setVisibility(View.GONE);
                }
            }

        });
    }


    @Override
    public Context getContext() {
        return this;
    }
}
