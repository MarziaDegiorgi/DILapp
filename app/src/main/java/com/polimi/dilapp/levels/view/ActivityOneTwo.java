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
import java.util.Collections;
import java.util.List;
/*
*
* In this Activity the child learns to associate a colour to an object.
*
* */
public class ActivityOneTwo extends AppCompatActivity implements IGame.View {

    ArrayList<String> colorSequence;
    IGame.Presenter presenter;
    MediaPlayer request;
    String element;
    CommonActivity common;
    String currentColour;

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

    private void disableViews(){
        ImageView imageToHide = findViewById(R.id.animation_box);
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        common.disableView(imageToHide);
        common.disableView(animationViewExtra);
        common.disableView(animationViewExtraTwo);
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
        currentColour = presenter.getCurrentSequenceElement();
        Animation animationBegin = AnimationUtils.loadAnimation(ActivityOneTwo.this, R.anim.rotation);

        common.startMainAnimation(this,animationBegin,resourceID,this);

        setAudioRequest();
    }

    private void setAudioRequest(){
        int objectClaimedID;
        if(currentColour.equals("all_colors")){
            objectClaimedID = presenter.getResourceId("request_"+element+"_item", R.raw.class);
        }else{
            objectClaimedID = presenter.getResourceId("request_" + currentColour + "_item", R.raw.class);
        }
        request = MediaPlayer.create(ActivityOneTwo.this, objectClaimedID);
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


    //TODO modify extra animation with something cool
    public void setAnimationBoxExtra(){
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimation = AnimationUtils.loadAnimation(ActivityOneTwo.this, R.anim.move_half_rotate);
        animationViewExtra.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtra.setAnimation(extraAnimation);
        animationViewExtra.startAnimation(extraAnimation);

        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimationTwo = AnimationUtils.loadAnimation(ActivityOneTwo.this, R.anim.move_half_rotate);
        animationViewExtraTwo.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtraTwo.setAnimation(extraAnimationTwo);
        animationViewExtraTwo.startAnimation(extraAnimationTwo);
    }

    public void setWaitingAnimation(){
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(ActivityOneTwo.this, R.anim.blink);
        common.startMainAnimation(this,animationWait,resourceID,this);
    }

    @Override
    public void setVideoCorrectAnswer(){
        disableViews();

        ImageView image = findViewById(R.id.animation_box_answer);
        image.setVisibility(View.VISIBLE);
        image.getResources().getDrawable(R.drawable.correct_answer);
        common.setVideoCorrectAnswer(image, this);

    }

    @Override
    public void setVideoWrongAnswerToRepeat() {
        disableViews();

        ImageView image = findViewById(R.id.animation_box_answer);
        image.setVisibility(View.VISIBLE);
        image.getResources().getDrawable(R.drawable.not_correct_answer);
        common.setVideoWrongAnswerToRepeat(image,this);
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        disableViews();

        ImageView image = findViewById(R.id.animation_box_answer);
        image.setVisibility(View.VISIBLE);
        image.getResources().getDrawable(R.drawable.not_correct_answer);
        common.setVideoWrongAnswerAndGoOn(image, this);
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityOneTwo");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityOneThree");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);
    }

    @Override
    public String getString() {
        return "ActivityOneTwo";
    }


    @Override
    public ArrayList<String> getSessionArray(int vectorID) {
        String[] sessionFruitVector = getResources().getStringArray(vectorID);
        if(vectorID == R.array.all_colors_items){
            return common.getPartialArray(sessionFruitVector);
        }else {
            List<String> array = new ArrayList<>(Arrays.asList(sessionFruitVector));
            Collections.sort(array);
            return (ArrayList<String>) array;
        }
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

    @Override
    public Intent newIntent() {
        return null;
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
