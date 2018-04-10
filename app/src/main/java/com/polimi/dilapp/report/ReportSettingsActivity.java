package com.polimi.dilapp.report;

import android.app.Dialog;
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
import com.polimi.dilapp.main.NewAccountActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                if(isChecked){
                    LinearLayout edit = findViewById(R.id.email_edit);
                    edit.setVisibility(View.VISIBLE);
                    final EditText emailValidate = (EditText)edit.findViewById(R.id.edit_box);
                    final Button confirm = findViewById(R.id.confirm_btn);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String email = emailValidate.getText().toString();
                            Log.i("[REPO SETTINGS]", "Text: " + email);
                            if (emailValidate.isEnabled()) {
                                if (isEmailValid(email)) {
                                    Log.i("[REPO SETTINGS]", "It's valid");
                                    emailValidate.setText(email);
                                    emailValidate.setClickable(false);
                                    emailValidate.setEnabled(false);
                                    emailValidate.setBackgroundColor(getResources().getColor(R.color.gray));
                                    emailValidate.setTextColor(getResources().getColor(R.color.dark_gray));
                                    confirm.setText(R.string.modify_email);
                                } else {
                                    Log.i("[REPO SETTINGS]", "It's not valid");
                                    //showPopUp(R.string.email_error);
                                    Toast.makeText(getApplicationContext(), "Indirizzo email non valido",
                                            Toast.LENGTH_LONG).show();
                                }
                            }else{
                                emailValidate.setClickable(true);
                                emailValidate.setEnabled(true);
                                emailValidate.setBackgroundColor(getResources().getColor(R.color.light_orange));
                                emailValidate.setTextColor(getResources().getColor(R.color.black));
                                confirm.setText(R.string.confirm_email);
                            }
                        }
                    });
                }else{
                    LinearLayout edit = findViewById(R.id.email_edit);
                    edit.setVisibility(View.GONE);
                }
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


    private void showPopUp(int stringId){
        final Dialog dialog = new Dialog(ReportSettingsActivity.this);
        dialog.setContentView(R.layout.pop_up);
        TextView tv = (TextView)dialog.findViewById(R.id.textView);
        tv.setText(stringId);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button close = (Button)dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
