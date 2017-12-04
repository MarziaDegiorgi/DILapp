package com.polimi.dilapp.levels;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.polimi.dilapp.R;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

//This is Activity 1.1
public class ActivityAlfa extends AppCompatActivity {

    //TO-DO: ADD TIMER, COUNTERS, SOUND

    public int correctAnswers = 0;
    public int totalAttempts = 0;
    //Timer globalTimer = new Timer();*/
    private int counter = 0;

    MediaPlayer request;
    NfcAdapter nfcAdapter;
    String currentElement;
    int presentationVideo;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    String[] sessionFruitVector;
    List<String> tempArray;
    String[] colors;
    List<String> colorSequence;
    VideoView videoView = findViewById(R.id.video_box);
    ImageView animationView = findViewById(R.id.animation_box);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alfa);
        Intent intent = getIntent();

        nfcAdapter = NfcAdapter.getDefaultAdapter(ActivityAlfa.this);
        if (nfcAdapter == null) {
            Toast.makeText(ActivityAlfa.this, "NFC non attivato!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }



        Log.d("Activity Alfa:", "the onCreate()has been executed.");
        //When the activity is created the introduction video starts
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        colors = getResources().getStringArray(R.array.colors);
        colorSequence = new ArrayList<String>(Arrays.asList(colors));

        //Introduction to th whole activity game
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //When the introduction video finishes the first session begins
            @Override
            public void onCompletion(MediaPlayer mp) {
                //here the global timer must starts
                startGame();
            }
        });
    }

    //color sequence: yellow, red, orange, violet, green, white, brown
    private void startGame(){
        if(colorSequence.isEmpty()){
            //termina attività 1.1
            //carica i contatori su DB
            //screen per tasto esci o continua cn 1.2
            Toast.makeText(ActivityAlfa.this, "Fine Attività 1.1", Toast.LENGTH_LONG).show();


        } else {
        String currentColor = colorSequence.get(0);
        colorSequence.remove(currentColor);
        startNewSession(currentColor);
        }
    }

    private void startNewSession(String currentColor){
        int vecortID = getResourceId(currentColor+"_items", R.array.class);
        presentationVideo = getResourceId( "video_set_of_" + currentColor + "_items", R.raw.class);

        sessionFruitVector = getResources().getStringArray(vecortID);
        tempArray = new ArrayList<String>(Arrays.asList(sessionFruitVector));
        //this set the video of the session: example yellow colors video.
        setVideoView();
            }


    private void chooseElement(){
        if(tempArray.isEmpty()){
            startGame();
            Toast.makeText(ActivityAlfa.this, "start new session", Toast.LENGTH_LONG).show();
        }else{
            Collections.sort(tempArray);
            currentElement = tempArray.get(0);
            tempArray.remove(currentElement);
            askCurrentElement();
        }
    }

    private void askCurrentElement(){
        setPresentationAnimation();
                //wait NFC tag//handleIntent(getIntent());
    }

    private void checkAnswer(String readTag){
        if(readTag.equals(currentElement)){
            //aumenta contatore risposte esatte e tentativi
            //audio risposta corretta + animazione
            correctAnswers++;
            totalAttempts++;
            Toast.makeText(ActivityAlfa.this, "Risposta corretta, andiamo avanti!", Toast.LENGTH_LONG).show();
            chooseElement();
            counter = 0;
        }else{
            //aumenta contatore tentativi
            //audio: hai sbagliato! hai presto "readTAG" non "currentElement" riprova!
            Toast.makeText(ActivityAlfa.this, "Hai sbagliato! prova di nuovo!", Toast.LENGTH_LONG).show();
            totalAttempts++;
            if(counter < 3){
                askCurrentElement();
            }else{
                chooseElement();
            }
        }
    }

    private void setVideoView(){

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + presentationVideo);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVisibility(View.INVISIBLE);
                chooseElement();
            }
        });
    }

    private void setPresentationAnimation(){
        int resourceID = getResourceId(currentElement, R.drawable.class);

        animationView.setVisibility(View.VISIBLE);
        animationView.setImageDrawable(getResources().getDrawable(resourceID));
        Animation animationBegin = AnimationUtils.loadAnimation(ActivityAlfa.this, R.anim.rotation);
        animationView.setVisibility(View.VISIBLE);
        animationView.setAnimation(animationBegin);
        animationView.startAnimation(animationBegin);
        setAudioRequest();
    }

    private void setAudioRequest(){
        int objectClaimedID = getResourceId("request_" + currentElement, R.raw.class);
        request = MediaPlayer.create(ActivityAlfa.this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setAnimationBoxExtra();
                setWaitingAnimation();
                handleIntent(getIntent());
            }
        });
    }

    private void setAnimationBoxExtra(){
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimation = AnimationUtils.loadAnimation(ActivityAlfa.this, R.anim.move);
        animationViewExtra.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtra.setAnimation(extraAnimation);
        animationViewExtra.startAnimation(extraAnimation);

        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimationTwo = AnimationUtils.loadAnimation(ActivityAlfa.this, R.anim.move);
        animationViewExtraTwo.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtraTwo.setAnimation(extraAnimationTwo);
        animationViewExtraTwo.startAnimation(extraAnimationTwo);
    }

    private void setWaitingAnimation(){
        int resourceID = getResourceId(currentElement, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(ActivityAlfa.this, R.anim.slide);
        animationWait = AnimationUtils.loadAnimation(ActivityAlfa.this, R.anim.blink);
        animationView.getResources().getDrawable(resourceID);
        animationView.setVisibility(View.VISIBLE);
        animationView.setAnimation(animationWait);
        animationView.startAnimation(animationWait);
    }

    @SuppressWarnings("rawtypes")
    public static int getResourceId(String name,  Class resType){

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



    //We want to handle NFC only when the Activity is in the foreground
    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this, nfcAdapter);
        super.onPause();
    }

    //onNewIntent let us stay in the same activity after reading a TAG
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    //Activity SINGLE_TOP launchMode: when an new intent is detected for an Activity for which there is already an instance available,
    //that instance is used, no other are created.
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        //same of the manifest -> forse è qui il problema (si riapre)
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    private synchronized void handleIntent(Intent intent) {
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
                Toast.makeText(ActivityAlfa.this, result, Toast.LENGTH_LONG).show();
                checkAnswer(result);
            }
        }
    }
}