package com.polimi.dilapp.levels.view;

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

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.GamePresenter;
import com.polimi.dilapp.levels.IGame;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityOneOne extends AppCompatActivity implements IGame.View {
    //TODO: ADD TIMER, COUNTERS, SOUND

    ArrayList<String> colorSequence;
    IGame.Presenter presenter;
    MediaPlayer request;
    String element;
    CommonActivity common;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);

        //set up the presenter and pass it to the common activity view
        presenter = new GamePresenter(this);
        common = new CommonActivity(presenter);

        setupSequence();

        boolean availability = presenter.checkNfcAvailability();
        if (availability) {
            setupVideoIntro();
        }else{
            finish();
        }
    }

    private void setupSequence(){
        String[] colors = getResources().getStringArray(R.array.colors);
        colorSequence = common.getList(colors);
    }

    private void setupVideoIntro(){
        //Introduction to the whole activity game
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        common.startIntro(uri, colorSequence,this);
    }

    @Override
    public void setVideoView(int videoID){
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoID);
        common.startMainVideo(uri, this);
    }

    @Override
    public void setPresentationAnimation(String currentElement){
        element = currentElement;
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationBegin = AnimationUtils.loadAnimation(ActivityOneOne.this, R.anim.rotation);

        common.startMainAnimation(this,animationBegin,resourceID,this);

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
                mp.release();
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
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(ActivityOneOne.this, R.anim.blink);
        common.startMainAnimation(this,animationWait,resourceID,this);
    }

    @Override
    public void setCorrectAnswerAnimation(){
        common.setCorrectAnswerAnimation(this, this);
    }

    @Override
    public void setNotCorrectAnswerAnimation(){
       common.setNotCorrectAnswerAnimation(this,this);
    }

    @Override
    public ArrayList<String> getSessionArray(int vectorID) {
        String[] sessionFruitVector = getResources().getStringArray(vectorID);
        return new ArrayList<>(Arrays.asList(sessionFruitVector));
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
