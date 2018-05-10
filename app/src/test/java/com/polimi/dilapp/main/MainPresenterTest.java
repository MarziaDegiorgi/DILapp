package com.polimi.dilapp.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.AppDatabase_Impl;
import com.polimi.dilapp.database.DatabaseInitializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Matchers.any;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, AppDatabase.class, Intent.class, DatabaseInitializer.class, Toast.class, Bundle.class})
public class MainPresenterTest {

    @Before
    public void test(){
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(Intent.class);
        PowerMockito.mockStatic(Toast.class);
        PowerMockito.mockStatic(Bundle.class);

        when(Log.i(any(String.class), any(String.class))).thenReturn(1);
        when(Log.e(any(String.class), any(String.class), any(Throwable.class))).thenReturn(1);
        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabaseImpl);
        when(Toast.makeText(any(Context.class), any(String.class), any(int.class))).thenReturn(toast);

        mainPresenter = new MainPresenter(mainView);
    }

    @Mock
    private MainPresenter mainPresenter;

    @Mock
    private IMain.View mainView;

    @Mock
    private Toast toast;

    @Mock
    private AppDatabase_Impl appDatabaseImpl;

    @Mock
    private DatabaseInitializer databaseInitializer;

    @Mock
    private Bundle savedInstanceState;

    @Mock
    private AppDatabase appDatabase;



    //Here we test resumeCurrentPlayer()
    @Test
    public void resumeCurrentPlayerTest(){
        mainPresenter.resumeCurrentPlayer(savedInstanceState);

        verify(databaseInitializer, Mockito.times(4));
        DatabaseInitializer.setCurrentPlayer(appDatabase, savedInstanceState.getInt(any(String.class)));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase,savedInstanceState.getInt(any(String.class)) );
        DatabaseInitializer.setObjectCurrentPlayer(appDatabase, savedInstanceState.getString(any(String.class)));
        DatabaseInitializer.setSubStringCurrentPlayer(appDatabase, savedInstanceState.getString(any(String.class)));

    }

//TODO: VERIFY THIS TEST
    //Here we test storeCurrenPlayer()
    @Test
    public void storeCurrentPlayerTest() {
        mainPresenter.resumeCurrentPlayer(savedInstanceState);
        mainPresenter.storeCurrentPlayer(savedInstanceState);

        verify(savedInstanceState, Mockito.times(1)).putInt("current_player", DatabaseInitializer.getCurrentPlayer(appDatabase));
        verify(savedInstanceState, Mockito.times(1)).putInt("level", DatabaseInitializer.getLevelCurrentPlayer(appDatabase));
        verify(savedInstanceState, Mockito.times(1)).putString("object", DatabaseInitializer.getObjectCurrentPlayer(appDatabase));
        verify(savedInstanceState, Mockito.times(1)).putString("subString", DatabaseInitializer.getSubStringCurrentPlayer(appDatabase));
    }


    //Here we test resetCurrentPlayer()
    @Test
    public void resetCurrentPlayerTest(){
        verify(databaseInitializer, Mockito.times(1)).resetCurrentPlayer(appDatabase);
    }

    

    }
