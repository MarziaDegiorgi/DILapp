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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

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
 * Activity View referred to 2.1 Level : Learning NUMBERS
 */
public class ActivityTwoOne extends AppCompatActivity implements IGame.View{

    private static final String AUDIO = "request__";
    private IGame.Presenter presenter;
    private ArrayList<String> numberSequence;
    private CommonActivity common;
    String element;
    MediaPlayer request;
    TableRow table;
    int num_column;

    Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        num_column = 1;

        Intent intent = getIntent();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
        String[] numbers = getResources().getStringArray(R.array.numbers);
        numberSequence = common.getList(numbers);
    }

    private void setupVideoIntro() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_2_1);
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
        image.setImageDrawable(ContextCompat.getDrawable(ActivityTwoOne.this,resourceID));
        image.setAnimation(animationBegin);
        image.setVisibility(View.VISIBLE);
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
    public void initTableView(String currentSubItem){
        int elementID = presenter.getResourceId(element, R.drawable.class);
        int resourceID = presenter.getResourceId("_"+currentSubItem, R.drawable.class);
        num_column = 1;

        //set up the current element required
        ImageView image = findViewById(R.id.image_box_multiple_elements);
        image.setImageDrawable(ContextCompat.getDrawable(ActivityTwoOne.this,elementID));
        image.setVisibility(View.VISIBLE);

        //set up the table and the first column(sub element required)
        if(table == null) {
            table = findViewById(R.id.table);
        }
        table.setVisibility(View.VISIBLE);
        ImageView firstSubElement = findViewById(R.id.column_1);
        firstSubElement.setImageDrawable(ContextCompat.getDrawable(ActivityTwoOne.this, resourceID));
        firstSubElement.setVisibility(View.VISIBLE);
        this.startAnimationSubItem(firstSubElement);

        int objectClaimedID = presenter.getResourceId(AUDIO +currentSubItem, R.raw.class);
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
        },1000);
    }

    private void startAnimationSubItem(ImageView image){
        Animation animationWait = AnimationUtils.loadAnimation(this, R.anim.blink);
        animationWait.setRepeatCount(4);
        image.setAnimation(animationWait);
        image.startAnimation(animationWait);
    }

    @Override
    public void setSubItemAnimation(String currentSubElement){
        int resourceID = presenter.getResourceId("_"+currentSubElement, R.drawable.class);
        num_column++;

        final ImageView subItemImage = getImageView();

        if(subItemImage!=null) {
            subItemImage.setImageDrawable(ContextCompat.getDrawable(this, resourceID));
        }
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
                        if(subItemImage != null){
                            subItemImage.setVisibility(View.VISIBLE);
                            startAnimationSubItem(subItemImage);
                        }
                        presenter.setEnableNFC();
                        presenter.handleIntent(getIntent());
                    }
                });
            }
        },500);
    }

    private ImageView getImageView(){
        ImageView columnView;

        switch (num_column){
            case 1:
                columnView = findViewById(R.id.column_1);
                break;
            case 2:
                columnView = findViewById(R.id.column_2);
                break;
            default:
                columnView = null;
                break;
        }
        return columnView;
    }

    private void setAudioRequest(final ImageView image){
        int objectClaimedID = presenter.getResourceId("request_" + element, R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
        final  AppCompatActivity activity = this;

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                request.start();
                request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (presenter.getMultipleElement()) {
                            image.setVisibility(View.INVISIBLE);
                            mp.release();
                            presenter.notifyFirstSubElement();
                        } else {
                            mp.release();
                            startAnimationSubItem(image);
                            common.disableLionHeadAnimation(activity);
                            presenter.setEnableNFC();
                            presenter.handleIntent(getIntent());
                        }
                    }
                });
            }
        }, 500);
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

        ImageView mainImage = findViewById(R.id.animation_box);
        mainImage.clearAnimation();

        common.enableLionHeadAnimation(ActivityTwoOne.this, this);

        int resourceID = presenter.getResourceId(element, R.drawable.class);

        final ImageView image = findViewById(R.id.animation_box_answer);
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(ContextCompat.getDrawable(ActivityTwoOne.this,resourceID));
        image.setVisibility(View.VISIBLE);

        Animation animationCorrect = AnimationUtils.loadAnimation(ActivityTwoOne.this, R.anim.bounce);
        image.setAnimation(animationCorrect);
        image.startAnimation(animationCorrect);
        common.setCorrectAnswer(image, ActivityTwoOne.this);
    }

    @Override
    public void setVideoWrongAnswerToRepeat() {
        MediaPlayer wrongAnswer = MediaPlayer.create(this, R.raw.request_wrong_answer_repeat);
        wrongAnswer.start();
        wrongAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                if(presenter.getMultipleElement()){
                    requestSubItem(presenter.getCurrentSubElement());
                }else {
                    setPresentationAnimation(presenter.getCurrentElement());
                }
            }
        });
        common.enableLionHeadAnimation(ActivityTwoOne.this, this);
    }

    void requestSubItem( String currentSubElement) {
        //set subItem audio request
        int objectClaimedID = presenter.getResourceId(AUDIO + currentSubElement, R.raw.class);
        request = MediaPlayer.create(this, objectClaimedID);
        final ImageView imageCurrentSubItem = getImageView();

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                request.start();
                request.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        startAnimationSubItem(imageCurrentSubItem);
                        mp.release();
                        presenter.setEnableNFC();
                        presenter.handleIntent(getIntent());
                    }
                });
            }
        },1500);
    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {
        MediaPlayer wrongAnswer = MediaPlayer.create(this, R.raw.request_wrong_answer_go_on);
        wrongAnswer.start();
        common.enableLionHeadAnimation(ActivityTwoOne.this, this);
        wrongAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(presenter.getNumberOfElements()>0) {
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

    /**
     *  Clean the XML resources used
     */
    @Override
    public void disableViews(){

        ImageView imageToHide = findViewById(R.id.animation_box);
        ImageView imageBoxMultipleItem = findViewById(R.id.image_box_multiple_elements);
        ImageView imageAnswer = findViewById(R.id.animation_box_answer);

        if(presenter.getMultipleElement()) {
          ImageView column1 = findViewById(R.id.column_1);
          ImageView column2 = findViewById(R.id.column_2);

          common.disableView(column1);
          common.disableView(column2);
          table.setVisibility(View.INVISIBLE);
        }

        common.disableView(imageToHide);
        common.disableView(imageAnswer);
        common.disableView(imageBoxMultipleItem);
    }

    @Override
    public void sendEmail(Intent i) {
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ActivityTwoOne.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
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

    public IGame.Presenter getPresenter(){
        return presenter;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        presenter.getEndTime();
        presenter.setObjectCurrentPlayer();
        presenter.setSubStringCurrentPlayer();
        if (request!=null){
            request.release();
            request = null;
        }
        startActivity(new Intent(ActivityTwoOne.this, StartGameActivity.class));
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
        Log.i("[ACTIVITY 21]", "I'm calling storeCurrentPlayer");

    }


}
