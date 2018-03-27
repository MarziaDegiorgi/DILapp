package com.polimi.dilapp.levels.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.GamePresenter;
import com.polimi.dilapp.levels.IGame;
import com.polimi.dilapp.startgame.StartGameActivity;

import java.util.List;

/**
 * Final Activity which consist on two session: learning simple mathematical sums and then applied them
 * during an activity at the supermarket (counting object inserted in the chart)
 */
public class ActivityThreeOne extends AppCompatActivity implements IGame.View{

    private IGame.Presenter presenter;
    final String AUDIO = "request_";
    Handler myHandler;
    private CommonActivity common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        presenter = new GamePresenter(this);
        myHandler = new Handler();
    }

    private void setupVideoIntro() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        //common.startIntro(uri, wordsSequence,this);
    }

    /**
     * Set up the session Video
     * @param videoID chosen by the presenter
     */
    @Override
    public void setVideoView(int videoID) {

    }

    @Override
    public void setPresentationAnimation(String currentElement) {

    }

    @Override
    public void setSubItemAnimation(String currentSubElement) {

    }

    @Override
    public void initGridView(String currentSubItem) {

    }

    @Override
    public void setVideoCorrectAnswer() {

    }

    @Override
    public void setVideoWrongAnswerToRepeat() {

    }

    @Override
    public void setVideoWrongAnswerAndGoOn() {

    }

    @Override
    public void setRepeatOrExitScreen() {

    }

    @Override
    public void setGoOnOrExitScreen() {

    }

    @Override
    public void disableViews() {

    }

    @Override
    public List<String> getSessionArray(int vectorID) {
        return null;
    }

    @Override
    public String getString() {
        return null;
    }

    @Override
    public Class getApplicationClass() {
        return this.getClass();
    }

    @Override
    public Context getScreenContext() {
        return this;
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
        startActivity(new Intent(ActivityThreeOne.this, StartGameActivity.class));
        finish();
    }
}
