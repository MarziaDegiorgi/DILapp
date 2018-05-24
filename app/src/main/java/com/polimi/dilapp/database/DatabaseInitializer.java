package com.polimi.dilapp.database;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;
import com.polimi.dilapp.R;

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

    public static ChildEntity insertChild(final AppDatabase db, String name, String birth, String photoPath) {
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
        return child;
    }

    public static void insertReports(final AppDatabase db, int id){
        ReportOneOneEntity reportOneOneEntity = new ReportOneOneEntity(id);
        db.reportOneOneDao().insertReport(reportOneOneEntity);
        ReportOneTwoEntity reportOneTwoEntity = new ReportOneTwoEntity(id);
        db.reportOneTwoDao().insertReport(reportOneTwoEntity);
        ReportOneThreeEntity reportOneThreeEntity = new ReportOneThreeEntity(id);
        db.reportOneThreeDao().insertReport(reportOneThreeEntity);
        ReportTwoOneEntity reportTwoOneEntity = new ReportTwoOneEntity(id);
        db.reportTwoOneDao().insertReport(reportTwoOneEntity);
        ReportTwoTwoEntity reportTwoTwoEntity = new ReportTwoTwoEntity(id);
        db.reportTwoTwoDao().insertReport(reportTwoTwoEntity);
        ReportTwoThreeEntity reportTwoThreeEntity = new ReportTwoThreeEntity(id);
        db.reportTwoThreeDao().insertReport(reportTwoThreeEntity);
        ReportThreeOneEntity reportThreeOneEntity = new ReportThreeOneEntity(id);
        db.reportThreeOneDao().insertReport(reportThreeOneEntity);
        ReportThreeTwoEntity reportThreeTwoEntity = new ReportThreeTwoEntity(id);
        db.reportThreeTwoDao().insertReport(reportThreeTwoEntity);

    }
    public static List<ChildEntity> getListOfChildren (final AppDatabase db) {
        return db.childDao().getAll();
    }

    public static Bitmap getChildPhoto (ContentResolver contentResolver, ChildEntity childEntity) throws IOException {
        Bitmap bitmap = decodeSampledBitmapFromResource(contentResolver, childEntity);
        String flag = "null";
        if(bitmap != null){flag = "ok!";}
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
       if(childEntity!=null && childEntity.getPhoto() !=null) {
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
           options.inSampleSize = calculateInSampleSize(options, 200, 200);
           options.inJustDecodeBounds = false;

           // Decode bitmap with inSampleSize set
           inputstream = contentResolver.openInputStream(uri);
           Bitmap bitmap = BitmapFactory.decodeStream(inputstream, null, options);
           inputstream.close();
           return bitmap;
       }else {
           //TODO: put default profile image
           Uri path = Uri.parse("android.resource://com.polimi.dilapp/" + R.drawable.avatar_default);
           InputStream inputstream = contentResolver.openInputStream(path);

           // First decode with inJustDecodeBounds=true to check dimensions
           final BitmapFactory.Options options = new BitmapFactory.Options();
           options.inJustDecodeBounds = true;
           BitmapFactory.decodeStream(inputstream, null, options);
           inputstream.close();

           if ((options.outWidth == -1) || (options.outHeight == -1)) {
               return null;
           }
           // Calculate inSampleSize
           options.inSampleSize = calculateInSampleSize(options, 200, 200);
           options.inJustDecodeBounds = false;

           // Decode bitmap with inSampleSize set
           inputstream = contentResolver.openInputStream(path);
           Bitmap bitmap = BitmapFactory.decodeStream(inputstream, null, options);
           inputstream.close();
           return bitmap;
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
        db.reportOneOneDao().delete(childEntity.getId());
        db.reportOneTwoDao().delete(childEntity.getId());
        db.reportOneThreeDao().delete(childEntity.getId());
        db.reportTwoOneDao().delete(childEntity.getId());
        db.reportTwoTwoDao().delete(childEntity.getId());
        db.reportTwoThreeDao().delete(childEntity.getId());
        db.reportThreeOneDao().delete(childEntity.getId());
        db.reportThreeTwoDao().delete(childEntity.getId());
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

    public static Date getLastProgressDate(AppDatabase db, int id) throws ParseException {
        ArrayList<Date> dates = getProgressDate(db, id);
        if(dates.size() != 0){
            return dates.get(dates.size()-1);
        }else{
            return null;
        }
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

    public static ArrayList<Integer> getCorrectAnswer(AppDatabase db, int id){
        String stringCorrectAnswer = db.childDao().getCorrectAnswer(id);
        ArrayList<Integer> correctAnswers = new ArrayList<>();
        if (stringCorrectAnswer != null) {
            String[] listProgress = stringCorrectAnswer.split(",");
            for(String element : listProgress){
                if(!element.equals("")) {
                    int ca = Integer.valueOf(element);
                    correctAnswers.add(ca);
                }
            }
        }
        return correctAnswers;
    }

    public static int getLastCorrectAnswer(AppDatabase db, int id) throws ParseException {
        ArrayList<Integer> correctAnswers = getCorrectAnswer(db, id);
        if(correctAnswers.size() != 0){
            return correctAnswers.get(correctAnswers.size()-1);
        }else{
            return -1;
        }
    }

    public static void setCorrectAnswer(AppDatabase db, int id, ArrayList<Integer> newCorrectAnswers){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < newCorrectAnswers.size(); i++){
            String s = Integer.toString(newCorrectAnswers.get(i));
            sb.append(s);
            sb.append(",");
        }
        db.childDao().setCorrectAnswer(id, sb.toString());
    }

    public static ArrayList<Integer> getTime(AppDatabase db, int id){
        String stringTime = db.childDao().getTime(id);
        ArrayList<Integer> time = new ArrayList<>();
        if (stringTime != null) {
            String[] listProgress = stringTime.split(",");
            for(String element : listProgress){
                if(!element.equals("")) {
                    int ca = Integer.valueOf(element);
                    time.add(ca);
                }
            }
        }
        return time;
    }

    public static int getLastTimePlayed(AppDatabase db, int id){
       ArrayList<Integer> timeArray = getTime(db, id);
       if(timeArray.size() != 0){
           return timeArray.get(timeArray.size()-1);
       }else{
        return -1;
       }
    }
    public static void setTime(AppDatabase db, int id, ArrayList<Integer> newTime){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < newTime.size(); i++){
            String s = Integer.toString(newTime.get(i));
            sb.append(s);
            sb.append(",");
        }
        db.childDao().setTime(id, sb.toString());
    }

    public static void enableAutoRepo(AppDatabase db){
        db.childDao().enableAutoRepo(getCurrentPlayer(db), "true");
    }

    public static void disableAutoRepo(AppDatabase db){
        db.childDao().disableAutoRepo(getCurrentPlayer(db),"false");
    }

    public static Boolean isAutoRepoEnabled(AppDatabase db){
        Boolean flag;
        if(db.childDao().isAutoRepoEnabled(getCurrentPlayer(db)) == null){
            db.childDao().disableAutoRepo(getCurrentPlayer(db), "false");
            flag = false;
        }else if(db.childDao().isAutoRepoEnabled(getCurrentPlayer(db)).equals("true")){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }

    public static Boolean isEmailSet(AppDatabase db){
        Log.i("[DB INITIALIZER]", "Email " +db.childDao().getEmail(getCurrentPlayer(db)));
        if((db.childDao().getEmail(getCurrentPlayer(db))) == null){
            return false;
        }else{
            return true;
        }
    }
    public static void setEmail(AppDatabase db, String email){
        db.childDao().setEmail(getCurrentPlayer(db), email);
    }

    public static String getEmail(AppDatabase db){
        return db.childDao().getEmail(getCurrentPlayer(db));
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

    public static DataPoint[] getAllErrorsOneTwo(AppDatabase db) {
        DataPoint[] data = new DataPoint[7];
        int idCurrentPlayer = db.childDao().getCurrentPlayer(true);
        data[0] = new DataPoint (1, db.reportOneTwoDao().getErrorsYellow(idCurrentPlayer));
        data[1] = new DataPoint (2, db.reportOneTwoDao().getErrorsRed(idCurrentPlayer));
        data[2] = new DataPoint (3, db.reportOneTwoDao().getErrorsOrangec(idCurrentPlayer));
        data[3] = new DataPoint (4, db.reportOneTwoDao().getErrorsViolet(idCurrentPlayer));
        data[4] = new DataPoint (5, db.reportOneTwoDao().getErrorsGreen(idCurrentPlayer));
        data[5] = new DataPoint (6, db.reportOneTwoDao().getErrorsWhite(idCurrentPlayer));
        data[6] = new DataPoint (7, db.reportOneTwoDao().getErrorsBrown(idCurrentPlayer));
        return data;
    }

    public static DataPoint[] getAllErrorsOneThree(AppDatabase db) {
        DataPoint[] data = new DataPoint[21];
        int idCurrentPlayer = db.childDao().getCurrentPlayer(true);
        data[0] = new DataPoint (1, db.reportOneThreeDao().getErrorsApple(idCurrentPlayer));
        data[1] = new DataPoint (2, db.reportOneThreeDao().getErrorsAsparagus(idCurrentPlayer));
        data[2] = new DataPoint (3, db.reportOneThreeDao().getErrorsBanana(idCurrentPlayer));
        data[3] = new DataPoint (4, db.reportOneThreeDao().getErrorsBroccoli(idCurrentPlayer));
        data[4] = new DataPoint (5, db.reportOneThreeDao().getErrorsCarrot(idCurrentPlayer));
        data[5] = new DataPoint (6, db.reportOneThreeDao().getErrorsCorn(idCurrentPlayer));
        data[6] = new DataPoint (7, db.reportOneThreeDao().getErrorsCucumber(idCurrentPlayer));
        data[7] = new DataPoint (8, db.reportOneThreeDao().getErrorsEggplant(idCurrentPlayer));
        data[8] = new DataPoint (9, db.reportOneThreeDao().getErrorsFennel(idCurrentPlayer));
        data[9] = new DataPoint (10, db.reportOneThreeDao().getErrorsGrapefruit(idCurrentPlayer));
        data[10] = new DataPoint (11, db.reportOneThreeDao().getErrorsGreenpea(idCurrentPlayer));
        data[11] = new DataPoint (12, db.reportOneThreeDao().getErrorsLemon(idCurrentPlayer));
        data[12] = new DataPoint (13, db.reportOneThreeDao().getErrorsOnion(idCurrentPlayer));
        data[13] = new DataPoint (14, db.reportOneThreeDao().getErrorsOrange(idCurrentPlayer));
        data[14] = new DataPoint (15, db.reportOneThreeDao().getErrorsPear(idCurrentPlayer));
        data[15] = new DataPoint (16, db.reportOneThreeDao().getErrorsPepper(idCurrentPlayer));
        data[16] = new DataPoint (17, db.reportOneThreeDao().getErrorsPotato(idCurrentPlayer));
        data[17] = new DataPoint (18, db.reportOneThreeDao().getErrorsStrawberry(idCurrentPlayer));
        data[18] = new DataPoint (19, db.reportOneThreeDao().getErrorsTangerine(idCurrentPlayer));
        data[19] = new DataPoint (20, db.reportOneThreeDao().getErrorsTomato(idCurrentPlayer));
        data[20] = new DataPoint (21, db.reportOneThreeDao().getErrorsWatermelon(idCurrentPlayer));
        return data;
    }

    public static DataPoint[] getAllErrorsTwoOne(AppDatabase db) {
        DataPoint[] data = new DataPoint[10];
        int idCurrentPlayer = db.childDao().getCurrentPlayer(true);
        data[0] = new DataPoint (1, db.reportTwoOneDao().getErrorsZero(idCurrentPlayer));
        data[1] = new DataPoint (2, db.reportTwoOneDao().getErrorsOne(idCurrentPlayer));
        data[2] = new DataPoint (3, db.reportTwoOneDao().getErrorsTwo(idCurrentPlayer));
        data[3] = new DataPoint (4, db.reportTwoOneDao().getErrorsThree(idCurrentPlayer));
        data[4] = new DataPoint (5, db.reportTwoOneDao().getErrorsFour(idCurrentPlayer));
        data[5] = new DataPoint (6, db.reportTwoOneDao().getErrorsFive(idCurrentPlayer));
        data[6] = new DataPoint (7, db.reportTwoOneDao().getErrorsSix(idCurrentPlayer));
        data[7] = new DataPoint (8, db.reportTwoOneDao().getErrorsSeven(idCurrentPlayer));
        data[8] = new DataPoint (9, db.reportTwoOneDao().getErrorsEight(idCurrentPlayer));
        data[9] = new DataPoint (10, db.reportTwoOneDao().getErrorsNine(idCurrentPlayer));
        return data;
    }

    public static DataPoint[] getAllErrorsTwoTwo(AppDatabase db) {
        DataPoint[] data = new DataPoint[21];
        int idCurrentPlayer = db.childDao().getCurrentPlayer(true);
        data[0] = new DataPoint (1, db.reportTwoTwoDao().getErrorsA(idCurrentPlayer));
        data[1] = new DataPoint (2, db.reportTwoTwoDao().getErrorsB(idCurrentPlayer));
        data[2] = new DataPoint (3, db.reportTwoTwoDao().getErrorsC(idCurrentPlayer));
        data[3] = new DataPoint (4, db.reportTwoTwoDao().getErrorsD(idCurrentPlayer));
        data[4] = new DataPoint (5, db.reportTwoTwoDao().getErrorsE(idCurrentPlayer));
        data[5] = new DataPoint (6, db.reportTwoTwoDao().getErrorsF(idCurrentPlayer));
        data[6] = new DataPoint (7, db.reportTwoTwoDao().getErrorsG(idCurrentPlayer));
        data[7] = new DataPoint (8, db.reportTwoTwoDao().getErrorsH(idCurrentPlayer));
        data[8] = new DataPoint (9, db.reportTwoTwoDao().getErrorsI(idCurrentPlayer));
        data[9] = new DataPoint (10, db.reportTwoTwoDao().getErrorsL(idCurrentPlayer));
        data[10] = new DataPoint (11, db.reportTwoTwoDao().getErrorsM(idCurrentPlayer));
        data[11] = new DataPoint (12, db.reportTwoTwoDao().getErrorsN(idCurrentPlayer));
        data[12] = new DataPoint (13, db.reportTwoTwoDao().getErrorsO(idCurrentPlayer));
        data[13] = new DataPoint (14, db.reportTwoTwoDao().getErrorsP(idCurrentPlayer));
        data[14] = new DataPoint (15, db.reportTwoTwoDao().getErrorsQ(idCurrentPlayer));
        data[15] = new DataPoint (16, db.reportTwoTwoDao().getErrorsR(idCurrentPlayer));
        data[16] = new DataPoint (17, db.reportTwoTwoDao().getErrorsS(idCurrentPlayer));
        data[17] = new DataPoint (18, db.reportTwoTwoDao().getErrorsT(idCurrentPlayer));
        data[18] = new DataPoint (19, db.reportTwoTwoDao().getErrorsU(idCurrentPlayer));
        data[19] = new DataPoint (20, db.reportTwoTwoDao().getErrorsV(idCurrentPlayer));
        data[20] = new DataPoint (21, db.reportTwoTwoDao().getErrorsZ(idCurrentPlayer));
        return data;
    }

    public static DataPoint[] getAllErrorsTwoThree(AppDatabase db) {
        DataPoint[] data = new DataPoint[21];
        int idCurrentPlayer = db.childDao().getCurrentPlayer(true);
        data[0] = new DataPoint (1, db.reportTwoThreeDao().getErrorsA(idCurrentPlayer));
        data[1] = new DataPoint (2, db.reportTwoThreeDao().getErrorsB(idCurrentPlayer));
        data[2] = new DataPoint (3, db.reportTwoThreeDao().getErrorsC(idCurrentPlayer));
        data[3] = new DataPoint (4, db.reportTwoThreeDao().getErrorsD(idCurrentPlayer));
        data[4] = new DataPoint (5, db.reportTwoThreeDao().getErrorsE(idCurrentPlayer));
        data[5] = new DataPoint (6, db.reportTwoThreeDao().getErrorsF(idCurrentPlayer));
        data[6] = new DataPoint (7, db.reportTwoThreeDao().getErrorsG(idCurrentPlayer));
        data[7] = new DataPoint (8, db.reportTwoThreeDao().getErrorsH(idCurrentPlayer));
        data[8] = new DataPoint (9, db.reportTwoThreeDao().getErrorsI(idCurrentPlayer));
        data[9] = new DataPoint (10, db.reportTwoThreeDao().getErrorsL(idCurrentPlayer));
        data[10] = new DataPoint (11, db.reportTwoThreeDao().getErrorsM(idCurrentPlayer));
        data[11] = new DataPoint (12, db.reportTwoThreeDao().getErrorsN(idCurrentPlayer));
        data[12] = new DataPoint (13, db.reportTwoThreeDao().getErrorsO(idCurrentPlayer));
        data[13] = new DataPoint (14, db.reportTwoThreeDao().getErrorsP(idCurrentPlayer));
        data[14] = new DataPoint (15, db.reportTwoThreeDao().getErrorsQ(idCurrentPlayer));
        data[15] = new DataPoint (16, db.reportTwoThreeDao().getErrorsR(idCurrentPlayer));
        data[16] = new DataPoint (17, db.reportTwoThreeDao().getErrorsS(idCurrentPlayer));
        data[17] = new DataPoint (18, db.reportTwoThreeDao().getErrorsT(idCurrentPlayer));
        data[18] = new DataPoint (19, db.reportTwoThreeDao().getErrorsU(idCurrentPlayer));
        data[19] = new DataPoint (20, db.reportTwoThreeDao().getErrorsV(idCurrentPlayer));
        data[20] = new DataPoint (21, db.reportTwoThreeDao().getErrorsZ(idCurrentPlayer));
        return data;
    }
    public static DataPoint[] getAllErrorsThreeOne(AppDatabase db) {
        DataPoint[] data = new DataPoint[10];
        int idCurrentPlayer = db.childDao().getCurrentPlayer(true);
        data[0] = new DataPoint (1, db.reportThreeOneDao().getErrorsZero(idCurrentPlayer));
        data[1] = new DataPoint (2, db.reportThreeOneDao().getErrorsOne(idCurrentPlayer));
        data[2] = new DataPoint (3, db.reportThreeOneDao().getErrorsTwo(idCurrentPlayer));
        data[3] = new DataPoint (4, db.reportThreeOneDao().getErrorsThree(idCurrentPlayer));
        data[4] = new DataPoint (5, db.reportThreeOneDao().getErrorsFour(idCurrentPlayer));
        data[5] = new DataPoint (6, db.reportThreeOneDao().getErrorsFive(idCurrentPlayer));
        data[6] = new DataPoint (7, db.reportThreeOneDao().getErrorsSix(idCurrentPlayer));
        data[7] = new DataPoint (8, db.reportThreeOneDao().getErrorsSeven(idCurrentPlayer));
        data[8] = new DataPoint (9, db.reportThreeOneDao().getErrorsEight(idCurrentPlayer));
        data[9] = new DataPoint (10, db.reportThreeOneDao().getErrorsNine(idCurrentPlayer));
        return data;
    }

    public static DataPoint[] getAllErrorsThreeTwo(AppDatabase db) {
        DataPoint[] data = new DataPoint[21];
        int idCurrentPlayer = db.childDao().getCurrentPlayer(true);
        data[0] = new DataPoint (1, db.reportThreeTwoDao().getErrorsApple(idCurrentPlayer));
        data[1] = new DataPoint (2, db.reportThreeTwoDao().getErrorsAsparagus(idCurrentPlayer));
        data[2] = new DataPoint (3, db.reportThreeTwoDao().getErrorsBanana(idCurrentPlayer));
        data[3] = new DataPoint (4, db.reportThreeTwoDao().getErrorsBroccoli(idCurrentPlayer));
        data[4] = new DataPoint (5, db.reportThreeTwoDao().getErrorsCarrot(idCurrentPlayer));
        data[5] = new DataPoint (6, db.reportThreeTwoDao().getErrorsCorn(idCurrentPlayer));
        data[6] = new DataPoint (7, db.reportThreeTwoDao().getErrorsCucumber(idCurrentPlayer));
        data[7] = new DataPoint (8, db.reportThreeTwoDao().getErrorsEggplant(idCurrentPlayer));
        data[8] = new DataPoint (9, db.reportThreeTwoDao().getErrorsFennel(idCurrentPlayer));
        data[9] = new DataPoint (10, db.reportThreeTwoDao().getErrorsGrapefruit(idCurrentPlayer));
        data[10] = new DataPoint (11, db.reportThreeTwoDao().getErrorsGreenpea(idCurrentPlayer));
        data[11] = new DataPoint (12, db.reportThreeTwoDao().getErrorsLemon(idCurrentPlayer));
        data[12] = new DataPoint (13, db.reportThreeTwoDao().getErrorsOnion(idCurrentPlayer));
        data[13] = new DataPoint (14, db.reportThreeTwoDao().getErrorsOrange(idCurrentPlayer));
        data[14] = new DataPoint (15, db.reportThreeTwoDao().getErrorsPear(idCurrentPlayer));
        data[15] = new DataPoint (16, db.reportThreeTwoDao().getErrorsPepper(idCurrentPlayer));
        data[16] = new DataPoint (17, db.reportThreeTwoDao().getErrorsPotato(idCurrentPlayer));
        data[17] = new DataPoint (18, db.reportThreeTwoDao().getErrorsStrawberry(idCurrentPlayer));
        data[18] = new DataPoint (19, db.reportThreeTwoDao().getErrorsTangerine(idCurrentPlayer));
        data[19] = new DataPoint (20, db.reportThreeTwoDao().getErrorsTomato(idCurrentPlayer));
        data[20] = new DataPoint (21, db.reportThreeTwoDao().getErrorsWatermelon(idCurrentPlayer));
        return data;
    }

    public static void setAllErrors(AppDatabase db, ArrayList<String> errorList, int level){
        int currentPlayer = getCurrentPlayer(db);
        int oldErrors = 0;
        for (String error : errorList) {
            if (level == 11) {
                switch (error) {
                    case "banana":
                        oldErrors = db.reportOneOneDao().getErrorsBanana(currentPlayer);
                        db.reportOneOneDao().setErrorsBanana(currentPlayer, oldErrors+1);
                        Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneOneDao().getErrorsBanana(currentPlayer));
                        break;
                    case "lemon":
                        oldErrors = db.reportOneOneDao().getErrorsLemon(currentPlayer);
                        db.reportOneOneDao().setErrorsLemon(currentPlayer, oldErrors+1);
                        Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneOneDao().getErrorsLemon(currentPlayer));
                        break;
                    case "corn":
                        oldErrors = db.reportOneOneDao().getErrorsCorn(currentPlayer);
                        db.reportOneOneDao().setErrorsCorn(currentPlayer, oldErrors+1);
                        Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneOneDao().getErrorsCorn(currentPlayer));
                        break;
                    case "grapefruit":
                        oldErrors = db.reportOneOneDao().getErrorsGrapefruit(currentPlayer);
                        db.reportOneOneDao().setErrorsGrapefruit(currentPlayer, oldErrors+1);
                        Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneOneDao().getErrorsGrapefruit(currentPlayer));
                        break;
                    case "watermelon":
                        oldErrors = db.reportOneOneDao().getErrorsWatermelon(currentPlayer);
                        db.reportOneOneDao().setErrorsWatermelon(currentPlayer, oldErrors+1);
                        Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneOneDao().getErrorsWatermelon(currentPlayer));
                        break;
                    case "strawberry":
                        oldErrors = db.reportOneOneDao().getErrorsStrawberry(currentPlayer);
                        db.reportOneOneDao().setErrorsStrawberry(currentPlayer, oldErrors+1);
                        Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneOneDao().getErrorsStrawberry(currentPlayer));
                        break;
                    case "apple":
                        oldErrors = db.reportOneOneDao().getErrorsApple(currentPlayer);
                        db.reportOneOneDao().setErrorsApple(currentPlayer, oldErrors+1);
                        break;
                    case "pepper":
                        oldErrors = db.reportOneOneDao().getErrorsPepper(currentPlayer);
                        db.reportOneOneDao().setErrorsPepper(currentPlayer, oldErrors+1);
                        break;
                    case "tomato":
                        oldErrors = db.reportOneOneDao().getErrorsTomato(currentPlayer);
                        db.reportOneOneDao().setErrorsTomato(currentPlayer, oldErrors+1);
                        break;
                    case "orange":
                        oldErrors = db.reportOneOneDao().getErrorsOrange(currentPlayer);
                        db.reportOneOneDao().setErrorsOrange(currentPlayer, oldErrors+1);
                        break;
                    case "carrot":
                        oldErrors = db.reportOneOneDao().getErrorsCarrot(currentPlayer);
                        db.reportOneOneDao().setErrorsCarrot(currentPlayer, oldErrors+1);
                        break;
                    case "onion":
                        oldErrors = db.reportOneOneDao().getErrorsOnion(currentPlayer);
                        db.reportOneOneDao().setErrorsOnion(currentPlayer, oldErrors+1);
                        break;
                    case "tangerine":
                        oldErrors = db.reportOneOneDao().getErrorsTangerine(currentPlayer);
                        db.reportOneOneDao().setErrorsTangerine(currentPlayer, oldErrors+1);
                        break;
                    case "eggplant":
                        oldErrors = db.reportOneOneDao().getErrorsEggplant(currentPlayer);
                        db.reportOneOneDao().setErrorsEggplant(currentPlayer, oldErrors+1);
                        break;
                    case "asparagus":
                        oldErrors = db.reportOneOneDao().getErrorsAsparagus(currentPlayer);
                        db.reportOneOneDao().setErrorsAsparagus(currentPlayer, oldErrors+1);
                        break;
                    case "broccoli":
                        oldErrors = db.reportOneOneDao().getErrorsBroccoli(currentPlayer);
                        db.reportOneOneDao().setErrorsBroccoli(currentPlayer, oldErrors+1);
                        break;
                    case "cucumber":
                        oldErrors = db.reportOneOneDao().getErrorsCucumber(currentPlayer);
                        db.reportOneOneDao().setErrorsCucumber(currentPlayer, oldErrors+1);
                        break;
                    case "pear":
                        oldErrors = db.reportOneOneDao().getErrorsPear(currentPlayer);
                        db.reportOneOneDao().setErrorsPear(currentPlayer, oldErrors+1);
                        break;
                    case "greenpea":
                        oldErrors = db.reportOneOneDao().getErrorsGreenpea(currentPlayer);
                        db.reportOneOneDao().setErrorsGreenpea(currentPlayer, oldErrors+1);
                        break;
                    case "fennel":
                        oldErrors = db.reportOneOneDao().getErrorsFennel(currentPlayer);
                        db.reportOneOneDao().setErrorsFennel(currentPlayer, oldErrors+1);
                        break;
                    case "potato":
                        oldErrors = db.reportOneOneDao().getErrorsPotato(currentPlayer);
                        db.reportOneOneDao().setErrorsPotato(currentPlayer, oldErrors+1);
                        break;
                }
            }
            if (level == 12) {
                switch (error) {
                    case "yellow":
                        oldErrors = db.reportOneTwoDao().getErrorsYellow(currentPlayer);
                        db.reportOneTwoDao().setErrorsYellow(currentPlayer, oldErrors+1);
                        break;
                    case "orangec":
                        oldErrors = db.reportOneTwoDao().getErrorsOrangec(currentPlayer);
                        db.reportOneTwoDao().setErrorsOrangec(currentPlayer, oldErrors+1);
                        break;
                    case "violet":
                        oldErrors = db.reportOneTwoDao().getErrorsViolet(currentPlayer);
                        db.reportOneTwoDao().setErrorsViolet(currentPlayer, oldErrors+1);
                        break;
                    case "green":
                        oldErrors = db.reportOneTwoDao().getErrorsGreen(currentPlayer);
                        db.reportOneTwoDao().setErrorsGreen(currentPlayer, oldErrors+1);
                        break;
                    case "white":
                        oldErrors = db.reportOneTwoDao().getErrorsWhite(currentPlayer);
                        db.reportOneTwoDao().setErrorsWhite(currentPlayer, oldErrors+1);
                        break;
                    case "brown":
                        oldErrors = db.reportOneTwoDao().getErrorsBrown(currentPlayer);
                        db.reportOneTwoDao().setErrorsBrown(currentPlayer, oldErrors+1);
                        break;
                    case "red":
                        oldErrors = db.reportOneTwoDao().getErrorsRed(currentPlayer);
                        db.reportOneTwoDao().setErrorsRed(currentPlayer, oldErrors+1);
                        break;
                        default:break;
                }
            }
            if (level == 13) {
                switch (error){
                case "banana":
                    oldErrors = db.reportOneThreeDao().getErrorsBanana(currentPlayer);
                    db.reportOneThreeDao().setErrorsBanana(currentPlayer, oldErrors+1);
                    Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneThreeDao().getErrorsBanana(currentPlayer));
                    break;
                case "lemon":
                    oldErrors = db.reportOneThreeDao().getErrorsLemon(currentPlayer);
                    db.reportOneThreeDao().setErrorsLemon(currentPlayer, oldErrors+1);
                    Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneThreeDao().getErrorsLemon(currentPlayer));
                    break;
                case "corn":
                    oldErrors = db.reportOneThreeDao().getErrorsCorn(currentPlayer);
                    db.reportOneThreeDao().setErrorsCorn(currentPlayer, oldErrors+1);
                    Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneThreeDao().getErrorsCorn(currentPlayer));
                    break;
                case "grapefruit":
                    oldErrors = db.reportOneThreeDao().getErrorsGrapefruit(currentPlayer);
                    db.reportOneThreeDao().setErrorsGrapefruit(currentPlayer, oldErrors+1);
                    Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneThreeDao().getErrorsGrapefruit(currentPlayer));
                    break;
                case "watermelon":
                    oldErrors = db.reportOneThreeDao().getErrorsWatermelon(currentPlayer);
                    db.reportOneThreeDao().setErrorsWatermelon(currentPlayer, oldErrors+1);
                    Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneThreeDao().getErrorsWatermelon(currentPlayer));
                    break;
                case "strawberry":
                    oldErrors = db.reportOneThreeDao().getErrorsStrawberry(currentPlayer);
                    db.reportOneThreeDao().setErrorsStrawberry(currentPlayer, oldErrors+1);
                    Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportOneThreeDao().getErrorsStrawberry(currentPlayer));
                    break;
                case "apple":
                    oldErrors = db.reportOneThreeDao().getErrorsApple(currentPlayer);
                    db.reportOneThreeDao().setErrorsApple(currentPlayer, oldErrors+1);
                    break;
                case "pepper":
                    oldErrors = db.reportOneThreeDao().getErrorsPepper(currentPlayer);
                    db.reportOneThreeDao().setErrorsPepper(currentPlayer, oldErrors+1);
                    break;
                case "tomato":
                    oldErrors = db.reportOneThreeDao().getErrorsTomato(currentPlayer);
                    db.reportOneThreeDao().setErrorsTomato(currentPlayer, oldErrors+1);
                    break;
                case "orange":
                    oldErrors = db.reportOneThreeDao().getErrorsOrange(currentPlayer);
                    db.reportOneThreeDao().setErrorsOrange(currentPlayer, oldErrors+1);
                    break;
                case "carrot":
                    oldErrors = db.reportOneThreeDao().getErrorsCarrot(currentPlayer);
                    db.reportOneThreeDao().setErrorsCarrot(currentPlayer, oldErrors+1);
                    break;
                case "onion":
                    oldErrors = db.reportOneThreeDao().getErrorsOnion(currentPlayer);
                    db.reportOneThreeDao().setErrorsOnion(currentPlayer, oldErrors+1);
                    break;
                case "tangerine":
                    oldErrors = db.reportOneThreeDao().getErrorsTangerine(currentPlayer);
                    db.reportOneThreeDao().setErrorsTangerine(currentPlayer, oldErrors+1);
                    break;
                case "eggplant":
                    oldErrors = db.reportOneThreeDao().getErrorsEggplant(currentPlayer);
                    db.reportOneThreeDao().setErrorsEggplant(currentPlayer, oldErrors+1);
                    break;
                case "asparagus":
                    oldErrors = db.reportOneThreeDao().getErrorsAsparagus(currentPlayer);
                    db.reportOneThreeDao().setErrorsAsparagus(currentPlayer, oldErrors+1);
                    break;
                case "broccoli":
                    oldErrors = db.reportOneThreeDao().getErrorsBroccoli(currentPlayer);
                    db.reportOneThreeDao().setErrorsBroccoli(currentPlayer, oldErrors+1);
                    break;
                case "cucumber":
                    oldErrors = db.reportOneThreeDao().getErrorsCucumber(currentPlayer);
                    db.reportOneThreeDao().setErrorsCucumber(currentPlayer, oldErrors+1);
                    break;
                case "pear":
                    oldErrors = db.reportOneThreeDao().getErrorsPear(currentPlayer);
                    db.reportOneThreeDao().setErrorsPear(currentPlayer, oldErrors+1);
                    break;
                case "greenpea":
                    oldErrors = db.reportOneThreeDao().getErrorsGreenpea(currentPlayer);
                    db.reportOneThreeDao().setErrorsGreenpea(currentPlayer, oldErrors+1);
                    break;
                case "fennel":
                    oldErrors = db.reportOneThreeDao().getErrorsFennel(currentPlayer);
                    db.reportOneThreeDao().setErrorsFennel(currentPlayer, oldErrors+1);
                    break;
                case "potato":
                    oldErrors = db.reportOneThreeDao().getErrorsPotato(currentPlayer);
                    db.reportOneThreeDao().setErrorsPotato(currentPlayer, oldErrors+1);
                    break;
                    default:   break;
            }
            }
            if (level == 21) {
                switch (error) {
                    case "_0":
                        oldErrors = db.reportTwoOneDao().getErrorsZero(currentPlayer);
                        db.reportTwoOneDao().setErrorsZero(currentPlayer, oldErrors+1);
                        break;
                    case "_1":
                        oldErrors = db.reportTwoOneDao().getErrorsOne(currentPlayer);
                        db.reportTwoOneDao().setErrorsOne(currentPlayer, oldErrors+1);
                        break;
                    case "_2":
                        oldErrors = db.reportTwoOneDao().getErrorsTwo(currentPlayer);
                        db.reportTwoOneDao().setErrorsTwo(currentPlayer, oldErrors+1);
                        break;
                    case "_3":
                        oldErrors = db.reportTwoOneDao().getErrorsThree(currentPlayer);
                        db.reportTwoOneDao().setErrorsThree(currentPlayer, oldErrors+1);
                        break;
                    case "_4":
                        oldErrors = db.reportTwoOneDao().getErrorsFour(currentPlayer);
                        db.reportTwoOneDao().setErrorsFour(currentPlayer, oldErrors+1);
                        break;
                    case "_5":
                        oldErrors = db.reportTwoOneDao().getErrorsFive(currentPlayer);
                        db.reportTwoOneDao().setErrorsFive(currentPlayer, oldErrors+1);
                        break;
                    case "_6":
                        oldErrors = db.reportTwoOneDao().getErrorsSix(currentPlayer);
                        db.reportTwoOneDao().setErrorsSix(currentPlayer, oldErrors+1);
                        break;
                    case "_7":
                        oldErrors = db.reportTwoOneDao().getErrorsSeven(currentPlayer);
                        db.reportTwoOneDao().setErrorsSeven(currentPlayer, oldErrors+1);
                        break;
                    case "_8":
                        oldErrors = db.reportTwoOneDao().getErrorsEight(currentPlayer);
                        db.reportTwoOneDao().setErrorsEight(currentPlayer, oldErrors+1);
                        break;
                    case "_9":
                        oldErrors = db.reportTwoOneDao().getErrorsNine(currentPlayer);
                        db.reportTwoOneDao().setErrorsNine(currentPlayer, oldErrors+1);
                        break;

                    default: break;
                }
            }
            if (level == 22) {
                switch (error) {
                    case "a":
                        oldErrors = db.reportTwoTwoDao().getErrorsA(currentPlayer);
                        db.reportTwoTwoDao().setErrorsA(currentPlayer, oldErrors+1);
                        break;
                    case "b":
                        oldErrors = db.reportTwoTwoDao().getErrorsB(currentPlayer);
                        db.reportTwoTwoDao().setErrorsB(currentPlayer, oldErrors+1);
                        break;
                    case "c":
                        oldErrors = db.reportTwoTwoDao().getErrorsC(currentPlayer);
                        db.reportTwoTwoDao().setErrorsC(currentPlayer, oldErrors+1);
                        break;
                    case "d":
                        oldErrors = db.reportTwoTwoDao().getErrorsD(currentPlayer);
                        db.reportTwoTwoDao().setErrorsD(currentPlayer, oldErrors+1);
                        break;
                    case "e":
                        oldErrors = db.reportTwoTwoDao().getErrorsE(currentPlayer);
                        db.reportTwoTwoDao().setErrorsE(currentPlayer, oldErrors+1);
                        break;
                    case "f":
                        oldErrors = db.reportTwoTwoDao().getErrorsF(currentPlayer);
                        db.reportTwoTwoDao().setErrorsF(currentPlayer, oldErrors+1);
                        break;
                    case "g":
                        oldErrors = db.reportTwoTwoDao().getErrorsG(currentPlayer);
                        db.reportTwoTwoDao().setErrorsG(currentPlayer, oldErrors+1);
                        break;
                    case "h":
                        oldErrors = db.reportTwoTwoDao().getErrorsH(currentPlayer);
                        db.reportTwoTwoDao().setErrorsH(currentPlayer, oldErrors+1);
                        break;
                    case "i":
                        oldErrors = db.reportTwoTwoDao().getErrorsI(currentPlayer);
                        db.reportTwoTwoDao().setErrorsI(currentPlayer, oldErrors+1);
                        break;
                    case "l":
                        oldErrors = db.reportTwoTwoDao().getErrorsL(currentPlayer);
                        db.reportTwoTwoDao().setErrorsL(currentPlayer, oldErrors+1);
                        break;
                    case "m":
                        oldErrors = db.reportTwoTwoDao().getErrorsM(currentPlayer);
                        db.reportTwoTwoDao().setErrorsM(currentPlayer, oldErrors+1);
                        break;
                    case "n":
                        oldErrors = db.reportTwoTwoDao().getErrorsN(currentPlayer);
                        db.reportTwoTwoDao().setErrorsN(currentPlayer, oldErrors+1);
                        break;
                    case "o":
                        oldErrors = db.reportTwoTwoDao().getErrorsO(currentPlayer);
                        db.reportTwoTwoDao().setErrorsO(currentPlayer, oldErrors+1);
                        break;
                    case "p":
                        oldErrors = db.reportTwoTwoDao().getErrorsP(currentPlayer);
                        db.reportTwoTwoDao().setErrorsP(currentPlayer, oldErrors+1);
                        break;
                    case "q":
                        oldErrors = db.reportTwoTwoDao().getErrorsQ(currentPlayer);
                        db.reportTwoTwoDao().setErrorsQ(currentPlayer, oldErrors+1);
                        break;
                    case "r":
                        oldErrors = db.reportTwoTwoDao().getErrorsR(currentPlayer);
                        db.reportTwoTwoDao().setErrorsR(currentPlayer, oldErrors+1);
                        break;
                    case "s":
                        oldErrors = db.reportTwoTwoDao().getErrorsS(currentPlayer);
                        db.reportTwoTwoDao().setErrorsS(currentPlayer, oldErrors+1);
                        break;
                    case "t":
                        oldErrors = db.reportTwoTwoDao().getErrorsT(currentPlayer);
                        db.reportTwoTwoDao().setErrorsT(currentPlayer, oldErrors+1);
                        break;
                    case "u":
                        oldErrors = db.reportTwoTwoDao().getErrorsU(currentPlayer);
                        db.reportTwoTwoDao().setErrorsU(currentPlayer, oldErrors+1);
                        break;
                    case "v":
                        oldErrors = db.reportTwoTwoDao().getErrorsV(currentPlayer);
                        db.reportTwoTwoDao().setErrorsV(currentPlayer, oldErrors+1);
                        break;
                    case "z":
                        oldErrors = db.reportTwoTwoDao().getErrorsZ(currentPlayer);
                        db.reportTwoTwoDao().setErrorsZ(currentPlayer, oldErrors+1);
                        break;
                        default:break;
                }
            }
            if (level == 23) {
                switch (error) {
                    case "a":
                        oldErrors = db.reportTwoThreeDao().getErrorsA(currentPlayer);
                        db.reportTwoThreeDao().setErrorsA(currentPlayer, oldErrors+1);
                        break;
                    case "b":
                        oldErrors = db.reportTwoThreeDao().getErrorsB(currentPlayer);
                        db.reportTwoThreeDao().setErrorsB(currentPlayer, oldErrors+1);
                        break;
                    case "c":
                        oldErrors = db.reportTwoThreeDao().getErrorsC(currentPlayer);
                        db.reportTwoThreeDao().setErrorsC(currentPlayer, oldErrors+1);
                        break;
                    case "d":
                        oldErrors = db.reportTwoThreeDao().getErrorsD(currentPlayer);
                        db.reportTwoThreeDao().setErrorsD(currentPlayer, oldErrors+1);
                        break;
                    case "e":
                        oldErrors = db.reportTwoThreeDao().getErrorsE(currentPlayer);
                        db.reportTwoThreeDao().setErrorsE(currentPlayer, oldErrors+1);
                        break;
                    case "f":
                        oldErrors = db.reportTwoThreeDao().getErrorsF(currentPlayer);
                        db.reportTwoThreeDao().setErrorsF(currentPlayer, oldErrors+1);
                        break;
                    case "g":
                        oldErrors = db.reportTwoThreeDao().getErrorsG(currentPlayer);
                        db.reportTwoThreeDao().setErrorsG(currentPlayer, oldErrors+1);
                        break;
                    case "h":
                        oldErrors = db.reportTwoThreeDao().getErrorsH(currentPlayer);
                        db.reportTwoThreeDao().setErrorsH(currentPlayer, oldErrors+1);
                        break;
                    case "i":
                        oldErrors = db.reportTwoThreeDao().getErrorsI(currentPlayer);
                        db.reportTwoThreeDao().setErrorsI(currentPlayer, oldErrors+1);
                        break;
                    case "l":
                        oldErrors = db.reportTwoThreeDao().getErrorsL(currentPlayer);
                        db.reportTwoThreeDao().setErrorsL(currentPlayer, oldErrors+1);
                        break;
                    case "m":
                        oldErrors = db.reportTwoThreeDao().getErrorsM(currentPlayer);
                        db.reportTwoThreeDao().setErrorsM(currentPlayer, oldErrors+1);
                        break;
                    case "n":
                        oldErrors = db.reportTwoThreeDao().getErrorsN(currentPlayer);
                        db.reportTwoThreeDao().setErrorsN(currentPlayer, oldErrors+1);
                        break;
                    case "o":
                        oldErrors = db.reportTwoThreeDao().getErrorsO(currentPlayer);
                        db.reportTwoThreeDao().setErrorsO(currentPlayer, oldErrors+1);
                        break;
                    case "p":
                        oldErrors = db.reportTwoThreeDao().getErrorsP(currentPlayer);
                        db.reportTwoThreeDao().setErrorsP(currentPlayer, oldErrors+1);
                        break;
                    case "q":
                        oldErrors = db.reportTwoThreeDao().getErrorsQ(currentPlayer);
                        db.reportTwoThreeDao().setErrorsQ(currentPlayer, oldErrors+1);
                        break;
                    case "r":
                        oldErrors = db.reportTwoThreeDao().getErrorsR(currentPlayer);
                        db.reportTwoThreeDao().setErrorsR(currentPlayer, oldErrors+1);
                        break;
                    case "s":
                        oldErrors = db.reportTwoThreeDao().getErrorsS(currentPlayer);
                        db.reportTwoThreeDao().setErrorsS(currentPlayer, oldErrors+1);
                        break;
                    case "t":
                        oldErrors = db.reportTwoThreeDao().getErrorsT(currentPlayer);
                        db.reportTwoThreeDao().setErrorsT(currentPlayer, oldErrors+1);
                        break;
                    case "u":
                        oldErrors = db.reportTwoThreeDao().getErrorsU(currentPlayer);
                        db.reportTwoThreeDao().setErrorsU(currentPlayer, oldErrors+1);
                        break;
                    case "v":
                        oldErrors = db.reportTwoThreeDao().getErrorsV(currentPlayer);
                        db.reportTwoThreeDao().setErrorsV(currentPlayer, oldErrors+1);
                        break;
                    case "z":
                        oldErrors = db.reportTwoThreeDao().getErrorsZ(currentPlayer);
                        db.reportTwoThreeDao().setErrorsZ(currentPlayer, oldErrors+1);
                        break;
                        default:break;
                }
            }
            if (level == 31) {
                switch (error) {
                    case "_0":
                        oldErrors = db.reportThreeOneDao().getErrorsZero(currentPlayer);
                        db.reportThreeOneDao().setErrorsZero(currentPlayer, oldErrors+1);
                        break;
                    case "_1":
                        oldErrors = db.reportThreeOneDao().getErrorsOne(currentPlayer);
                        db.reportThreeOneDao().setErrorsOne(currentPlayer, oldErrors+1);
                        break;
                    case "_2":
                        oldErrors = db.reportThreeOneDao().getErrorsTwo(currentPlayer);
                        db.reportThreeOneDao().setErrorsTwo(currentPlayer, oldErrors+1);
                        break;
                    case "_3":
                        oldErrors = db.reportThreeOneDao().getErrorsThree(currentPlayer);
                        db.reportThreeOneDao().setErrorsThree(currentPlayer, oldErrors+1);
                        break;
                    case "_4":
                        oldErrors = db.reportThreeOneDao().getErrorsFour(currentPlayer);
                        db.reportThreeOneDao().setErrorsFour(currentPlayer, oldErrors+1);
                        break;
                    case "_5":
                        oldErrors = db.reportThreeOneDao().getErrorsFive(currentPlayer);
                        db.reportThreeOneDao().setErrorsFive(currentPlayer, oldErrors+1);
                        break;
                    case "_6":
                        oldErrors = db.reportThreeOneDao().getErrorsSix(currentPlayer);
                        db.reportThreeOneDao().setErrorsSix(currentPlayer, oldErrors+1);
                        break;
                    case "_7":
                        oldErrors = db.reportThreeOneDao().getErrorsSeven(currentPlayer);
                        db.reportThreeOneDao().setErrorsSeven(currentPlayer, oldErrors+1);
                        break;
                    case "_8":
                        oldErrors = db.reportThreeOneDao().getErrorsEight(currentPlayer);
                        db.reportThreeOneDao().setErrorsEight(currentPlayer, oldErrors+1);
                        break;
                    case "_9":
                        oldErrors = db.reportThreeOneDao().getErrorsNine(currentPlayer);
                        db.reportThreeOneDao().setErrorsNine(currentPlayer, oldErrors+1);
                        break;
                        default:break;
                }
                if (level == 32) {
                    switch (error){
                        case "banana":
                            oldErrors = db.reportThreeTwoDao().getErrorsBanana(currentPlayer);
                            db.reportThreeTwoDao().setErrorsBanana(currentPlayer, oldErrors+1);
                            Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportThreeTwoDao().getErrorsBanana(currentPlayer));
                            break;
                        case "lemon":
                            oldErrors = db.reportThreeTwoDao().getErrorsLemon(currentPlayer);
                            db.reportThreeTwoDao().setErrorsLemon(currentPlayer, oldErrors+1);
                            Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportThreeTwoDao().getErrorsLemon(currentPlayer));
                            break;
                        case "corn":
                            oldErrors = db.reportThreeTwoDao().getErrorsCorn(currentPlayer);
                            db.reportThreeTwoDao().setErrorsCorn(currentPlayer, oldErrors+1);
                            Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportThreeTwoDao().getErrorsCorn(currentPlayer));
                            break;
                        case "grapefruit":
                            oldErrors = db.reportThreeTwoDao().getErrorsGrapefruit(currentPlayer);
                            db.reportThreeTwoDao().setErrorsGrapefruit(currentPlayer, oldErrors+1);
                            Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportThreeTwoDao().getErrorsGrapefruit(currentPlayer));
                            break;
                        case "watermelon":
                            oldErrors = db.reportThreeTwoDao().getErrorsWatermelon(currentPlayer);
                            db.reportThreeTwoDao().setErrorsWatermelon(currentPlayer, oldErrors+1);
                            Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportThreeTwoDao().getErrorsWatermelon(currentPlayer));
                            break;
                        case "strawberry":
                            oldErrors = db.reportThreeTwoDao().getErrorsStrawberry(currentPlayer);
                            db.reportThreeTwoDao().setErrorsStrawberry(currentPlayer, oldErrors+1);
                            Log.i("[DB INITIALIZER]", "Storing a new error: " + error + " "+ db.reportThreeTwoDao().getErrorsStrawberry(currentPlayer));
                            break;
                        case "apple":
                            oldErrors = db.reportThreeTwoDao().getErrorsApple(currentPlayer);
                            db.reportThreeTwoDao().setErrorsApple(currentPlayer, oldErrors+1);
                            break;
                        case "pepper":
                            oldErrors = db.reportThreeTwoDao().getErrorsPepper(currentPlayer);
                            db.reportThreeTwoDao().setErrorsPepper(currentPlayer, oldErrors+1);
                            break;
                        case "tomato":
                            oldErrors = db.reportThreeTwoDao().getErrorsTomato(currentPlayer);
                            db.reportThreeTwoDao().setErrorsTomato(currentPlayer, oldErrors+1);
                            break;
                        case "orange":
                            oldErrors = db.reportThreeTwoDao().getErrorsOrange(currentPlayer);
                            db.reportThreeTwoDao().setErrorsOrange(currentPlayer, oldErrors+1);
                            break;
                        case "carrot":
                            oldErrors = db.reportThreeTwoDao().getErrorsCarrot(currentPlayer);
                            db.reportThreeTwoDao().setErrorsCarrot(currentPlayer, oldErrors+1);
                            break;
                        case "onion":
                            oldErrors = db.reportThreeTwoDao().getErrorsOnion(currentPlayer);
                            db.reportThreeTwoDao().setErrorsOnion(currentPlayer, oldErrors+1);
                            break;
                        case "tangerine":
                            oldErrors = db.reportThreeTwoDao().getErrorsTangerine(currentPlayer);
                            db.reportThreeTwoDao().setErrorsTangerine(currentPlayer, oldErrors+1);
                            break;
                        case "eggplant":
                            oldErrors = db.reportThreeTwoDao().getErrorsEggplant(currentPlayer);
                            db.reportThreeTwoDao().setErrorsEggplant(currentPlayer, oldErrors+1);
                            break;
                        case "asparagus":
                            oldErrors = db.reportThreeTwoDao().getErrorsAsparagus(currentPlayer);
                            db.reportThreeTwoDao().setErrorsAsparagus(currentPlayer, oldErrors+1);
                            break;
                        case "broccoli":
                            oldErrors = db.reportThreeTwoDao().getErrorsBroccoli(currentPlayer);
                            db.reportThreeTwoDao().setErrorsBroccoli(currentPlayer, oldErrors+1);
                            break;
                        case "cucumber":
                            oldErrors = db.reportThreeTwoDao().getErrorsCucumber(currentPlayer);
                            db.reportThreeTwoDao().setErrorsCucumber(currentPlayer, oldErrors+1);
                            break;
                        case "pear":
                            oldErrors = db.reportThreeTwoDao().getErrorsPear(currentPlayer);
                            db.reportThreeTwoDao().setErrorsPear(currentPlayer, oldErrors+1);
                            break;
                        case "greenpea":
                            oldErrors = db.reportThreeTwoDao().getErrorsGreenpea(currentPlayer);
                            db.reportThreeTwoDao().setErrorsGreenpea(currentPlayer, oldErrors+1);
                            break;
                        case "fennel":
                            oldErrors = db.reportThreeTwoDao().getErrorsFennel(currentPlayer);
                            db.reportThreeTwoDao().setErrorsFennel(currentPlayer, oldErrors+1);
                            break;
                        case "potato":
                            oldErrors = db.reportThreeTwoDao().getErrorsPotato(currentPlayer);
                            db.reportThreeTwoDao().setErrorsPotato(currentPlayer, oldErrors+1);
                            break;
                            default:break;
                    }
                }
            }
            }
        }
    }
