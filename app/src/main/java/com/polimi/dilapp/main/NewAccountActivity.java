package com.polimi.dilapp.main;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.polimi.dilapp.R;
import com.polimi.dilapp.data.Child;
import com.polimi.dilapp.data.ListOfChildren;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.ChildEntity;
import com.polimi.dilapp.database.DatabaseInitializer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NewAccountActivity extends AppCompatActivity {

    private static final int GET_FROM_GALLERY = 3;
    private ImageButton avatar;
    private Bitmap bitmap;
    private String photoPath;



    @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_newaccount);


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
                    DatabaseInitializer.insertChild(AppDatabase.getAppDatabase(getApplicationContext()), edit_name.getText().toString(), Integer.parseInt(edit_age.getText().toString()), photoPath);
                    Toast.makeText(NewAccountActivity.this, DatabaseInitializer.getListOfChildren(AppDatabase.getAppDatabase(getApplicationContext())).size()+": Account created!", Toast.LENGTH_LONG).show();
                    Intent inputForm = new Intent(getApplicationContext(), CreateAccountActivity.class);
                    startActivity(inputForm);
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
            Uri selectedImage = data.getData();
            photoPath = selectedImage.toString();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}

