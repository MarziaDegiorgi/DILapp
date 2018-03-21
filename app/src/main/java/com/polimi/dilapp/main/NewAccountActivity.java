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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.polimi.dilapp.R;

import java.io.IOException;

public class NewAccountActivity extends AppCompatActivity implements INewAccount.View{

    private static final int GET_FROM_GALLERY = 3;
    private ImageButton avatar;
    private Bitmap bitmap;
    private String photoPath;
    private INewAccount.Presenter presenter;
    private Uri photoData = null;
    private String name = null;
    private String age = null;


    @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_newaccount);

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

        //link to the creation of a new account child
        Button newAccountButton = findViewById(R.id.form_button);
        newAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            switch ( v.getId() ) {
                case R.id.form_button:
                    final EditText edit_name = (EditText)findViewById(R.id.edit_name);
                    final EditText edit_age = (EditText)findViewById(R.id.edit_age);
                    name = edit_name.getText().toString();
                    age = edit_age.getText().toString();
                    presenter.insertChild(name, age,photoPath);
                    Intent inputForm = new Intent(getApplicationContext(), CreateAccountActivity.class);
                    startActivity(inputForm);
                    finish();
                    break;
            }
        }
        });
        if (savedInstanceState != null) {
            String photoData = savedInstanceState.getString("PhotoData");
            String name = savedInstanceState.getString("Name");
            String age = savedInstanceState.getString("Age");
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
            if(age != null) {
                this.age = age;
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
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.pop_up);
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
        if(age != null){
            savedInstanceState.putString("Age", age);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

}

