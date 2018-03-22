package com.polimi.dilapp.main;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.polimi.dilapp.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewAccountActivity extends AppCompatActivity implements INewAccount.View{

    private static final int GET_FROM_GALLERY = 3;
    private ImageButton avatar;
    private Bitmap bitmap;
    private String photoPath;
    private INewAccount.Presenter presenter;
    private Uri photoData = null;
    private String name = null;
    private String birth = null;
    private EditText edit_name;
    private EditText edit_birth;



    @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_newaccount);
            edit_name = (EditText)findViewById(R.id.edit_name);
            edit_birth = (EditText)findViewById(R.id.edit_birth);

        //Set up the presenter
        presenter = new NewAccountPresenter(this);
            bitmap = null;
            avatar = (ImageButton) findViewById(R.id.avatar);
            avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "ggmmaaaa";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    edit_birth.setText(current);
                    edit_birth.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        edit_birth.addTextChangedListener(tw);

        //link to the creation of a new account child
        Button newAccountButton = findViewById(R.id.form_button);
        newAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    name = edit_name.getText().toString();
                    birth = edit_birth.getText().toString();
                    switch (name){
                        case "":
                            if(birth.equals("")){
                                showPopUp(R.string.fields_missing);
                            }else{
                                showPopUp(R.string.name_missing);
                            }
                            break;
                        default:
                            if(birth.equals("")) {
                                showPopUp(R.string.birth_missing);
                                break;
                            }
                    }
        }
        });
        if (savedInstanceState != null) {
            String photoData = savedInstanceState.getString("PhotoData");
            String name = savedInstanceState.getString("Name");
            String birth = savedInstanceState.getString("Birth");
            String photoPath = savedInstanceState.getString("PhotoPath");
            if(photoData != null) {
                Log.i("[SAVED PHOTO DATA]", photoData);
                try {
                    presenter.reloadPhoto(photoData);
                    this.photoData = Uri.parse(photoData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(photoPath != null) {
                this.photoPath = photoPath;
            }
            if(name != null) {
                this.name = name;
            }
            if(birth != null) {
                this.birth = birth;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            photoData = data.getData();
            if (!presenter.setPhoto(photoData)){
                showPopUp(R.string.error_image);
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public ContentResolver getContentRes() {
        return getContentResolver();
    }

    @Override
    public void setPhoto(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public ImageButton getAvatar() {
        return avatar;
    }

    @Override
    protected void onSaveInstanceState (Bundle savedInstanceState) {
        if(photoData != null) {
            savedInstanceState.putString("PhotoData", photoData.toString());
        }
        if(photoPath != null) {
            savedInstanceState.putString("PhotoPath", photoPath);
        }
        if(name != null) {
            savedInstanceState.putString("Name", name);
        }
        if(birth != null){
            savedInstanceState.putString("Birth", birth);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    private void showPopUp(int stringId){
        final Dialog dialog = new Dialog(NewAccountActivity.this);
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

