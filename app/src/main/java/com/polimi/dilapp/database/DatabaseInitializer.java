package com.polimi.dilapp.database;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.polimi.dilapp.data.Child;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.net.Uri.parse;

public class DatabaseInitializer {

    public static void insertChild(final AppDatabase db, String name, int age, String photoPath) {
        ChildEntity child = new ChildEntity();
        child.setName(name);
        child.setAge(age);
        child.setPhoto(photoPath);
        db.childDao().insertChild(child);

        List<ChildEntity> list = getListOfChildren(db);
        for (int i = 0; i < list.size(); i++) {
            Log.i("Child"+ i, list.get(i).getName());
        }
    }
    public static List<ChildEntity> getListOfChildren (final AppDatabase db) {
        return db.childDao().getAll();
    }

    public static Bitmap getChildPhoto (ContentResolver contentResolver, ChildEntity childEntity) throws IOException {
        Uri uri = parse(childEntity.getPhoto());
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri);
        return bitmap;
    }

}
