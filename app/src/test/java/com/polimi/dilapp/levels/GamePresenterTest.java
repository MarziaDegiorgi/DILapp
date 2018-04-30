package com.polimi.dilapp.levels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Handler;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, AppDatabase.class, SystemClock.class, Toast.class, NfcAdapter.class, Intent.class, AsyncTask.class, DatabaseInitializer.class})
public class GamePresenterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IGame.View iGame;

    @Mock
    private AppDatabase_Impl appDatabaseImpl;

    @Mock
    private AppDatabase appDatabase;

    @Mock
    private GamePresenter gamePresenter;

    @Mock
    private ChildDao childDao;

    @Mock
    private ArrayList<String> mockedArray;

    @Mock
    private String mockedString;

    @Mock
    private int mockedIntValue;

    @Mock
    private Toast toast;

    @Mock
    private Tag tag;

    @Mock
    private NfcAdapter nfcAdapter;

    @Mock
    private Intent intent;

    @Mock
    private GamePresenter.NdefReaderTask ndefReaderTask;

    @Mock
    private Activity activity;

    @Mock
    DatabaseInitializer databaseInitializer;


    @Before
    public void test() {

        //startGameTest initialization
        long longNumber = 1;
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(NfcAdapter.class);
        PowerMockito.mockStatic(SystemClock.class);
        PowerMockito.mockStatic(Intent.class);
        PowerMockito.mockStatic(Toast.class);
        PowerMockito.mockStatic(AsyncTask.class);
        PowerMockito.mockStatic(Handler.class);

        when(Log.i(any(String.class), any(String.class))).thenReturn(1);
        when(Log.e(any(String.class), any(String.class), any(Throwable.class))).thenReturn(1);
        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabaseImpl);
        when(appDatabaseImpl.childDao()).thenReturn(childDao);
        when(iGame.getString()).thenReturn("ActivityOneOne");
        when(SystemClock.elapsedRealtime()).thenReturn(longNumber);
        when(Toast.makeText(any(Context.class), any(String.class), any(int.class))).thenReturn(toast);


        //isTheCurrentSessionArrayEmpty
        ArrayList<String> currentSessionArray = new ArrayList<>();
        when(iGame.getSessionArray(any(int.class))).thenReturn(currentSessionArray);


        try {
            gamePresenter = new GamePresenter(iGame);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //Here we test startGame(...);
    @Test
    public void gameIsStarted() {
        ArrayList<String> currentSequence = new ArrayList<>();
        currentSequence.add("a");
        gamePresenter.startGame(currentSequence);
        assertTrue(gamePresenter.isStarted());
    }


    @Test
    public void emptyCurrentSequenceInStartGame() {
        ArrayList<String> currentSequence = new ArrayList<>();
        gamePresenter.startGame(currentSequence);
        assertFalse(gamePresenter.isStarted());
    }

    //Here we test getResourceId(... , ...);
    @Test
    public void getResourceIdCorrectFunctioningTest() {
        String name = "lemon";
        int resourceId = gamePresenter.getResourceId(name, R.drawable.class);

        Assert.assertEquals(resourceId, R.drawable.lemon);
    }

    @Test(expected = Exception.class)
    public void getResourceIdThrowingExceptionTest() {
        gamePresenter.getResourceId(any(String.class), R.drawable.class);
    }


    //Here we test startNewSession(...);
    @Test
    public void startNewSessionTest() {
        String currentSequenceElement = "subset_names_one";
        when(iGame.getSessionArray(any(int.class))).thenReturn(mockedArray);
        try {
            Whitebox.invokeMethod(gamePresenter, "startNewSession", currentSequenceElement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(gamePresenter.getNewSessionStarted());
    }

    //Here we test startNewTurn with empty sequence
    @Test
    public void isTheGameEnded() {
        ArrayList<String> emptySequence = new ArrayList<String>();
        emptySequence.add("test");
        gamePresenter.startGame(emptySequence);
        try {
            Whitebox.invokeMethod(gamePresenter, "startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int diff = gamePresenter.getTotalAttempts() - gamePresenter.getCorrectAnswers();
        int percentage = (20 * gamePresenter.getTotalAttempts()) / 100;
        if (diff > percentage) {
            verify(iGame, Mockito.times(

                    1)).setRepeatOrExitScreen();
        } else {
            verify(iGame, Mockito.times(1)).setGoOnOrExitScreen();
        }
        assertTrue(gamePresenter.isEnded());

    }

    @Test
    public void isTheNewTurnStarted() {
        ArrayList<String> notEmptySequence = new ArrayList<String>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        try {
            Whitebox.invokeMethod(gamePresenter, "startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(gamePresenter.getNewTurnStarted());
        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("startNewSession", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Here we test chooseElement();
    @Test
    public void isTheNewElementChosen() {
        ArrayList<String> notEmptySequence = new ArrayList<String>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.chooseElement();

        assertEquals("carrot", gamePresenter.getCurrentSequenceElement());
        assertEquals(1, gamePresenter.getNumberOfElements());
    }

    @Test
    public void noMoreElementsInTheTempArray() {
        ArrayList<String> notEmptySequence = new ArrayList<String>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.chooseElement();

        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Here we test askCurrentElement();
    @Test
    public void isTheCurrentElementAsked() {
        gamePresenter.askCurrentElement();
        verify(iGame, Mockito.times(1)).setPresentationAnimation(gamePresenter.getCurrentSequenceElement());
    }

    @Test
    public void correctAnswerTest() {
        int total = gamePresenter.getTotalAttempts();
        int correct = gamePresenter.getCorrectAnswers();
        total++;
        correct++;

        try {
            Whitebox.invokeMethod(gamePresenter, "correctAnswer");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(0, gamePresenter.getCounter());
        Assert.assertEquals(total, gamePresenter.getTotalAttempts());
        Assert.assertEquals(correct, gamePresenter.getCorrectAnswers());

        verify(iGame, Mockito.times(1)).setVideoCorrectAnswer();

    }

    @Test
    public void wrongAnswerElseTest() {
        int total = gamePresenter.getTotalAttempts();
        total++;
        gamePresenter.setCounter(2);
        try {
            Whitebox.invokeMethod(gamePresenter, "wrongAnswer", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(0, gamePresenter.getCounter());
        Assert.assertEquals(total, gamePresenter.getTotalAttempts());
        verify(iGame, Mockito.times(1)).setVideoWrongAnswerAndGoOn();
    }

    @Test
    public void wrongAnswerIfTest() {
        int total = gamePresenter.getTotalAttempts();
        int counter = gamePresenter.getCounter();
        counter++;
        total++;
        gamePresenter.setCounter(0);
        try {
            Whitebox.invokeMethod(gamePresenter, "wrongAnswer", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(counter, gamePresenter.getCounter());
        Assert.assertEquals(total, gamePresenter.getTotalAttempts());
        verify(iGame, Mockito.times(1)).setVideoWrongAnswerToRepeat();
    }


    @Test
    public void notifyFirstSubElementTest() {
        gamePresenter.notifyFirstSubElement();
        verify(iGame, Mockito.times(1)).initGridView(gamePresenter.getCurrentSubElement());
    }

    @Test
    public void nfcNotAvailableOrEnabledTest() {
        when(NfcAdapter.getDefaultAdapter(any(Context.class))).thenReturn(null);
        boolean check = gamePresenter.checkNfcAvailability();
        Assert.assertEquals(false, check);
    }

    @Test
    public void nfcAvailableTest() {
        when(NfcAdapter.getDefaultAdapter(any(Context.class))).thenReturn(nfcAdapter);
        boolean check = gamePresenter.checkNfcAvailability();
        Assert.assertEquals(true, check);
    }

    @Test
    public void checkCorrectAnswerMultipleItemTest() throws Exception {
        String currentElement = "_peperone";
        String currentSubElement= "e";
        int numberOfElements = 8;

        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
            Whitebox.invokeMethod(gamePresenter, "checkAnswer", "p" );
        } catch (Exception e) {
            e.printStackTrace();
        }

        numberOfElements--;

        assertEquals(numberOfElements, gamePresenter.getNumberOfElements());
        assertEquals(currentSubElement, gamePresenter.getCurrentSubElement());

        verify(iGame, Mockito.times(1)).setSubItemAnimation(currentSubElement);
    }

    @Test
    public void checkWrongAnswerMultipleItem(){
        String currentElement = "_peperone";
        String currentSubElement= "p";
        int numberOfElements = 8;

        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
            Whitebox.invokeMethod(gamePresenter, "checkAnswer", "e" );
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(currentSubElement, gamePresenter.getCurrentSubElement());
        assertEquals(numberOfElements, gamePresenter.getNumberOfElements());

        verify(iGame, Mockito.times(1)).setVideoWrongAnswerToRepeat();
    }

    @Test
    public void isMultipleItemTest() {
       String currentElement = "_mela";
        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
       assertTrue(gamePresenter.getMultipleElement());
    }

    @Test
    public void isNotMultipleItemTest() {
        String currentElement = "ahcbjdckjernkjnvkerqvlkrnvlnrvjnrekjsssnjvqe";
        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(gamePresenter.getMultipleElement());
    }

    @Test
    public void singleSubElementTest() {
        String currentElement = "_1";
        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(gamePresenter.getMultipleElement());
    }

    @Test
    public void checkSubElementTest() {
    String currentSubElement ="1";
    String currentElement="_12";

    try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
        } catch (Exception e) {
            e.printStackTrace();
        }

    gamePresenter.notifyFirstSubElement();

    assertEquals(currentSubElement, gamePresenter.getCurrentSubElement());
    verify(iGame, Mockito.times(1)).initGridView(currentSubElement);
    }

    @Test
    public void onDestroyTest() {
        gamePresenter.onDestroy();
        Assert.assertEquals(null, gamePresenter.getActivityInterface());
    }


    //TODO: check setLevelCurrentPlayer(); [Giuli]
    @Test
    public void setLevelCurrentPlayerTest() {

        when(gamePresenter.getActivityInterface().getString()).thenReturn("ActivityOneOne");
        gamePresenter.setLevelCurrentPlayer();
    }
}