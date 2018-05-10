package com.polimi.dilapp.main;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;

public class MainPresenter implements IMain.Presenter{

    private IMain.View mainView;
    private AppDatabase db;
    private TextView[] dotsText;


    MainPresenter(IMain.View view){

        this.mainView= view;
        db = AppDatabase.getAppDatabase(mainView.getContext());
    }

    @Override
    public void resumeCurrentPlayer(Bundle savedInstanceState) {
        DatabaseInitializer.setCurrentPlayer(db, savedInstanceState.getInt("current_player"));
        DatabaseInitializer.setLevelCurrentPlayer(db, savedInstanceState.getInt("level"));
        DatabaseInitializer.setObjectCurrentPlayer(db, savedInstanceState.getString("object"));
        DatabaseInitializer.setSubStringCurrentPlayer(db, savedInstanceState.getString("subString"));
        Log.i("Current player: ", String.valueOf(DatabaseInitializer.getCurrentPlayer(db)));

    }

    @Override
    public void storeCurrentPlayer(Bundle savedInstanceState) {
        savedInstanceState.putInt("current_player", DatabaseInitializer.getCurrentPlayer(db));
        savedInstanceState.putInt("level", DatabaseInitializer.getLevelCurrentPlayer(db));
        savedInstanceState.putString("object", DatabaseInitializer.getObjectCurrentPlayer(db));
        savedInstanceState.putString("subString", DatabaseInitializer.getSubStringCurrentPlayer(db));
    }

    @Override
    public void resetCurrentPlayer() {
        DatabaseInitializer.resetCurrentPlayer(db);
        Log.e("[MAIN PRESENTER]", " I'm resetting the current player");
    }

    @Override
    public void addDotsIndicator(Context context, LinearLayout linearLayout, int position) {
        dotsText = new TextView[3];
        linearLayout.removeAllViews();
        for(int i=0; i<dotsText.length; i++){
            dotsText[i] = new TextView(context);
            dotsText[i].setText(Html.fromHtml("&#8226"));
            dotsText[i].setTextSize(35);
            mainView.setColorCurrentDot(dotsText, position,false);
            linearLayout.addView(dotsText[i]);
        }

        if(dotsText.length>0){
            mainView.setColorCurrentDot(dotsText, position,true);

        }

    }

    @Override
    public int getDotsNumber() {
        return dotsText.length;
    }


}
