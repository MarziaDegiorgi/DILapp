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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;
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

public class ActivityThreeTwo extends AppCompatActivity implements IGame.View{

    ArrayList<String> recipeSequence;
    IGame.Presenter presenter;
    MediaPlayer request;
    String element;
    CommonActivity common;
    String object;
    int counterID = 0;
    private Handler myHandler = new Handler();
    private Animation animationOut;

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
        Intent intent = getIntent();
        object = intent.getStringExtra("object");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game_level_3_2);

        //set up the presenter and pass it to the common activity view
        try {
            presenter = new GamePresenter(this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        common = new CommonActivity(presenter);
        common.setAnimations(ActivityThreeTwo.this);
        presenter.setRecipeLevel();
        setupSequence();

        boolean availability = presenter.checkNfcAvailability();
        if (availability) {
            setupVideoIntro();
        }else{
            finish();
        }
    }

    private void setupSequence(){
        String[] recipes = getResources().getStringArray(R.array.recipes);
        recipeSequence = common.getList(recipes);

    }

    private void setupVideoIntro(){
        //Introduction to the whole activity game
        if(musicService != null && isBound && isPlaying){
            musicService.pauseMusic();
        }
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_3_2);
        common.startIntro(uri, recipeSequence,this);
    }

    @Override
    public void disableViews(){
        counterID = 0;
        for(int i=0; i<6; i++){
            int imageToCheckId = presenter.getResourceId("answer"+i, R.id.class);
            final ImageView answerToCheck = findViewById(imageToCheckId);
                answerToCheck.setVisibility(View.INVISIBLE);
                answerToCheck.setTag("none");
            }
        }



    @Override
    public void setVideoView(int videoID){
        disableViews();
        if(musicService != null && isBound && isPlaying){
            if(musicService.isPlaying()) {
                musicService.pauseMusic();
            }
        }
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoID);
        //common.startMainVideo(uri, this);
        final VideoView video = findViewById(R.id.video_box);
        video.setVisibility(View.VISIBLE);
        video.setVideoURI(uri);

        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video.setVisibility(View.INVISIBLE);
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.chooseElement();
                    }
                },600);
                mp.release();
            }
        });
    }

    @Override
    public void setPresentationAnimation(String currentElement){
        element = currentElement;
        if(musicService!= null && isBound && isPlaying){
            musicService.resumeMusic();
        }
        setLionHeadAnimation();
        setAudioRequest();
    }

    private void setAudioRequest(){
        int objectClaimedID = presenter.getResourceId("request_recipe_element", R.raw.class);
        request = MediaPlayer.create(ActivityThreeTwo.this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopLionHeadAnimation();
                setWaitingAnimation();
                mp.release();
                presenter.setEnableNFC();
                presenter.handleIntent(getIntent());
            }
        });
    }

    private void disableImage(String element){
        for(int i=0; i<6; i++){
            int imageToCheckId = presenter.getResourceId("imageView"+i, R.id.class);
            final ImageView imageToCheck = findViewById(imageToCheckId);
            String stringToCheck = (String) imageToCheck.getTag();
            Log.i("tag", stringToCheck);
            Log.i("element", element);
            if(stringToCheck.equals(element)){
                imageToCheck.setVisibility(View.INVISIBLE);
                imageToCheck.setTag("none");
                i = 7;
            }
        }
    }

    private void setLionHeadAnimation(){
        ImageView lionHeadImage = findViewById(R.id.lion_head_game_3);
        lionHeadImage.setVisibility(View.VISIBLE);
        Animation animationLionHead = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.lion_rotation_waiting);
        lionHeadImage.setAnimation(animationLionHead);
        lionHeadImage.startAnimation(animationLionHead);
    }

    private void stopLionHeadAnimation(){
        ImageView lionHeadImage = findViewById(R.id.lion_head_game_3);
        lionHeadImage.setVisibility(View.VISIBLE);
        lionHeadImage.clearAnimation();
    }

    public void setWaitingAnimation(){
        ImageView lionHeadImage = findViewById(R.id.lion_head_game_3);
        ImageView lionTaleImage = findViewById(R.id.tale_game_3);
        ImageView lionBodyImage = findViewById(R.id.lion_body_game_3);
        lionBodyImage.setVisibility(View.VISIBLE);
        lionTaleImage.setVisibility(View.VISIBLE);
        lionHeadImage.setVisibility(View.VISIBLE);

        Animation animationLionWait = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.tale_rotation);
        lionTaleImage.setAnimation(animationLionWait);
        lionTaleImage.setVisibility(View.VISIBLE);
        lionTaleImage.startAnimation(animationLionWait);
    }

    private void setupViewItems(List<String> arrayList){
        List<String> temporalArray = arrayList;
        int size = temporalArray.size();
        for(int i = 0; i<size; i++){
            String temporalElement = temporalArray.get(i);
            int claimedImageView = presenter.getResourceId("imageView"+ i, R.id.class);
            final ImageView image = findViewById(claimedImageView);
            image.setVisibility(View.VISIBLE);
            int claimedDrawableID = presenter.getResourceId(temporalElement, R.drawable.class);
            image.setImageDrawable(getResources().getDrawable(claimedDrawableID));
            image.setTag(temporalElement);
            Log.i("[Tag]",(String) image.getTag());
        }
    }

    @Override
    public void setVideoCorrectAnswer(){
        setLionHeadAnimation();
       String correctElement = presenter.getCurrentReadTag();
       int resourceID = presenter.getResourceId(correctElement, R.drawable.class);
       int imageID = presenter.getResourceId("answer"+ counterID, R.id.class);
       counterID ++;
       final ImageView image = findViewById(imageID);
       image.setImageDrawable(getResources().getDrawable(resourceID));
       image.setVisibility(View.VISIBLE);
       disableImage(correctElement);
       MediaPlayer request = MediaPlayer.create(ActivityThreeTwo.this, R.raw.request_correct_answer);
       request.start();

        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopLionHeadAnimation();
                mp.release();
                //delay the choose of the next element of 1 sec
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.chooseElement();
                    }
                },1000);
            }
        });

    }

    @Override
    public void setVideoWrongAnswerToRepeat() {
        setLionHeadAnimation();
        MediaPlayer request = MediaPlayer.create(ActivityThreeTwo.this, R.raw.wrong_answer_to_repeat_recipe);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setPresentationAnimation(presenter.getCurrentSequenceElement());
                mp.release();
            }
        });
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        setLionHeadAnimation();
        MediaPlayer request = MediaPlayer.create(this, R.raw.request_wrong_answer_go_on);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                presenter.chooseElement();
                mp.release();
            }
        });
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityThreeTwo");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
        overridePendingTransition(R.anim.left_enter, R.anim.left_exit);
        finish();
    }

    @Override
    public void setGoOnOrExitScreen() {
        if(musicService != null && isBound && isPlaying){
            musicService.stopMusic();
            doUnbindService();
        }
        final VideoView video = findViewById(R.id.video_box);
        video.setVisibility(View.VISIBLE);
        Uri uriEnd = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_end_game);
        video.setVideoURI(uriEnd);
        Animation animationIn = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.alpha_enter);
        animationOut = AnimationUtils.loadAnimation(ActivityThreeTwo.this, R.anim.alpha_exit);
        video.setAnimation(animationIn);
        video.startAnimation(animationIn);
        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video.setAnimation(animationOut);
                video.startAnimation(animationOut);
                video.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), StartGameActivity.class);
                intent.putExtra("Activity","com.polimi.dilapp.startgame.StartGameActivity");
                intent.putExtra("ButtonName", "Avanti");
                startActivity(intent);
                overridePendingTransition(R.anim.left_enter, R.anim.left_exit);
                mp.release();
                finish();

            }
        });
    }

    @Override
    public String getString() {
        return "ActivityThreeTwo";
    }

    @Override
    public ArrayList<String> getSessionArray(int vectorID) {
        String[] sessionFruitVector = getResources().getStringArray(vectorID);
        List<String> array = new ArrayList<>(Arrays.asList(sessionFruitVector));
        Collections.sort(array);
        setupViewItems(array);
        return (ArrayList<String>) array;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        if(isBound){
            doUnbindService();
        }
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
       doUnbindService();
        presenter.getEndTime();
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
                isPlaying = false;
            }else {
                speaker.setImageDrawable(getDrawable(R.drawable.music_on));
                musicService.resumeMusic();
                isPlaying = true;
            }
        }
    }


}
