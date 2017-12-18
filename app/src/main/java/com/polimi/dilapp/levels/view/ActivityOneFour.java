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
* In this Activity we verify what the children has learned so far, in order to unlock the following section.
*
*/
public class ActivityOneFour extends AppCompatActivity implements IGame.View {

    ArrayList<String> mixedSequence;
    ArrayList<String> colorSequence;
    ArrayList<String> nameSequence;
    ArrayList<String> shapeSequence;


    IGame.Presenter presenter;
    MediaPlayer request;
    String element;
    CommonActivity common;
    String currentSequenceElement;


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
        List<String> tempArray = new ArrayList<>(Arrays.asList(colors));
        colorSequence = common.getList(colors);
        mixedSequence = new ArrayList<>();

        Collections.sort(tempArray);
        tempArray.remove("all_colors");
        mixedSequence.add(tempArray.get(0));
        mixedSequence.add(tempArray.get(1));

        String[] names = getResources().getStringArray(R.array.names);
        tempArray = new ArrayList<>(Arrays.asList(names));
        nameSequence = common.getList(names);

        Collections.sort(tempArray);
        tempArray.remove("all_fruits");
        mixedSequence.add(tempArray.get(0));
        mixedSequence.add(tempArray.get(1));

        String[] shapes = getResources().getStringArray(R.array.shapes);
        tempArray = new ArrayList<>(Arrays.asList(shapes));
        shapeSequence = common.getList(shapes);

        Collections.sort(tempArray);
        tempArray.remove("all_shapes");
        mixedSequence.add(tempArray.get(0));
        mixedSequence.add(tempArray.get(1));
    }

    private void setupVideoIntro(){
        /*
        *
        * We need to specify in the intro that every session has the intent to prepare a basket of fruits with different
        * methodologies: then each video session will only show the elements that will be considered in each session.
        *
        * */
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        common.startIntro(uri, mixedSequence,this);
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
        Animation animationBegin = AnimationUtils.loadAnimation(ActivityOneFour.this, R.anim.rotation);

        common.startMainAnimation(this,animationBegin,resourceID,this);

        setAudioRequest();
    }

    private void setAudioRequest(){
        int objectClaimedID;
        if(nameSequence.contains(currentSequenceElement)){
            objectClaimedID = presenter.getResourceId("request_" + element, R.raw.class);
        } else{
            if(colorSequence.contains(currentSequenceElement)){
                objectClaimedID = presenter.getResourceId("request_" + currentSequenceElement + "_item", R.raw.class);
            }else{
                objectClaimedID = presenter.getResourceId("request_shape", R.raw.class);
            }
        }
        request = MediaPlayer.create(ActivityOneFour.this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setAnimationBoxExtra();
                setWaitingAnimation();
                mp.release();
                presenter.setupForegroundDispatch();
                presenter.handleIntent(getIntent());
            }
        });
    }


    //TODO modify extra animation with something cool
    public void setAnimationBoxExtra(){
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimation = AnimationUtils.loadAnimation(ActivityOneFour.this, R.anim.move);
        animationViewExtra.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtra.setAnimation(extraAnimation);
        animationViewExtra.startAnimation(extraAnimation);

        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimationTwo = AnimationUtils.loadAnimation(ActivityOneFour.this, R.anim.move);
        animationViewExtraTwo.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtraTwo.setAnimation(extraAnimationTwo);
        animationViewExtraTwo.startAnimation(extraAnimationTwo);
    }

    public void setWaitingAnimation(){
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(ActivityOneFour.this, R.anim.blink);
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
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityOneFour");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityTwoOne");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);
    }

    @Override
    public String getString() {
        return "ActivityOneFour";
    }

    @Override
    public List<String> getSessionArray(int vectorID) {
        String[] sessionFruitVector = getResources().getStringArray(vectorID);
            List<String> array = new ArrayList<>(Arrays.asList(sessionFruitVector));
            Collections.sort(array);
            return array;
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
    }

    @Override
    protected void onPause() {
        presenter.stopForegroundDispatch();
        super.onPause();
    }

    //onNewIntent let us stay in the same activity after reading a TAG
    @Override
    protected void onNewIntent(Intent intent) {
        presenter.setupForegroundDispatch();
        presenter.handleIntent(intent);
    }
}
