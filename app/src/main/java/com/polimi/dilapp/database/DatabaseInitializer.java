package com.polimi.dilapp.database;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

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
        child.setCurrentPlayer(false);
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
        Bitmap bitmap = decodeSampledBitmapFromResource(contentResolver, childEntity);
        String flag = "null";
        if(bitmap != null){flag = "ok!";}
        Log.i("Image of "+childEntity.getName(), flag);
        return bitmap;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(ContentResolver contentResolver, ChildEntity childEntity) throws IOException {
       if(childEntity.getPhoto() !=null) {
           Uri uri = parse(childEntity.getPhoto());
           InputStream inputstream = contentResolver.openInputStream(uri);

           // First decode with inJustDecodeBounds=true to check dimensions
           final BitmapFactory.Options options = new BitmapFactory.Options();
           options.inJustDecodeBounds = true;
           BitmapFactory.decodeStream(inputstream, null, options);
           inputstream.close();

           if ((options.outWidth == -1) || (options.outHeight == -1)) {
               return null;
           }
           // Calculate inSampleSize
           options.inSampleSize = calculateInSampleSize(options, 500, 500);
           options.inJustDecodeBounds = false;

           // Decode bitmap with inSampleSize set
           inputstream = contentResolver.openInputStream(uri);
           Bitmap bitmap = BitmapFactory.decodeStream(inputstream, null, options);
           inputstream.close();
           return bitmap;
       }else {
           return null;
       }

    }

    public static ChildEntity getChildById (AppDatabase db, int id) {
        List<ChildEntity> list = getListOfChildren(db);
        ChildEntity selectedChild = null;
        for(ChildEntity childEntity : list){
            if (childEntity.getId() == id) {
                selectedChild = childEntity;
            }
        }
        return selectedChild;
    }

    public static void setCurrentPlayer (AppDatabase db, int id) {
       db.childDao().updateCurrentPlayer(id, true);
    }
    public static void resetCurrentPlayer (AppDatabase db) {
        db.childDao().resetCurrentPlayer(false, true);
    }
    public static int getCurrentPlayer(AppDatabase db) {
        return db.childDao().getCurrentPlayer(true);
    }
    public static int getLevelCurrentPlayer(AppDatabase db) {
        return db.childDao().getLevelCurrentPlayer(true);
    }
    public static void setLevelCurrentPlayer(AppDatabase db, int level) {
      db.childDao().setLevelCurrentPlayer(level, true);
    }
    public static void deletePlayer(AppDatabase db, ChildEntity childEntity){
        db.childDao().delete(childEntity);
    }
}
