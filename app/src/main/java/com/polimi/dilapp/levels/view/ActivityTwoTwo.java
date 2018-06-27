package com.polimi.dilapp.levels.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

    private boolean isBound = false;
    private MusicService musicService;
    private boolean isPlaying = true;

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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);

        try {
            presenter = new GamePresenter(this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        common = new CommonActivity(presenter);
        common.setAnimations(ActivityTwoTwo.this);

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
        if(musicService != null && isBound && isPlaying){
            musicService.pauseMusic();
        }
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_2_2);
        common.startIntro(uri, alphabetSequence,this);
    }

    @Override
    public void setVideoView(int videoID) {
        if(musicService != null && isBound && isPlaying){
            if(musicService.isPlaying()) {
                musicService.pauseMusic();
            }
        }
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoID);
        common.startMainVideo(uri, this);
    }

    @Override
    public void setPresentationAnimation(String currentElement) {
        if(musicService!= null && isBound && isPlaying){
            musicService.resumeMusic();
        }
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

    @Override
    public List<String> getSessionArray(int vectorID) {
        String[] sessionNumberVector = getResources().getStringArray(vectorID);
        return new ArrayList<>(Arrays.asList(sessionNumberVector));
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
    public Class getApplicationClass() {
        return this.getClass();
    }

    @Override
    public void setVideoCorrectAnswer() {
        disableViews();

        ImageView image = findViewById(R.id.animation_box_answer);
        int imageID = presenter.getResourceId(element, R.drawable.class);
        Animation answerAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_and_bounce);

        image.setImageDrawable(ContextCompat.getDrawable(ActivityTwoTwo.this ,imageID));
        image.setAnimation(answerAnimation);
        image.setVisibility(View.VISIBLE);
        image.startAnimation(answerAnimation);

        common.setCorrectAnswer(image, this);
    }

    @Override
    public void setVideoWrongAnswerToRepeat() {
        disableViews();

        common.setWrongAnswerToRepeat(ActivityTwoTwo.this);
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        disableViews();

        common.setWrongAnswerAndGoOn( ActivityTwoTwo.this);
    }

    @Override
    public void disableViews(){
        ImageView imageToHide = findViewById(R.id.animation_box);
        ImageView imageAnswer = findViewById(R.id.animation_box_answer);
        common.disableView(imageToHide);
        common.disableView(imageAnswer);
    }

    @Override
    public void disableImageView() {
        //not used
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityTwoTwo");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
        overridePendingTransition(R.anim.left_enter, R.anim.left_exit);
        finish();
    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityTwoThree");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);
        overridePendingTransition(R.anim.left_enter, R.anim.left_exit);
        finish();
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
    public void initTableView(String currentSubItem) {
        //The gridView is not used
    }

    @Override
    protected void onResume() {
        super.onResume();
       presenter.setupForegroundDispatch();
        if(!isBound){
            doBindService();
        }
        if(musicService!= null && isBound && isPlaying){
            musicService.resumeMusic();
        }
    }

    @Override
    protected void onPause() {
      presenter.stopForegroundDispatch();
        super.onPause();
        if(musicService!= null && isBound && isPlaying){
            musicService.pauseMusic();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        if(isBound){
            doUnbindService();
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
        presenter.getEndTime();
        presenter.setObjectCurrentPlayer();
        presenter.setSubStringCurrentPlayer();
        if(request != null){
            request.release();
            request = null;
        }
        startActivity(new Intent(ActivityTwoTwo.this, StartGameActivity.class));
        finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
        Log.i("[ACTIVITY 22]", "I'm calling storeCurrentPlayer");

    }


    public void onMusicChange(View view) {
        ImageView speaker = findViewById(R.id.music);
        if(musicService!=null && isBound){
            if(musicService.isPlaying()){
                speaker.setImageDrawable(getDrawable(R.drawable.music_off));
                musicService.pauseMusic();
                isPlaying = false;
            }else {
                speaker.setImageDrawable(getDrawable(R.drawable.music_on));
                musicService.resumeMusic();
                isPlaying = true;
            }
        }
    }
}
