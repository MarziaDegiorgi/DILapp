package com.polimi.dilapp.main;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.EditText;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

import java.io.IOException;

public class NewAccountPresenter implements INewAccount.Presenter{

    private INewAccount.View addAccountView;
    private AppDatabase db;

    NewAccountPresenter(INewAccount.View view){

        this.addAccountView= view;
        db = AppDatabase.getAppDatabase(addAccountView.getContext());
    }

    @Override
    public void insertChild(EditText name, EditText age, String photoPath) {
        DatabaseInitializer.insertChild(db, name.getText().toString(), Integer.parseInt(age.getText().toString()), photoPath);

    }

    @Override
    public void setPhoto(Intent data) {
        Uri selectedImage = data.getData();
        if (selectedImage != null) {
            addAccountView.setPhoto(selectedImage.toString());
        }
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(addAccountView.getContentRes(), selectedImage);
            addAccountView.setBitmap(bitmap);
            addAccountView.getAvatar().setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
