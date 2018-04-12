package com.polimi.dilapp.report;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.main.NewAccountActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.polimi.dilapp.database.AppDatabase.getAppDatabase;

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
