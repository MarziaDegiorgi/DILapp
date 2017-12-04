package com.polimi.dilapp.levels;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import com.polimi.dilapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityOneOne extends AppCompatActivity implements IActivityOneOne.View {
    //TO-DO: ADD TIMER, COUNTERS, SOUND

    VideoView videoView;
    ImageView animationView;
    String[] colors;
    List<String> colorSequence;
    IActivityOneOne.Presenter presenter;
    MediaPlayer request;
    String element;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_oneone);

        presenter = new ActivityOneOnePresenter(this);

        setupSequence();
        boolean availability = presenter.checkNfcAvailability();
        if (availability) {
            setupVideoIntro();
        }else{
            finish();
        }


    }


    private void setupSequence(){
        colors = getResources().getStringArray(R.array.colors);
        colorSequence = new ArrayList<String>(Arrays.asList(colors));
    }

    private void setupVideoIntro(){
        //Introduction to th whole activity game
        videoView = findViewById(R.id.video_box);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //When the introduction video finishes the first session begins
            @Override
            public void onCompletion(MediaPlayer mp) {
                presenter.startGame(colorSequence);
            }
        });
    }

    public void setVideoView(int videoID){
        videoView = findViewById(R.id.video_box);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoID);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVisibility(View.INVISIBLE);
                presenter.chooseElement();
            }
        });
    }

    public void setPresentationAnimation(String currentElement){
        element = currentElement;
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        animationView = findViewById(R.id.animation_box);
        animationView.setVisibility(View.VISIBLE);
        animationView.setImageDrawable(getResources().getDrawable(resourceID));
        Animation animationBegin = AnimationUtils.loadAnimation(ActivityOneOne.this, R.anim.rotation);
        animationView.setVisibility(View.VISIBLE);
        animationView.setAnimation(animationBegin);
        animationView.startAnimation(animationBegin);
        setAudioRequest();
    }

    private void setAudioRequest(){
        int objectClaimedID = presenter.getResourceId("request_" + element, R.raw.class);
        request = MediaPlayer.create(ActivityOneOne.this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setAnimationBoxExtra();
                setWaitingAnimation();
                presenter.handleIntent(getIntent());
            }
        });
    }

    public void setAnimationBoxExtra(){
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimation = AnimationUtils.loadAnimation(ActivityOneOne.this, R.anim.move);
        animationViewExtra.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtra.setAnimation(extraAnimation);
        animationViewExtra.startAnimation(extraAnimation);

        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimationTwo = AnimationUtils.loadAnimation(ActivityOneOne.this, R.anim.move);
        animationViewExtraTwo.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtraTwo.setAnimation(extraAnimationTwo);
        animationViewExtraTwo.startAnimation(extraAnimationTwo);
    }

    public void setWaitingAnimation(){
        animationView = findViewById(R.id.animation_box);
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(ActivityOneOne.this, R.anim.blink);
        animationView.getResources().getDrawable(resourceID);
        animationView.setVisibility(View.VISIBLE);
        animationView.setAnimation(animationWait);
        animationView.startAnimation(animationWait);
    }

    public void setCorrectAnswerAnimation(){
        Animation animationCorrect = AnimationUtils.loadAnimation(ActivityOneOne.this, R.anim.slide);
        animationView = findViewById(R.id.animation_box);
        animationView.getResources().getDrawable(R.drawable.correct_answer);
        animationView.setVisibility(View.VISIBLE);
        animationView.setAnimation(animationCorrect);
        animationView.startAnimation(animationCorrect);
        request = MediaPlayer.create(ActivityOneOne.this, R.raw.correct_answer);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                animationView.setVisibility(View.INVISIBLE);
                presenter.chooseElement();
            }
        });
    }

    public void setNotCorrectAnswerAnimation(){
        Animation animationNotCorrect = AnimationUtils.loadAnimation(ActivityOneOne.this, R.anim.slide);
        animationView = findViewById(R.id.animation_box);
        animationView.getResources().getDrawable(R.drawable.not_correct_answer);
        animationView.setVisibility(View.VISIBLE);
        animationView.setAnimation(animationNotCorrect);
        animationView.startAnimation(animationNotCorrect);
        request = MediaPlayer.create(ActivityOneOne.this, R.raw.not_correct_answer);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                animationView.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public ArrayList<String> getSessionArray(int vectorID) {
        String[] sessionFruitVector = getResources().getStringArray(vectorID);
        return new ArrayList<String>(Arrays.asList(sessionFruitVector));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public Class getApplicationClass(){
        return this.getClass();
    }


    @Override
    public Context getScreenContext() {
        return this;
    }
    //We want to handle NFC only when the Activity is in the foreground

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setupForegroundDispatch();
    }

    @Override
    protected void onPause() {
        presenter.stopForegroundDispatch();
        super.onPause();
    }

    //onNewIntent let us stay in the same activity after reading a TAG
    @Override
    protected void onNewIntent(Intent intent) {
        presenter.handleIntent(intent);
    }
}
