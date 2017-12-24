package com.polimi.dilapp.levels;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.AppDatabase_Impl;
import com.polimi.dilapp.database.ChildDao;
import com.polimi.dilapp.database.DatabaseInitializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, AppDatabase.class, SystemClock.class})
public class GamePresenterTest {

    @Mock
    private IGame.View iGame;

    @Mock
    private AppDatabase_Impl appDatabase;


    private GamePresenter gamePresenter;

    @Mock
    private ChildDao childDao;

    @Mock
    private ArrayList<String> arrayOfElements;

    @Before
    public void test(){
        long longNumber = 1;
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(SystemClock.class);
        when(Log.i(any(String.class),any(String.class))).thenReturn(1);
        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabase);
        when(appDatabase.childDao()).thenReturn(childDao);
        when(iGame.getString()).thenReturn("ActivityOneOne");
        when(SystemClock.elapsedRealtime()).thenReturn(longNumber);
        gamePresenter = new GamePresenter(iGame);
    }

    @Test
    public void gameIsStarted(){
        ArrayList<String> currentSequence = new ArrayList<>();
        currentSequence.add("a");
        gamePresenter.startGame(currentSequence);
        assertTrue(gamePresenter.isStarted());
    }

}