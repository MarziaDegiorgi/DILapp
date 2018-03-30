package com.polimi.dilapp.database;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        String stringProgress = db.childReportDao().getProgress(id);
        ArrayList<Float> progress = new ArrayList<>();
        if (stringProgress != null) {
            String[] listProgress = stringProgress.split(",");
            for(String element : listProgress){
                float f = Float.parseFloat(element);
                progress.add(f);
            }
        }else{
            progress.add(0.0f);
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
        db.childReportDao().setProgress(id, sb.toString());
    }
    public static int getErrorsA(AppDatabase db, int id){
     return db.childReportDao().getErrorsA(id);
    }
    public static void setErrorsA(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsA(id, number);
    }
    public static int getErrorsB(AppDatabase db, int id){
        return db.childReportDao().getErrorsB(id);
    }
    public static void setErrorsB(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsB(id, number);
    }
    public static int getErrorsC(AppDatabase db, int id){
        return db.childReportDao().getErrorsC(id);
    }
    public static void setErrorsC(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsC(id, number);
    }
    public static int getErrorsD(AppDatabase db, int id){
        return db.childReportDao().getErrorsD(id);
    }
    public static void setErrorsD(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsD(id, number);
    }
    public static int getErrorsE(AppDatabase db, int id){
        return db.childReportDao().getErrorsE(id);
    }
    public static void setErrorsE(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsE(id, number);
    }
    public static int getErrorsF(AppDatabase db, int id){
        return db.childReportDao().getErrorsF(id);
    }
    public static void setErrorsF(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsF(id, number);
    }
    public static int getErrorsG(AppDatabase db, int id){
        return db.childReportDao().getErrorsG(id);
    }
    public static void setErrorsG(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsG(id, number);
    }
    public static int getErrorsH(AppDatabase db, int id){
        return db.childReportDao().getErrorsH(id);
    }
    public static void setErrorsH(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsH(id, number);
    }
    public static int getErrorsI(AppDatabase db, int id){
        return db.childReportDao().getErrorsI(id);
    }
    public static void setErrorsI(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsI(id, number);
    }
    public static int getErrorsL(AppDatabase db, int id){
        return db.childReportDao().getErrorsL(id);
    }
    public static void setErrorsL(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsL(id, number);
    }
    public static int getErrorsM(AppDatabase db, int id){
        return db.childReportDao().getErrorsM(id);
    }
    public static void setErrorsM(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsM(id, number);
    }
    public static int getErrorsN(AppDatabase db, int id){
        return db.childReportDao().getErrorsN(id);
    }
    public static void setErrorsN(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsN(id, number);
    }
    public static int getErrorsO(AppDatabase db, int id){
        return db.childReportDao().getErrorsO(id);
    }
    public static void setErrorsO(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsO(id, number);
    }
    public static int getErrorsP(AppDatabase db, int id){
        return db.childReportDao().getErrorsP(id);
    }
    public static void setErrorsP(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsP(id, number);
    }
    public static int getErrorsQ(AppDatabase db, int id){
        return db.childReportDao().getErrorsQ(id);
    }
    public static void setErrorsQ(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsQ(id, number);
    }
    public static int getErrorsR(AppDatabase db, int id){
        return db.childReportDao().getErrorsR(id);
    }
    public static void setErrorsR(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsR(id, number);
    }
    public static int getErrorsS(AppDatabase db, int id){
        return db.childReportDao().getErrorsS(id);
    }
    public static void setErrorsS(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsS(id, number);
    }
    public static int getErrorsT(AppDatabase db, int id){
        return db.childReportDao().getErrorsT(id);
    }
    public static void setErrorsT(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsT(id, number);
    }
    public static int getErrorsU(AppDatabase db, int id){
        return db.childReportDao().getErrorsU(id);
    }
    public static void setErrorsU(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsU(id, number);
    }
    public static int getErrorsV(AppDatabase db, int id){
        return db.childReportDao().getErrorsV(id);
    }
    public static void setErrorsV(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsV(id, number);
    }public static int getErrorsZ(AppDatabase db, int id){
        return db.childReportDao().getErrorsZ(id);
    }
    public static void setErrorsZ(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsZ(id, number);
    }
    public static int getErrorsOne(AppDatabase db, int id){
        return db.childReportDao().getErrorsOne(id);
    }
    public static void setErrorsOne(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsOne(id, number);
    }
    public static int getErrorsTwo(AppDatabase db, int id){
        return db.childReportDao().getErrorsTwo(id);
    }
    public static void setErrorsTwo(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsTwo(id, number);
    }
    public static int getErrorsThree(AppDatabase db, int id){
        return db.childReportDao().getErrorsThree(id);
    }
    public static void setErrorsThree(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsThree(id, number);
    }
    public static int getErrorsFour(AppDatabase db, int id){
        return db.childReportDao().getErrorsFour(id);
    }
    public static void setErrorsFour(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsFour(id, number);
    }
    public static int getErrorsFive(AppDatabase db, int id){
        return db.childReportDao().getErrorsFive(id);
    }
    public static void setErrorsFive(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsFive(id, number);
    }
    public static int getErrorsSix(AppDatabase db, int id){
        return db.childReportDao().getErrorsSix(id);
    }
    public static void setErrorsSix(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsSix(id, number);
    }
    public static int getErrorsSeven(AppDatabase db, int id){
        return db.childReportDao().getErrorsSeven(id);
    }
    public static void setErrorsSeven(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsSeven(id, number);
    }
    public static int getErrorsEight(AppDatabase db, int id){
        return db.childReportDao().getErrorsEight(id);
    }
    public static void setErrorsEight(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsEight(id, number);
    }
    public static int getErrorsNine(AppDatabase db, int id){
        return db.childReportDao().getErrorsNine(id);
    }
    public static void setErrorsNine(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsNine(id, number);
    }
    public static int getErrorsZero(AppDatabase db, int id){
        return db.childReportDao().getErrorsZero(id);
    }
    public static void setErrorsZero(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsZero(id, number);
    }
    public static int getErrorsBanana(AppDatabase db, int id){
        return db.childReportDao().getErrorsBanana(id);
    }
    public static void setErrorsBanana(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsBanana(id, number);
    }
    public static int getErrorsCorn(AppDatabase db, int id){
        return db.childReportDao().getErrorsCorn(id);
    }
    public static void setErrorsCorn(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsCorn(id, number);
    }
    public static int getErrorsLemon(AppDatabase db, int id){
        return db.childReportDao().getErrorsLemon(id);
    }
    public static void setErrorsLemon(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsLemon(id, number);
    }
    public static int getErrorsGrapefruit(AppDatabase db, int id){
        return db.childReportDao().getErrorsGrapefruit(id);
    }
    public static void setErrorsGrapefruit(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsGrapefruit(id, number);
    }
    public static int getErrorsWatermelon(AppDatabase db, int id){
        return db.childReportDao().getErrorsWatermelon(id);
    }
    public static void setErrorsWatermelon(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsWatermelon(id, number);
    }
    public static int getErrorsStrawberry(AppDatabase db, int id){
        return db.childReportDao().getErrorsStrawberry(id);
    }
    public static void setErrorsStrawberry(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsStrawberry(id, number);
    }
    public static int getErrorsApple(AppDatabase db, int id){
        return db.childReportDao().getErrorsApple(id);
    }
    public static void setErrorsApple(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsApple(id, number);
    }
    public static int getErrorsPepper(AppDatabase db, int id){
        return db.childReportDao().getErrorsPepper(id);
    }
    public static void setErrorsPepper(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsPepper(id, number);
    }
    public static int getErrorsTomato(AppDatabase db, int id){
        return db.childReportDao().getErrorsTomato(id);
    }
    public static void setErrorsTomato(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsTomato(id, number);
    }
    public static int getErrorsOrange(AppDatabase db, int id){
        return db.childReportDao().getErrorsOrange(id);
    }
    public static void setErrorsOrange(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsOrange(id, number);
    }
    public static int getErrorsCarrot(AppDatabase db, int id){
        return db.childReportDao().getErrorsCarrot(id);
    }
    public static void setErrorsCarrot(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsCarrot(id, number);
    }
    public static int getErrorsOnion(AppDatabase db, int id){
        return db.childReportDao().getErrorsOnion(id);
    }
    public static void setErrorsOnion(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsOnion(id, number);
    }
    public static int getErrorsTangerine(AppDatabase db, int id){
        return db.childReportDao().getErrorsTangerine(id);
    }
    public static void setErrorsTangerine(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsTangerine(id, number);
    }
    public static int getErrorsEggplant(AppDatabase db, int id){
        return db.childReportDao().getErrorsEggplant(id);
    }
    public static void setErrorsEggplant(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsEggplant(id, number);
    }
    public static int getErrorsAsparagus(AppDatabase db, int id){
        return db.childReportDao().getErrorsAsparagus(id);
    }
    public static void setErrorsAsparagus(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsAsparagus(id, number);
    }
    public static int getErrorsBroccoli(AppDatabase db, int id){
        return db.childReportDao().getErrorsBroccoli(id);
    }
    public static void setErrorsBroccoli(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsBroccoli(id, number);
    }
    public static int getErrorsCucumber(AppDatabase db, int id){
        return db.childReportDao().getErrorsCucumber(id);
    }
    public static void setErrorsCucumber(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsCucumber(id, number);
    }
    public static int getErrorsPear(AppDatabase db, int id){
        return db.childReportDao().getErrorsPear(id);
    }
    public static void setErrorsPear(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsPear(id, number);
    }
    public static int getErrorsGreenpea(AppDatabase db, int id){
        return db.childReportDao().getErrorsGreenpea(id);
    }
    public static void setErrorsGreenpea(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsGreenpea(id, number);
    }
    public static int getErrorsFennel(AppDatabase db, int id){
        return db.childReportDao().getErrorsFennel(id);
    }
    public static void setErrorsFennel(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsFennel(id, number);
    }
    public static int getErrorsPotato(AppDatabase db, int id){
        return db.childReportDao().getErrorsPotato(id);
    }
    public static void setErrorsPotato(AppDatabase db, int id, int number){
        db.childReportDao().setErrorsPotato(id, number);
    }

}
