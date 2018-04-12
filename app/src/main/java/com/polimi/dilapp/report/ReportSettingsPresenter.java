package com.polimi.dilapp.report;


import android.arch.persistence.room.Database;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

import static com.polimi.dilapp.database.AppDatabase.getAppDatabase;

public class ReportSettingsPresenter extends AppCompatActivity implements IReportSettings.Presenter{
    private IReportSettings.View activityInterface;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public ReportSettingsPresenter(IReportSettings.View view){
        activityInterface = view;
        db = AppDatabase.getAppDatabase(activityInterface.getContext());

    }
    @Override
    public void enableAutoRepo(String email){
        DatabaseInitializer.enableAutoRepo(db);
        Log.i("[REPORT SETTING]", "Current email "+DatabaseInitializer.getEmail(db));
        if(DatabaseInitializer.getEmail(db).equals("")){
            DatabaseInitializer.setEmail(db, email);
        }
    }

    @Override
    public void disableAutoRepo() {
        DatabaseInitializer.disableAutoRepo(db);
    }

@Override
    public void setAutoRepo(Button confirm, LinearLayout edit){
    edit.setVisibility(View.VISIBLE);
    final EditText emailValidate = (EditText) edit.findViewById(R.id.edit_box);
    checkRepo(confirm, emailValidate);

    }

    private void checkRepo(final Button confirm, final EditText emailValidate){
        if (!DatabaseInitializer.isEmailSet(db)) {
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String email = emailValidate.getText().toString();
                    if (isEmailValid(email)) {
                        Log.i("[REPO SETTINGS]", "It's valid");
                        emailValidate.setText(email);
                        emailValidate.setClickable(false);
                        emailValidate.setEnabled(false);
                        emailValidate.setBackgroundResource(R.color.gray);
                        emailValidate.setTextColor(ContextCompat.getColor(activityInterface.getContext(), R.color.dark_gray));
                        confirm.setText(R.string.modify_email);
                        DatabaseInitializer.enableAutoRepo(db);
                        DatabaseInitializer.setEmail(db, email);
                    } else {
                        Log.i("[REPO SETTINGS]", "It's not valid");
                        Toast.makeText(getApplicationContext(), "Indirizzo email non valido",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Log.i("[REPO SETTINGS]", "I'm in else");
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
                }
            });

        }
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
}
