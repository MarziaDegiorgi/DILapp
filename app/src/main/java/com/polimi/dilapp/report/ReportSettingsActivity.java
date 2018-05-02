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
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

public class ReportSettingsActivity extends AppCompatActivity implements IReportSettings.View{
    private IReportSettings.Presenter presenter;
    private LinearLayout edit;
    private Button confirm;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_report);
        db = AppDatabase.getAppDatabase(this);
        presenter = new ReportSettingsPresenter(this);
        edit = findViewById(R.id.email_edit);
        confirm = findViewById(R.id.confirm_btn);
        final Button confirm = findViewById(R.id.confirm_btn);
        Switch mySwitch = findViewById(R.id.switch1);

        Boolean flag = presenter.isAutoRepoEnabled();
        mySwitch.setChecked(flag);
        presenter.setAutoRepo(confirm, edit);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if(isChecked) {
                    DatabaseInitializer.enableAutoRepo(db);
                    presenter.setAutoRepo(confirm, edit);
                } else {
                    DatabaseInitializer.disableAutoRepo(db);
                    edit.setVisibility(View.GONE);
                    presenter.disableAutoRepo();
                }
            }

        });
    }
@Override
public void onResume() {
    super.onResume();
    Switch mySwitch = findViewById(R.id.switch1);
    mySwitch.setChecked(presenter.isAutoRepoEnabled());
    presenter.setAutoRepo(confirm,edit);
}

    @Override
    public void onRestart() {
        super.onRestart();
        Switch mySwitch = findViewById(R.id.switch1);
        mySwitch.setChecked(presenter.isAutoRepoEnabled());
        presenter.setAutoRepo(confirm,edit);
    }

    @Override
    public Context getContext() {
        return this;
    }

}
