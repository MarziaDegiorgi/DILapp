package com.polimi.dilapp.startgame;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.AppDatabase_Impl;
import com.polimi.dilapp.database.ChildDao;
import com.polimi.dilapp.database.DatabaseInitializer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AppDatabase.class, Bundle.class, DatabaseInitializer.class, Log.class})
public class StartGameTest {

    @Before
    public void test(){
        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(Bundle.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(Log.class);


        when(Log.i(any(String.class), any(String.class))).thenReturn(1);
        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabaseImpl);
        when(appDatabaseImpl.childDao()).thenReturn(childDao);

        startGamePresenter = new StartGamePresenter(startGameView);
    }


    @Mock
    private AppDatabase_Impl appDatabaseImpl;

    @Mock
    private ChildDao childDao;

    @Mock
    private IStartGame.View startGameView;

    @Mock
    private StartGamePresenter startGamePresenter;

    @Mock
    private AppDatabase appDatabase;

    @Mock
    private DatabaseInitializer databaseInitializer;

    @Mock
    private Bundle savedInstanceState;


    //Here we test OnDestroy()
    @Test
    public void onDestroyTest(){
        startGamePresenter.onDestroy();

        Assert.assertEquals(null, startGamePresenter.getAppDatabase());
        Assert.assertEquals(null, startGamePresenter.getStartGameView());
    }

    //Here we test resumeCurrentPlayer()
    @Test
    public void resumeCurrentPlayerTest(){
        startGamePresenter.resumeCurrentPlayer(savedInstanceState);

        verify(databaseInitializer, Mockito.times(4));
        DatabaseInitializer.setCurrentPlayer(appDatabase, savedInstanceState.getInt("current_player"));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, savedInstanceState.getInt("level"));
        DatabaseInitializer.setObjectCurrentPlayer(appDatabase, savedInstanceState.getString("object"));
        DatabaseInitializer.setSubStringCurrentPlayer(appDatabase, savedInstanceState.getString("subString"));
    }

    //Here we test storeCurrentPlayer()
    @Test
    public void storeCurrentPlayer(){
        startGamePresenter.storeCurrentPlayer(savedInstanceState);

        verify(databaseInitializer, Mockito.times(4));
        savedInstanceState.putInt("current_player", DatabaseInitializer.getCurrentPlayer(appDatabase));
        savedInstanceState.putInt("level", DatabaseInitializer.getLevelCurrentPlayer(appDatabase));
        savedInstanceState.putString("object", DatabaseInitializer.getObjectCurrentPlayer(appDatabase));
        savedInstanceState.putString("subString", DatabaseInitializer.getSubStringCurrentPlayer(appDatabase));
    }


    //Here we test getLevelCurrentPlayer()
    @Test
    public void getLevelCurrentPlayerTest(){
        startGamePresenter.getLevelCurrentPlayer();

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.getLevelCurrentPlayer(appDatabase);
    }

    //Here we test getObjectCurrentPlayer()
    @Test
    public void getObjectCurrentPlayerTest(){
        startGamePresenter.getObjectCurrentPlayer();

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.getObjectCurrentPlayer(appDatabase);
    }


    //Here we test resumeCurrentPlayer()
    @Test
    public void resumeCurrentPlayerTwoParTest(){
        startGamePresenter.resumeCurrentPlayer(appDatabase, savedInstanceState);

        verify(databaseInitializer, Mockito.times(4));
        DatabaseInitializer.setCurrentPlayer(appDatabase, savedInstanceState.getInt("current_player"));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, savedInstanceState.getInt("level"));
        DatabaseInitializer.setObjectCurrentPlayer(appDatabase, savedInstanceState.getString("object"));
        DatabaseInitializer.setSubStringCurrentPlayer(appDatabase, savedInstanceState.getString("subString"));
    }


    //Here we test setCurrentPlayer()
    //Here we test resetCurrentPlayer()



}
