package com.polimi.dilapp.startgame;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.levels.view.ActivityOneOne;
import com.polimi.dilapp.levels.view.ActivityOneThree;
import com.polimi.dilapp.levels.view.ActivityOneTwo;
import com.polimi.dilapp.levels.view.ActivityThreeOne;
import com.polimi.dilapp.levels.view.ActivityThreeTwo;
import com.polimi.dilapp.levels.view.ActivityTwoOne;
import com.polimi.dilapp.levels.view.ActivityTwoThree;
import com.polimi.dilapp.levels.view.ActivityTwoTwo;


public class StartGamePresenter implements  IStartGame.Presenter {

    private IStartGame.View startGameView;
    private AppDatabase db;

    StartGamePresenter(IStartGame.View view){
        this.startGameView = view;
        db = AppDatabase.getAppDatabase(startGameView.getScreenContext());
    }

    @Override
    public void onDestroy() {

        startGameView = null;
        db = null;
    }

    @Override
    public void onPlayButtonPressed() {
        Log.e("[StartGamePresenter]", "Current player: "+ String.valueOf(DatabaseInitializer.getCurrentPlayer(db))+" - Level current player: "+ String.valueOf(getLevelCurrentPlayer()));
        linkToActivity(getLevelCurrentPlayer(), getObjectCurrentPlayer());
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
    public int getLevelCurrentPlayer(){
        return DatabaseInitializer.getLevelCurrentPlayer(db);
    }

    public String getObjectCurrentPlayer(){
        return DatabaseInitializer.getObjectCurrentPlayer(db);
    }

    @Override
    public void linkToActivity(int level, String object){
        switch (level){
            case 0:
                Intent activity11 = new Intent(startGameView.getScreenContext(), ActivityOneOne.class);
                activity11.putExtra("object", object);
                startGameView.getScreenContext().startActivity(activity11);
                break;
            case 11:
                Intent activity11b = new Intent(startGameView.getScreenContext(), ActivityOneOne.class);
                activity11b.putExtra("object", object);
                startGameView.getScreenContext().startActivity(activity11b);
                break;
            case 12:
                Intent activity12 = new Intent(startGameView.getScreenContext(), ActivityOneTwo.class);
                activity12.putExtra("object", object);
                startGameView.getScreenContext().startActivity(activity12);
                break;
            case 13:
                Intent activity13 = new Intent(startGameView.getScreenContext(), ActivityOneThree.class);
                activity13.putExtra("object", object);
                startGameView.getScreenContext().startActivity(activity13);
                break;
            case 21:
                Intent activity21 = new Intent(startGameView.getScreenContext(), ActivityTwoOne.class);
                activity21.putExtra("object", object);
                startGameView.getScreenContext().startActivity(activity21);
                break;
            case 22:
                Intent activity22 = new Intent(startGameView.getScreenContext(), ActivityTwoTwo.class);
                activity22.putExtra("object", object);
                startGameView.getScreenContext().startActivity(activity22);
                break;
            case 23:
                Intent activity23 = new Intent(startGameView.getScreenContext(), ActivityTwoThree.class);
                activity23.putExtra("object", object);
                startGameView.getScreenContext().startActivity(activity23);
                break;
            case 31:
                Intent activity31 = new Intent(startGameView.getScreenContext(), ActivityThreeOne.class);
                startGameView.getScreenContext().startActivity(activity31);
                break;
            case 32:
                Intent activity32 = new Intent(startGameView.getScreenContext(), ActivityThreeTwo.class);
                startGameView.getScreenContext().startActivity(activity32);
                break;
            default: Log.e(this.toString(),"ERROR: Invalid level");
            break;
        }
    }

    @Override
    public void resumeCurrentPlayer(AppDatabase db, Bundle savedInstanceState) {
        DatabaseInitializer.setCurrentPlayer(db, savedInstanceState.getInt("current_player"));
        DatabaseInitializer.setLevelCurrentPlayer(db, savedInstanceState.getInt("level"));
        DatabaseInitializer.setObjectCurrentPlayer(db, savedInstanceState.getString("object"));
        DatabaseInitializer.setSubStringCurrentPlayer(db, savedInstanceState.getString("subString"));
        Log.i("Current player: ", String.valueOf(DatabaseInitializer.getCurrentPlayer(db)));
        Log.i("[STARTGAME PRESENTER]", "Resuming current player" +String.valueOf(DatabaseInitializer.getCurrentPlayer(db)));
        Log.i("[STARTGAME PRESENTER]", "Resuming level" +String.valueOf(DatabaseInitializer.getLevelCurrentPlayer(db)));
    }

    @Override
    public void setCurrentPlayer(int currentPlayer, int level, String object, String subString){
        DatabaseInitializer.setCurrentPlayer(db, currentPlayer);
        DatabaseInitializer.setLevelCurrentPlayer(db, level);
        DatabaseInitializer.setObjectCurrentPlayer(db, object);
        DatabaseInitializer.setSubStringCurrentPlayer(db, subString);
    }

    @Override
    public void resetCurrentPlayer() {
        DatabaseInitializer.resetCurrentPlayer(db);
        Log.i("[STARTGAME PRESENTER]", " I'm resetting the current player");
    }

    IStartGame.View getStartGameView(){return startGameView;}
    AppDatabase getAppDatabase(){return db;}
}
