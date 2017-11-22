package com.polimi.dilapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Roberta on 17/11/2017.
 */

//This is Activity 1.1
public class ActivityAlfa extends AppCompatActivity{

   /* private int correctAnswers = 0;
    private int totalAttempts = 0;
    //Timer globalTimer = new Timer();*/

    TextToSpeech textToSpeech;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alfa);
        Intent intent = getIntent();
        Log.d("Activity Alfa:","the onCreate()has been executed.");
        //When the activity is created the introduction video starts
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        textToSpeech = new TextToSpeech(ActivityAlfa.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                    if(status != textToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.ITALIAN);
                    }
            }
        });

        VideoView videoIntro = findViewById(R.id.video_box);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.intro);
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
    private void startSessionOne(){

        Log.d("Activity Alfa:", "session one begins!");

        //This is the video of the first session of 4 fruits: banana, lemon, corn, grapefruit
        final VideoView videoView = findViewById(R.id.video_box);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.dummy_video);
        videoView.setVideoURI(uri);
        videoView.start();

        //When the video is terminated the animation with the first required fruit begins with its audio
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVisibility(View.INVISIBLE);

                ImageView animationView = findViewById(R.id.animation_box);
                animationView.setVisibility(View.VISIBLE);
                animationView.setImageDrawable(getResources().getDrawable(R.drawable.dummy_fruit));
                Animation animation = AnimationUtils.loadAnimation(ActivityAlfa.this, R.anim.animation_definition);
                animationView.setVisibility(View.VISIBLE);
                animationView.setAnimation(animation);

                String presentation = getResources().getString(R.string.fruit_presentation);
                CharSequence text = presentation;

                textToSpeech.speak(presentation, TextToSpeech.QUEUE_FLUSH, null, null);

                //here we have to put the audio of the animation

            }
        });






        //tempArray contains the fruits of the session
        String[] tempArray = getResources().getStringArray(R.array.yellow_items);
        int tempArrayLenght = tempArray.length;
        Arrays.sort(tempArray);


        /*for(int i=0; i<(tempArrayLenght-1);i++){
            totalAttempts++;
            String currentItem = tempArray[i];




        }*/




    }



}

