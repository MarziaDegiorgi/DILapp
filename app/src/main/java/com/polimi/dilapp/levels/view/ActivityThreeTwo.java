package com.polimi.dilapp.levels.view;

import android.content.Context;
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

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.GamePresenter;
import com.polimi.dilapp.levels.IGame;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ActivityThreeTwo extends AppCompatActivity implements IGame.View{

    ArrayList<String> recipeSequence;
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
        String[] shapes = getResources().getStringArray(R.array.shapes);
        recipeSequence = common.getList(shapes);

    }

    private void setupVideoIntro(){
        //Introduction to the whole activity game
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        common.startIntro(uri, recipeSequence,this);
    }

    @Override
    public void disableViews(){
        /*ImageView imageToHide = findViewById(R.id.animation_box);
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        common.disableView(imageToHide);
        common.disableView(animationViewExtra);
        common.disableView(animationViewExtraTwo);*/
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
        Animation animationBegin = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.rotation);
        setLionHeadAnimation();
        common.startMainAnimation(this,animationBegin,resourceID,this);

        setAudioRequest();
    }

    private void setAudioRequest(){
        int objectClaimedID = presenter.getResourceId("request_shape", R.raw.class);
        request = MediaPlayer.create(ActivityThreeTwo.this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setAnimationBoxExtra();
                stopLionHeadAnimation();
                setWaitingAnimation();
                mp.release();
                presenter.setEnableNFC();
                presenter.handleIntent(getIntent());
            }
        });
    }


    public void setAnimationBoxExtra(){
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimation = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.move_half_rotate);
        animationViewExtra.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtra.setAnimation(extraAnimation);
        animationViewExtra.startAnimation(extraAnimation);

        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        animationViewExtra.setVisibility(View.VISIBLE);
        Animation extraAnimationTwo = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.move_half_rotate);
        animationViewExtraTwo.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        animationViewExtraTwo.setAnimation(extraAnimationTwo);
        animationViewExtraTwo.startAnimation(extraAnimationTwo);
    }

    private void setLionHeadAnimation(){
        ImageView lionHeadImage = findViewById(R.id.lion_head_game);
        lionHeadImage.setVisibility(View.VISIBLE);
        Animation animationLionHead = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.lion_rotation_waiting);
        lionHeadImage.setAnimation(animationLionHead);
        lionHeadImage.startAnimation(animationLionHead);
    }

    private void stopLionHeadAnimation(){
        ImageView lionHeadImage = findViewById(R.id.lion_head_game);
        lionHeadImage.setVisibility(View.VISIBLE);
        lionHeadImage.clearAnimation();
    }

    public void setWaitingAnimation(){
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        Animation animationWait = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.waiting_rotation);
        ImageView lionHeadImage = findViewById(R.id.lion_head_game);
        ImageView lionTaleImage = findViewById(R.id.tale_game);
        ImageView lionBodyImage = findViewById(R.id.lion_body_game);
        lionBodyImage.setVisibility(View.VISIBLE);
        lionTaleImage.setVisibility(View.VISIBLE);
        lionHeadImage.setVisibility(View.VISIBLE);

        Animation animationLionWait = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.tale_rotation);
        lionTaleImage.setAnimation(animationLionWait);
        lionTaleImage.setVisibility(View.VISIBLE);
        lionTaleImage.startAnimation(animationLionWait);
        common.startMainAnimation(this,animationWait,resourceID,this);
    }

    @Override
    public void setVideoCorrectAnswer(){
        disableViews();
        setLionHeadAnimation();
        String correctElement = presenter.getCurrentElement().replace("shape", "");
        int resourceID = presenter.getResourceId(correctElement, R.drawable.class);
        final ImageView image = findViewById(R.id.animation_box_answer);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(getResources().getDrawable(resourceID));
        image.setVisibility(View.VISIBLE);

        Animation animationCorrect = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.bounce);
        image.setAnimation(animationCorrect);
        image.startAnimation(animationCorrect);
        common.setVideoCorrectAnswer(image, this);
    }

    @Override
    public void setVideoWrongAnswerToRepeat() {
        ImageView image = findViewById(R.id.animation_box_answer);
        image.clearAnimation();
        image.setVisibility(View.INVISIBLE);
        setLionHeadAnimation();
        common.setVideoWrongAnswerToRepeat(this);
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        setLionHeadAnimation();
        ImageView image = findViewById(R.id.animation_box_answer);
        image.clearAnimation();
        image.setVisibility(View.INVISIBLE);
        common.setVideoWrongAnswerAndGoOn(this);
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityThreeTwo");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
    }

    @Override
    public void setGoOnOrExitScreen() {
        //TODO: DA SOSTITUIRE START GAME CON L'ACTIVITY DI FINE GIOCO
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.startgame.StartGameActivity");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);

    }

    @Override
    public String getString() {
        return "ActivityThreeTwo";
    }

    @Override
    public ArrayList<String> getSessionArray(int vectorID) {
        /*String[] sessionFruitVector = getResources().getStringArray(vectorID);
        if(vectorID == R.array.all_shapes_items){
            return common.getPartialArray(sessionFruitVector);
        }else {
            List<String> array = new ArrayList<>(Arrays.asList(sessionFruitVector));
            Collections.sort(array);
            return (ArrayList<String>) array;
        }*/
        return null;
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
    public void initGridView(String currentSubItem) {
        //NOT USED
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        presenter.setObjectCurrentPlayer();
        presenter.setSubStringCurrentPlayer();
        startActivity(new Intent(ActivityThreeTwo.this, StartGameActivity.class));
        finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
        Log.i("[ACTIVITY 32]", "I'm calling storeCurrentPlayer");

    }
}
