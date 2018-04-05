package com.polimi.dilapp.levels.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.GamePresenter;
import com.polimi.dilapp.levels.GridViewAdapter;
import com.polimi.dilapp.levels.IGame;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activity View referred to 2.3 Level : Learning WORDS
 */

public class ActivityTwoThree extends AppCompatActivity implements IGame.View {

    private IGame.Presenter presenter;
    private ArrayList<String> wordsSequence;
    private CommonActivity common;
    String element;
    MediaPlayer request;
    GridView gridview;
    GridViewAdapter imageAdapter;
    private final String AUDIO = "request_";
    Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);

        try {
            presenter = new GamePresenter(this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        String[] words = getResources().getStringArray(R.array.words);
        wordsSequence = common.getList(words);
    }

    private void setupVideoIntro() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        common.startIntro(uri, wordsSequence,this);
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

        Animation animationBegin = AnimationUtils.loadAnimation(ActivityTwoThree.this, R.anim.combination_set);

        final ImageView image = findViewById(R.id.animation_box);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(ContextCompat.getDrawable(ActivityTwoThree.this,resourceID));
        image.setVisibility(View.VISIBLE);

        image.setAnimation(animationBegin);
        image.startAnimation(animationBegin);

        int objectClaimedID = presenter.getResourceId("request_word", R.raw.class);
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
    public void setSubItemAnimation(String currentSubElement){
        int resourceID = presenter.getResourceId(currentSubElement, R.drawable.class);

        gridview.setVisibility(View.VISIBLE);
        imageAdapter.addImageResource(resourceID);
        imageAdapter.notifyDataSetChanged();

        //set subItem audio request
        requestSubItem(currentSubElement);
    }

    @Override
    public void initGridView(String currentSubItem) {
        //resource of the first sub element required
        int resourceID = presenter.getResourceId(currentSubItem, R.drawable.class);
        //resource of the element required
        int wordID = presenter.getResourceId(element, R.drawable.class);

        //set up the gridView with the adapter
        gridview = findViewById(R.id.gridView);
        imageAdapter = new GridViewAdapter(this, resourceID);
        gridview.setAdapter(imageAdapter);

        //set up the image of the object required
        ImageView word = findViewById(R.id.image_box_multiple_elements);
        word.setImageDrawable(ContextCompat.getDrawable(ActivityTwoThree.this,wordID));

        //set up visibility of the gridview and the image
        gridview.setVisibility(View.VISIBLE);
        word.setVisibility(View.VISIBLE);

        //audio request
        int objectClaimedID = presenter.getResourceId(AUDIO + currentSubItem, R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                presenter.setEnableNFC();
                presenter.handleIntent(getIntent());
            }
        });
    }

    private void setAudioRequest(final ImageView image){
        int objectClaimedID = presenter.getResourceId(AUDIO + element, R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                request.start();
                request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (presenter.getMultipleElement()) {
                            image.setVisibility(View.INVISIBLE);
                            presenter.notifyFirstSubElement();
                        } else {
                            setWaitingAnimation();
                            presenter.setEnableNFC();
                            presenter.handleIntent(getIntent());
                        }
                    }
                });
            }
        }, 700);
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

        //takes image associated with the word
        int imageId = presenter.getResourceId("img" + element, R.drawable.class);
        final ImageView image = findViewById(R.id.animation_box_answer);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(ContextCompat.getDrawable(ActivityTwoThree.this,imageId));
        image.setVisibility(View.VISIBLE);

        //select the animation response
        Animation animation;
        switch (element) {
        case "_faro":
            animation = AnimationUtils.loadAnimation(this, R.anim.blink);
            image.setAnimation(animation);
            break;
        case "_mare":
            animation = AnimationUtils.loadAnimation(this, R.anim.move);
            image.setAnimation(animation);
            break;
        case "_noce":
            animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
            image.setAnimation(animation);
            break;
        default:
            animation = AnimationUtils.loadAnimation(this, R.anim.jump_and_rotate);
            image.setAnimation(animation);
            break;
        }

        //audio response
        MediaPlayer correctAnswer = MediaPlayer.create(this, R.raw.request_correct_answer);
        image.startAnimation(animation);
        correctAnswer.start();
        correctAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                image.clearAnimation();
                image.setVisibility(View.INVISIBLE);
                mp.release();
                presenter.chooseElement();
            }
        });
    }

    void disableImageView(ImageView image){
        image.setVisibility(View.INVISIBLE);
    }

    void requestSubItem( String currentSubElement) {
        //set subItem audio request
        int objectClaimedID = presenter.getResourceId(AUDIO + currentSubElement, R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                request.start();
                request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        presenter.setEnableNFC();
                        presenter.handleIntent(getIntent());
                    }
                });
            }
        },800);
    }

    @Override
    public void setVideoWrongAnswerToRepeat() {
        MediaPlayer wrongAnswer = MediaPlayer.create(this, R.raw.request_wrong_answer_repeat);
        wrongAnswer.start();
        wrongAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                requestSubItem(presenter.getCurrentSubElement());
            }
        });
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        MediaPlayer wrongAnswer = MediaPlayer.create(this, R.raw.request_wrong_answer_go_on);
        wrongAnswer.start();
        wrongAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(presenter.getNumberOfElements() > 0) {
                    mp.release();
                }else {
                    disableViews();
                    mp.release();
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.chooseElement();
                        }
                    },1800);
                }
            }
        });
    }

    @Override
    public void disableViews(){
        ImageView imageToHide = findViewById(R.id.animation_box);
        ImageView requestObject = findViewById(R.id.image_box_multiple_elements);
        ImageView answerBox = findViewById(R.id.animation_box_answer);

        gridview.setVisibility(View.INVISIBLE);
        imageAdapter.clearImageResources();

        this.disableImageView(answerBox);
        this.disableImageView(imageToHide);
        this.disableImageView(requestObject);
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityTwoThree");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityThreeOne");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);
    }

    @Override
    public String getString() {
        return "ActivityTwoThree";
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
    public void onBackPressed()
    {
        super.onBackPressed();
        presenter.getEndTime();
        presenter.setObjectCurrentPlayer();
        presenter.setSubStringCurrentPlayer();
        startActivity(new Intent(ActivityTwoThree.this, StartGameActivity.class));
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
        Log.i("[ACTIVITY 23]", "I'm calling storeCurrentPlayer");
        Log.i("[ACTIVITY 23]", "SavedInstaceState "+ String.valueOf(savedInstanceState));
    }
}
