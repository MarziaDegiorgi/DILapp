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
import java.util.List;

/**
 * Activity View referred to 2.1 Level : Learning NUMBERS
 */


public class ActivityTwoOne extends AppCompatActivity implements IGame.View{

    private IGame.Presenter presenter;
    private ArrayList<String> numberSequence;
    private CommonActivity common;
    String element;
    MediaPlayer request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_multiple_game_answers);

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

    private void setupSequence() {
        String[] numbers = getResources().getStringArray(R.array.numbers);
        numberSequence = common.getList(numbers);
    }

    private void setupVideoIntro() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        common.startIntro(uri, numberSequence,this);
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

        Animation animationBegin = AnimationUtils.loadAnimation(ActivityTwoOne.this, R.anim.combination_set);

        ImageView image = findViewById(R.id.animation_box_question);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(getResources().getDrawable(resourceID));
        image.setVisibility(View.VISIBLE);

        image.setAnimation(animationBegin);
        image.startAnimation(animationBegin);
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
        ImageView animationBox = findViewById(R.id.animation_box_two);
        animationBox.setVisibility(View.VISIBLE);
        Animation extraAnimation = AnimationUtils.loadAnimation(ActivityTwoOne.this, R.anim.move);
        animationBox.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
        animationBox.setAnimation(extraAnimation);
        animationBox.startAnimation(extraAnimation);
    }

    public void setWaitingAnimation(){
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(ActivityTwoOne.this, R.anim.blink);
        common.startMainAnimation(this, animationWait, resourceID, this.getScreenContext());
    }

    @Override
    public List<String> getSessionArray(int vectorID) {
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


    private void disableViews(){
        ImageView imageToHide = findViewById(R.id.animation_box);
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        common.disableView(imageToHide);
        common.disableView(animationViewExtra);
        common.disableView(animationViewExtraTwo);
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), RepeatOrExitScreen.class);
        intent.putExtra("activity","ActivityTwoOne");
        startActivity(intent);
    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), GoOnOrExitScreen.class);
        intent.putExtra("activity","ActivityTwoTwo");
        startActivity(intent);
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

    @Override
    public Intent newIntent() {
        return getIntent();
    }
}
