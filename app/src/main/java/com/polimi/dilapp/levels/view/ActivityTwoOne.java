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
import android.widget.GridView;
import android.widget.ImageView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.GamePresenter;
import com.polimi.dilapp.levels.GridViewAdapter;
import com.polimi.dilapp.levels.IGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activity View referred to 2.1 Level : Learning NUMBERS
 */
public class ActivityTwoOne extends AppCompatActivity implements IGame.View{

    private static final String AUDIO = "request__";
    private IGame.Presenter presenter;
    private ArrayList<String> numberSequence;
    private CommonActivity common;
    String element;
    MediaPlayer request;
    GridView gridview;
    GridViewAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);

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

        final ImageView image = findViewById(R.id.animation_box);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(getResources().getDrawable(resourceID));
        image.setVisibility(View.VISIBLE);

        image.setAnimation(animationBegin);
        image.startAnimation(animationBegin);
        int objectClaimedID = presenter.getResourceId("request_item", R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                setAudioRequest(image);
            }
        });
    }

    @Override
    public void initGridView(String currentSubItem){
        int resourceID = presenter.getResourceId("_"+currentSubItem, R.drawable.class);
        gridview = findViewById(R.id.gridView);
        imageAdapter = new GridViewAdapter(this, resourceID);
        gridview.setAdapter(imageAdapter);
        gridview.setVisibility(View.VISIBLE);
        int objectClaimedID = presenter.getResourceId(AUDIO +currentSubItem, R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                presenter.handleIntent(getIntent());
            }
        });
    }

    @Override
    public void setSubItemAnimation(String currentSubElement){
        int resourceID = presenter.getResourceId("_"+currentSubElement, R.drawable.class);

        gridview.setVisibility(View.VISIBLE);
        imageAdapter.addImageResource(resourceID);
        imageAdapter.notifyDataSetChanged();

        //set subItem audio request
        int objectClaimedID = presenter.getResourceId(AUDIO + currentSubElement, R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                presenter.handleIntent(getIntent());
            }
        });
    }

    private void setAudioRequest(final ImageView image){
        int objectClaimedID = presenter.getResourceId("request_" + element, R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(presenter.getMultipleElement()) {
                    image.setVisibility(View.INVISIBLE);
                    mp.release();
                    presenter.notifyFirstSubElement();
                }else {
                    setWaitingAnimation();
                    mp.release();
                    presenter.handleIntent(getIntent());
                }
            }
        });
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
        int resourceID = presenter.getResourceId(element, R.drawable.class);
        image.getResources().getDrawable(resourceID);
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

    /**
     *  Clean the XML resources used
     */
    private void disableViews(){
        gridview.setVisibility(View.INVISIBLE);
        imageAdapter.clearImageResources();

        ImageView imageToHide = findViewById(R.id.animation_box);
        ImageView animationViewExtra = findViewById(R.id.animation_box_two);
        ImageView animationViewExtraTwo = findViewById(R.id.animation_box_three);
        common.disableView(imageToHide);
        common.disableView(animationViewExtra);
        common.disableView(animationViewExtraTwo);
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityTwoOne");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityTwoTwo");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);
    }

    @Override
    public String getString() {
        return "ActivityTwoOne";
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

    public List<String> getSequence(){
        return numberSequence;
    }

    public IGame.Presenter getPresenter(){
        return presenter;
    }

    public CommonActivity getCommonActivity(){
        return common;
    }
}
