package com.polimi.dilapp.main;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageButton;


public interface INewAccount {
    interface  View {
        Context getContext();
        ContentResolver getContentRes();
        void setPhoto(String photoPath);
        void setBitmap(Bitmap bitmap);
        ImageButton getAvatar();
    }

    interface  Presenter {
        void insertChild(EditText name, EditText age, String photoPath);
        void setPhoto(Intent data);
    }
}
