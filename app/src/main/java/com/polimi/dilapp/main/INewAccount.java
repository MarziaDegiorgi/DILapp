package com.polimi.dilapp.main;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;


public interface INewAccount {
    interface  View {
        Context getContext();
        ContentResolver getContentRes();
        void setPhoto(String photoPath);
        void setBitmap(Bitmap bitmap);
        ImageButton getAvatar();
    }

    interface  Presenter {
        void insertChild(String name, String age, String photoPath);
        Boolean setPhoto(Uri selectedImage);
        void reloadPhoto(String image) throws IOException;
    }
}
