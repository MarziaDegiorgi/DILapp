package com.polimi.dilapp.levels.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levelmap.LevelMapActivity;
import com.polimi.dilapp.levels.GamePresenter;
import com.polimi.dilapp.levels.IGame;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activity View referred to 2.2 Level : Learning ALPHABET
 */

public class ActivityTwoTwo extends AppCompatActivity implements IGame.View {
    private IGame.Presenter presenter;
    private ArrayList<String> alphabetSequence;
    private CommonActivity common;
    String element;
    MediaPlayer request;

    Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);

        presenter = new GamePresenter(this);
        common = new CommonActivity(presenter);

        myHandler = new Handler();

        setupSequence();

        boolean availability = presenter.checkNfcAvailability();
        if (availability) {
            setupVideoIntro();
        }else{
            finish();
        }
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
        int requestItemID = presenter.getResourceId("request_letter", R.raw.class);
        final int objectClaimedID = presenter.getResourceId("request_" + element, R.raw.class);
        request = MediaPlayer.create(this, requestItemID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                setItemRequest(objectClaimedID);
            }
        });
    }

    public void setItemRequest(int resource){
        request = MediaPlayer.create(this, resource);
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                request.start();
                request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        setWaitingAnimation();
                        mp.release();
                        presenter.setEnableNFC();
                        presenter.handleIntent(getIntent());
                    }
                });
            }
        }, 1000);
    }

    public void setWaitingAnimation(){
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(this, R.anim.blink);
        common.startMainAnimation(this,animationWait,resourceID,this.getScreenContext());
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
        int imageID = presenter.getResourceId(element, R.drawable.class);
        Animation answerAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_and_bounce);

        image.setImageDrawable(this.getResources().getDrawable(imageID));
        image.setAnimation(answerAnimation);
        image.setVisibility(View.VISIBLE);
        image.startAnimation(answerAnimation);

        common.setVideoCorrectAnswer(image, this);
    }

    @Override
    public void setVideoWrongAnswerToRepeat() {
        disableViews();

        common.setVideoWrongAnswerToRepeat(this);
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        disableViews();

        common.setVideoWrongAnswerAndGoOn( this);
    }

    @Override
    public void disableViews(){
        ImageView imageToHide = findViewById(R.id.animation_box);
        ImageView imageAnswer = findViewById(R.id.animation_box_answer);
        common.disableView(imageToHide);
        common.disableView(imageAnswer);
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityTwoTwo");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityTwoThree");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);
    }

    @Override
    public String getString() {
        return "ActivityTwoTwo";
    }

    @Override
    public void setSubItemAnimation(String currentSubElement) {
        //Not used in this class
    }

    @Override
    public void initGridView(String currentSubItem) {
        //The gridView is not used
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
        presenter.setEnableNFC();
        presenter.handleIntent(intent);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        presenter.setObjectCurrentPlayer();
        presenter.setSubStringCurrentPlayer();
        startActivity(new Intent(ActivityTwoTwo.this, StartGameActivity.class));
        finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
        Log.i("[ACTIVITY 22]", "I'm calling storeCurrentPlayer");

    }
}
