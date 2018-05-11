package com.polimi.dilapp.main;


import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.AppDatabase_Impl;
import com.polimi.dilapp.database.ChildDao;
import com.polimi.dilapp.database.ChildEntity;
import com.polimi.dilapp.database.DatabaseInitializer;

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
@PrepareForTest({Log.class, DatabaseInitializer.class, Bundle.class, AppDatabase.class, ChildEntity.class})
public class CreateAccountPresenterTest {

    @Before
    public void test(){
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(Bundle.class);
        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(ChildEntity.class);

        when(Log.i(any(String.class), any(String.class))).thenReturn(1);
        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabaseImpl);
        when(appDatabaseImpl.childDao()).thenReturn(childDao);

        createAccountPresenter = new CreateAccountPresenter(createAccountView);
    }

    @Mock
    private DatabaseInitializer databaseInitializer;

    @Mock
    private CreateAccountPresenter createAccountPresenter;

    @Mock
    private Bundle savedInstanceState;

    @Mock
    private AppDatabase_Impl appDatabaseImpl;

    @Mock
    private ChildDao childDao;

    @Mock
    private ICreateAccount.View createAccountView;

    @Mock
    private AppDatabase appDatabase;

    @Mock
    private ChildEntity childEntity;

    //Here we test getListOfChildren()
    @Test
    public void getListOfChildrenTest(){
        createAccountPresenter.getListOfChildren();

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.getListOfChildren(AppDatabase.getAppDatabase(any(Context.class)));
    }


    //Here we test resumeCurrentPlayer(...)
    @Test
    public void resumeCurrentPlayerTest(){
        createAccountPresenter.resumeCurrentPlayer(savedInstanceState);

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setCurrentPlayer(appDatabase, savedInstanceState.getInt("current_player"));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, savedInstanceState.getInt("level"));
        DatabaseInitializer.setObjectCurrentPlayer(appDatabase, savedInstanceState.getString("object"));
        DatabaseInitializer.setSubStringCurrentPlayer(appDatabase, savedInstanceState.getString("subString"));
    }

    //Here we test storeCurrentPlayer()
    @Test
    public void storeCurrentPlayer(){
        createAccountPresenter.storeCurrentPlayer(savedInstanceState);

        verify(databaseInitializer, Mockito.times(4));
        savedInstanceState.putInt("current_player", DatabaseInitializer.getCurrentPlayer(appDatabase));
        savedInstanceState.putInt("level", DatabaseInitializer.getLevelCurrentPlayer(appDatabase));
        savedInstanceState.putString("object", DatabaseInitializer.getObjectCurrentPlayer(appDatabase));
        savedInstanceState.putString("subString", DatabaseInitializer.getSubStringCurrentPlayer(appDatabase));
    }

    //Here we test deletePlayer()
    @Test
    public void deletePlayerTest(){
        createAccountPresenter.deletePlayer(childEntity);

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.deletePlayer(appDatabase, childEntity);
    }
}
