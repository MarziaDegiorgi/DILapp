package com.polimi.dilapp.main;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NewAccountActivity extends AppCompatActivity implements INewAccount.View{

    private static final int GET_FROM_GALLERY = 3;
    private ImageButton avatar;
    private Bitmap bitmap;
    private String photoPath;
    private INewAccount.Presenter presenter;


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
                    presenter.insertChild(edit_name,edit_age,photoPath);
                    Intent inputForm = new Intent(getApplicationContext(), CreateAccountActivity.class);
                    startActivity(inputForm);
                    finish();
                    break;
            }
        }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            if (!presenter.setPhoto(data)){
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
}

