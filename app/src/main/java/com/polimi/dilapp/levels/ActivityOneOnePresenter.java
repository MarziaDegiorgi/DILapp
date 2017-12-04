package com.polimi.dilapp.levels;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;

import com.polimi.dilapp.R;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ActivityOneOnePresenter implements IActivityOneOne.Presenter {
    //TODO: ADD TIMER, COUNTERS, SOUND

    private Chronometer chronometer;
    private int correctAnswers = 0;
    private int totalAttempts = 0;
    //Timer globalTimer = new Timer();*/
    private int counter = 0;

    private NfcAdapter nfcAdapter;
    private String currentElement;
    private int presentationVideo;
    private static final String MIME_TEXT_PLAIN = "text/plain";
    private List<String> tempArray;
    private List<String> currentSequence;


    private IActivityOneOne.View activityOneOneInterface;

   ActivityOneOnePresenter(IActivityOneOne.View view){
       this.activityOneOneInterface = view;
   }





    public void startGame(List<String> sequence){
        chronometer.start();
        currentSequence = sequence;
        if(currentSequence.isEmpty()){
            Toast.makeText(activityOneOneInterface.getScreenContext(), "Problema! Niente Risorse!", Toast.LENGTH_LONG).show();
        } else {
            String currentColor = currentSequence.get(0);
            currentSequence.remove(currentColor);
            startNewSession(currentColor);
        }
    }

    //NEXT ELEMENT IN THE ARRAY
    private void startNewTurn(){
        if(currentSequence.isEmpty()){
            //ActivityOneOne ends
            //TODO UPDATE COUNTERS IN DB
            chronometer.stop();
            Long time = chronometer.getBase();
            Toast.makeText(activityOneOneInterface.getScreenContext(), "Fine Attività 1.1", Toast.LENGTH_LONG).show();
            //TODO: visualize screen with buttons "continue" and "exit"

        } else {
            String currentColor = currentSequence.get(0);
            currentSequence.remove(currentColor);
            startNewSession(currentColor);
        }
    }

    //NEXT ARRAY IN THE SEQUENCE
    private void startNewSession(String currentColor){
        int vectorID = getResourceId(currentColor+"_items", R.array.class);
        presentationVideo = getResourceId( "video_set_of_" + currentColor + "_items", R.raw.class);

        tempArray = activityOneOneInterface.getSessionArray(vectorID);
        //this set the video of the session: example yellow colors video.
        activityOneOneInterface.setVideoView(presentationVideo);
    }


    public void chooseElement(){
        if(tempArray.isEmpty()){
            startNewTurn();
            Toast.makeText(activityOneOneInterface.getScreenContext(), "start new session", Toast.LENGTH_LONG).show();
        }else{
            Collections.sort(tempArray);
            currentElement = tempArray.get(0);
            tempArray.remove(currentElement);
            askCurrentElement();
        }
    }

    private void askCurrentElement(){
        activityOneOneInterface.setPresentationAnimation(currentElement);

    }

    private void checkAnswer(String readTag) {
        if (readTag.equals(currentElement)) {
            correctAnswers++;
            totalAttempts++;
            Toast.makeText(activityOneOneInterface.getScreenContext(), "Risposta corretta, andiamo avanti!", Toast.LENGTH_LONG).show();
            counter = 0;
            activityOneOneInterface.setCorrectAnswerAnimation();
        } else {

            totalAttempts++;
            Toast.makeText(activityOneOneInterface.getScreenContext(), "Hai sbagliato! prova di nuovo!", Toast.LENGTH_LONG).show();
            activityOneOneInterface.setNotCorrectAnswerAnimation();
            if (counter < 2) {
                counter++;
                askCurrentElement();
            } else {
               chooseElement();
            }
        }
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
            int retId = field.getInt(null);
            return retId;
        }
        catch (Exception e) {
            // Log.d(TAG, "Failure to get drawable id.", e);
        }
        return 0;
    }

    @Override
    public void onDestroy() {
        activityOneOneInterface = null;
    }

    @Override
    public boolean checkNfcAvailability() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activityOneOneInterface.getScreenContext());
        if (nfcAdapter == null) {
            Toast.makeText(activityOneOneInterface.getScreenContext(), "NFC non attivato!", Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    //TODO CHECK IF IT'S NECESSARY TO USE BOTH IF CASES.
    @Override
    public void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);
            } else {
                Log.d(TAG, "Wrong mime type: " + type);
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
        }
    }

    //Activity SINGLE_TOP launchMode: when an new intent is detected for an Activity for which there is already an instance available,
    //that instance is used, no other are created.
    public void setupForegroundDispatch() {
        final Intent intent = new Intent(activityOneOneInterface.getScreenContext(), activityOneOneInterface.getApplicationClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activityOneOneInterface.getScreenContext(), 0, intent, 0);

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

        nfcAdapter.enableForegroundDispatch((Activity) activityOneOneInterface, pendingIntent, filters, techList);
    }

    public void stopForegroundDispatch() {
        nfcAdapter.disableForegroundDispatch((Activity) activityOneOneInterface);
    }

    //CODE TO READ THE NDEF TAG
    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {
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
        protected void onPostExecute(String result) {
            if (result != null) {
                //only for debug
                Toast.makeText(activityOneOneInterface.getScreenContext(), result, Toast.LENGTH_LONG).show();
                checkAnswer(result);
            }
        }
    }

}
