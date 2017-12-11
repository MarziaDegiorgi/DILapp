package com.polimi.dilapp.levels.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.GamePresenter;
import com.polimi.dilapp.levels.IGame;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Activity View referred to 2.2 Level : Learning ALPHABET
 */

public class ActivityTwoTwo extends AppCompatActivity implements IGame.View {
    private IGame.Presenter presenter;
    private ArrayList<String> alphabetSequence;
    private CommonActivity common;
    String element;
    MediaPlayer request;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);

        presenter = new GamePresenter(this);
        common = new CommonActivity(presenter);

        setupSequence();
        setupVideoIntro();
        /*boolean availability = presenter.checkNfcAvailability();
        if (availability) {
            setupVideoIntro();
        }else{
            finish();
        }*/

    }


    private void setupSequence() {
        String[] letters = getResources().getStringArray(R.array.letters);
        alphabetSequence = common.getList(letters);
    }

    private void setupVideoIntro() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        common.startIntro(uri, alphabetSequence,this);
    }

    @Override
    public void setVideoView(int videoID) {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoID);
        common.startMainVideo(uri, this);
    }

    @Override
    public void setPresentationAnimation(String currentElement) {
        element = currentElement;
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationBegin = AnimationUtils.loadAnimation(this, R.anim.rotation);

        common.startMainAnimation(this,animationBegin,resourceID,this.getScreenContext());
        setAudioRequest();
    }

    private void setAudioRequest(){
        int objectClaimedID = presenter.getResourceId("request_" + element, R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
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

    }

    public void setWaitingAnimation(){
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(this, R.anim.blink);
        common.startMainAnimation(this,animationWait,resourceID,this.getScreenContext());
    }

    @Override
    public ArrayList<String> getSessionArray(int vectorID) {
        String[] sessionNumberVector = getResources().getStringArray(vectorID);
        return new ArrayList<>(Arrays.asList(sessionNumberVector));
    }

    @Override
    public Context getScreenContext() {

        return this;
    }

    @Override
    public Class getApplicationClass() {
        return this.getClass();
    }

    @Override
    public void setVideoCorrectAnswer() {

    }

    @Override
    public void setVideoWrongAnswerToRepeat() {

    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {

    }

    @Override
    public void setRepeatOrExitScreen() {

    }

    @Override
    public void setGoOnOrExitScreen() {

    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
    //onNewIntent let us stay in the same activity after reading a TAG
    @Override
    protected void onNewIntent(Intent intent) {

        presenter.handleIntent(intent);
    }
}
