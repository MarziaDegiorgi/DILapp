package com.polimi.dilapp;

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

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

/**
 * Created by Roberta on 17/11/2017.
 */

//This is Activity 1.1
public class ActivityAlfa extends AppCompatActivity {

    //TO-DO: ADD TIMER, COUNTERS, SOUND

   /* private int correctAnswers = 0;
    private int totalAttempts = 0;
    //Timer globalTimer = new Timer();*/

    MediaPlayer request;
    Thread thread;
    NfcAdapter nfcAdapter;
    String currentReadElement = "none ";
    String currentElement = "lemon";
    public static final String MIME_TEXT_PLAIN = "text/plain";

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

        VideoView videoIntro = findViewById(R.id.video_box);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        videoIntro.setVideoURI(uri);
        videoIntro.start();
        videoIntro.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //When the introduction video finishes the first session begins
            @Override
            public void onCompletion(MediaPlayer mp) {
                //here the global timer must starts
                startSessionOne();
            }
        });


    }


    //sessionOne includes all the yellow items
    private void startSessionOne() {
        Log.d("Activity Alfa:", "session one begins!");

        //This is the video of the first session of 4 fruits: banana, lemon, corn, grapefruit
        final VideoView videoView = findViewById(R.id.video_box);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_set_of_object);
        videoView.setVideoURI(uri);
        videoView.start();

        //When the video is terminated the animation with the first required fruit begins with its audio
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVisibility(View.INVISIBLE);
                //currentElement = "lemon";
                final ImageView animationView = findViewById(R.id.animation_box);
                animationView.setVisibility(View.VISIBLE);
                animationView.setImageDrawable(getResources().getDrawable(R.drawable.dummy_fruit));
                Animation animationBegin = AnimationUtils.loadAnimation(ActivityAlfa.this, R.anim.rotation);
                animationView.setVisibility(View.VISIBLE);
                animationView.setAnimation(animationBegin);

                request = MediaPlayer.create(ActivityAlfa.this, R.raw.request_object);
                request.start();
                request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
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

                        Animation animationWait = AnimationUtils.loadAnimation(ActivityAlfa.this, R.anim.slide);
                        animationWait = AnimationUtils.loadAnimation(ActivityAlfa.this, R.anim.blink);
                        animationView.getResources().getDrawable(R.drawable.dummy_fruit);
                        animationView.setVisibility(View.VISIBLE);
                        animationView.setAnimation(animationWait);
                        animationView.startAnimation(animationWait);
                        //wait NFC tag
                        //put here to read only one nfc when required.
                        setupForegroundDispatch(ActivityAlfa.this, nfcAdapter);
                        handleIntent(getIntent());
                        startElementTwo();




                    }
                });

            }
        });

    }

    private void startElementTwo(){
        if (currentReadElement.equals(currentElement)) {
            //animation + audio for correct answer
            //here toast for debug
            Toast.makeText(ActivityAlfa.this, "Corretto!", Toast.LENGTH_LONG);
            //then the application proceeds with the next fruit
        } else {

            //animation + audio for not correct answer
            //here toast for debug
            Toast.makeText(ActivityAlfa.this, "Non corretto!", Toast.LENGTH_LONG);
            for (int i = 0; i < 2; i++) {
                //audio+animation again, to require the same object
                handleIntent(getIntent());
                if (!currentReadElement.equals(currentElement)) {
                    //animation + audio for not correct answer
                    //here toast for debug
                    Toast.makeText(ActivityAlfa.this, "Non corretto!", Toast.LENGTH_LONG);
                    i++;
                } else {
                    i = 2;
                }
            }

        }
    }

    //We want to handle NFC only when the Activity is in the foreground
    @Override
    protected void onResume() {
        super.onResume();
       // setupForegroundDispatch(this, nfcAdapter);
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

    private void handleIntent(Intent intent) {
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
                    //break;
                    return;
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
            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                currentReadElement = result;
                //only for debug
                Toast.makeText(ActivityAlfa.this, currentReadElement, Toast.LENGTH_LONG).show();
            }
        }
    }
}