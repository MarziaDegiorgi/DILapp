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
import java.util.Collections;
import java.util.List;

/**
 * Final Activity which consist on two session: learning simple mathematical sums and then applied them
 * during an activity at the supermarket (counting object inserted in the chart)
 */
public class ActivityThreeOne extends AppCompatActivity implements IGame.View{

    private IGame.Presenter presenter;
    private List<String> countSequence;
    final String AUDIO = "request_";
    Handler myHandler;
    String element;
    private CommonActivity common;
    GridView gridview;
    GridView answerView;
    GridViewAdapter imageAdapter;
    GridViewAdapter answerAdapter;
    MediaPlayer request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game_level_3_1);

        try {
            presenter = new GamePresenter(this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myHandler = new Handler();
        common = new CommonActivity(presenter);

        this.setupCountSequence();

        boolean availability = presenter.checkNfcAvailability();
        if (availability) {
            setupVideoIntro();
        }else{
            finish();
        }
    }

    /**
     * setup the sequence of number to count
     */
    private void setupCountSequence() {
        String[] count = getResources().getStringArray(R.array.count);
        countSequence =common.getList(count);
    }

    /**
     * Set up the Intro Video and call the presenter to start the level
     */
    private void setupVideoIntro() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        common.startIntro(uri, countSequence,this);
    }

    /**
     * Set up the session Video
     * @param videoID chosen by the presenter
     */
    @Override
    public void setVideoView(int videoID) {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoID);
        common.startMainVideo(uri, this);
    }

    /**
     * The main animation to set up is the sequence of object that the child is requested to count
     * Here a gridView is initialized shown the number of objects that correspond to the number required
     *
     * @param currentElement chosen by the presenter is the number of object to setup
     */
    @Override
    public void setPresentationAnimation(String currentElement) {
        String number;
        common.enableKiteAnimationBackground(this, ActivityThreeOne.this);
        common.enableLionBackground(this);
        if(presenter.getNumberOfElements()>1){
            number = currentElement.substring(1,3);
        }else {
            number = currentElement.substring(1, 2);
        }

        Log.i("[ACTIVITY_COUNT]", "number to count : "+ number);
        setBlackboard(number);
        setAudioRequest();
    }

    /**
     * Used for multiple numbers that required to check each sub element
     * @param currentSubElement the element actually required by the presenter
     */
    @Override
    public void setSubItemAnimation(String currentSubElement) {

        int resourceID = presenter.getResourceId("_"+ presenter.getCurrentReadTag() , R.drawable.class);
        int audioId = presenter.getResourceId(AUDIO + "_"+ presenter.getCurrentReadTag(), R.raw.class);
        request = MediaPlayer.create(this, audioId);
        final AppCompatActivity activity = this;
        final Context context = ActivityThreeOne.this;

        answerView = findViewById(R.id.answerView);
        answerAdapter = new GridViewAdapter(this.getApplicationContext(), resourceID);
        answerView.setAdapter(answerAdapter);

        answerView.setVisibility(View.VISIBLE);

        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                common.enableLionHeadAnimation(context, activity);
                presenter.setEnableNFC();
                presenter.handleIntent(getIntent());
            }
        });
    }

    private void setAudioRequest(){

        int resourceId = presenter.getResourceId("request_count_object", R.raw.class);
        request = MediaPlayer.create(this, resourceId);
        final AppCompatActivity activity = this;
        final Context context = ActivityThreeOne.this;

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                request.start();
                request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (presenter.getMultipleElement() && presenter.getNumberOfElements() > 1) {
                            mp.release();
                            presenter.notifyFirstSubElement();
                        } else {
                            mp.release();
                            common.enableLionHeadAnimation(context, activity);
                            presenter.setEnableNFC();
                            presenter.handleIntent(getIntent());
                        }
                    }
                });
            }
        }, 500);
    }

    /**
     * In case of multiple object expected this method wait for the first element
     * @param currentSubItem : current sub element
     */
    @Override
    public void initGridView(String currentSubItem) {

        presenter.setEnableNFC();
        presenter.handleIntent(getIntent());

    }

    private void setBlackboard(String numberOfElements) {
        //take "apples" as objects to show
        int resourceID = presenter.getResourceId("apple" , R.drawable.class);

        int number = Integer.parseInt(numberOfElements);

        gridview = findViewById(R.id.objectView);
        imageAdapter = new GridViewAdapter(this.getApplicationContext(), resourceID);
        gridview.setAdapter(imageAdapter);

        for(int i=1; i<number; i++){
            imageAdapter.addImageResource(resourceID);
        }

        gridview.setVisibility(View.VISIBLE);

        Log.i("[ACTIVITY_COUNT]", " element: "+ element);
    }

    @Override
    public void setVideoCorrectAnswer() {
        //audio response
        int objectClaimedID = presenter.getResourceId(AUDIO + presenter.getCurrentElement(), R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
        final MediaPlayer correctAnswer = MediaPlayer.create(this, R.raw.request_correct_answer);
        final ImageView answer = findViewById(R.id.numberAnswer);
        final AppCompatActivity activity = this;

        final Animation rotate = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.rotation);

        final int elementID = presenter.getResourceId(presenter.getCurrentElement(), R.drawable.class);

        if(presenter.getMultipleElement()) {
            int lastItemID = presenter.getResourceId(AUDIO + "_" + presenter.getCurrentReadTag(), R.raw.class);
            int lastElementId= presenter.getResourceId("_"+presenter.getCurrentReadTag(),R.drawable.class);
            answerAdapter.addImageResource(lastElementId);
            MediaPlayer lastObject = MediaPlayer.create(this, lastItemID);
            lastObject.start();
            lastObject.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                    answer.setImageDrawable(getResources().getDrawable(elementID));
                    answer.setAnimation(rotate);
                    answer.setVisibility(View.VISIBLE);
                    answer.startAnimation(rotate);
                    }
                },1000);
            }
            });
        }else {
            answer.setImageDrawable(getResources().getDrawable(elementID));
            answer.setAnimation(rotate);
            answer.setVisibility(View.VISIBLE);
            answer.startAnimation(rotate);
        }
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                request.start();
                request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        correctAnswer.start();
                        correctAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                                common.disableLionHeadAnimation(activity);
                                disableViews();
                                presenter.chooseElement();
                            }
                        });
                    }
                });
            }
        },800);
    }

    private void disableAnswerGrid() {
        answerView.setVisibility(View.INVISIBLE);
        answerAdapter.clearImageResources();
        if(presenter.getMultipleElement()){
            disableAnswerGrid();
        }
    }

    @Override
    public void setVideoWrongAnswerToRepeat() {

        final MediaPlayer wrongAnswer = MediaPlayer.create(this, R.raw.request_wrong_answer_repeat);

        wrongAnswer.start();
        wrongAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                if (presenter.getMultipleElement()) {
                    presenter.setEnableNFC();
                    presenter.handleIntent(getIntent());
                } else {
                    setPresentationAnimation(presenter.getCurrentElement());
                }
            }
        });
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        request = MediaPlayer.create(this, R.raw.request_wrong_answer_go_on);
        request.start();
        request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                    disableViews();
                    mp.release();
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.chooseElement();
                        }
                    },1800);
            }
        });
    }

    @Override
    public void setRepeatOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityThreeOne");
        intent.putExtra("ButtonName", "Ripeti");
        startActivity(intent);
    }

    @Override
    public void setGoOnOrExitScreen() {
        Intent intent = new Intent(getApplicationContext(), EndLevelScreen.class);
        intent.putExtra("Activity","com.polimi.dilapp.levels.view.ActivityThreeTwo");
        intent.putExtra("ButtonName", "Avanti");
        startActivity(intent);
    }

    @Override
    public void disableViews() {
        ImageView answer =  findViewById(R.id.numberAnswer);
        gridview.setVisibility(View.INVISIBLE);
        imageAdapter.clearImageResources();
        common.disableView(answer);
    }

    @Override
    public void sendEmail(String email, String subject) {

    }

    @Override
    public List<String> getSessionArray(int vectorID) {

        String[] sessionCountVector = getResources().getStringArray(vectorID);
        List<String> array = new ArrayList<>(Arrays.asList(sessionCountVector));
        Collections.sort(array);
        return array;
    }

    @Override
    public String getString() {
        return "ActivityThreeOne";
    }

    @Override
    public Class getApplicationClass() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
        Log.i("[ACTIVITY 31]", "I'm calling storeCurrentPlayer");
        Log.i("[ACTIVITY 31]", "SavedInstaceState "+String.valueOf(savedInstanceState));
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
        startActivity(new Intent(ActivityThreeOne.this, StartGameActivity.class));
        finish();
    }
}
