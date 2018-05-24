package com.polimi.dilapp.main;


import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

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
    public void insertChild(String name, String birth, String photoPath) {
        DatabaseInitializer.insertChild(db, name, birth, photoPath);
        List<ChildEntity> listOfChildren = DatabaseInitializer.getListOfChildren(db);
        int childId = listOfChildren.get(listOfChildren.size()-1).getId();
        Log.i("[NEW ACCOUNT PRESENTER]", "New child with id "+ childId);
        DatabaseInitializer.insertReports(db, childId);
    }

    @Override
    public Boolean setPhoto(Uri selectedImage) {
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

    @Override
    public void reloadPhoto(String image) throws IOException {
        Uri uri = Uri.parse(image);
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(addAccountView.getContentRes(), uri);
        addAccountView.setBitmap(bitmap);
        addAccountView.getAvatar().setImageBitmap(bitmap);
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
