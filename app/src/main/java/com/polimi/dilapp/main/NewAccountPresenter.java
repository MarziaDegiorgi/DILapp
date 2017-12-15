package com.polimi.dilapp.main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.ChildEntity;
import com.polimi.dilapp.database.DatabaseInitializer;

import java.io.IOException;
import java.util.List;

public class NewAccountPresenter implements INewAccount.Presenter {

    private INewAccount.View addAccountView;
    private AppDatabase db;

    NewAccountPresenter(INewAccount.View view) {

        this.addAccountView = view;
        db = AppDatabase.getAppDatabase(addAccountView.getContext());
    }

    @Override
    public void insertChild(EditText name, EditText age, String photoPath) {
        DatabaseInitializer.insertChild(db, name.getText().toString(), Integer.parseInt(age.getText().toString()), photoPath);

    }

    @Override
    public Boolean setPhoto(Intent data) {
        Uri selectedImage = data.getData();
        if (checkExistingAccount(selectedImage)) {
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
            return true;
        } else {
            return false;
        }

    }


    private Boolean checkExistingAccount(Uri selectedImage) {
        List<ChildEntity> list = db.childDao().getAll();
        Boolean isValid = true;
        for (ChildEntity child : list) {
            if (child.getPhoto().equals(selectedImage.toString())) {
                isValid = false;
            }
        }
        return isValid;
    }

}
