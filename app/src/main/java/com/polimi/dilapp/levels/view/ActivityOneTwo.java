package com.polimi.dilapp.levels.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
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
import com.polimi.dilapp.main.MusicService;
import com.polimi.dilapp.startgame.StartGameActivity;
import java.text.ParseException;
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

    private boolean isBound = false;
    private MusicService musicService;

    private ServiceConnection connection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            musicService = ((MusicService.ServiceBinder)binder).getService();
            Log.i("[Connection]", "Music Service Created ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
            Log.i("[Connection]", "Music Service Created ");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
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
        common.setAnimations(ActivityOneTwo.this);
        presenter.setColourLevel();
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
        if(musicService != null && isBound && presenter.isMusicPlaying()){
            musicService.pauseMusic();
        }
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_1_2);
        common.startIntro(uri, colorSequence,this);
    }

    @Override
    public void disableViews(){
        ImageView imageToHide = findViewById(R.id.animation_box);
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        ImageView animationBoxAnswer = findViewById(R.id.animation_box_answer);
        common.disableView(animationBoxAnswer);
        common.disableView(imageToHide);
        common.disableView(animationViewExtra);
        common.disableView(animationViewExtraTwo);
    }

    @Override
    public void disableImageView() {
        ImageView imageToHide = findViewById(R.id.animation_box);
        imageToHide.clearAnimation();
        imageToHide.setImageDrawable(null);
        imageToHide.setVisibility(View.INVISIBLE);
    }


    @Override
    public void setVideoView(int videoID){
        if(musicService != null && isBound && presenter.isMusicPlaying()){
            if(musicService.isPlaying()) {
                musicService.pauseMusic();
            }
        }
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoID);
        common.startMainVideo(uri, this);
    }

    @Override
    public void setPresentationAnimation(String currentElement){
        if(musicService!= null && isBound && presenter.isMusicPlaying()){
            musicService.resumeMusic();
        }
        element = currentElement;
        currentColour = presenter.getCurrentSequenceElement();
        int resourceID = presenter.getResourceId(currentColour, R.drawable.class);
        Animation animationBegin = AnimationUtils.loadAnimation(ActivityOneTwo.this, R.anim.waiting_rotation);
        common.enableLionHeadAnimation(ActivityOneTwo.this, this);
        common.startMainAnimation(this,animationBegin,resourceID,this);

        setAudioRequest();
    }

    private void setAudioRequest(){
        int objectClaimedID;
        objectClaimedID = presenter.getResourceId("request_" + currentColour + "_item", R.raw.class);
        final AppCompatActivity activity = this;

        request = MediaPlayer.create(ActivityOneTwo.this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
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
        Animation animationWait = AnimationUtils.loadAnimation(ActivityOneTwo.this, R.anim.bounce);
        ImageView mainImage = findViewById(R.id.animation_box);
        mainImage.clearAnimation();
        mainImage.setImageDrawable(null);
        common.enableLionBackground(this);
        common.enableLionTailAnimation(this, ActivityOneTwo.this);

        common.startMainAnimation(this,animationWait,resourceID,this);
    }

    @Override
    public void setVideoCorrectAnswer(){
        disableViews();
        ImageView mainImage = findViewById(R.id.animation_box);
        mainImage.clearAnimation();
        mainImage.setImageDrawable(null);
        mainImage.setVisibility(View.INVISIBLE);

        common.enableLionHeadAnimation(ActivityOneTwo.this, this);
        String currentReadTag = presenter.getCurrentReadTag();
        int resourceID = presenter.getResourceId(currentReadTag, R.drawable.class);
        final ImageView image = findViewById(R.id.animation_box_answer);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(ContextCompat.getDrawable(ActivityOneTwo.this,resourceID));
        image.setVisibility(View.VISIBLE);

        Animation animationCorrect = AnimationUtils.loadAnimation(ActivityOneTwo.this, R.anim.bounce);
        image.setAnimation(animationCorrect);
        image.startAnimation(animationCorrect);
        common.setCorrectAnswer(image, ActivityOneTwo.this);
    }

    @Override
    public void setVideoWrongAnswerToRepeat() {
        disableViews();
        common.enableLionHeadAnimation(ActivityOneTwo.this, this);
        MediaPlayer wrongAnswer = MediaPlayer.create(this, R.raw.wrong_answer_to_repeat_color);
        wrongAnswer.start();
        wrongAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setPresentationAnimation(currentColour);
                mp.release();
            }
        });
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        disableViews();
        common.enableLionHeadAnimation(ActivityOneTwo.this, this);
        MediaPlayer wrongAnswer = MediaPlayer.create(this, R.raw.request_wrong_answer_go_on);
        wrongAnswer .start();
        wrongAnswer .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ImageView mainImage = findViewById(R.id.animation_box);
                mainImage.setImageDrawable(null);
                mainImage.clearAnimation();
                mainImage.setVisibility(View.INVISIBLE);
                presenter.chooseElement();
                mp.release();
            }
        });
    }


    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityOneTwo");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
        overridePendingTransition(R.anim.left_enter, R.anim.left_exit);
        finish();
    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityOneThree");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);
        overridePendingTransition(R.anim.left_enter, R.anim.left_exit);
        finish();
    }

    @Override
    public String getString() {
        return "ActivityOneTwo";
    }

    @Override
    public void setSubItemAnimation(String currentSubElement) {
       //Not used in this class
    }

    @Override
    public void initTableView(String currentSubItem) {
        //NOT USED
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
        presenter = null;
        common = null;
        if(isBound){
            doUnbindService();
        }
    }

    @Override
    public Class getApplicationClass(){

        return this.getClass();
    }


    @Override
    public void stopAnimationSubItem() {
        //NOT USED
    }

    @Override
    public Context getScreenContext() {

        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setupForegroundDispatch();
        if(!isBound && presenter.isMusicPlaying()){
            doBindService();
        }
        if(musicService!= null && isBound && presenter.isMusicPlaying()){
            musicService.resumeMusic();
        }
    }

    @Override
    protected void onPause() {
        presenter.stopForegroundDispatch();
        super.onPause();
        if(musicService!= null && isBound && presenter.isMusicPlaying()){
            musicService.pauseMusic();
        }
    }

    //onNewIntent let us stay in the same activity after reading a TAG
    @Override
    protected void onNewIntent(Intent intent) {
        presenter.handleIntent(intent);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        doUnbindService();
        disableViews();
        if(request != null){
            request.release();
            request = null;
        }
        common.onDestroy();
        presenter.getEndTime();
        presenter.setObjectCurrentPlayer();
        presenter.setSubStringCurrentPlayer();
        startActivity(new Intent(ActivityOneTwo.this, StartGameActivity.class));
        finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
        Log.i("[ACTIVITY 12]", "I'm calling storeCurrentPlayer");
    }


    void doBindService(){
        Intent backgroundMusic = new Intent(this, MusicService.class);
        bindService(backgroundMusic,
                connection,Context.BIND_AUTO_CREATE);
        isBound = true;
        Log.i("[MainActivity]", "[BindedService]");
    }

    void doUnbindService(){
        if(isBound){
            unbindService(connection);
            isBound = false;
        }
    }


    public void onMusicChange(View view) {
        ImageView speaker = findViewById(R.id.music);
        if(musicService!=null && isBound){
            if(musicService.isPlaying()){
                speaker.setImageDrawable(getDrawable(R.drawable.music_off));
                musicService.pauseMusic();
                presenter.setMusicPlaying(false);
            }else {
                speaker.setImageDrawable(getDrawable(R.drawable.music_on));
                musicService.resumeMusic();
                presenter.setMusicPlaying(true);
            }
        }
        else if(!isBound){
            doBindService();
            presenter.setMusicPlaying(true);
        }
    }
}
