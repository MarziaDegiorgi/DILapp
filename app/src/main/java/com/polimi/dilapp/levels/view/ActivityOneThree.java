package com.polimi.dilapp.levels.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.GamePresenter;
import com.polimi.dilapp.levels.IGame;
import com.polimi.dilapp.startgame.StartGameActivity;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
*
* In this Activity the child learns to associate a shape to an object.
*
* */
public class ActivityOneThree extends AppCompatActivity implements IGame.View {

    ArrayList<String> shapeSequence;
    IGame.Presenter presenter;
    MediaPlayer request;
    String element;
    CommonActivity common;
    String object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        object = intent.getStringExtra("object");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);

        //set up the presenter and pass it to the common activity view
        try {
            presenter = new GamePresenter(this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        common = new CommonActivity(presenter);
        common.setAnimations(ActivityOneThree.this);
        setupSequence();

        boolean availability = presenter.checkNfcAvailability();
        if (availability) {
            setupVideoIntro();
        }else{
            finish();
        }
    }

    private void setupSequence(){
        String[] shapes = getResources().getStringArray(R.array.shapes);
        shapeSequence = common.getList(shapes);

    }

    private void setupVideoIntro(){
        //Introduction to the whole activity game
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_1_3);
        common.startIntro(uri, shapeSequence,this);
    }

    @Override
    public void disableViews(){
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
        Animation animationBegin = AnimationUtils.loadAnimation(ActivityOneThree.this, R.anim.rotation);
        common.enableLionHeadAnimation(ActivityOneThree.this, this);
        common.startMainAnimation(this,animationBegin,resourceID,this);

        setAudioRequest();
    }

    private void setAudioRequest(){
        int objectClaimedID = presenter.getResourceId("request_shape", R.raw.class);
        final AppCompatActivity activity = this;
        final Context context = ActivityOneThree.this;

        request = MediaPlayer.create(ActivityOneThree.this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                common.enableKiteAnimationBackground(activity, context);
                common.disableLionHeadAnimation(activity);
                setWaitingAnimation();
                mp.release();
                presenter.setEnableNFC();
                presenter.handleIntent(getIntent());
            }
        });
    }

    public void setWaitingAnimation(){
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(ActivityOneThree.this, R.anim.waiting_rotation);

        common.enableLionBackground(this);
        common.enableLionTailAnimation(this, ActivityOneThree.this);

        common.startMainAnimation(this,animationWait,resourceID,this);
    }

    @Override
    public void setVideoCorrectAnswer(){
        disableViews();
        common.enableLionHeadAnimation(ActivityOneThree.this, this);
        String correctElement = presenter.getCurrentElement().replace("shape", "");
        int resourceID = presenter.getResourceId(correctElement, R.drawable.class);
        final ImageView image = findViewById(R.id.animation_box_answer);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(ContextCompat.getDrawable(ActivityOneThree.this,resourceID));
        image.setVisibility(View.VISIBLE);

        Animation animationCorrect = AnimationUtils.loadAnimation(ActivityOneThree.this, R.anim.bounce);
        image.setAnimation(animationCorrect);
        image.startAnimation(animationCorrect);
        common.setCorrectAnswer(image, ActivityOneThree.this);
    }

    @Override
    public void setVideoWrongAnswerToRepeat() {
        ImageView image = findViewById(R.id.animation_box_answer);
        image.clearAnimation();
        image.setVisibility(View.INVISIBLE);
        common.enableLionHeadAnimation(ActivityOneThree.this, this);
        MediaPlayer request = MediaPlayer.create(ActivityOneThree.this, R.raw.wrong_answer_to_repeat_shape);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                presenter.askCurrentElement();
                mp.release();
            }
        });
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        common.enableLionHeadAnimation(ActivityOneThree.this, this);
        ImageView image = findViewById(R.id.animation_box_answer);
        image.clearAnimation();
        image.setVisibility(View.INVISIBLE);
        common.setWrongAnswerAndGoOn(ActivityOneThree.this);
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityOneThree");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
        overridePendingTransition(R.anim.left_enter, R.anim.left_exit);
        finish();
    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityTwoOne");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);
        overridePendingTransition(R.anim.left_enter, R.anim.left_exit);
        finish();

    }

    @Override
    public String getString() {
        return "ActivityOneThree";
    }

    @Override
    public List<String> getSessionArray(int vectorID) {
        String[] sessionFruitVector = getResources().getStringArray(vectorID);
        if(vectorID == R.array.all_shapes_items){
            return common.getPartialArray(sessionFruitVector);
        }else {
            List<String> array = new ArrayList<>(Arrays.asList(sessionFruitVector));
            Collections.sort(array);
            return array;
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

    @Override
    public void setSubItemAnimation(String currentSubElement) {
        // Not used in this class
    }

    @Override
    public void initTableView(String currentSubItem) {
        //NOT USED
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        presenter.getEndTime();
        presenter.setObjectCurrentPlayer();
        presenter.setSubStringCurrentPlayer();
        startActivity(new Intent(ActivityOneThree.this, StartGameActivity.class));
        finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
        Log.i("[ACTIVITY 13]", "I'm calling storeCurrentPlayer");

    }


    public void onMusicChange(View view) {
    }
}
