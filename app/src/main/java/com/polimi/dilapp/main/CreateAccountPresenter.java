package com.polimi.dilapp.main;


import android.arch.persistence.room.Database;
import android.os.Bundle;
import android.util.Log;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.ChildEntity;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.levelmap.ILevelMap;

import java.util.List;

public class CreateAccountPresenter implements  ICreateAccount.Presenter{

    private ICreateAccount.View createAccountView;
    private AppDatabase db;

    CreateAccountPresenter(ICreateAccount.View view){

        this.createAccountView= view;
        db = AppDatabase.getAppDatabase(createAccountView.getContext());
    }

    @Override
    public List<ChildEntity> getListOfChildren() {
        return DatabaseInitializer.getListOfChildren(AppDatabase.getAppDatabase(createAccountView.getContext()));
    }

    @Override
    public void resumeCurrentPlayer(Bundle savedInstanceState) {
        DatabaseInitializer.setCurrentPlayer(db, savedInstanceState.getInt("current_player"));
        DatabaseInitializer.setLevelCurrentPlayer(db, savedInstanceState.getInt("level"));
        Log.i("Current player: ", String.valueOf(DatabaseInitializer.getCurrentPlayer(db)));

    }

    @Override
    public void storeCurrentPlayer(Bundle savedInstanceState) {
        savedInstanceState.putInt("current_player", DatabaseInitializer.getCurrentPlayer(db));
        savedInstanceState.putInt("level", DatabaseInitializer.getLevelCurrentPlayer(db));
    }

}