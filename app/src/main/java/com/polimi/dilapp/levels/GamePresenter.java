package com.polimi.dilapp.levels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class GamePresenter implements IGame.Presenter {

    private final String CLASS = "[GamePresenter]";

    private int correctAnswers=0;
    private int totalAttempts=0;
    private int counter = 0;
    private int counterColourSession = 0;

    Handler myHandler;

    private NfcAdapter nfcAdapter;
    private List<String> currentSequence;
    private String currentElement;
    private String currentSubElement;
    private int subElementIndex ;

    private static final String MIME_TEXT_PLAIN = "text/plain";
    private List<String> tempArray;

    private int initTime;
    private int endTime;
    private int totaltime;
    //TODO set adjustments: they are the approximated lenght in seconds of the videos of the activity
    private int adjustment = 0;
    private IGame.View activityInterface;
    private String currentSequenceElement;
    private boolean multipleElement = false;
    private int numberOfElements;
    private AppDatabase db;
    private String currentReadTag;

    private boolean gameStarted;
    private boolean newSessionStarted;
    private boolean newTurnStarted;
    private boolean gameEnded;
    private boolean colourLevel;
    private boolean actionDetected;
    private boolean enableNFC;
    private boolean recipeLevel;

   public GamePresenter(IGame.View view){

       this.activityInterface = view;
       subElementIndex = 1;
       Log.i("Activity interface", String.valueOf(activityInterface));
       this.multipleElement = false;
       this.numberOfElements = 1;
       recipeLevel = false;
       gameStarted = false;
       newSessionStarted = false;
       actionDetected = false;
       newTurnStarted = false;
       gameEnded = false;
       enableNFC = false;
       colourLevel = false;
       myHandler = new Handler();
       db = AppDatabase.getAppDatabase(activityInterface.getScreenContext());
   }

   @Override
    public void startGame(List<String> sequence){
       String subString = DatabaseInitializer.getSubStringCurrentPlayer(db);
       //current system time in seconds
        setLevelCurrentPlayer();
        Toast.makeText(activityInterface.getScreenContext(), "Level current player: "+DatabaseInitializer.getLevelCurrentPlayer(db), Toast.LENGTH_LONG).show();
        initTime = (int) (SystemClock.elapsedRealtime()/1000);
        Log.i("[INIT_TIME]:", String.valueOf(initTime));
        currentSequence = sequence;
        gameStarted = true;
        if(currentSequence.isEmpty()){
            Log.i(CLASS, "empty current sequence.");
            gameStarted = false;
        } else {
            if (subString != null) {
                int index = currentSequence.indexOf(subString);
                if (index > 0) {
                    for (int i = index-1; i >= 0; i--) {
                        currentSequence.remove(i);
                    }
                }
                DatabaseInitializer.setSubStringCurrentPlayer(db, null);
            }
            currentSequenceElement = currentSequence.get(0);
            currentSequence.remove(0);
            startNewSession(currentSequenceElement);

        }
    }

    //NEXT ELEMENT IN THE ARRAY
    private void startNewTurn(){
        if(currentSequence.isEmpty()){
            //ActivityOneTwo ends
            gameEnded = true;
            newTurnStarted = false;
            colourLevel = false;
            endTime = (int)(SystemClock.elapsedRealtime()/1000);
            Log.i("[INIT_TIME]", String.valueOf(endTime));
            setTimeParameter();
            //only for debug
            String i = String.valueOf(totaltime);
            Log.i("Total time:", i);
            //TODO UPDATE COUNTERS and TOTAL TIME IN DB
            Log.i("[Game Presenter]:", "Activity Ended.");
            int diff = totalAttempts - correctAnswers;
            int percentage = (20*totalAttempts)/100;
            if(diff > percentage){
                //repeat Activity or go back to the main menu
                activityInterface.setRepeatOrExitScreen();
            }else{
                //unlock next Activity or exit or go back to the main menu
                activityInterface.setGoOnOrExitScreen();
            }

        } else {
            Log.i("[Game Presenter]:", "new Turn started with a new sequence element and new session array.");
            newTurnStarted = true;
            currentSequenceElement = currentSequence.get(0);
            currentSequence.remove(0);
            startNewSession(currentSequenceElement);
        }
    }

    //NEXT ARRAY IN THE SEQUENCE
    private void startNewSession(String currentSequenceElement){
        int vectorID = getResourceId(currentSequenceElement +"_items", R.array.class);
        int presentationVideo = getResourceId("video_set_of_" + currentSequenceElement + "_items", R.raw.class);
        activityInterface.setVideoView(presentationVideo);
        tempArray = activityInterface.getSessionArray(vectorID);
        newSessionStarted = true;
        Log.i(CLASS, "Starting a new session" + tempArray.toString());
        //this set the video of the session: example yellow colors video.
    }


    public void chooseElement(){
        String object = DatabaseInitializer.getObjectCurrentPlayer(db);
        subElementIndex = 1;
        if(colourLevel){
            chooseColour();
        } else {
            if (recipeLevel) {
            chooseRecipe();
            } else {
                newTurnStarted = false;
                if (tempArray.isEmpty()) {
                    Log.i(CLASS, "Array is Empty -> Starting a new Turn");
                    startNewTurn();
                } else {
                    if (object != null) {
                        int index = tempArray.indexOf(object);
                        if (index > 0) {
                            for (int i = index - 1; i >= 0; i--) {
                                tempArray.remove(i);
                            }
                        }
                        DatabaseInitializer.setObjectCurrentPlayer(db, null);
                    }
                    currentElement = tempArray.get(0);
                    tempArray.remove(0);
                    Log.i(CLASS, "Choose next element -> " + currentElement);
                    this.checkMultipleItems();
                    askCurrentElement();
                }
            }
        }
    }

    public void askCurrentElement(){
        Log.i(CLASS, "Ask View to set Animation -> " + currentElement );
        activityInterface.setPresentationAnimation(currentElement);
    }

    /**
     * Check the correctness of the nfc intent comparing it with the current string element that can be
     * a single item such as an object or a multiple item composed by multiple object (ex. words that are composed by letters).
     *  Multiple item are characterized by this form: "_home" that is composed by h, o, m, e.
     * @param readTag of the NFC got as intent
     */
    private void checkAnswer(String readTag) {
        enableNFC = false;
        currentReadTag = readTag;
        if (colourLevel) {
            if(tempArray.contains(readTag)){
                Log.i(CLASS, "[CheckAnswer][ColourItem][Correct] " + readTag);
                this.correctAnswerColour();
            } else {
                Log.i(CLASS, "[CheckAnswer][ColourItem][Wrong] " + readTag + ", current element: " + currentElement);
                this.wrongAnswerColour();
            }

        } else {

            if (recipeLevel) {
                if(tempArray.contains(readTag)){
                    tempArray.remove(readTag);
                    Log.i(CLASS, "[CheckAnswer][RecipeItem][Correct] " + readTag);
                    this.correctAnswerRecipe();
                } else {
                    Log.i(CLASS, "[CheckAnswer][RecipeItem][Wrong] " + readTag + ", current element: " + currentElement);
                    this.wrongAnswerRecipe();
                }
            } else {
                if (!multipleElement) {
                    if (currentElement.contains("_")) {
                        check("_" + readTag);
                    } else {
                        check(readTag);
                    }
                } else {
                    if (numberOfElements > 1) {
                        // Correct answer
                        if (readTag.equals(currentSubElement)) {
                            this.updateSubItem();
                        } else {
                            totalAttempts++;
                            if (counter < 2) {
                                counter++;
                                activityInterface.setVideoWrongAnswerToRepeat();
                            } else {
                                counter = 0;
                                //ask the next sub item
                                this.updateSubItem();
                                activityInterface.setVideoWrongAnswerAndGoOn();
                            }
                        }
                    } else {
                        if (readTag.equals(currentSubElement)) {
                            subElementIndex = 1;
                            Log.i(CLASS, "[CheckAnswer][lastSubItem]" + currentSubElement);
                            this.correctAnswer();
                        } else {
                            totalAttempts++;
                            if (counter < 2) {
                                counter++;
                                activityInterface.setVideoWrongAnswerToRepeat();
                            } else {
                                counter = 0;
                                numberOfElements = 0;
                                activityInterface.setVideoWrongAnswerAndGoOn();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Update the subitem to the next one
     */
    private void updateSubItem(){
        subElementIndex++;
        Log.i(CLASS, "[CheckAnswer][MultipleItem]" + currentSubElement +
                " index:" + subElementIndex);
        if (subElementIndex <= currentElement.length()) {
            // Set next sub Item
            currentSubElement = currentElement.substring(subElementIndex, subElementIndex + 1);
            Log.i(CLASS, "[CheckAnswer][updatedSubitem]" + currentSubElement);
            //Display correct result
            numberOfElements--;
            Log.i(CLASS, "[CheckAnswer][CallingNewItem]" + currentSubElement);
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activityInterface.setSubItemAnimation(currentSubElement);
                }
            },1500);
        }
    }

    private void check(String readTag){
        if (readTag.equals(currentElement)) {
            Log.i(CLASS, "[CheckAnswer][SingleItem][Correct] " + readTag);
            this.correctAnswer();
        } else {
            String shapeElement = currentElement.replace("shape", "");
            Log.i("shape element", shapeElement);
            if (readTag.equals(shapeElement)) {
                Log.i(CLASS, "[CheckAnswer][SingleItem][Correct][ShapeElement] " + readTag);
                this.correctAnswer();
            } else {
                Log.i(CLASS, "[CheckAnswer][SingleItem][Wrong] " + readTag + ", current element: " + currentElement);
                this.wrongAnswer();
            }
        }
    }

    /**
     * Update the correct answer calling the view to set a video
     */
    private void correctAnswer(){
        counter = 0;
        correctAnswers++;
        totalAttempts++;
        activityInterface.setVideoCorrectAnswer();
    }

    private void correctAnswerColour(){
        counter = 0;
        counterColourSession ++;
        correctAnswers++;
        totalAttempts++;
        int size = tempArray.size();
        if(size == counterColourSession)
        {
            counter = 0;
            counterColourSession = 0;
            startNewTurn();
        } else{
                if (counterColourSession < 4) {
                    activityInterface.setVideoCorrectAnswer();
                } else {
                    counter = 0;
                    counterColourSession = 0;
                    startNewTurn();
                }
            }
        }


    private void wrongAnswerColour(){
        totalAttempts++;
        if (counter < 3) {
            counter++;
            activityInterface.setVideoWrongAnswerToRepeat();
        } else {
            counter = 0;
            counterColourSession = 0;
            startNewTurn();
        }
    }

    private void correctAnswerRecipe(){
        counter = 0;
        correctAnswers++;
        totalAttempts++;
        int size = tempArray.size();
        if(size == 0)
        {
            counter = 0;
            startNewTurn();
        } else{
                activityInterface.setVideoCorrectAnswer();
        }
    }


    private void wrongAnswerRecipe(){
        totalAttempts++;
        if (counter < 6) {
            counter++;
            activityInterface.setVideoWrongAnswerToRepeat();
        } else {
            counter = 0;
            startNewTurn();
        }
    }



    /**
     * Update the correct answer calling the view to the correspondent video
     */
    private void wrongAnswer(){
        totalAttempts++;
        if (counter < 2) {
            counter++;
            activityInterface.setVideoWrongAnswerToRepeat();
        } else {
            counter = 0;
            activityInterface.setVideoWrongAnswerAndGoOn();
        }
    }

    /**
     *  Check if an element is composed by multiple objects and set the flag variables
     */
    public void checkMultipleItems(){
        if(currentElement.contains("_") && currentElement.length() > 2){
            multipleElement = true;
            subElementIndex = 1;
            numberOfElements = currentElement.length() - 1;
            //init char inside the string
            currentSubElement = currentElement.substring(subElementIndex, subElementIndex+1);
            Log.i(CLASS, "[CheckMultipleItems][True] " + numberOfElements);
            Log.i(CLASS, "[CurrentSubElement] " + currentSubElement);
        }else{
            numberOfElements=1;
            multipleElement = false;
            Log.i(CLASS, "[CheckMultipleItems][False] " + numberOfElements);
            Log.i(CLASS, "[arrayLength]"+currentElement.length());
        }
    }

    private void chooseColour(){
        newTurnStarted = false;
        Log.i(CLASS, "Ask current colour element" );
        activityInterface.setPresentationAnimation(currentSequenceElement);
    }

    private void chooseRecipe(){
        newTurnStarted = false;
        Log.i(CLASS, "Ask current recipe element" );
        activityInterface.setPresentationAnimation(currentSequenceElement);
    }

    @Override
    public void notifyFirstSubElement(){
        activityInterface.initGridView(currentSubElement);
    }

    @SuppressWarnings("rawtypes")
    public int getResourceId(String name, Class resType){

        try {
            Class res = null;
            if(resType == R.drawable.class)
                res = R.drawable.class;
            if(resType == R.id.class)
                res = R.id.class;
            if(resType == R.string.class)
                res = R.string.class;
            if(resType == R.raw.class)
                res = R.raw.class;
            if(resType == R.array.class)
                res = R.array.class;
            Field field = res.getField(name);
            return field.getInt(null);
        }
        catch (Exception e) {
            Log.e("[Game Presenter]:", "failure to retrieve id, exception thrown.", e);
        }
        return 0;
    }

    @Override
    public void onDestroy() {
        activityInterface = null;
    }

    @Override
    public boolean checkNfcAvailability() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activityInterface.getScreenContext());
        if (nfcAdapter == null) {
            Toast.makeText(activityInterface.getScreenContext(), "NFC non attivato!", Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);
                Log.i("[HandleIntent]:", "Tag Detected" + type);
            } else {
                Log.i("Wrong mime type: " , type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();
            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                }
            }
            Log.i("[HandleIntent]:", "Action Detected" + action);
        }
    }

    //Activity SINGLE_TOP launchMode: when an new intent is detected for an Activity for which there is already an instance available,
    //that instance is used, no other are created.
    public void setupForegroundDispatch() {
        final Intent intent = new Intent(activityInterface.getScreenContext(), activityInterface.getApplicationClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activityInterface.getScreenContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        nfcAdapter.enableForegroundDispatch((Activity) activityInterface, pendingIntent, filters, techList);
    }

    public void stopForegroundDispatch() {
        nfcAdapter.disableForegroundDispatch((Activity) activityInterface);
    }

    //CODE TO READ THE NDEF TAG
    @SuppressLint("StaticFieldLeak")
    class NdefReaderTask extends AsyncTask<Tag, Void, String> {
        @Override
        protected String doInBackground(Tag... parameters) {
            Tag tag = parameters[0];
            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                return null;
            }
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();
            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Encoding not supported!", e);
                    }
                }
            }
            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            byte[] payload = record.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = payload[0] & 0063;
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(final String result) {
            if (enableNFC) {
                if (result != null) {
                    //only for debug
                    enableNFC = false;
                    Toast.makeText(activityInterface.getScreenContext(), result, Toast.LENGTH_LONG).show();
                    Log.i("[OnPostExecute]", "NFC Read result: " + result);
                    int tagID = getResourceId("nfc_sound", R.raw.class);
                    MediaPlayer tag = MediaPlayer.create(activityInterface.getScreenContext(), tagID);
                    tag.start();
                    tag.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                            checkAnswer(result);

                        }
                    });
                }
            }else{
                Log.i("[NFC] ", "not enabled");
            }
        }
    }
        private void setTimeParameter(){

        totaltime = endTime - initTime - adjustment;
        Log.i("init time:", String.valueOf(totaltime));
        }

        @Override
        public String getCurrentSequenceElement(){
            return currentSequenceElement;
        }

        @Override
        public String getCurrentSubElement() {
            return currentSubElement;
        }

        @Override
        public int getNumberOfElements() {
            return numberOfElements;
        }

        @Override
        public boolean getMultipleElement() {
            return multipleElement;
        }

        @Override
        public void setLevelCurrentPlayer(){
            int level;
            Log.e("Activity interface ", activityInterface.getString());
            switch (activityInterface.getString()){
                case "ActivityOneOne":
                    level = 11;
                    Log.e("Switch ", "11");
                    break;
                case "ActivityOneTwo":
                    level = 12;
                    Log.e("Switch ", "12");
                    break;
                case "ActivityOneThree":
                    level = 13;
                    Log.e("Switch ", "13");
                    break;
                case "ActivityTwoOne":
                    level = 21;
                    Log.e("Switch ", "21");
                    break;
                case "ActivityTwoTwo":
                    level = 22;
                    Log.e("Switch ", "22");
                    break;
                case "ActivityTwoThree":
                    level = 23;
                    Log.e("Switch ", "23");
                    break;
                case "ActivityThreeOne":
                    level = 31;
                    Log.e("Switch ", "23");
                    break;
                case "ActivityThreeTwo":
                    level = 32;
                    break;
                default:
                    level = 0;
                    Log.e("Switch ", "default");
                    break;
            }
            Log.e("[GamePresenterLevel]", String.valueOf(level));
            DatabaseInitializer.setLevelCurrentPlayer(db, level);
            Log.e("[GamePresenterLevel]", String.valueOf(DatabaseInitializer.getLevelCurrentPlayer(db)));
        }


        @Override
        public void setObjectCurrentPlayer(){
            DatabaseInitializer.setObjectCurrentPlayer(db, currentElement);
            Log.i(CLASS, "I'm saving "+currentElement+" in the database.");
        }

    @Override
    public void setSubStringCurrentPlayer(){
        DatabaseInitializer.setSubStringCurrentPlayer(db, currentSequenceElement);
        Log.i(CLASS, "I'm saving "+currentSequenceElement+" in the database.");
    }
        public void setColourLevel(){
            colourLevel = true;
        }

    @Override
    public void setEnableNFC() {
        enableNFC = true;
    }

    @Override
    public void setRecipeLevel() {
        recipeLevel = true;
    }

    @Override
    public String getCurrentReadTag() {
        return currentReadTag;
    }

    @Override
    public String getCurrentElement() {
        return currentElement;
    }

    boolean isStarted(){
            return gameStarted;
        }

        //The following methods have been added oly for testing purpose
        boolean isEnded(){return gameEnded;}
        boolean getNewSessionStarted(){
            return newSessionStarted;
        }
        boolean getNewTurnStarted(){
            return newTurnStarted;
        }
        int getTotalAttempts(){
            return totalAttempts;
        }
        int getCorrectAnswers(){
            return correctAnswers;
        }
        int getCounter(){
            return counter;
        }
        void setCounter(int i){
            counter = i;
        }

        IGame.View getActivityInterface(){
            return activityInterface;
        }

    @Override
    public void storeCurrentPlayer(Bundle savedInstanceState) {
        savedInstanceState.putInt("current_player", DatabaseInitializer.getCurrentPlayer(db));
        savedInstanceState.putInt("level", DatabaseInitializer.getLevelCurrentPlayer(db));
        savedInstanceState.putString("object", DatabaseInitializer.getObjectCurrentPlayer(db));
        savedInstanceState.putString("subString", DatabaseInitializer.getSubStringCurrentPlayer(db));
        Log.i("[GAME PRESENTER]", "Storing current player " +String.valueOf(DatabaseInitializer.getCurrentPlayer(db)));
        Log.i("[GAME PRESENTER]", "Storing level " +String.valueOf(DatabaseInitializer.getLevelCurrentPlayer(db)));
    }
}
