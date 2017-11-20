package com.polimi.dilapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.Arrays;

/**
 * Created by Roberta on 17/11/2017.
 */

//This is Activity 1.1
public class ActivityAlfa extends AppCompatActivity {

   /* private int correctAnswers = 0;
    private int totalAttempts = 0;
    //Timer globalTimer = new Timer();*/



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alfa);
        Intent intent = getIntent();
        Log.d("Activity Alfa:","the onCreate()has been executed.");
        //When the activity is created the introduction video starts
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        VideoView videoIntro = (VideoView)findViewById(R.id.video_box);
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

    /*private VideoView startNewVideo(int viewIdentifier, int uriReference){
        VideoView videoView = (VideoView)findViewById(viewIdentifier);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+uriReference);
        videoView.setVideoURI(uri);
        videoView.start();
        /*videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
    return videoView;
    }

    public void startNewAnimation(int animationIdentifier, int viewIdentifier){
        Animation animation = AnimationUtils.loadAnimation(this, animationIdentifier);
        ImageView animationView = (ImageView) findViewById(viewIdentifier);
        animationView.setAnimation(animation);
    }*/

    //sessionOne includes all the yellow items
    private void startSessionOne(){

        Log.d("Activity Alfa:", "session one begins!");
        //This is the video of the first session of 4 fruits: banana, lemon, corn, grapefruit
        VideoView videoView = (VideoView)findViewById(R.id.video_box);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.dummy_video);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ImageView animationView = (ImageView) findViewById(R.id.animation_box);
                animationView.setVisibility(View.VISIBLE);
            }
        });

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_definition);
            ImageView animationView = (ImageView) findViewById(R.id.animation_box);
            animationView.setVisibility(View.VISIBLE);
            animationView.setAnimation(animation);

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

