package com.polimi.dilapp.database;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.net.Uri.parse;

public class DatabaseInitializer {

    public static void insertChild(final AppDatabase db, String name, String birth, String photoPath) {
        ChildEntity child = new ChildEntity();
        child.setName(name);
        child.setBirth(birth);
        child.setPhoto(photoPath);
        child.setCurrentPlayer(false);
        db.childDao().insertChild(child);
        ReportOneOneEntity reportOneOneEntity = new ReportOneOneEntity(child.getId());
        ReportOneTwoEntity reportOneTwoEntity = new ReportOneTwoEntity(child.getId());
        ReportOneThreeEntity reportOneThreeEntity = new ReportOneThreeEntity(child.getId());
        ReportTwoOneEntity reportTwoOneEntity = new ReportTwoOneEntity(child.getId());
        ReportTwoTwoEntity reportTwoTwoEntity = new ReportTwoTwoEntity(child.getId());
        ReportTwoThreeEntity reportTwoThreeEntity = new ReportTwoThreeEntity(child.getId());

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
    public static String getNameCurrentPlayer(AppDatabase db){
        return db.childDao().getNameCurrentPlayer(true);
    }
    public static String getBirthCurrentPlayer(AppDatabase db){
        return db.childDao().getBirthCurrentPlayer(true);
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
    public static void setObjectCurrentPlayer(AppDatabase db, String object) {
        db.childDao().setObjectCurrentPlayer(object, true);
    }
    public static String getObjectCurrentPlayer(AppDatabase db) {
        return db.childDao().getObjectCurrentPlayer(true);
    }
    public static void setSubStringCurrentPlayer(AppDatabase db, String subString) {
        db.childDao().setSubStringCurrentPlayer(subString, true);
    }
    public static String getSubStringCurrentPlayer(AppDatabase db) {
        return db.childDao().getSubStringCurrentPlayer(true);
    }
    public static ArrayList<Float> getProgress(AppDatabase db, int id){
        String stringProgress = db.childDao().getProgress(id);
        ArrayList<Float> progress = new ArrayList<>();
        if (stringProgress != null) {
            String[] listProgress = stringProgress.split(",");
            for(String element : listProgress){
                if(!element.equals("")) {
                    float f = Float.parseFloat(element);
                    progress.add(f);
                }
            }
        }
        return progress;
    }
    public static void setProgress(AppDatabase db, int id, ArrayList<Float> newProgress){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < newProgress.size(); i++){
            String s = Float.toString(newProgress.get(i));
            sb.append(s);
            sb.append(",");
        }
        db.childDao().setProgress(id, sb.toString());
    }

    public static ArrayList<Date> getProgressDate(AppDatabase db, int id) throws ParseException {
        String stringProgress = db.childDao().getProgressDate(id);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Date> progress = new ArrayList<>();
        if (stringProgress != null) {
            String[] listProgress = stringProgress.split(",");
            for(String element : listProgress){
                Date date = df.parse(element);
                progress.add(date);
            }
        }
        Log.i("[DATABASE INIT]", "I'm getting progress date: " + progress);
        return progress;
    }

    public static void setProgressDate(AppDatabase db, int id, ArrayList<Date> newProgress){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < newProgress.size(); i++){
            String formattedDate = df.format(newProgress.get(i));
            sb.append(formattedDate);
            sb.append(",");
        }
        Log.i("[DATABASE INIT]", "I'm setting progress date: " + sb.toString());
        db.childDao().setProgressDate(id, sb.toString());
    }


    public static DataPoint[] getAllErrorsOneOne(AppDatabase db) {
        DataPoint[] data = new DataPoint[21];
        int idCurrentPlayer = db.childDao().getCurrentPlayer(true);
        data[0] = new DataPoint (1, db.reportOneOneDao().getErrorsApple(idCurrentPlayer));
        data[1] = new DataPoint (2, db.reportOneOneDao().getErrorsAsparagus(idCurrentPlayer));
        data[2] = new DataPoint (3, db.reportOneOneDao().getErrorsBanana(idCurrentPlayer));
        data[3] = new DataPoint (4, db.reportOneOneDao().getErrorsBroccoli(idCurrentPlayer));
        data[4] = new DataPoint (5, db.reportOneOneDao().getErrorsCarrot(idCurrentPlayer));
        data[5] = new DataPoint (6, db.reportOneOneDao().getErrorsCorn(idCurrentPlayer));
        data[6] = new DataPoint (7, db.reportOneOneDao().getErrorsCucumber(idCurrentPlayer));
        data[7] = new DataPoint (8, db.reportOneOneDao().getErrorsEggplant(idCurrentPlayer));
        data[8] = new DataPoint (9, db.reportOneOneDao().getErrorsFennel(idCurrentPlayer));
        data[9] = new DataPoint (10, db.reportOneOneDao().getErrorsGrapefruit(idCurrentPlayer));
        data[10] = new DataPoint (11, db.reportOneOneDao().getErrorsGreenpea(idCurrentPlayer));
        data[11] = new DataPoint (12, db.reportOneOneDao().getErrorsLemon(idCurrentPlayer));
        data[12] = new DataPoint (13, db.reportOneOneDao().getErrorsOnion(idCurrentPlayer));
        data[13] = new DataPoint (14, db.reportOneOneDao().getErrorsOrange(idCurrentPlayer));
        data[14] = new DataPoint (15, db.reportOneOneDao().getErrorsPear(idCurrentPlayer));
        data[15] = new DataPoint (16, db.reportOneOneDao().getErrorsPepper(idCurrentPlayer));
        data[16] = new DataPoint (17, db.reportOneOneDao().getErrorsPotato(idCurrentPlayer));
        data[17] = new DataPoint (18, db.reportOneOneDao().getErrorsStrawberry(idCurrentPlayer));
        data[18] = new DataPoint (19, db.reportOneOneDao().getErrorsTangerine(idCurrentPlayer));
        data[19] = new DataPoint (20, db.reportOneOneDao().getErrorsTomato(idCurrentPlayer));
        data[20] = new DataPoint (21, db.reportOneOneDao().getErrorsWatermelon(idCurrentPlayer));
        return data;
    }
}
