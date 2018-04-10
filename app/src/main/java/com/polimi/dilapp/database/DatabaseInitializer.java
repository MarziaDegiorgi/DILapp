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
        /*ReportThreeOneEntity reportThreeOneEntity = new ReportThreeOneEntity(id);
        db.reportThreeOneDao().insertReport(reportThreeOneEntity);
        ReportThreeTwoEntity reportThreeTwoEntity = new ReportThreeTwoEntity(id);
        db.reportThreeTwoDao().insertReport(reportThreeTwoEntity);
        */
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
           options.inSampleSize = calculateInSampleSize(options, 400, 400);
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
    public static void setTime(AppDatabase db, int id, ArrayList<Integer> newTime){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < newTime.size(); i++){
            String s = Integer.toString(newTime.get(i));
            sb.append(s);
            sb.append(",");
        }
        db.childDao().setTime(id, sb.toString());
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

    public static void setAllErrorsOneOne(AppDatabase db, ArrayList<String> errorList, int level){
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
            //TODO: add getter as for level 11
            if (level == 12) {
                switch (error) {
                    case "yellow":
                        db.reportOneTwoDao().setErrorsYellow(currentPlayer, oldErrors+1);
                    case "orangec":
                        db.reportOneTwoDao().setErrorsOrangec(currentPlayer, oldErrors+1);
                    case "violet":
                        db.reportOneTwoDao().setErrorsViolet(currentPlayer, oldErrors+1);
                    case "green":
                        db.reportOneTwoDao().setErrorsGreen(currentPlayer, oldErrors+1);
                    case "white":
                        db.reportOneTwoDao().setErrorsWhite(currentPlayer, oldErrors+1);
                    case "brown":
                        db.reportOneTwoDao().setErrorsBrown(currentPlayer, oldErrors+1);
                }
            }
            if (level == 13) {
                switch (error) {
                    case "banana":
                        db.reportOneThreeDao().setErrorsBanana(currentPlayer, oldErrors+1);
                        break;
                    case "lemon":
                        db.reportOneThreeDao().setErrorsLemon(currentPlayer, oldErrors+1);
                        break;
                    case "corn":
                        db.reportOneThreeDao().setErrorsCorn(currentPlayer, oldErrors+1);
                        break;
                    case "grapefruit":
                        db.reportOneThreeDao().setErrorsGrapefruit(currentPlayer, oldErrors+1);
                        break;
                    case "watermelon":
                        db.reportOneThreeDao().setErrorsWatermelon(currentPlayer, oldErrors+1);
                        break;
                    case "strawberry":
                        db.reportOneThreeDao().setErrorsStrawberry(currentPlayer, oldErrors+1);
                        break;
                    case "apple":
                        db.reportOneThreeDao().setErrorsApple(currentPlayer, oldErrors+1);
                        break;
                    case "pepper":
                        db.reportOneThreeDao().setErrorsPepper(currentPlayer, oldErrors+1);
                        break;
                    case "tomato":
                        db.reportOneThreeDao().setErrorsTomato(currentPlayer, oldErrors+1);
                        break;
                    case "orange":
                        db.reportOneThreeDao().setErrorsOrange(currentPlayer, oldErrors+1);
                        break;
                    case "carrot":
                        db.reportOneThreeDao().setErrorsCarrot(currentPlayer, oldErrors+1);
                        break;
                    case "onion":
                        db.reportOneThreeDao().setErrorsOnion(currentPlayer, oldErrors+1);
                        break;
                    case "tangerine":
                        db.reportOneThreeDao().setErrorsTangerine(currentPlayer, oldErrors+1);
                        break;
                    case "eggplant":
                        db.reportOneThreeDao().setErrorsEggplant(currentPlayer, oldErrors+1);
                        break;
                    case "asparagus":
                        db.reportOneThreeDao().setErrorsAsparagus(currentPlayer, oldErrors+1);
                        break;
                    case "broccoli":
                        db.reportOneThreeDao().setErrorsBroccoli(currentPlayer, oldErrors+1);
                        break;
                    case "cucumber":
                        db.reportOneThreeDao().setErrorsCucumber(currentPlayer, oldErrors+1);
                        break;
                    case "pear":
                        db.reportOneThreeDao().setErrorsPear(currentPlayer, oldErrors+1);
                        break;
                    case "greenpea":
                        db.reportOneThreeDao().setErrorsGreenpea(currentPlayer, oldErrors+1);
                        break;
                    case "fennel":
                        db.reportOneThreeDao().setErrorsFennel(currentPlayer, oldErrors+1);
                        break;
                    case "potato":
                        db.reportOneThreeDao().setErrorsPotato(currentPlayer, oldErrors+1);
                        break;
                }
            }
            if (level == 21) {
                switch (error) {
                    case "_0":
                        db.reportTwoOneDao().setErrorsZero(currentPlayer, oldErrors+1);
                    case "_1":
                        db.reportTwoOneDao().setErrorsOne(currentPlayer, oldErrors+1);
                    case "_2":
                        db.reportTwoOneDao().setErrorsTwo(currentPlayer, oldErrors+1);
                    case "_3":
                        db.reportTwoOneDao().setErrorsThree(currentPlayer, oldErrors+1);
                    case "_4":
                        db.reportTwoOneDao().setErrorsFour(currentPlayer, oldErrors+1);
                    case "_5":
                        db.reportTwoOneDao().setErrorsFive(currentPlayer, oldErrors+1);
                    case "_6":
                        db.reportTwoOneDao().setErrorsSix(currentPlayer, oldErrors+1);
                    case "_7":
                        db.reportTwoOneDao().setErrorsSeven(currentPlayer, oldErrors+1);
                    case "_8":
                        db.reportTwoOneDao().setErrorsEight(currentPlayer, oldErrors+1);
                    case "_9":
                        db.reportTwoOneDao().setErrorsNine(currentPlayer, oldErrors+1);
                }
            }
            if (level == 22) {
                switch (error) {
                    case "a":
                        db.reportTwoTwoDao().setErrorsA(currentPlayer, oldErrors+1);
                        break;
                    case "b":
                        db.reportTwoTwoDao().setErrorsB(currentPlayer, oldErrors+1);
                        break;
                    case "c":
                        db.reportTwoTwoDao().setErrorsC(currentPlayer, oldErrors+1);
                        break;
                    case "d":
                        db.reportTwoTwoDao().setErrorsD(currentPlayer, oldErrors+1);
                        break;
                    case "e":
                        db.reportTwoTwoDao().setErrorsE(currentPlayer, oldErrors+1);
                        break;
                    case "f":
                        db.reportTwoTwoDao().setErrorsF(currentPlayer, oldErrors+1);
                        break;
                    case "g":
                        db.reportTwoTwoDao().setErrorsG(currentPlayer, oldErrors+1);
                        break;
                    case "h":
                        db.reportTwoTwoDao().setErrorsH(currentPlayer, oldErrors+1);
                        break;
                    case "i":
                        db.reportTwoTwoDao().setErrorsI(currentPlayer, oldErrors+1);
                        break;
                    case "l":
                        db.reportTwoTwoDao().setErrorsL(currentPlayer, oldErrors+1);
                        break;
                    case "m":
                        db.reportTwoTwoDao().setErrorsM(currentPlayer, oldErrors+1);
                        break;
                    case "n":
                        db.reportTwoTwoDao().setErrorsN(currentPlayer, oldErrors+1);
                        break;
                    case "o":
                        db.reportTwoTwoDao().setErrorsO(currentPlayer, oldErrors+1);
                        break;
                    case "p":
                        db.reportTwoTwoDao().setErrorsP(currentPlayer, oldErrors+1);
                        break;
                    case "q":
                        db.reportTwoTwoDao().setErrorsQ(currentPlayer, oldErrors+1);
                        break;
                    case "r":
                        db.reportTwoTwoDao().setErrorsR(currentPlayer, oldErrors+1);
                        break;
                    case "s":
                        db.reportTwoTwoDao().setErrorsS(currentPlayer, oldErrors+1);
                        break;
                    case "t":
                        db.reportTwoTwoDao().setErrorsT(currentPlayer, oldErrors+1);
                        break;
                    case "u":
                        db.reportTwoTwoDao().setErrorsU(currentPlayer, oldErrors+1);
                        break;
                    case "v":
                        db.reportTwoTwoDao().setErrorsV(currentPlayer, oldErrors+1);
                        break;
                    case "z":
                        db.reportTwoTwoDao().setErrorsZ(currentPlayer, oldErrors+1);
                        break;
                }
            }
            if (level == 23) {
                switch (error) {
                    case "a":
                        db.reportTwoThreeDao().setErrorsA(currentPlayer, oldErrors+1);
                        break;
                    case "b":
                        db.reportTwoThreeDao().setErrorsB(currentPlayer, oldErrors+1);
                        break;
                    case "c":
                        db.reportTwoThreeDao().setErrorsC(currentPlayer, oldErrors+1);
                        break;
                    case "d":
                        db.reportTwoThreeDao().setErrorsD(currentPlayer, oldErrors+1);
                        break;
                    case "e":
                        db.reportTwoThreeDao().setErrorsE(currentPlayer, oldErrors+1);
                        break;
                    case "f":
                        db.reportTwoThreeDao().setErrorsF(currentPlayer, oldErrors+1);
                        break;
                    case "g":
                        db.reportTwoThreeDao().setErrorsG(currentPlayer, oldErrors+1);
                        break;
                    case "h":
                        db.reportTwoThreeDao().setErrorsH(currentPlayer, oldErrors+1);
                        break;
                    case "i":
                        db.reportTwoThreeDao().setErrorsI(currentPlayer, oldErrors+1);
                        break;
                    case "l":
                        db.reportTwoThreeDao().setErrorsL(currentPlayer, oldErrors+1);
                        break;
                    case "m":
                        db.reportTwoThreeDao().setErrorsM(currentPlayer, oldErrors+1);
                        break;
                    case "n":
                        db.reportTwoThreeDao().setErrorsN(currentPlayer, oldErrors+1);
                        break;
                    case "o":
                        db.reportTwoThreeDao().setErrorsO(currentPlayer, oldErrors+1);
                        break;
                    case "p":
                        db.reportTwoThreeDao().setErrorsP(currentPlayer, oldErrors+1);
                        break;
                    case "q":
                        db.reportTwoThreeDao().setErrorsQ(currentPlayer, oldErrors+1);
                        break;
                    case "r":
                        db.reportTwoThreeDao().setErrorsR(currentPlayer, oldErrors+1);
                        break;
                    case "s":
                        db.reportTwoThreeDao().setErrorsS(currentPlayer, oldErrors+1);
                        break;
                    case "t":
                        db.reportTwoThreeDao().setErrorsT(currentPlayer, oldErrors+1);
                        break;
                    case "u":
                        db.reportTwoThreeDao().setErrorsU(currentPlayer, oldErrors+1);
                        break;
                    case "v":
                        db.reportTwoThreeDao().setErrorsV(currentPlayer, oldErrors+1);
                        break;
                    case "z":
                        db.reportTwoThreeDao().setErrorsZ(currentPlayer, oldErrors+1);
                        break;
                }
                //TODO: add remaining levels
            } else {
                switch (error) {
                    default:
                        break;
                }
            }
        }
    }
}
