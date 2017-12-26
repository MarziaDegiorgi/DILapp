package com.polimi.dilapp.levels;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.AppDatabase_Impl;
import com.polimi.dilapp.database.ChildDao;
import com.polimi.dilapp.database.DatabaseInitializer;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, AppDatabase.class, SystemClock.class, Toast.class})
public class GamePresenterTest {

    @Mock
    private IGame.View iGame;

    @Mock
    private AppDatabase_Impl appDatabase;


    private GamePresenter gamePresenter;

    @Mock
    private ChildDao childDao;

    @Mock
    private ArrayList<String> mockedArray;

    @Before
    public void test(){

        //startGameTest initialization
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

        //isTheCurrentSessionArrayEmpty
        ArrayList<String> currentSessionArray = new ArrayList<>();
        when(iGame.getSessionArray(any(int.class))).thenReturn(currentSessionArray);

        gamePresenter = new GamePresenter(iGame);

    }

    @Test
    public void gameIsStarted(){
        ArrayList<String> currentSequence = new ArrayList<>();
        currentSequence.add("a");
        gamePresenter.startGame(currentSequence);
        assertTrue(gamePresenter.isStarted());
    }


    @Test
    public void emptyCurrentSequenceInStartGame(){
        ArrayList<String> currentSequence = new ArrayList<>();
        gamePresenter.startGame(currentSequence);
        assertFalse(gamePresenter.isStarted());
    }

   @Test
    public void getResourceIdTest(){
       String name = "lemon";
       int resourceId = gamePresenter.getResourceId(name, R.drawable.class);

       Assert.assertEquals(resourceId, R.drawable.lemon );
   }

   @Test
   public void startNewSessionTest(){
       String currentSequenceElement = "subset_names_one";
       when(iGame.getSessionArray(any(int.class))).thenReturn(mockedArray);
       try {
           Whitebox.invokeMethod(gamePresenter, "startNewSession", currentSequenceElement );
       } catch (Exception e) {
           e.printStackTrace();
       }

       assertTrue(gamePresenter.getNewSessionStarted());
   }

   //Here we test startNewTurn with empty sequence
   @Test
   public void isTheGameEnded(){
       ArrayList<String> emptySequence = new ArrayList<String>();
       emptySequence.add("test");
       gamePresenter.startGame(emptySequence);
       try {
           Whitebox.invokeMethod(gamePresenter, "startNewTurn");
       } catch (Exception e) {
           e.printStackTrace();
       }
       assertTrue(gamePresenter.isEnded());
   }
}