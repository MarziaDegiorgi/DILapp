package com.polimi.dilapp;

import android.content.Intent;
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

    NfcAdapter nfcAdapter;
    String currentReadElement = "";
    String currentElement = "";
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
                        handleIntent(getIntent());

                       /* if(currentReadElement==""){
                            try {
                                ActivityAlfa.this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else{


                        if (currentReadElement == currentElement) {
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
                                if (currentReadElement != currentElement) {
                                    //animation + audio for not correct answer
                                    //here toast for debug
                                    Toast.makeText(ActivityAlfa.this, "Non corretto!", Toast.LENGTH_LONG);
                                    i++;
                                } else {
                                    i = 2;
                                }
                            }

                        }

                    }*/
                    }
                });

            }
        });


        //tempArray contains the fruits of the session
        String[] tempArray = getResources().getStringArray(R.array.yellow_items);
        int tempArrayLenght = tempArray.length;
        Arrays.sort(tempArray);


        /*for(int i=0; i<(tempArrayLenght-1);i++){
            totalAttempts++;
            String currentItem = tempArray[i];


*/

    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);
            } else {
                Log.d(TAG, "Mime type errato: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();
            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }
    //CODE TO READ THE NDEF TAG

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {
        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];
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
                        Log.e(TAG, "Encoding non supportato", e);
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
                Toast.makeText(ActivityAlfa.this, result, Toast.LENGTH_LONG).show();
            }
        }
    }
}