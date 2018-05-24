package com.polimi.dilapp.report;


import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportSettingsPresenter implements IReportSettings.Presenter{
    private IReportSettings.View activityInterface;
    private AppDatabase db;
    @VisibleForTesting
    public boolean enableReport;

    public ReportSettingsPresenter(IReportSettings.View view){
        activityInterface = view;
        db = AppDatabase.getAppDatabase(activityInterface.getContext());

    }
    @Override
    public void enableAutoRepo(String email){
        DatabaseInitializer.enableAutoRepo(db);
        enableReport = true;
        Log.i("[REPORT SETTING]", "Current email "+DatabaseInitializer.getEmail(db));
        if(DatabaseInitializer.getEmail(db).equals("")){
            DatabaseInitializer.setEmail(db, email);
        }
    }

    @Override
    public void disableAutoRepo() {
        enableReport = false;
        DatabaseInitializer.disableAutoRepo(db);
    }

@Override
    public void setAutoRepo(Button confirm, LinearLayout edit){
    final EditText emailValidate = edit.findViewById(R.id.edit_box);
    if(isAutoRepoEnabled()){
        edit.setVisibility(View.VISIBLE);
        checkRepo(confirm, emailValidate);
    }else{
        edit.setVisibility(View.GONE);
    }
    }


    public void checkRepo(final Button confirm, final EditText emailValidate){
        if (!DatabaseInitializer.isEmailSet(db)) {
            emailtoSet(confirm, emailValidate);
        }else {
            Log.i("[REPO SETTINGS]", "I'm in else");
            emailAlreadySet(confirm, emailValidate);
        }
    }

    public void emailtoSet(final Button confirm, final EditText emailValidate) {
        emailValidate.setClickable(true);
        emailValidate.setEnabled(true);
        emailValidate.setBackgroundResource(R.color.light_orange);
        emailValidate.setTextColor(ContextCompat.getColor(activityInterface.getContext(), R.color.black));
        confirm.setText(R.string.confirm_email);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailValidate.getText().toString();
                Log.i("[REPORT SETTING]", "Email "+ email);
                if (isEmailValid(email)) {
                    Log.i("[REPO SETTINGS]", "It's valid");
                    DatabaseInitializer.setEmail(db, email);
                    emailAlreadySet(confirm, emailValidate);
                } else {
                    Log.i("[REPO SETTINGS]", "It's not valid");
                    Toast.makeText(activityInterface.getContext(), "Indirizzo email non valido",
                            Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void emailAlreadySet(final Button confirm, final EditText emailValidate){
        emailValidate.setText(DatabaseInitializer.getEmail(db));
        emailValidate.setClickable(false);
        emailValidate.setEnabled(false);
        emailValidate.setBackgroundResource(R.color.gray);
        emailValidate.setTextColor(ContextCompat.getColor(activityInterface.getContext(), R.color.dark_gray));
        confirm.setText(R.string.modify_email);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[REPO SETTINGS]", "I'm in onClick else");
                emailValidate.setClickable(true);
                emailValidate.setEnabled(true);
                emailValidate.setBackgroundResource(R.color.light_orange);
                emailValidate.setTextColor(ContextCompat.getColor(activityInterface.getContext(), R.color.black));
                confirm.setText(R.string.confirm_email);
                DatabaseInitializer.setEmail(db,null);
                checkRepo(confirm, emailValidate);
            }
        });
    }


    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public Boolean isAutoRepoEnabled(){
        return DatabaseInitializer.isAutoRepoEnabled(db);
    }

    @Override
    public void checkAutoReport(){
        if(DatabaseInitializer.getEmail(db) == null){
            enableReport=false;
            DatabaseInitializer.disableAutoRepo(db);
        }
    }

    public boolean getisReportEnabled(){
        return enableReport;
    }
}
